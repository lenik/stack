package com.bee32.sem.inventory.util;

import java.io.Serializable;

import javax.free.Nullables;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;

import org.hibernate.annotations.Index;

import com.bee32.plover.model.validation.core.NLength;
import com.bee32.plover.util.TextUtil;

@Embeddable
public class CBatch
        implements Cloneable, Serializable {

    private static final long serialVersionUID = 1L;

    public static final int BATCH1_LENGTH = 30;
    public static final int BATCH2_LENGTH = 30;
    public static final int BATCH3_LENGTH = 30;
    public static final int BATCH4_LENGTH = 30;
    public static final int BATCH5_LENGTH = 30;

    static int size;
    static String[] batchLabels;
    static {
        size = 5;
        batchLabels = new String[size];
        for (int i = 0; i < size; i++)
            batchLabels[i] = "批号" + (i + 1);
    }

    final String[] batches;

    public CBatch() {
        batches = new String[size];
    }

    public CBatch(String... init) {
        this();
        if (init == null)
            throw new NullPointerException("init");
        int initSize = Math.min(size, init.length);
        for (int i = 0; i < initSize; i++)
            batches[i] = init[i];
    }

    @Override
    public CBatch clone() {
        CBatch o = new CBatch();
        System.arraycopy(batches, 0, o.batches, 0, size);
        return o;
    }

    @Column(length = BATCH1_LENGTH)
    @Index(name = "##_batch1")
    @NLength(max = BATCH1_LENGTH)
    public String getBatch1() {
        return getBatch(1);
    }

    public void setBatch1(String batch1) {
        setBatch(1, batch1);
    }

    @Column(length = BATCH2_LENGTH)
    @Index(name = "##_batch2")
    @NLength(max = BATCH2_LENGTH)
    public String getBatch2() {
        return getBatch(2);
    }

    public void setBatch2(String batch2) {
        setBatch(2, batch2);
    }

    @Column(length = BATCH3_LENGTH)
    @Index(name = "##_batch3")
    @NLength(max = BATCH3_LENGTH)
    public String getBatch3() {
        return getBatch(3);
    }

    public void setBatch3(String batch3) {
        setBatch(3, batch3);
    }

    @Column(length = BATCH4_LENGTH)
    @Index(name = "##_batch4")
    @NLength(max = BATCH4_LENGTH)
    public String getBatch4() {
        return getBatch(4);
    }

    public void setBatch4(String batch4) {
        setBatch(4, batch4);
    }

    @Column(length = BATCH5_LENGTH)
    @Index(name = "##_batch5")
    @NLength(max = BATCH5_LENGTH)
    public String getBatch5() {
        return getBatch(5);
    }

    public void setBatch5(String batch5) {
        setBatch(5, batch5);
    }

    @Transient
    public String getBatch(int index) {
        return batches[index - 1];
    }

    public void setBatch(int index, String batch) {
        batch = TextUtil.normalizeSpace(batch, true);
        batches[index - 1] = batch;
    }

    @Transient
    public String[] getBatches() {
        return batches;
    }

    @Transient
    public String[] getBatchLabels() {
        return batchLabels;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof CBatch))
            return false;
        CBatch o = (CBatch) obj;
        if (size != o.size)
            return false;
        for (int i = 0; i < size; i++)
            if (!Nullables.equals(batches[i], o.batches[i]))
                return false;
        return true;
    }

    @Override
    public int hashCode() {
        int hash = size * 0x32821265;
        for (int i = 0; i < size; i++) {
            String el = batches[i];
            if (el != null)
                hash += el.hashCode();
            hash *= 17;
        }
        return hash;
    }

}
