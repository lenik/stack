package com.bee32.zebra.tk.slim.cmd;

import java.util.ArrayList;
import java.util.List;

import net.bodz.bas.http.ctx.IAnchor;
import net.bodz.bas.ui.model.cmd.AbstractCommandProvider;
import net.bodz.bas.ui.model.cmd.ICommand;
import net.bodz.mda.xjdoc.Xjdocs;
import net.bodz.mda.xjdoc.model.ClassDoc;

import com.bee32.zebra.tk.site.IZebraSiteAnchors;

public class RelatedCommandsProvider
        extends AbstractCommandProvider
        implements IZebraSiteAnchors {

    @Override
    public List<ICommand> getCommands(Object o) {
        List<ICommand> list = new ArrayList<ICommand>();

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