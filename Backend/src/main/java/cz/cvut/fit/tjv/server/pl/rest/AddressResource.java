/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fit.tjv.server.pl.rest;

import cz.cvut.fit.tjv.server.pl.dto.AddressDTO;
import cz.cvut.fit.tjv.server.dl.entities.Address;
import cz.cvut.fit.tjv.server.dl.dao.AddressController;
import cz.cvut.fit.tjv.server.dl.entities.Customer;
import cz.cvut.fit.tjv.server.dl.entities.Vehicle;
import cz.cvut.fit.tjv.server.pl.dto.CustomerDTO;
import cz.cvut.fit.tjv.server.pl.dto.VehicleDTO;

import java.util.function.Function;
import java.util.stream.Collectors;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/address")
public class AddressResource extends AbstractCRUDResource<Address, AddressDTO> {
    
    static final Function<Address, AddressDTO> ENTITY_TO_DTO_CONVERTER
            = e -> new AddressDTO(e.getId(), e.getStreet(), e.getHouseNumber(), e.getCity(), e.getZip(), e.getCountry());
    
    static final Function<AddressDTO, Address> DTO_TO_ENTITY_CONVERTER
            = d -> new Address(d.getId(), d.getStreet(), d.getHouseNumber(), d.getCity(), d.getZip(), d.getCountry());
           
    @EJB
    private AddressController controller;
    
    public AddressResource() {
        super(DTO_TO_ENTITY_CONVERTER, ENTITY_TO_DTO_CONVERTER);
    }
    
    @Override
    protected AddressController getController() {
        return controller;
    }
    
    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response retrieve(@PathParam("id") Integer id) {
        return super.retrieveById(id);
    }
    
    @PUT
    @Path("/{id}/customer")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response addVehicle(CustomerDTO customerToAdd, @PathParam("id") Integer addressId) {
        try {
            // Add vehicle to customer
            Customer customer = CustomerResource.DTO_TO_ENTITY_CONVERTER.apply(customerToAdd);
            
            return Response
                        .ok(entityToDtoConverter.apply(controller.addCustomer(addressId, customer)))
                        .build();
        } catch (EJBException exception) {
            return Response.status(Response.Status.CONFLICT).build();
        }
    }
    
    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Integer id) {
        return super.deleteById(id);
    }
}