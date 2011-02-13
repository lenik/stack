package com.bee32.plover.repr.cascade;

public class ExampleStyle
        implements Overridable<ExampleStyle> {

    @Override
    public ExampleStyle override() {
        return null;
    }

    public String getFontName() {
        return "Tahoma";
    }

}
