/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fit.tjv.server.dl.dao;

import cz.cvut.fit.tjv.server.dl.entities.Address;
import cz.cvut.fit.tjv.server.dl.entities.Customer;
import java.util.Objects;
import javax.ejb.Stateless;

/**
 *
 * @author tomastaro
 */
@Stateless
public class AddressController extends AbstractCRUDController<Address> {

    public AddressController() {
        super(Address.class);
    }

    @Override
    protected Integer getEntityId(Address e) {
        return e.getId();
    }
    
    public Address addCustomer(int id, Customer c) {
        // Fetch current address
        Address address = retrieve(id);
        
        // Add customer to the address
        address.getCustomers().add(Objects.requireNonNull(c));
        
        // Update customer data in the DB
        updateOrCreate(address);
        
        return address;
    }
}