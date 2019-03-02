/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fit.tjv.server.pl.rest;

import cz.cvut.fit.tjv.server.pl.dto.VehicleDTO;
import cz.cvut.fit.tjv.server.dl.entities.Vehicle;
import cz.cvut.fit.tjv.server.dl.dao.VehicleController;
import java.util.function.Function;
import javax.ejb.EJB;
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
@Path("/vehicle")
public class VehicleResource extends AbstractCRUDResource<Vehicle, VehicleDTO> {
    
    static final Function<Vehicle, VehicleDTO> ENTITY_TO_DTO_CONVERTER
            = e -> new VehicleDTO(e.getId(), e.getCreated(), e.getPlate(), e.getBrand(), e.getModel(), CustomerResource.ENTITY_TO_DTO_CONVERTER.apply(e.getCustomer()));
    static final Function<VehicleDTO, Vehicle> DTO_TO_ENTITY_CONVERTER
            = d -> new Vehicle(d.getId(), d.getCreated(), d.getPlate(), d.getBrand(), d.getModel(), CustomerResource.DTO_TO_ENTITY_CONVERTER.apply(d.getCustomer()));

    @EJB
    private VehicleController controller;
    
    public VehicleResource() {
        super(DTO_TO_ENTITY_CONVERTER, ENTITY_TO_DTO_CONVERTER);
    }
    
    @Override
    protected VehicleController getController() {
        return controller;
    }
    
    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response retrieve(@PathParam("id") Integer id) {
        return super.retrieveById(id);
    }
    
    @DELETE
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response delete(@PathParam("id") Integer id) {
        return super.deleteById(id);
    }
}
