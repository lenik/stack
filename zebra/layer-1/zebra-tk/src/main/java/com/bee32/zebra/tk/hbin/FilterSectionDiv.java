package com.bee32.zebra.tk.hbin;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import net.bodz.bas.html.dom.IHtmlTag;
import net.bodz.bas.html.dom.tag.HtmlDivTag;
import net.bodz.bas.html.dom.tag.HtmlUlTag;
import net.bodz.bas.html.util.IFontAwesomeCharAliases;
import net.bodz.bas.t.pojo.Pair;

import com.bee32.zebra.tk.site.SwitchOverride;
import com.tinylily.model.base.CoObject;

public class FilterSectionDiv
        extends SectionDiv {

    public FilterSectionDiv(IHtmlTag parent, String id) {
        super(parent, id, "过滤/Filter", IFontAwesomeCharAliases.FA_FILTER);
    }

    public <K> SwitchOverride<K> switchEntity(String label, boolean optional, Iterable<? extends CoObject> list,
            String param, K currentId, boolean currentIsNo) {
        List<Pair<K, String>> pairs = new ArrayList<>();
        for (CoObject o : list)
            pairs.add(Pair.of((K) o.getId(), o.getLabel()));
        return switchPairs(label, optional, pairs, param, currentId, currentIsNo);
    }

    public <K> SwitchOverride<K> switchPairs(String label, boolean optional, Iterable<? extends Entry<K, ?>> pairs,
            String param, K selection, boolean selectNull) {
        boolean selectAll = selection == null && !selectNull;

        HtmlDivTag div = contentDiv.div().class_("zu-switcher");
        div.b().text(label + ": ");

        HtmlUlTag ul = div.ul();

        if (optional) {
            IHtmlTag tag = ul.li();
            if (!selectAll)
                tag = tag.a().href("?all-" + param + "=1");
            tag.text("全部");
        }

        for (Entry<K, ?> pair : pairs) {
            IHtmlTag tag = ul.li();
            K key = pair.getKey();

            if (!optional && selectAll) {
                selection = key;
                selectNull = key == null;
                selectAll = false;
            }

            boolean selected;
            if (key == null)
                selected = selection == null && selectNull;
            else
                selected = selection != null && selection.equals(key);

            if (selected)
                tag.class_("active");
            else {
                if (key == null)
                    tag = tag.a().href("?no-" + param + "=1");
                else
                    tag = tag.a().href("?" + param + "=" + key);
            }

            tag.text(pair.getValue());
        }

        return new SwitchOverride<K>(selection, selectNull);
    }

}
