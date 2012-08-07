package com.bee32.sem.inventory.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.bee32.plover.faces.utils.FacesAssembledContext;
import com.bee32.plover.site.scope.PerSite;
import com.bee32.sem.inventory.SEMInventoryTerms;

@PerSite
@Component
public class BatchMetadata
        implements Serializable {

    private static final long serialVersionUID = 1L;

    public int arraySize = 1;
    public List<Integer> indexes = new ArrayList<Integer>();
    public List<Integer> indexes_1 = new ArrayList<Integer>();
    public List<String> labels = new ArrayList<String>();
    public List<String> labels_1 = new ArrayList<String>();

    static SEMInventoryTerms terms = SEMInventoryTerms.getInstance();

    public BatchMetadata() {
        String _batchSize = terms.getTermLabel("batchSize");
        if (_batchSize != null)
            try {
                arraySize = Integer.parseInt(_batchSize) + 1;
            } catch (NumberFormatException e) {
                arraySize = 1;
            }

        for (int index = 0; index < arraySize; index++)
            if (index == 0) {
                indexes.add(0);
                labels.add("批号");
            } else {
                // String label = "编组" + index;
                String label = terms.getTermLabel("batch" + index);

                indexes.add(index);
                indexes_1.add(index);
                labels.add(label);
                labels_1.add(label);
            }
    }

    public int getArraySize() {
        return arraySize;
    }

    /**
     * Index numbers (0-based)
     */
    public List<Integer> getIndexes() {
        return indexes;
    }

    /**
     * Index numbers (1-based)
     */
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
