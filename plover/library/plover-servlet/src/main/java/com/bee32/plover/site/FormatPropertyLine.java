package com.bee32.plover.site;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.free.ParseException;

public class FormatPropertyLine
        implements Serializable {

    private static final long serialVersionUID = 1L;

    // prefix KEY infix VAL suffix
    String _prefix;
    String key;
    String _infix;
    String value;
    String _suffix;

    static final Pattern LINE_FORMAT;
    static {
        LINE_FORMAT = Pattern.compile("^(\\s*)(\\w+)(\\s*=\\s*)(.*?)(\\s*(#.*)?)$");
    }

    public FormatPropertyLine(String key, String value) {
        if (key == null)
            throw new NullPointerException("key");
        this.key = key;
        this.value = value;
    }

    public FormatPropertyLine(String line)
            throws ParseException {
        parse(line);
    }

    public void parse(String line)
            throws ParseException {
        if (line == null)
            throw new NullPointerException("line");

        Matcher m = LINE_FORMAT.matcher(line);
        if (!m.matches())
            throw new ParseException("Invalid syntax: " + line);

        _prefix = m.group(1);
        key = m.group(2);
        _infix = m.group(3);
        value = m.group(4);
        _suffix = m.group(5);
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (_prefix != null)
            sb.append(_prefix);
        sb.append(key);
        if (_infix != null)
            sb.append(_infix);
        sb.append(value);
        if (_suffix != null)
            sb.append(_suffix);
        return sb.toString();
    }

}
