package com.bee32.plover.html;

import java.util.ArrayList;

public class FormSection
        extends ArrayList<IFormChild> {

    private static final long serialVersionUID = 1L;

    String title;

    public FormSection(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
