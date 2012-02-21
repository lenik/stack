package com.bee32.sem.inventory.util;

import com.bee32.plover.model.validation.core.NLength;

public class BatchArrayEntry {

    final BatchMetadata metadata;
    final BatchArray array;
    final int index;

    public BatchArrayEntry(BatchMetadata metadata, BatchArray array, int index) {
        this.metadata = metadata;
        this.array = array;
        this.index = index;
    }

    public BatchArray getArray() {
        return array;
    }

    public int getIndex() {
        return index;
    }

    public String getLabel() {
        return metadata.getLabels().get(index);
    }

    @NLength(max = BatchArray.BATCH0_LENGTH)
    public String getValue() {
        return array.getBatch(index);
    }

    public void setValue(String value) {
        array.setBatch(index, value);
    }

}
