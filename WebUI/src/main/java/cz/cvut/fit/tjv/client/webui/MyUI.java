package cz.cvut.fit.tjv.client.webui;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.data.Binder;
import com.vaadin.data.converter.LocalDateTimeToDateConverter;
import com.vaadin.data.converter.StringToIntegerConverter;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.DateTimeField;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.renderers.ButtonRenderer;
import com.vaadin.ui.renderers.DateRenderer;
import cz.cvut.fit.tjv.client.restclient.AddressClient;
import cz.cvut.fit.tjv.client.restclient.CustomerClient;
import cz.cvut.fit.tjv.client.restclient.VehicleClient;
import cz.cvut.fit.tjv.server.pl.dto.AddressDTO;
import cz.cvut.fit.tjv.server.pl.dto.CustomerDTO;
import cz.cvut.fit.tjv.server.pl.dto.VehicleDTO;
import java.text.DateFormat;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This UI is the application entry point. A UI may either represent a browser window 
 * (or tab) or some part of an HTML page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be 
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@Theme("mytheme")
public class MyUI extends UI {
    
    private List<AddressDTO> addresses;
    private List<VehicleDTO> vehicles;
    private List<CustomerDTO> customers;

    private final AddressClient addressClient = AddressClient.getInstance();
    private final VehicleClient vehicleClient = VehicleClient.getInstance();
    private final CustomerClient customerClient = CustomerClient.getInstance();
    
    @Override
    protected void init(VaadinRequest vaadinRequest) {
               
        final VerticalLayout layout = new VerticalLayout();
        
        try {
            addresses = new ArrayList<>(Arrays.asList(addressClient.retrieveAllJson()));
            customers = new ArrayList<>(Arrays.asList(customerClient.retrieveAllJson()));
            vehicles = new ArrayList<>(Arrays.asList(vehicleClient.retrieveAllJson()));
        } catch (Exception e) {
            // Backend is not running
            Notification.show("Backend service is not running " + e, Notification.Type.ERROR_MESSAGE);
            setContent(layout);
            return;
        }

        // Addresses
        Label addressHead = new Label("Address");
        
        Grid<AddressDTO> addressGrid = new Grid<>();
        addressGrid.addColumn(s -> s.getId()).setCaption("ID");
        addressGrid.addColumn(AddressDTO::getStreet).setCaption("Street");
        addressGrid.addColumn(AddressDTO::getHouseNumber).setCaption("House number");
        addressGrid.addColumn(AddressDTO::getCity).setCaption("City");
        addressGrid.addColumn(AddressDTO::getZip).setCaption("Zip");
        addressGrid.addColumn(AddressDTO::getCountry).setCaption("Country");
        
        // Delete button for each address
        addressGrid.addColumn(s -> "Delete", new ButtonRenderer<>(e -> {
            Integer chosenAddressId = e.getItem().getId();
            
            boolean safeToDelete = true;
            for (CustomerDTO c : customers) {
                if (c.getAddressId() == chosenAddressId) {
                    safeToDelete = false;
                    break;
                }
            }
            
            if (safeToDelete) {
                addressClient.delete(e.getItem().getId());
                addresses.clear();
                addresses.addAll(Arrays.asList(addressClient.retrieveAllJson()));
                addressGrid.setItems(addresses);
            } else {
                Notification.show("This address can't be deleted, because some customer holds a reference to it!", Notification.Type.ERROR_MESSAGE);
            }
        }));
        
        // Fill out the address table
        addressGrid.setItems(addresses);
                
        // Create labels and textfields
        Label addOrEditAddress = new Label("Add/edit address");
        TextField addressID = new TextField("ID");
        TextField addressStreet = new TextField("Street");
        TextField addressHouseNumber = new TextField("House number");
        TextField addressCity = new TextField("City");
        TextField addressZip = new TextField("Zip");
        TextField addressCountry = new TextField("Country");
        
        // Data to DTO bindings
        final Binder<AddressDTO> addressBinder = new Binder<>(AddressDTO.class);
        addressBinder.forField(addressID).asRequired().withConverter(new StringToIntegerConverter("Must be integer")).bind("id");
        addressBinder.forField(addressStreet).asRequired().bind("street");
        addressBinder.forField(addressHouseNumber).asRequired().bind("houseNumber");
        addressBinder.forField(addressCity).asRequired().bind("city");
        addressBinder.forField(addressZip).asRequired().bind("zip");
        addressBinder.forField(addressCountry).asRequired().bind("country");
        
        addressGrid.addSelectionListener(e -> {
            if (e.getFirstSelectedItem().isPresent()) {
                addressBinder.readBean(e.getFirstSelectedItem().get());
            }
        });
        
        HorizontalLayout addAddressLayout = new HorizontalLayout(addressID, addressStreet, addressHouseNumber, addressCity, addressZip, addressCountry);
        Button submitAddress = new Button("Add or edit address", e -> {
           AddressDTO a = new AddressDTO();
            if (addressBinder.writeBeanIfValid(a)) {
                addressClient.createOrUpdateJson(a);
                addresses.clear();
                addresses.addAll(Arrays.asList(addressClient.retrieveAllJson()));
                addressGrid.setItems(addresses);
            }
        });
        
        addressGrid.setHeightByRows(5);
        addressGrid.setWidth("800px");
        
        // Vehicles
        Label vehiclesHead = new Label("Vehicles");
        
        Grid<VehicleDTO> vehicleGrid = new Grid<>();
        vehicleGrid.addColumn(v -> v.getId()).setCaption("ID");
        vehicleGrid.addColumn(VehicleDTO::getCustomerId).setCaption("Customer ID");
        vehicleGrid.addColumn(VehicleDTO::getPlate).setCaption("Plate");
        vehicleGrid.addColumn(VehicleDTO::getBrand).setCaption("Brand");
        vehicleGrid.addColumn(VehicleDTO::getModel).setCaption("Model");
        vehicleGrid.addColumn(VehicleDTO::getCreated, new DateRenderer(
                DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT)
        )).setCaption("Created");
        
        // Create delete button for every vehicle
        vehicleGrid.addColumn(s -> "Delete", new ButtonRenderer<>(e -> {
            vehicleClient.delete(e.getItem().getId());
            vehicles.clear();
            vehicles.addAll(Arrays.asList(vehicleClient.retrieveAllJson()));
            vehicleGrid.setItems(vehicles);
        }));
        
        // Fill out the vehicle table
        vehicleGrid.setItems(vehicles);
        
        
        // Create labels and textfields
        Label addOrEditVehicle = new Label("Add/edit vehicle");
        TextField vehicleId = new TextField("ID");
        TextField vehicleCustomerId = new TextField("Customer ID");
        TextField vehiclePlate = new TextField("Plate");
        TextField vehicleBrand = new TextField("Brand");
        TextField vehicleModel = new TextField("Model");
        DateTimeField vehicleAdded = new DateTimeField("Created");
        
        
        // Data to DTO binding
        final Binder<VehicleDTO> vehicleBinder = new Binder<>(VehicleDTO.class);
        vehicleBinder.forField(vehicleId).asRequired().withConverter(new StringToIntegerConverter("Must be integer")).bind("id");
        vehicleBinder.forField(vehicleCustomerId).asRequired().withConverter(new StringToIntegerConverter("Must be integer")).bind(vehicle -> {
            if (vehicle.getCustomer() != null) {
                return vehicle.getCustomer().getId();
            }
            
            return null;
        }, (vehicle, customerId) -> {
            if (vehicle.getCustomer() != null) {
                vehicle.getCustomer().setId(customerId);
            }
        });
        vehicleBinder.forField(vehiclePlate).asRequired().bind("plate");
        vehicleBinder.forField(vehicleBrand).asRequired().bind("brand");
        vehicleBinder.forField(vehicleModel).asRequired().bind("model");
        vehicleBinder.forField(vehicleAdded).withConverter(new LocalDateTimeToDateConverter(ZoneId.systemDefault())).bind("created");
        
        vehicleGrid.addSelectionListener(e -> {
            if (e.getFirstSelectedItem().isPresent()) {
                vehicleBinder.readBean(e.getFirstSelectedItem().get());
            }
        });
        
        HorizontalLayout addVehicleLayout = new HorizontalLayout(vehicleId, vehicleCustomerId, vehiclePlate, vehicleBrand, vehicleModel, vehicleAdded);
        Button submitVehicle = new Button("Add or edit vehicle", e -> {
           VehicleDTO v = new VehicleDTO();
            if (vehicleBinder.writeBeanIfValid(v)) {
                // Vehicle with set Customer ID has been added
                // Find customer, which we need to pass to the API with the vehicle
                Integer customerId = Integer.parseInt(vehicleCustomerId.getValue());
                
                CustomerDTO customerToAdd = customerClient.retrieveJson(customerId);
                
                if (customerToAdd == null) {
                    Notification.show("Customer with such Customer ID does not exist!" , Notification.Type.ERROR_MESSAGE);
                    return;
                }
                
                v.setCustomer(customerClient.retrieveJson(customerId));
                    
                // First, we need to create the entity with added customer
                vehicleClient.createOrUpdateJson(v);
                    
                // We add the relationship from customer to vehicle
                customerClient.addVehicleJson(customerId, v);
                
                vehicles.clear();
                vehicles.addAll(Arrays.asList(vehicleClient.retrieveAllJson()));
                vehicleGrid.setItems(vehicles);
            }
        });
                
        vehicleGrid.setHeightByRows(5);
        vehicleGrid.setWidth("800px");
        
        
        // Customers
        Label customerHead = new Label("Customers");
        
        Grid<CustomerDTO> customerGrid = new Grid<>();
        customerGrid.addColumn(s -> s.getId()).setCaption("ID");
        customerGrid.addColumn(CustomerDTO::getAddressId).setCaption("Address ID");
        customerGrid.addColumn(CustomerDTO::getName).setCaption("Name");
        customerGrid.addColumn(CustomerDTO::getSurname).setCaption("Surname");
        
        // Create delete button for every address
        customerGrid.addColumn(s -> "Delete", new ButtonRenderer<>(e -> {
            Integer customerIdToDelete = e.getItem().getId();
            
            boolean safeToDelete = true;
            for (VehicleDTO v : vehicles) {
                if (v.getCustomerId() == customerIdToDelete) {
                    safeToDelete = false;
                    break;
                }
            }
            
            if (safeToDelete) {
                customerClient.delete(e.getItem().getId());
                customers.clear();
                customers.addAll(Arrays.asList(customerClient.retrieveAllJson()));
                customerGrid.setItems(customers);
            } else {
                Notification.show("This customer can't be deleted, because some vehicle holds a reference to it!", Notification.Type.ERROR_MESSAGE);
            }
        }));
        
        // Fill out the customer table
        customerGrid.setItems(customerClient.retrieveAllJson());
        
        
        // Create labels and fields
        Label addOrEditCustomer = new Label("Add/edit customer");
        TextField customerId = new TextField("ID");
        TextField customerAddressId = new TextField("Address ID");
        TextField customerName = new TextField("Name");
        TextField customerSurname = new TextField("Surname");
        
        // Data to DTO binding
        final Binder<CustomerDTO> customerBinder = new Binder<>(CustomerDTO.class);
        customerBinder.forField(customerId).asRequired().withConverter(new StringToIntegerConverter("Must be integer")).bind("id");
        customerBinder.forField(customerAddressId).asRequired().withConverter(new StringToIntegerConverter("Must be integer")).bind(customer -> {
            if (customer.getAddress() != null) {
                return customer.getAddress().getId();
            }
            return null;
        }, (customer, addressId) -> {
            if (customer.getAddress() != null) {
                customer.getAddress().setId(addressId);
            }
        });
        customerBinder.forField(customerName).asRequired().bind("name");
        customerBinder.forField(customerSurname).asRequired().bind("surname");
        
        customerGrid.addSelectionListener(e -> {
            if (e.getFirstSelectedItem().isPresent()) {
                customerBinder.readBean(e.getFirstSelectedItem().get());
            }
        });
        
        HorizontalLayout addCustomerLayout = new HorizontalLayout(customerId, customerAddressId, customerName, customerSurname);
        Button submitCustomer = new Button("Add or edit customer", e -> {
           CustomerDTO c = new CustomerDTO();
            if (customerBinder.writeBeanIfValid(c)) {
                Integer addressIdToAdd = Integer.parseInt(customerAddressId.getValue());
                
                AddressDTO addressToAdd = addressClient.retrieveJson(addressIdToAdd);
                
                if (addressToAdd == null) {
                    Notification.show("Address with such Address ID does not exist!" , Notification.Type.ERROR_MESSAGE);
                    return;
                }
                
                c.setAddress(addressToAdd);
                
                customerClient.createOrUpdateJson(c);
                
                addressClient.addCustomerJson(addressIdToAdd, c);
                
                customers.clear();
                customers.addAll(Arrays.asList(customerClient.retrieveAllJson()));
                customerGrid.setItems(customers);
            }
        });
        
        customerAddressId.setReadOnly(false);

        layout.addComponents(addressHead, addressGrid, addAddressLayout, submitAddress,
                vehiclesHead, vehicleGrid, addVehicleLayout, submitVehicle,
                customerHead, customerGrid, addCustomerLayout, submitCustomer);
        
        setContent(layout);
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
