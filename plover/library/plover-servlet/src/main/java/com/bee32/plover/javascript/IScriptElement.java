package com.bee32.plover.javascript;

import java.io.IOException;

import javax.free.IIndentedOut;

public interface IScriptElement
        extends IDependent<IScriptElement> {

    void format(IIndentedOut out)
            throws IOException;

}
