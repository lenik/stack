package com.bee32.plover.html;

import java.util.List;
import java.util.Set;

/**
 * Multi-selection model.
 */
public interface IMultiSelectionModel<T> {

    Set<T> getValues();

    List<T> getCandidates();

    Set<Integer> getIndexes();

    void setIndexes(Set<Integer> indexes);

}
