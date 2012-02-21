package com.bee32.sem.inventory.util;

import java.io.Serializable;
import java.util.Arrays;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;

import org.hibernate.annotations.Index;

import com.bee32.plover.model.validation.core.NLength;
import com.bee32.plover.orm.entity.IPopulatable;
import com.bee32.plover.util.TextUtil;

@Embeddable
public class BatchArray
        implements Serializable, Cloneable, IPopulatable {

    private static final long serialVersionUID = 1L;

    public static final int BATCH0_LENGTH = 30;
    public static final int BATCH1_LENGTH = 30;
    public static final int BATCH2_LENGTH = 30;
    public static final int BATCH3_LENGTH = 30;
    public static final int BATCH4_LENGTH = 30;

    public static final int MAX_ARRAYSIZE = 5;
    final String[] array;

    public BatchArray() {
        array = new String[MAX_ARRAYSIZE];
    }

    public BatchArray(BatchArray init) {
        this();
        populate(init);
    }

    public BatchArray(String... init) {
        this();
        if (init == null)
            throw new NullPointerException("init");
        int n = Math.min(array.length, init.length);
        for (int i = 0; i < n; i++)
            array[i] = init[i];
    }

    @Override
    public BatchArray clone() {
        BatchArray o = new BatchArray();
        System.arraycopy(array, 0, o.array, 0, array.length);
        return o;
    }

    @Override
    public void populate(Object source) {
        if (source instanceof BatchArray) {
            BatchArray o = (BatchArray) source;
            int n = Math.min(array.length, o.array.length);
            for (int i = 0; i < n; i++)
                array[i] = o.array[i];
        }
    }

    @Column(length = BATCH0_LENGTH)
    @Index(name = "##_batch0")
    @NLength(max = BATCH0_LENGTH)
    public String getBatch0() {
        return getBatch(0);
    }

    public void setBatch0(String batch0) {
        setBatch(0, batch0);
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

    @Transient
    public String getBatch(int index) {
        return array[index];
    }

    public void setBatch(int index, String batch) {
        batch = TextUtil.normalizeSpace(batch, true);
        array[index] = batch;
    }

    @Transient
    public String[] getArray() {
        return array;
    }

    @Transient
    public BatchArrayEntry[] getEntries() {
        BatchMetadata metadata = BatchMetadata.getInstance();
        BatchArrayEntry[] entries = new BatchArrayEntry[array.length];
        for (int index = 0; index < array.length; index++) {
            BatchArrayEntry entry = new BatchArrayEntry(metadata, this, index);
            entries[index] = entry;
        }
        return entries;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof BatchArray))
            return false;
        BatchArray o = (BatchArray) obj;
        if (!Arrays.equals(array, o.array))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        int hash = Arrays.hashCode(array);
        return hash;
    }

    @Override
    public String toString() {
        return Arrays.toString(array);
    }

}
