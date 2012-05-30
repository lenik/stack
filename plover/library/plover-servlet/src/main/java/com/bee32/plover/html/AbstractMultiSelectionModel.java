package com.bee32.plover.html;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public abstract class AbstractMultiSelectionModel
        implements IMultiSelectionModel {

    @Override
    public Set<Integer> getIndexes() {
        Set<Integer> indexes = new LinkedHashSet<Integer>();
        List<?> candidates = getCandidates();
        for (Object value : getValues()) {
            int index = candidates.indexOf(value);
            indexes.add(index);
        }
        return indexes;
    }

    @Override
    public void setIndexes(Set<Integer> indexes) {
        Set<Object> values = getValues();
        values.clear();

        List<?> candidates = getCandidates();
        int size = candidates.size();
        for (Integer index : indexes) {
            if (index == null)
                continue;
            if (index < 0 || index >= size)
                continue;
            Object value = candidates.get(index);
            values.add(value);
        }
    }

}
