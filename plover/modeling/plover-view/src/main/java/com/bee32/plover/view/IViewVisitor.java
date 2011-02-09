package com.bee32.plover.view;

public interface IViewVisitor {

    void visitDiv(Div div);

    void visitSpan(Span span);

    void visitGrid(Grid grid, boolean end);

    void visitSwirl(Swirl swirl, boolean end);

    void visitFreeform(Freeform freeform, boolean end);

}
