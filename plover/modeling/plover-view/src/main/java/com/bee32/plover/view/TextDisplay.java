package com.bee32.plover.view;

import java.io.IOException;

import javax.free.BCharOut;
import javax.free.IPrintOut;

public class TextDisplay
        extends AbstractDisplay {

    private final IPrintOut out;

    public TextDisplay() {
        out = new BCharOut();
    }

    public TextDisplay(IPrintOut out) {
        if (out == null)
            throw new NullPointerException("out");
        this.out = out;
    }

    @Override
    public IPrintOut getOut()
            throws IOException {
        return out;
    }

    public String getContents() {
        return out.toString();
    }

    @Override
    public String toString() {
        // Get the abbreviated form?
        return out.toString();
    }

}
