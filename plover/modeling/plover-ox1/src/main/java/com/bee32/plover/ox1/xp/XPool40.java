package com.bee32.plover.ox1.xp;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import com.bee32.plover.orm.entity.Entity;

/**
 * 属性池-X40
 *
 * X40 属性池比 X30 属性池多出 10 个文本字段。
 *
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

    @SuppressWarnings("unchecked")
    @Override
    public void populate(Object source) {
        if (source instanceof XPool40)
            _populate((XPool40<? extends Entity<?>>) source);
        else
            super.populate(source);
    }

    protected void _populate(XPool40<? extends Entity<?>> o) {
        super._populate(o);
        a6 = o.a6;
        a7 = o.a7;
        a8 = o.a8;
        a9 = o.a9;
        a10 = o.a10;
        aa3 = o.aa3;
        aa4 = o.aa4;
        aa5 = o.aa5;
        aaaa2 = o.aaaa2;
        aaaa3 = o.aaaa3;
    }

    /**
     * 属性A6
     *
     * 长度为 32 的扩展字符串属性。
     */
    @Column(length = LEN_A)
    public String getA6() {
        return a6;
    }

    public void setA6(String a6) {
        this.a6 = a6;
    }

    /**
     * 属性A7
     *
     * 长度为 32 的扩展字符串属性。
     */
    @Column(length = LEN_A)
    public String getA7() {
        return a7;
    }

    public void setA7(String a7) {
        this.a7 = a7;
    }

    /**
     * 属性A8
     *
     * 长度为 32 的扩展字符串属性。
     */
    @Column(length = LEN_A)
    public String getA8() {
        return a8;
    }

    public void setA8(String a8) {
        this.a8 = a8;
    }

    /**
     * 属性A9
     *
     * 长度为 32 的扩展字符串属性。
     */
    @Column(length = LEN_A)
    public String getA9() {
        return a9;
    }

    public void setA9(String a9) {
        this.a9 = a9;
    }

    /**
     * 属性A10
     *
     * 长度为 32 的扩展字符串属性。
     */
    @Column(length = LEN_A)
    public String getA10() {
        return a10;
    }

    public void setA10(String a10) {
        this.a10 = a10;
    }

    /**
     * 属性AA3
     *
     * 长度为 64 的扩展字符串属性。
     */
    @Column(length = LEN_AA)
    public String getAa3() {
        return aa3;
    }

    public void setAa3(String aa3) {
        this.aa3 = aa3;
    }

    /**
     * 属性AA4
     *
     * 长度为 64 的扩展字符串属性。
     */
    @Column(length = LEN_AA)
    public String getAa4() {
        return aa4;
    }

    public void setAa4(String aa4) {
        this.aa4 = aa4;
    }

    /**
     * 属性AA5
     *
     * 长度为 64 的扩展字符串属性。
     */
    @Column(length = LEN_AA)
    public String getAa5() {
        return aa5;
    }

    public void setAa5(String aa5) {
        this.aa5 = aa5;
    }

    /**
     * 属性AAA2
     *
     * 长度为 250 的扩展字符串属性。
     */
    @Column(length = LEN_AAAA)
    public String getAaaa2() {
        return aaaa2;
    }

    public void setAaaa2(String aaaa2) {
        this.aaaa2 = aaaa2;
    }

    /**
     * 属性AAA3
     *
     * 长度为 250 的扩展字符串属性。
     */
    @Column(length = LEN_AAAA)
    public String getAaaa3() {
        return aaaa3;
    }

    public void setAaaa3(String aaaa3) {
        this.aaaa3 = aaaa3;
    }

}
