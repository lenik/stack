package com.bee32.sem.mail;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import com.bee32.plover.arch.util.EnumAlt;
import com.bee32.plover.arch.util.NoSuchEnumException;

public class MailType
        extends EnumAlt<Integer, MailType> {

    private static final long serialVersionUID = 1L;

    static final Map<String, MailType> nameMap = getNameMap(MailType.class);
    static final Map<Integer, MailType> valueMap = getValueMap(MailType.class);

    MailType baseType;

    public MailType(int value, String name) {
        super(value, name);
    }

    public MailType(int value, String name, MailType baseType) {
        super(baseType == null ? value : baseType.value + value, name);
        this.baseType = baseType;
    }

    public static Collection<MailType> values() {
        Collection<MailType> values = valueMap.values();
        return Collections.unmodifiableCollection(values);
    }

    public static MailType forName(String altName) {
        MailType mailType = nameMap.get(altName);
        if (mailType == null)
            throw new NoSuchEnumException(MailType.class, altName);
        return mailType;
    }

    public static MailType valueOf(int value) {
        MailType mailType = valueMap.get(value);
        if (mailType == null)
            throw new NoSuchEnumException(MailType.class, value);

        return mailType;
    }

    public static final MailType __system__ /**/= new MailType(0x00000, "__system__");
    public static final MailType BCAST /*     */= new MailType(1, "bcast", __system__);
    public static final MailType NOTICE /*    */= new MailType(2, "notice", __system__);

    public static final MailType __issue__ /* */= new MailType(0x10000, "__issue__");
    public static final MailType ISSUE /*     */= new MailType(1, "issue", __issue__);

    public static final MailType __convertion__ = new MailType(0x20000, "__converted__");
    public static final MailType EMAIL /*     */= new MailType(1, "email", __convertion__);
    public static final MailType FAX /*       */= new MailType(2, "fax", __convertion__);

    public static final MailType __social__ /**/= new MailType(0x30000, "__social__");
    public static final MailType USER /*      */= new MailType(1, "user", __social__);
    public static final MailType QUESTION /*  */= new MailType(2, "question", __social__);
    public static final MailType POST /*      */= new MailType(3, "post", __social__);
    public static final MailType COMMENT/*    */= new MailType(4, "comment", __social__);

}
