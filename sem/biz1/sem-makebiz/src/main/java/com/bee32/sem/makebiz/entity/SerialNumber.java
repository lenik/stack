package com.bee32.sem.makebiz.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import com.bee32.plover.ox1.color.UIEntityAuto;

/**
 * 序列号
 *
 * 对每个生产的产品进行编号，赋予一个唯一的号码。
 */
@Entity
@SequenceGenerator(name = "idgen", sequenceName = "serial_number_seq", allocationSize = 1)
public class SerialNumber
        extends UIEntityAuto<Long> {

    private static final long serialVersionUID = 1L;

    public static final int NUMBER_LENGTH = 50;

    MakeProcess process;
    String number;

    /**
     * 工艺流转单
     *
     * 本序列号对应的工艺流转单。
     *
     * @return
     */
    @ManyToOne(optional = false)
    public MakeProcess getProcess() {
        return process;
    }

    public void setProcess(MakeProcess process) {
        this.process = process;
    }

    /**
     * 编号
     *
     * 以文字表达的号码。
     *
     * @return
     */
    @Column(length = NUMBER_LENGTH)
    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

}
