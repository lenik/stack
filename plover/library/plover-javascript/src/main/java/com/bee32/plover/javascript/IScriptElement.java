package com.bee32.plover.javascript;

import java.io.IOException;

import javax.free.IIndentedOut;
import javax.servlet.http.HttpServletRequest;

public interface IScriptElement
        extends IDependent<IScriptElement> {

    void format(HttpServletRequest req, IIndentedOut out)
            throws IOException;

}
