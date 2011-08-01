package com.bee32.plover.orm.feaCat;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;

@Entity
@SequenceGenerator(name = "idgen", sequenceName = "tiger_seq")
public class Tiger
        extends Cat {

    private static final long serialVersionUID = 1L;

    private int power;

    public Tiger() {
        super();
    }

    public Tiger(String name, String color) {
        super(name, color);
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

}
