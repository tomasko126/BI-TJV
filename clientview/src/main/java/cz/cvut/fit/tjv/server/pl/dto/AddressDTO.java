/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fit.tjv.server.pl.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author tomastaro
 */
@XmlRootElement
public class AddressDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Integer id;
    
    private String street;
    
    private String houseNumber;
    
    private String city;

    private String zip;
    
    private String country;
        
    public AddressDTO() {}
    
    public AddressDTO(Integer id, String street, String houseNumber, String city, String zip, String country) {
        this.id = id;
        this.street = street;
        this.houseNumber = houseNumber;
        this.city = city;
        this.zip = zip;
        this.country = country;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }   

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 31 * hash + Objects.hashCode(this.id);
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
        final AddressDTO other = (AddressDTO) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
}
