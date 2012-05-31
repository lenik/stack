package com.bee32.plover.site.cfg;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import com.bee32.plover.arch.AppProfileManager;
import com.bee32.plover.arch.IAppProfile;
import com.bee32.plover.html.AbstractMultiSelectionModel;

/**
 * profileName* => label.
 */
public class MultiProfileSelection
        extends AbstractMultiSelectionModel<Relabel<String>> {

    Set<Relabel<String>> values;
    List<Relabel<String>> candidates;

    public MultiProfileSelection(Set<String> profileNames) {
        if (profileNames == null)
            values = new LinkedHashSet<Relabel<String>>();
        else
            values = Relabel.convert(profileNames);

        candidates = new ArrayList<Relabel<String>>(relabelsDef);
        for (Relabel<String> value : values)
            if (!candidates.contains(value))
                candidates.add(value);
    }

    @Override
    public Set<Relabel<String>> getValues() {
        return values;
    }

    @Override
    public List<Relabel<String>> getCandidates() {
        return candidates;
    }

    static final List<Relabel<String>> relabelsDef;
    static {
        relabelsDef = new ArrayList<Relabel<String>>();
        for (Entry<String, IAppProfile> entry : AppProfileManager.getProfileMap().entrySet()) {
            String profileName = entry.getKey();
            IAppProfile profile = entry.getValue();
            String label = profile.getAppearance().getDisplayName();
            Relabel<String> relabel = new Relabel<String>(profileName, label);
            relabelsDef.add(relabel);
        }
    }

}
