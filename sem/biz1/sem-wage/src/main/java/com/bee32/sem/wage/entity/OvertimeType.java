package com.bee32.sem.wage.entity;

import javax.persistence.Entity;

import com.bee32.plover.ox1.dict.NameDict;

@Entity
public class OvertimeType
        extends NameDict {

    private static final long serialVersionUID = 1L;

    int type;
    double modulus;

    public OvertimeType(String name, String label, int type, double modulus) {
        super(name, label);
        this.type = type;
        this.modulus = modulus;
    }

    /**
     * 补贴类型
     */
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    /**
     * 补贴系数
     */
    public double getModulus() {
        return modulus;
    }

    public void setModulus(double modulus) {
        this.modulus = modulus;
    }
}
