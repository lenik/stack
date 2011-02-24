package com.bee32.plover.arch.ui.annotated;

public @interface Refdoc {

    String qualifier();

    String title();

    /**
     * Using xxx://... for absolute URL, otherwise, the url is class-related.
     */
    String url();

    String[] tags() default {};

}
