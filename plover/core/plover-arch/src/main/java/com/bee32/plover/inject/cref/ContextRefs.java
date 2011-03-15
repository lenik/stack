package com.bee32.plover.inject.cref;

public class ContextRefs {

    public static final ContextRef STD = new StdScanContext();
    public static final ContextRef STD_TEST = new StdScanTestContext();
    public static final ContextRef TEMPLATE = new TemplateContext(STD);
    public static final ContextRef TEMPLATE_TEST = new TemplateTestContext(STD_TEST);

}
