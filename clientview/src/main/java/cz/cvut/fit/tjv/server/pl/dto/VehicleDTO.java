/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fit.tjv.server.pl.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author tomastaro
 */
@XmlRootElement
public class VehicleDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Integer id;
    
    private Date created;
    
    private String plate;

    private String brand;
    
    private String model;
        
    private CustomerDTO customer;
    
    public VehicleDTO() {}
    
    public VehicleDTO(Integer id, Date created, String plate, String brand, String model, CustomerDTO customer) {
        this.id = id;
        this.created = created;
        this.plate = plate;
        this.brand = brand;
        this.model = model;
        if (customer != null) {
            this.customer = customer;
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    
    public CustomerDTO getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerDTO customer) {
        this.customer = customer;
    }

    public Integer getCustomerId() {
        if (this.getCustomer() != null) {
            return this.getCustomer().getId();
        }
        
        return null;
    }


    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final VehicleDTO other = (VehicleDTO) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
}
