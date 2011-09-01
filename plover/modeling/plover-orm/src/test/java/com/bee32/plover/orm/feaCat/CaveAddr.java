package com.bee32.plover.orm.feaCat;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class CaveAddr
        implements Serializable {

    private static final long serialVersionUID = 1L;

    String city;
    String address;

    public CaveAddr() {
        System.err.println("CaveAddr::ctor");
    }

    public CaveAddr(String city, String address) {
        System.err.println("CaveAddr::ctor(x,x)");
        this.city = city;
        this.address = address;
    }

    @Column(length = 30)
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        System.err.println("    city = " + city);
        this.city = city;
    }

    @Column(length = 100)
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        System.err.println("    address = " + address);
        this.address = address;
    }

}
