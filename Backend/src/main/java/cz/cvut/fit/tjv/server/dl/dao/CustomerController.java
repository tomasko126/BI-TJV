/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fit.tjv.server.dl.dao;

import cz.cvut.fit.tjv.server.dl.entities.Address;
import cz.cvut.fit.tjv.server.dl.entities.Customer;
import cz.cvut.fit.tjv.server.dl.entities.Vehicle;
import java.util.Objects;
import javax.ejb.Stateless;

/**
 *
 * @author tomastaro
 */
@Stateless
public class CustomerController extends AbstractCRUDController<Customer> {

    public CustomerController() {
        super(Customer.class);
    }

    @Override
    protected Integer getEntityId(Customer e) {
        return e.getId();
    }
    
    public Customer addVehicle(int id, Vehicle v) {
        // Fetch customer
        Customer customer = retrieve(id);
        
        // Add vehicle to the customer
        customer.getVehicles().add(Objects.requireNonNull(v));
        
        // Update customer data in the DB
        updateOrCreate(customer);
        
        return customer;
    }
    
    public Customer removeVehicle(int id, Vehicle v) {
        // Fetch customer
        Customer customer = retrieve(id);
        
        // Remove vehicle
        customer.getVehicles().remove(Objects.requireNonNull(v));
        
        updateOrCreate(customer);
        
        return customer;
    }
}