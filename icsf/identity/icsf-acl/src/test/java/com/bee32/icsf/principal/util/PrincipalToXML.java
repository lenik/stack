package com.bee32.icsf.principal.util;

import javax.free.EncodeException;
import javax.free.XMLs;

import com.bee32.icsf.principal.Group;

public class PrincipalToXML {

    public static void main(String[] args)
            throws EncodeException {
        Group group1 = new Group("MyGroup");
        String xml = XMLs.encode(group1);
        System.out.println(xml);
    }

}
