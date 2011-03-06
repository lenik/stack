package com.bee32.plover.inject.util;

import java.util.ArrayList;
import java.util.List;

public interface NQCLiterals {

    NameQualifiedClassPreorder preorder = NameQualifiedClassPreorder.getInstance();

    NameQualifiedClass fooList = new NameQualifiedClass(List.class, "foo");
    NameQualifiedClass barList = new NameQualifiedClass(List.class, "bar");
    NameQualifiedClass foobarList = new NameQualifiedClass(List.class, "foo.bar");

    NameQualifiedClass fooArrayList = new NameQualifiedClass(ArrayList.class, "foo");
    NameQualifiedClass foobarArrayList = new NameQualifiedClass(ArrayList.class, "foo.bar");

}
