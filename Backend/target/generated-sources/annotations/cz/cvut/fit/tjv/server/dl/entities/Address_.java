package cz.cvut.fit.tjv.server.dl.entities;

import cz.cvut.fit.tjv.server.dl.entities.Customer;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-12-30T22:25:32")
@StaticMetamodel(Address.class)
public class Address_ { 

    public static volatile SingularAttribute<Address, String> zip;
    public static volatile SingularAttribute<Address, String> country;
    public static volatile SingularAttribute<Address, String> city;
    public static volatile SingularAttribute<Address, String> street;
    public static volatile SingularAttribute<Address, String> houseNumber;
    public static volatile SingularAttribute<Address, Integer> id;
    public static volatile CollectionAttribute<Address, Customer> customers;

}