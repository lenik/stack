package com.bee32.sem.makebiz.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import com.bee32.plover.orm.entity.EntityAuto;

/**
 * 序列号
 */
@Entity
@SequenceGenerator(name = "idgen", sequenceName = "serial_number_seq", allocationSize = 1)
public class SerialNumber
        extends EntityAuto<Long> {

    private static final long serialVersionUID = 1L;

    public static final int NUMBER_LENGTH = 50;

    MakeProcess process;
    String number;

    @ManyToOne(optional = false)
    public MakeProcess getProcess() {
        return process;
    }

    public void setProcess(MakeProcess process) {
        this.process = process;
    }

    @Column(length = NUMBER_LENGTH)
    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

}
