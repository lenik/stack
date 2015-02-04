package com.bee32.zebra.tk.hbin;

import java.util.Map.Entry;

import net.bodz.bas.html.dom.IHtmlTag;
import net.bodz.bas.html.dom.tag.HtmlDivTag;
import net.bodz.bas.html.dom.tag.HtmlUlTag;
import net.bodz.bas.html.util.IFontAwesomeCharAliases;

public class FilterSectionDiv
        extends SectionDiv {

    public FilterSectionDiv(IHtmlTag parent, String id) {
        super(parent, id, "过滤/Filter", IFontAwesomeCharAliases.FA_FILTER);
    }

    public void build(SwitcherModelGroup switchers) {
        for (SwitcherModel<?> switcher : switchers.models) {
            build(switcher);
        }
    }

    public <K> void build(SwitcherModel<K> switcher) {
        String param = switcher.getParam();

        HtmlDivTag div = contentDiv.div().class_("zu-switcher");
        div.b().text(switcher.getLabel() + ": ");

        HtmlUlTag ul = div.ul();

        if (switcher.isOptional()) {
            IHtmlTag tag = ul.li();
            if (!switcher.isSelectAll())
                tag = tag.a().href("?all-" + param + "=1");
            tag.text("全部");
        }

        for (Entry<K, ?> pair : switcher.getPairs()) {
            IHtmlTag tag = ul.li();
            K key = pair.getKey();

            if (switcher.isSelectNone() && switcher.isRequired()) {
                switcher.setSelection1(key);
                switcher.setSelectNull(key == null);
            }

            boolean selected = switcher.isSelected(key);

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
    }

}
