package com.bee32.plover.view;

import java.io.IOException;

import javax.free.IPrintOut;
import javax.servlet.ServletResponse;

import com.bee32.plover.util.Mime;

public interface IDisplay {

    /**
     * Get the preferred content type.
     *
     * @return The preferred content type, <code>null</code> if unknown.
     */
    Mime getPreferredContentType();

    /**
     * Primarily, you need this.
     * <p>
     * The print out is usually a wrapper to the response output stream.
     *
     * @return Non-<code>null</code> print out.
     */
    IPrintOut getOut()
            throws IOException;

    /**
     * This is used for SWT construction.
     *
     * @return Non-<code>null</code> composite object when used in SWT application, or
     *         <code>null</code>.
     */
    Object getParentComposite();

    /**
     * If you wanna do redirection, go with this.
     */
    ServletResponse getResponse();

}
