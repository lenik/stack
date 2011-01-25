package com.bee32.plover.repr.tree;

import java.util.Iterator;
import java.util.Map.Entry;

public interface IReprTree {

    Iterator<Entry<String, ?>> entries();

    TreeIterator<?> walk();

    Object get(String path);

}
