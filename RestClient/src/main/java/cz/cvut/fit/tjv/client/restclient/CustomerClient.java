/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fit.tjv.client.restclient;

import cz.cvut.fit.tjv.server.pl.dto.CustomerDTO;
import cz.cvut.fit.tjv.server.pl.dto.VehicleDTO;
import java.util.Date;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;

public class CustomerClient {

    private static final String RESOURCE_PATH = "/customer";
    private static final String RESOURCE_ID_PATH = "{id}";
    private static final String RESOURCE_VEHICLE_PATH = "/vehicle";

    private static final CustomerClient INSTANCE = new CustomerClient();

    private CustomerClient() {
    }

    public static CustomerClient getInstance() {
        return INSTANCE;
    }

    public void createOrUpdateXml(CustomerDTO e) {
        RestClient.processRequest(RESOURCE_PATH, t -> t.request().put(Entity.xml(e)));
    }

    public void createOrUpdateJson(CustomerDTO e) {
        RestClient.processRequest(RESOURCE_PATH, t -> t.request().put(Entity.json(e)));
    }

    public CustomerDTO retrieveXml(Integer id) {
        return RestClient.processRequest(RESOURCE_PATH, t -> t.path(RESOURCE_ID_PATH)
                .resolveTemplate("id", id)
                .request(MediaType.APPLICATION_XML).get(CustomerDTO.class));
    }

    public CustomerDTO retrieveJson(Integer id) {
        return RestClient.processRequest(RESOURCE_PATH, t -> t.path(RESOURCE_ID_PATH)
                .resolveTemplate("id", id)
                .request(MediaType.APPLICATION_JSON).get(CustomerDTO.class));
    }

    public CustomerDTO[] retrieveAllJson() {
        return RestClient.processRequest(RESOURCE_PATH, t -> t.request(MediaType.APPLICATION_JSON).get(CustomerDTO[].class));
    }

    public CustomerDTO[] retrieveAllXml() {
        return RestClient.processRequest(RESOURCE_PATH, t -> t.request(MediaType.APPLICATION_XML).get(CustomerDTO[].class));
    }

    public void delete(Integer id) {
        RestClient.processRequest(RESOURCE_PATH,
                t -> {
                    int status = t.path(RESOURCE_ID_PATH).resolveTemplate("id", id).request().delete().getStatus();
                    return status;
                });
    }
    
    private void addOrRemoveVehicleJson(int customerId, VehicleDTO vehicle, boolean remove) {
        RestClient.processRequest(RESOURCE_PATH, t -> {
            int status;
            
            if (remove) {
                status = t.path(RESOURCE_ID_PATH).resolveTemplate("id", customerId).path(RESOURCE_VEHICLE_PATH).request().method("delete", Entity.json(vehicle)).getStatus();
            } else {
                status = t.path(RESOURCE_ID_PATH).resolveTemplate("id", customerId).path(RESOURCE_VEHICLE_PATH).request().post(Entity.json(vehicle)).getStatus();
            }
            
            return status;
        });

    }

    public void addVehicleJson(int customerId, VehicleDTO vehicleToAdd) {
        addOrRemoveVehicleJson(customerId, vehicleToAdd, false);
    }

    public void removeVehicleJson(int customerId, VehicleDTO vehicleToRemove) {
        addOrRemoveVehicleJson(customerId, vehicleToRemove, true);
    }

}
