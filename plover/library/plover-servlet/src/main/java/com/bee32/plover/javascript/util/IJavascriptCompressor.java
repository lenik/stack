package com.bee32.plover.javascript.util;

import javax.free.ParseException;

public interface IJavascriptCompressor {

    /**
     * Remove unnecessary space like spacebar, carriage-return, tab character, etc.
     */
    int REMOVE_SPACE = 1;

    /**
     * Rename variable names, function names, class names, to a short form.
     */
    int RENAME_SYMNAMES = 2;

    /**
     * Obfuscate the control flow.
     */
    int OBFUSCATE = 4;

    /**
     * Compress a given javascript, with the specified options.
     * <p>
     * The options are optional, any of options can be ignored if it is not supported.
     *
     * @param options
     *            Bitwise OR-ed options.
     * @return The compressed form.
     * @throws NullPointerException
     *             If <code>javascript</code> is <code>null</code>.
     * @throws ParseException
     *             If illegal code occurred in the javascript, which blocks the compressor from
     *             parsing the script.
     */
    String compress(String javascript, int options)
            throws ParseException;

}
