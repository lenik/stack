package com.bee32.zebra.tk.hbin;

import java.util.Map.Entry;

import net.bodz.bas.html.io.IHtmlOut;
import net.bodz.bas.html.io.tag.HtmlDiv;
import net.bodz.bas.html.io.tag.HtmlUl;
import net.bodz.bas.html.util.IFontAwesomeCharAliases;

public class FilterSectionDiv
        extends SectionDiv_htm1 {

    SwitcherModelGroup switchers;

    public FilterSectionDiv(SwitcherModelGroup switchers) {
        super("过滤/Filter", IFontAwesomeCharAliases.FA_FILTER);
        this.switchers = switchers;
    }

    @Override
    protected void buildContent(HtmlDiv out) {
        if (switchers != null)
            for (SwitcherModel<?> switcher : switchers.models)
                build(out, switcher);
    }

    public <K> void build(HtmlDiv out, SwitcherModel<K> switcher) {
        String param = switcher.getParam();

        HtmlDiv div = out.div().class_("zu-switcher");
        div.b().text(switcher.getLabel() + ": ");

        HtmlUl ul = div.ul();

        if (switcher.isOptional()) {
            IHtmlOut tag = ul.li();
            if (switcher.isEnabled())
                tag = tag.a().href("?all-" + param + "=1");
            tag.text("全部");
        }

        for (Entry<K, ?> pair : switcher.getPairs()) {
            IHtmlOut tag = ul.li();
            K key = pair.getKey();
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
