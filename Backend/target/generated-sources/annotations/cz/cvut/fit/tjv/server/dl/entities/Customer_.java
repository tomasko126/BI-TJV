package cz.cvut.fit.tjv.server.dl.entities;

import cz.cvut.fit.tjv.server.dl.entities.Address;
import cz.cvut.fit.tjv.server.dl.entities.Vehicle;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-12-30T22:25:32")
@StaticMetamodel(Customer.class)
public class Customer_ { 

    public static volatile SingularAttribute<Customer, Address> address;
    public static volatile SingularAttribute<Customer, String> surname;
    public static volatile SingularAttribute<Customer, String> name;
    public static volatile ListAttribute<Customer, Vehicle> vehicles;
    public static volatile SingularAttribute<Customer, Integer> id;

}