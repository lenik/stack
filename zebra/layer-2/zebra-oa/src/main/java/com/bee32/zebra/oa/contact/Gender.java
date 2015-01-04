package com.bee32.zebra.oa.contact;

import net.bodz.bas.t.predef.Predef;
import net.bodz.bas.t.predef.PredefMetadata;
import net.bodz.mda.xjdoc.model.javadoc.IXjdocAware;

public class Gender
        extends Predef<Gender, Character> {

    private static final long serialVersionUID = 1L;

    public static final PredefMetadata<Gender, Character> METADATA = PredefMetadata.forClass(Gender.class);

    private Gender(char val, String name) {
        super(val, name, METADATA);
    }

    /**
     * 未知
     */
    public static final Gender UNKNOWN = new Gender('-', "UNKNOWN");

    /**
     * 男
     */
    public static final Gender MALE = new Gender('m', "MALE");

    /**
     * 女
     */
    public static final Gender FEMALE = new Gender('f', "FEMALE");

    /**
     * 其它
     */
    public static final Gender OTHER = new Gender('x', "OTHER");

    static {
        IXjdocAware.fn.injectFields(Gender.class, false);
    }

}
