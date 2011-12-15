package com.bee32.plover.orm.feaCat;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class AbstractAddress
        implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final int COUNTRY_LENGTH = 16;
    public static final int CITY_LENGTH = 16;
    public static final int ADDRESS_LENGTH = 100;

    String country;
    String city;
    String address;

    public AbstractAddress() {
    }

    public AbstractAddress(String city, String address) {
        this("China", city, address);
    }

    public AbstractAddress(String country, String city, String address) {
        this.country = country;
        this.city = city;
        this.address = address;
    }

    @Column(length = COUNTRY_LENGTH)
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Column(length = CITY_LENGTH)
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Column(length = ADDRESS_LENGTH)
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
