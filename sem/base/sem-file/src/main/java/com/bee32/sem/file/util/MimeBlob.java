package com.bee32.sem.file.util;

import java.io.Serializable;

public class MimeBlob
        implements Serializable {

    private static final long serialVersionUID = 1L;

    final String contentType;
    final String encoding;
    final byte[] data;
    final int offset;
    final int length;
    final int end;

    byte[] _data;

    public MimeBlob(String contentType, byte[] data) {
        this(contentType, "utf-8", data, 0, data.length);
    }

    public MimeBlob(String contentType, String encoding, byte[] data, int offset, int length) {
        if (contentType == null)
            throw new NullPointerException("contentType");
        if (data == null)
            throw new NullPointerException("data");

        end = offset + length;
        if (offset < 0 || offset > data.length)
            throw new IndexOutOfBoundsException("start " + offset);
        if (end < 0 || end > data.length)
            throw new IndexOutOfBoundsException("end " + end);

        this.contentType = contentType;
        this.encoding = encoding;
        this.data = data;
        this.offset = offset;
        this.length = length;
    }

    public String getContentType() {
        return contentType;
    }

    public String getEncoding() {
        return encoding;
    }

    public byte[] getRawData() {
        return data;
    }

    public int getRawOffset() {
        return offset;
    }

    public int getLength() {
        return length;
    }

    public synchronized byte[] getData() {
        if (_data == null) {
            if (offset == 0 && length == data.length)
                _data = data;
            else {
                _data = new byte[length];
                System.arraycopy(data, offset, _data, 0, length);
            }
        }
        return _data;
    }

}
