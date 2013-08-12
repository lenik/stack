package com.bee32.plover.html;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import com.bee32.plover.arch.util.EnumAlt;
import com.bee32.plover.arch.util.EnumAltRegistry;

/**
 * 多重/枚举替代
 *
 * <p lang="en">
 * Multi Enum-Alternations
 */
public class MultiEnumAlts
        extends AbstractMultiSelectionModel<EnumAlt<?, ?>> {

    final Class<? extends EnumAlt<?, ?>> enumType;
    final Set<EnumAlt<?, ?>> selection;

    // redundant.
    List<EnumAlt<?, ?>> candidates;

    public MultiEnumAlts(Class<? extends EnumAlt<?, ?>> enumType) {
        this.enumType = enumType;
        this.selection = new LinkedHashSet<EnumAlt<?, ?>>();

        candidates = new ArrayList<EnumAlt<?, ?>>();
        for (Entry<String, ? extends EnumAlt<?, ?>> entry : EnumAltRegistry.nameMap(enumType).entrySet()) {
            // String name = entry.getKey();
            EnumAlt<?, ?> alt = entry.getValue();
            // String label = alt.getLabel();
            candidates.add(alt);
        }
    }

    @Override
    public Set<EnumAlt<?, ?>> getSelection() {
        return selection;
    }

    @Override
    public List<EnumAlt<?, ?>> getCandidates() {
        return candidates;
    }

}
