package com.bee32.zebra.tk.slim;

import net.bodz.bas.c.reflect.NoSuchPropertyException;
import net.bodz.bas.err.ParseException;
import net.bodz.bas.repr.form.IFormDecl;
import net.bodz.bas.repr.form.PathField;
import net.bodz.bas.repr.form.PathFieldList;
import net.bodz.bas.repr.form.PathFieldMap;

public class SlimPathFieldMap
        extends PathFieldMap {

    private static final long serialVersionUID = 1L;

    protected final IFormDecl formDecl;

    public SlimPathFieldMap(IFormDecl formDecl) {
        if (formDecl == null)
            throw new NullPointerException("formDecl");
        this.formDecl = formDecl;
    }

    public void parse(String spec, String... pathProperties)
            throws NoSuchPropertyException, ParseException {
        PathFieldList pathFieldList = new PathFieldList();

        for (char c : spec.toCharArray()) {
            switch (c) {
            case 'n': // TODO
                pathFieldList.parseAndAdd(formDecl, "index");
                break;
            case 'i':
                pathFieldList.parseAndAdd(formDecl, "id");
                break;
            case 's':
                pathFieldList.parseAndAdd(formDecl, "priority", "creationDate", "lastModifiedDate", "flags", "state");
                break;
            case 'a':
                pathFieldList.parseAndAdd(formDecl, "accessMode", "ownerUser", "ownerGroup");
                break;
            case '*':
                pathFieldList.parseAndAdd(formDecl, pathProperties);
                break;
            default:
                throw new IllegalArgumentException("Bad column group specifier: " + c);
            }
        }

        for (PathField pathField : pathFieldList)
            put(pathField.getPath(), pathField);
    }

}
