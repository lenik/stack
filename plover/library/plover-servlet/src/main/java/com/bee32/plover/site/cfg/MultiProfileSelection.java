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

    Set<Relabel<String>> selection;
    List<Relabel<String>> candidates;

    /**
     * Profile name as the key, the profile.appearance.label as the label.
     */
    public MultiProfileSelection(Set<String> profileNames) {
        if (profileNames == null)
            selection = new LinkedHashSet<Relabel<String>>();
        else
            selection = Relabel.convert(profileNames);

        candidates = new ArrayList<Relabel<String>>(relabelsDef);
        for (Relabel<String> relabel : selection) {
            if (relabel == null)
                throw new NullPointerException("relabel");
            if (!candidates.contains(relabel))
                candidates.add(relabel);
        }
    }

    @Override
    public Set<Relabel<String>> getSelection() {
        return selection;
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
