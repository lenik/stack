package com.bee32.plover.util;

import java.io.Serializable;

import javax.free.ParseException;

public abstract class StringBacked<T>
        implements Serializable {

    private static final long serialVersionUID = 1L;

    private boolean defSet;
    protected String def;

    private transient boolean formSet;
    protected transient T form;

    public StringBacked(String def) {
        this.def = def;
        this.defSet = true;
    }

    public StringBacked(T form) {
        this.form = form;
        this.formSet = true;
    }

    public synchronized String getDef() {
        if (!defSet) {
            def = toDef(form);
            defSet = true;
        }
        return def;
    }

    public synchronized void setDef(String def) {
        if (this.def != def) {
            this.def = def;
            this.defSet = true;
            this.formSet = false;
        }
    }

    public synchronized T getForm()
            throws ParseException {
        if (!formSet) {
            form = toForm(def);
            formSet = true;
        }
        return form;
    }

    public void setForm(T form) {
        if (this.form != form) {
            this.form = form;
            this.formSet = true;
            this.defSet = false;
        }
    }

    protected abstract T toForm(String def)
            throws ParseException;

    protected abstract String toDef(T form);

    @Override
    public String toString() {
        return getDef();
    }

}
