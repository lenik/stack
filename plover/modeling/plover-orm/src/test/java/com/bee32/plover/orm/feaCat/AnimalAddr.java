package com.bee32.plover.orm.feaCat;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@Embeddable
public class AnimalAddr
        extends AbstractAddress {

    private static final long serialVersionUID = 1L;

    String zoo;

    public AnimalAddr() {
        super();
    }

    public AnimalAddr(String country, String city, String address) {
        super(country, city, address);
    }

    public AnimalAddr(String city, String address) {
        super(city, address);
    }

    @Column(length = 30)
    public String getZoo() {
        return zoo;
    }

    public void setZoo(String zoo) {
        this.zoo = zoo;
    }

}
