/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fit.tjv.client.restclient;

import cz.cvut.fit.tjv.server.pl.dto.AddressDTO;
import cz.cvut.fit.tjv.server.pl.dto.CustomerDTO;
import cz.cvut.fit.tjv.server.pl.dto.VehicleDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author tomastaro
 */
public class AddressClient {
    private static final String RESOURCE_PATH = "/address";
    private static final String RESOURCE_ID_PATH = "{id}";
    private static final String RESOURCE_PATH_ADD = "/customer";

    private static final AddressClient INSTANCE = new AddressClient();
    
    private AddressClient() {}
    
    public static AddressClient getInstance() {
        return INSTANCE;
    }

    public void createOrUpdateJson(AddressDTO e) {
        RestClient.processRequest(RESOURCE_PATH, t -> t.request(MediaType.APPLICATION_JSON).put(Entity.json(e), AddressDTO.class));
     
    }

    public void createOrUpdateXml(AddressDTO e) {
        RestClient.processRequest(RESOURCE_PATH, t -> t.request().put(Entity.xml(e)));
    }

    public AddressDTO retrieveJson(Integer id) {
        return RestClient.processRequest(RESOURCE_PATH, t -> t.path(RESOURCE_ID_PATH)
                .resolveTemplate("id", id)
                .request(MediaType.APPLICATION_JSON).get(AddressDTO.class));
    }

    public AddressDTO retrieveXml(Integer id) {
        return RestClient.processRequest(RESOURCE_PATH, t -> t.path(RESOURCE_ID_PATH)
                .resolveTemplate("id", id)
                .request(MediaType.APPLICATION_XML).get(AddressDTO.class));
    }

    public AddressDTO[] retrieveAllJson() {
        return RestClient.processRequest(RESOURCE_PATH, t -> t.request(MediaType.APPLICATION_JSON).get(AddressDTO[].class));
    }

    public AddressDTO[] retrieveAllXml() {
        return RestClient.processRequest(RESOURCE_PATH, t -> t.request(MediaType.APPLICATION_XML).get(AddressDTO[].class));
    }

    public void delete(Integer id) {
        RestClient.processRequest(RESOURCE_PATH,
                t -> {
                    int status = t.path(RESOURCE_ID_PATH).resolveTemplate("id", id).request().delete().getStatus();
                    return status;
                });
    }
    
    public void addCustomerJson(Integer addressId, CustomerDTO customer) {
        RestClient.processRequest(RESOURCE_PATH, t -> {
            int status = t.path(RESOURCE_ID_PATH).resolveTemplate("id", addressId).path(RESOURCE_PATH_ADD).request().post(Entity.json(customer)).getStatus();
            
            return status;
        });

    }
}