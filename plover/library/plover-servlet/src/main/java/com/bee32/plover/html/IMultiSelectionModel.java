package com.bee32.plover.html;

import java.util.List;
import java.util.Set;

/**
 * Multi-selection model.
 */
public interface IMultiSelectionModel {

    Set<Object> getValues();

    List<?> getCandidates();

    Set<Integer> getIndexes();

    void setIndexes(Set<Integer> indexes);

}
