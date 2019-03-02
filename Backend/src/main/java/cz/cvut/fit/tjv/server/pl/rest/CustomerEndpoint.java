/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fit.tjv.server.pl.rest;

import javax.ws.rs.core.Application;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.ws.rs.ApplicationPath;

/**
 *
 * @author tomastaro
 */
@ApplicationPath("/api")
public class CustomerEndpoint extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        return Stream
                .of(AddressResource.class, VehicleResource.class, CustomerResource.class)
                .collect(Collectors.toSet());
    }
    
}