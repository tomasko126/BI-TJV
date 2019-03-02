/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fit.tjv.server.pl.rest;

import cz.cvut.fit.tjv.server.pl.dto.CustomerDTO;
import cz.cvut.fit.tjv.server.dl.entities.Customer;
import cz.cvut.fit.tjv.server.dl.dao.CustomerController;
import cz.cvut.fit.tjv.server.dl.entities.Vehicle;
import cz.cvut.fit.tjv.server.pl.dto.VehicleDTO;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author tomastaro
 */
@Path("/customer")
public class CustomerResource extends AbstractCRUDResource<Customer, CustomerDTO> {
    
    static final Function<Customer, CustomerDTO> ENTITY_TO_DTO_CONVERTER
            = e -> e == null ? null : new CustomerDTO(e.getId(), e.getName(), e.getSurname(), AddressResource.ENTITY_TO_DTO_CONVERTER.apply(e.getAddress())/*, e.getVehicles().stream().map(VehicleResource.ENTITY_TO_DTO_CONVERTER).collect(Collectors.toList())*/);
    static final Function<CustomerDTO, Customer> DTO_TO_ENTITY_CONVERTER
            = d -> new Customer(d.getId(), d.getName(), d.getSurname(), AddressResource.DTO_TO_ENTITY_CONVERTER.apply(d.getAddress())/*, d.getVehicles().stream().map(VehicleResource.DTO_TO_ENTITY_CONVERTER).collect(Collectors.toList())*/);
    
    
    @EJB
    private CustomerController controller;
    
    public CustomerResource() {
        super(DTO_TO_ENTITY_CONVERTER, ENTITY_TO_DTO_CONVERTER);
    }
    
    @Override
    protected CustomerController getController() {
        return controller;
    }
    
    @GET
    @Path("/{customerId}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response retrieve(@PathParam("customerId") Integer id) {
        return super.retrieveById(id);
    }
    
    @PUT
    @Path("/{customerId}/vehicle")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response addVehicle(VehicleDTO vehicleToAdd, @PathParam("customerId") Integer customerId) {
        try {
            // Add customer ID to the Vehicle entity
            Vehicle vehicle = VehicleResource.DTO_TO_ENTITY_CONVERTER.apply(vehicleToAdd);
            vehicle.setCustomer(controller.retrieve(customerId));
            
            return Response
                        .ok(entityToDtoConverter.apply(controller.addVehicle(customerId, vehicle)))
                        .build();
        } catch (EJBException exception) {
            return Response.status(Response.Status.CONFLICT).build();
        }
    }
    
    @DELETE
    @Path("/{customerId}/vehicle")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response removeVehicle(VehicleDTO vehicleToRemove, @PathParam("customerId") Integer customerId) {
        try {
            // Add customer ID to the Vehicle entity
            Vehicle vehicle = VehicleResource.DTO_TO_ENTITY_CONVERTER.apply(vehicleToRemove);
            vehicle.setCustomer(controller.retrieve(customerId));
            
            return Response
                        .ok(entityToDtoConverter.apply(controller.removeVehicle(customerId, vehicle)))
                        .build();
        } catch (EJBException exception) {
            return Response.status(Response.Status.CONFLICT).build();
        }
    }
    
    @DELETE
    @Path("/{customerId}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response delete(@PathParam("customerId") Integer id) {
        return super.deleteById(id);
    }
}
