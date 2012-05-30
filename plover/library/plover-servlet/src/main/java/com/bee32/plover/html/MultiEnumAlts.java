package com.bee32.plover.html;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import com.bee32.plover.arch.util.EnumAlt;
import com.bee32.plover.arch.util.EnumAltRegistry;

public class MultiEnumAlts
        extends AbstractMultiSelectionModel {

    final Class<? extends EnumAlt<?, ?>> enumType;
    final Set<Object> values;

    // redundant.
    List<EnumAlt<?, ?>> candidates;

    public MultiEnumAlts(Class<? extends EnumAlt<?, ?>> enumType) {
        this.enumType = enumType;
        this.values = new LinkedHashSet<Object>();

        candidates = new ArrayList<EnumAlt<?, ?>>();
        for (Entry<String, ? extends EnumAlt<?, ?>> entry : EnumAltRegistry.getNameMap(enumType).entrySet()) {
            // String name = entry.getKey();
            EnumAlt<?, ?> alt = entry.getValue();
            // String label = alt.getLabel();
            candidates.add(alt);
        }
    }

    @Override
    public Set<Object> getValues() {
        return values;
    }

    @Override
    public List<EnumAlt<?, ?>> getCandidates() {
        return candidates;
    }

}
