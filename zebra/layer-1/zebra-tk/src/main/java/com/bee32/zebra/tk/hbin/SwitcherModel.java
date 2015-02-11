package com.bee32.zebra.tk.hbin;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.bodz.bas.t.pojo.Pair;

public class SwitcherModel<K> {

    boolean multiple;
    boolean required;

    String param;
    String label;

    List<Pair<K, String>> pairs;
    Set<K> selection;
    boolean selectNull;

    public SwitcherModel() {
        this(false, false);
    }

    public SwitcherModel(boolean multiple, boolean required) {
        this.multiple = multiple;
        this.required = required;
        pairs = new ArrayList<>();
        selection = new HashSet<>();
    }

    public boolean isMultiple() {
        return multiple;
    }

    public void setMultiple(boolean multiple) {
        this.multiple = multiple;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public boolean isOptional() {
        return !required;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<Pair<K, String>> getPairs() {
        return pairs;
    }

    public void setPairs(List<Pair<K, String>> pairs) {
        this.pairs = pairs;
    }

    public Set<K> getSelection() {
        return selection;
    }

    public void setSelection(Set<K> selection) {
        if (selection == null)
            throw new NullPointerException("selection");
        if (!multiple) {
            if (selection.size() > 1)
                throw new IllegalArgumentException("multiple selection.");
            if (!selection.isEmpty())
                selectNull = false;
        }
        this.selection = selection;
    }

    public K getSelection1() {
        if (selection == null || selection.isEmpty())
            return null;
        else
            return selection.iterator().next();
    }

    public void setSelection1(K selection) {
        this.selection.clear();
        if (selection != null)
            this.selection.add(selection);
        selectNull = false;
    }

    public boolean isSelectNull() {
        return selectNull;
    }

    public void setSelectNull(boolean selectNull) {
        this.selectNull = selectNull;
        if (selectNull && !multiple)
            selection.clear();
    }

    public boolean isSelectNone() {
        return selection.isEmpty() && !selectNull;
    }

    public boolean isEnabled() {
        if (isRequired())
            return true;
        else
            return !isSelectNone();
    }

    public boolean isSelected(K key) {
        if (key == null)
            return selectNull;
        else
            return selection.contains(key);
    }

}
