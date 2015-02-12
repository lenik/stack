package com.bee32.zebra.tk.hbin;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import net.bodz.bas.c.object.Nullables;
import net.bodz.bas.t.pojo.Pair;

import com.tinylily.model.base.CoObject;

public class SwitcherModelGroup {

    List<SwitcherModel<?>> models;

    public SwitcherModelGroup() {
        models = new ArrayList<>();
    }

    public <K> SwitcherModel<K> entityOf(String label, boolean optional, Iterable<? extends CoObject> list,
            String param, K selection, boolean selectNull) {
        SwitcherModel<K> model = new SwitcherModel<>(false, !optional);
        model.setParam(param);
        model.setLabel(label);
        model.setSelection1(selection);
        model.setSelectNull(selectNull);
        for (CoObject o : list)
            model.pairs.add(Pair.of((K) o.getId(), o.getLabel()));
        model.updateDefaults();
        models.add(model);
        return model;
    }

    public <K> SwitcherModel<K> entryOf(String label, boolean optional, Iterable<? extends Entry<K, ?>> pairs,
            String param, K selection, boolean selectNull) {
        SwitcherModel<K> model = new SwitcherModel<>(false, !optional);
        model.setParam(param);
        model.setLabel(label);
        model.setSelection1(selection);
        model.setSelectNull(selectNull);
        for (Entry<K, ?> pair : pairs) {
            K key = pair.getKey();
            Object value = pair.getValue();
            model.pairs.add(Pair.of(key, Nullables.toString(value)));
        }
        model.updateDefaults();
        models.add(model);
        return model;
    }

}
