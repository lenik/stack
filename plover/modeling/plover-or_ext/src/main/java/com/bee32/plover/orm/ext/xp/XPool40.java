package com.bee32.plover.orm.ext.xp;

import javax.persistence.MappedSuperclass;

import com.bee32.plover.orm.entity.Entity;

/**
 * XPool40 contains 10 more text columns then XPool30.
 *
 * @see XPool30
 */
@MappedSuperclass
public class XPool40<Es extends Entity<?>>
        extends XPool30<Es> {

    private static final long serialVersionUID = 1L;

    String a6;
    String a7;
    String a8;
    String a9;
    String a10;

    String aa3;
    String aa4;
    String aa5;

    String aaaa2;
    String aaaa3;

    public String getA6() {
        return a6;
    }

    public void setA6(String a6) {
        this.a6 = a6;
    }

    public String getA7() {
        return a7;
    }

    public void setA7(String a7) {
        this.a7 = a7;
    }

    public String getA8() {
        return a8;
    }

    public void setA8(String a8) {
        this.a8 = a8;
    }

    public String getA9() {
        return a9;
    }

    public void setA9(String a9) {
        this.a9 = a9;
    }

    public String getA10() {
        return a10;
    }

    public void setA10(String a10) {
        this.a10 = a10;
    }

    public String getAa3() {
        return aa3;
    }

    public void setAa3(String aa3) {
        this.aa3 = aa3;
    }

    public String getAa4() {
        return aa4;
    }

    public void setAa4(String aa4) {
        this.aa4 = aa4;
    }

    public String getAa5() {
        return aa5;
    }

    public void setAa5(String aa5) {
        this.aa5 = aa5;
    }

    public String getAaaa2() {
        return aaaa2;
    }

    public void setAaaa2(String aaaa2) {
        this.aaaa2 = aaaa2;
    }

    public String getAaaa3() {
        return aaaa3;
    }

    public void setAaaa3(String aaaa3) {
        this.aaaa3 = aaaa3;
    }

}
