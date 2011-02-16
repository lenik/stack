package com.bee32.icsf.principal;

import javax.free.EncodeException;
import javax.free.XMLs;

public class PrincipalToXML {

    public static void main(String[] args)
            throws EncodeException {
        SimpleGroup group1 = new SimpleGroup("MyGroup", null);
        String xml = XMLs.encode(group1);
        System.out.println(xml);
    }

}
