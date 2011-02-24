package com.bee32.plover.arch.ui.annotated;

public @interface Image {

    String qualifier();

    int widthHint() default 0;

    int heightHint() default 0;

    /**
     * Using xxx://... for absolute URL, otherwise, the url is class-related.
     */
    String url();

}
