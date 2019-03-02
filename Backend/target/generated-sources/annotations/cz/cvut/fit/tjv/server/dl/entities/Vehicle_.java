package cz.cvut.fit.tjv.server.dl.entities;

import cz.cvut.fit.tjv.server.dl.entities.Customer;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-12-30T22:25:32")
@StaticMetamodel(Vehicle.class)
public class Vehicle_ { 

    public static volatile SingularAttribute<Vehicle, Date> created;
    public static volatile SingularAttribute<Vehicle, String> plate;
    public static volatile SingularAttribute<Vehicle, String> model;
    public static volatile SingularAttribute<Vehicle, Integer> id;
    public static volatile SingularAttribute<Vehicle, String> brand;
    public static volatile SingularAttribute<Vehicle, Customer> customer;

}