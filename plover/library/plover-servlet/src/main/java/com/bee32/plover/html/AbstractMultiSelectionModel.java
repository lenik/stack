package com.bee32.plover.html;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public abstract class AbstractMultiSelectionModel<T>
        implements IMultiSelectionModel<T> {

    @Override
    public Set<Integer> getSelectedIndexes() {
        Set<Integer> indexes = new LinkedHashSet<Integer>();
        List<T> candidates = getCandidates();
        for (Object sel : getSelection()) {
            int index = candidates.indexOf(sel);
            indexes.add(index);
        }
        return indexes;
    }

    @Override
    public void setSelectedIndexes(Set<Integer> selectedIndexes) {
        Set<T> selection = getSelection();
        selection.clear();

        List<T> candidates = getCandidates();
        int size = candidates.size();
        for (Integer index : selectedIndexes) {
            if (index == null)
                continue;
            if (index < 0 || index >= size)
                continue;
            T sel = candidates.get(index);
            selection.add(sel);
        }
    }

}
