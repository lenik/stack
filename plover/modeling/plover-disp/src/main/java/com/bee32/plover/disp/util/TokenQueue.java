package com.bee32.plover.disp.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TokenQueue
        implements ITokenQueue {

    private final String[] tokens;
    private int index;

    public TokenQueue(String[] tokens) {
        if (tokens == null)
            throw new NullPointerException("tokens");
        this.tokens = tokens;
        this.index = 0;
    }

    public TokenQueue(String path) {
        if (path == null)
            throw new NullPointerException("path");

        if (path.isEmpty()) {
            this.tokens = new String[0];
            return;
        }

        boolean hasTrailingSlash = path.charAt(path.length() - 1) == '/';

        List<String> tokens = new ArrayList<String>(20);
        int start = 0;
        int slash;
        while ((slash = path.indexOf('/', start)) != -1) {
            if (slash > start) { // skip empty tokens like '//'
                String token = path.substring(start, slash);
                tokens.add(token);
            }
            start = slash + 1;
        }

        // start = 0 or (lastSlash + 1)
        if (path.length() > start) {
            String token = path.substring(start);
            tokens.add(token);
        }

        if (hasTrailingSlash)
            tokens.add(INDEX);

        this.tokens = tokens.toArray(new String[0]);
    }

    @Override
    public int available() {
        return tokens.length - index;
    }

    @Override
    public String getRemainingPath() {
        int remaining = tokens.length - index;
        StringBuilder buf = new StringBuilder(remaining * 20);
        for (int i = index; i < tokens.length; i++) {
            if (i != index)
                buf.append('/');
            buf.append(tokens[i]);
        }
        return buf.toString();
    }

    @Override
    public boolean isEmpty() {
        return index >= tokens.length;
    }

    @Override
    public void skip(int n) {
        int index = this.index + n;
        if (index > tokens.length)
            throw new IllegalArgumentException("Skip to underflow: " + n);
        this.index = index;
    }

    @Override
    public String[] shift(int n) {
        if (index + n > tokens.length)
            return null;
        String[] copy = Arrays.copyOfRange(tokens, index, index + n);
        index += n;
        return copy;
    }

    @Override
    public String shift() {
        if (index >= tokens.length)
            return null;
        return tokens[index++];
    }

    @Override
    public Integer shiftInt() {
        Integer n = peekInt();
        if (n != null)
            index++;
        return n;
    }

    @Override
    public Long shiftLong() {
        Long n = peekLong();
        if (n != null)
            index++;
        return n;
    }

    @Override
    public String peek() {
        if (index >= tokens.length)
            return null;
        return tokens[index];
    }

    @Override
    public String peek(int offset) {
        int index = this.index + offset;
        if (index >= tokens.length)
            return null;
        return tokens[index];
    }

    @Override
    public Integer peekInt() {
        return peekInt(0);
    }

    @Override
    public Integer peekInt(int offset) {
        int index = this.index + offset;
        if (index >= tokens.length)
            return null;
        if (!isNumber(tokens[index]))
            return null;
        return Integer.valueOf(tokens[index]);
    }

    @Override
    public Long peekLong() {
        return peekLong(0);
    }

    @Override
    public Long peekLong(int offset) {
        int index = this.index + offset;
        if (index >= tokens.length)
            return null;
        if (!isNumber(tokens[index]))
            return null;
        long n = Long.parseLong(tokens[index]);
        return n;
    }

    static boolean isNumber(String str) {
        int i = str.length();
        while (--i >= 0) {
            char c = str.charAt(i);
            if (c < '0' || c > '9')
                return false;
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        for (int i = 0; i < index; i++) {
            if (i != 0)
                buf.append('/');
            buf.append(tokens[i]);
        }

        // "a/b <---> /c"
        buf.append(" <---> ");

        for (int i = index; i < tokens.length; i++) {
            buf.append('/');
            buf.append(tokens[i]);
        }
        return buf.toString();
    }

}
