package com.bee32.plover.zk.test;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Textbox;

public class MyText
        extends GenericForwardComposer {

    private static final long serialVersionUID = 1L;

    private TextSource textSource;

    private Textbox text1;

    @Override
    public void doAfterCompose(Component comp)
            throws Exception {
        super.doAfterCompose(comp);

        String src = String.valueOf(textSource);
        text1.setValue(src);
    }

}
