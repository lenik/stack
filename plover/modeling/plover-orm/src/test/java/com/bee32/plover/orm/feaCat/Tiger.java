package com.bee32.plover.orm.feaCat;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import com.bee32.plover.orm.builtin.PloverConf;

@Entity
@SequenceGenerator(name = "idgen", sequenceName = "tiger_seq")
public class Tiger
        extends Cat {

    private static final long serialVersionUID = 1L;

    private int power;

    PloverConf conf;

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

    @ManyToOne
    public PloverConf getConf() {
        return conf;
    }

    public void setConf(PloverConf conf) {
        this.conf = conf;
    }

}
