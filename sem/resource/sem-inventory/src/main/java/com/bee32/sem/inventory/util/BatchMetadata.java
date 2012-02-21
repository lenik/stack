package com.bee32.sem.inventory.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.bee32.plover.faces.utils.FacesAssembledContext;
import com.bee32.plover.site.scope.PerSite;

@PerSite
@Component
public class BatchMetadata
        implements Serializable {

    private static final long serialVersionUID = 1L;

    public int arraySize;
    public List<Integer> indexes;
    public List<Integer> indexes_1;
    public List<String> labels;
    public List<String> labels_1;

    public BatchMetadata() {
        arraySize = 3;
        indexes = new ArrayList<Integer>();
        indexes_1 = new ArrayList<Integer>();
        labels = new ArrayList<String>();
        labels_1 = new ArrayList<String>();
        for (int i = 0; i < arraySize; i++)
            if (i == 0) {
                indexes.add(0);
                labels.add("批号");
            } else {
                int index = i;
                String label = "编组" + i;
                indexes.add(index);
                indexes_1.add(index);
                labels.add(label);
                labels_1.add(label);
            }
    }

    public int getArraySize() {
        return arraySize;
    }

    public List<Integer> getIndexes() {
        return indexes;
    }

    public List<Integer> getIndexes_1() {
        return indexes_1;
    }

    public List<String> getLabels() {
        return labels;
    }

    public List<String> getLabels_1() {
        return labels_1;
    }

    public static BatchMetadata getInstance() {
        BatchMetadata metadata = FacesAssembledContext.bean.getBean(BatchMetadata.class);
        return metadata;
    }

}
