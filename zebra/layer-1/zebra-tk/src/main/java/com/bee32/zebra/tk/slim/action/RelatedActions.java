package com.bee32.zebra.tk.slim.action;

import java.util.ArrayList;
import java.util.List;

import net.bodz.bas.http.ctx.IAnchor;
import net.bodz.bas.ui.model.action.AbstractActionProvider;
import net.bodz.bas.ui.model.action.IAction;
import net.bodz.mda.xjdoc.Xjdocs;
import net.bodz.mda.xjdoc.model.ClassDoc;

import com.bee32.zebra.tk.site.IZebraSiteAnchors;

public class RelatedActions
        extends AbstractActionProvider
        implements IZebraSiteAnchors {

    @Override
    public List<IAction> getActions(Object o) {
        List<IAction> list = new ArrayList<IAction>();

        ClassDoc classDoc = Xjdocs.getDefaultProvider().getClassDoc(o.getClass());
        List<String> rels = classDoc.getTag("rel", List.class);

        if (rels != null && !rels.isEmpty()) {
            for (String rel : rels) {
                int colon = rel.indexOf(':');
                IAnchor href = _webApp_.join(rel.substring(0, colon).trim());
                String text = rel.substring(colon + 1).trim();
            }
        }
        return list;
    }

}