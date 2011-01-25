package com.bee32.plover.repr.tree;

public interface TreeProvider {

    IReprTree build(Class<?> clazz, Object obj)
            throws BuildException;

}
