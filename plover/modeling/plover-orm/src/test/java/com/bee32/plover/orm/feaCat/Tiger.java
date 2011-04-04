package com.bee32.plover.orm.feaCat;

import javax.persistence.Entity;

import com.bee32.plover.orm.entity.EntityBean;

@Entity
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

    @Override
    protected boolean equalsEntity(EntityBean<Long> otherEntity) {
        if (!super.equalsEntity(otherEntity))
            return false;

        Tiger o = (Tiger) otherEntity;
        if (power != o.power)
            return false;

        return true;
    }

    @Override
    protected int hashCodeEntity() {
        return super.hashCodeEntity() + power * 31;
    }

}
