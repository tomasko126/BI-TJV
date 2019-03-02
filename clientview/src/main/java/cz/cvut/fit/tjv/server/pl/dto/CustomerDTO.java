/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fit.tjv.server.pl.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author tomastaro
 */
@XmlRootElement
public class CustomerDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Integer id;
    
    private String name;
    
    private String surname;
    
    private AddressDTO address;
    
    public CustomerDTO() {}
    
    public CustomerDTO(Integer id, String name, String surname, AddressDTO address) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.address = address;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public AddressDTO getAddress() {
        return address;
    }

    public void setAddress(AddressDTO address) {
        this.address = address;
    }
    
    public Integer getAddressId() {
        return getAddress().getId();
    }    

    @Override
    public int hashCode() {
        int hash = 5;
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
        final CustomerDTO other = (CustomerDTO) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }   
}