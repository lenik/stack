package com.bee32.plover.repr.view.pgrid;

public interface LayoutVisitor {

    void beginDiv(String path, Object obj);

    void endDiv(String path, Object obj);

}
