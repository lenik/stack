package com.bee32.icsf.principal;

import javax.free.EncodeException;
import javax.free.XMLs;

public class PrincipalToXML {

    public static void main(String[] args)
            throws EncodeException {
        Group group1 = new Group("MyGroup");
        String xml = XMLs.encode(group1);
        System.out.println(xml);
    }

}
