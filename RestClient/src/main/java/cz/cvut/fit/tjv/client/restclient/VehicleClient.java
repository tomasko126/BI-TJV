/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fit.tjv.client.restclient;

import cz.cvut.fit.tjv.server.pl.dto.AddressDTO;
import cz.cvut.fit.tjv.server.pl.dto.VehicleDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author tomastaro
 */
public class VehicleClient {
    private static final String RESOURCE_PATH = "/vehicle";
    private static final String RESOURCE_ID_PATH = "{id}";

    private static final VehicleClient INSTANCE = new VehicleClient();
    
    private VehicleClient() {}
    
    public static VehicleClient getInstance() {
        return INSTANCE;
    }

    public void createOrUpdateJson(VehicleDTO e) {
        RestClient.processRequest(RESOURCE_PATH, t -> t.request(MediaType.APPLICATION_JSON).put(Entity.json(e), VehicleDTO.class));
     
    }

    public void createOrUpdateXml(VehicleDTO e) {
        RestClient.processRequest(RESOURCE_PATH, t -> t.request().put(Entity.xml(e)));
    }

    public VehicleDTO retrieveJson(Integer id) {
        return RestClient.processRequest(RESOURCE_PATH, t -> t.path(RESOURCE_ID_PATH)
                .resolveTemplate("id", id)
                .request(MediaType.APPLICATION_JSON).get(VehicleDTO.class));
    }

    public VehicleDTO retrieveXml(Integer id) {
        return RestClient.processRequest(RESOURCE_PATH, t -> t.path(RESOURCE_ID_PATH)
                .resolveTemplate("id", id)
                .request(MediaType.APPLICATION_XML).get(VehicleDTO.class));
    }

    public VehicleDTO[] retrieveAllJson() {
        return RestClient.processRequest(RESOURCE_PATH, t -> t.request(MediaType.APPLICATION_JSON).get(VehicleDTO[].class));
    }

    public VehicleDTO[] retrieveAllXml() {
        return RestClient.processRequest(RESOURCE_PATH, t -> t.request(MediaType.APPLICATION_XML).get(VehicleDTO[].class));
    }

    public void delete(Integer id) {
        RestClient.processRequest(RESOURCE_PATH,
                t -> {
                    int status = t.path(RESOURCE_ID_PATH).resolveTemplate("id", id).request().delete().getStatus();
                    return status;
                });
    }
}