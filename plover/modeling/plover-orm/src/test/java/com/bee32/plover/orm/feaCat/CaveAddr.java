package com.bee32.plover.orm.feaCat;

import javax.persistence.Embeddable;

@Embeddable
public class CaveAddr
        extends AbstractAddress {

    private static final long serialVersionUID = 1L;

    int caveNumber;

    public CaveAddr() {
    }

    public CaveAddr(String city, String address) {
        super(city, address);
    }

    public int getCaveNumber() {
        return caveNumber;
    }

    public void setCaveNumber(int caveNumber) {
        this.caveNumber = caveNumber;
    }

}
