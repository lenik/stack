package com.bee32.plover.orm.feaCat;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.bee32.plover.orm.builtin.PloverConf;

@Entity
@SequenceGenerator(name = "idgen", sequenceName = "tiger_seq")
public class Tiger
        extends Cat {

    private static final long serialVersionUID = 1L;

    int power;
    Date birthday;

    PloverConf conf;

    public Tiger() {
        super();
    }

    public Tiger(String name, String color) {
        super(name, color);
    }

    @Override
    public void populate(Object source) {
        if (source instanceof Tiger)
            _populate((Tiger) source);
        else
            super.populate(source);
    }

    protected void _populate(Tiger o) {
        super._populate(o);
        power = o.power;
        birthday = o.birthday;
        conf = o.conf;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    @Temporal(TemporalType.DATE)
    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    @ManyToOne
    public PloverConf getConf() {
        return conf;
    }

    public void setConf(PloverConf conf) {
        this.conf = conf;
    }

    @Transient
    @Override
    protected String getInternal() {
        return "Hello";
    }

}
