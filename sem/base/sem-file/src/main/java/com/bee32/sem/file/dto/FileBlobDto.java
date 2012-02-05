package com.bee32.sem.file.dto;

import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.ox1.digest.DigestEntityDto;
import com.bee32.sem.file.entity.FileBlob;
import com.bee32.sem.file.util.ImageBlob;

public class FileBlobDto
        extends DigestEntityDto<FileBlob> {

    private static final long serialVersionUID = 1L;

    public static final int CONTENT = 1;

    public FileBlobDto() {
        super();
    }

    public FileBlobDto(int fmask) {
        super(fmask);
    }

    long length;
    byte[] header;

    ImageBlob thumbnail;
    ImageBlob preview;

    int refCount;

    @Override
    protected void _marshal(FileBlob source) {
        length = source.getLength();
        header = source.getHeader();
        thumbnail = source.getThumbnail();
        preview = source.getPreview();
        refCount = source.getRefCount();
    }

    @Override
    protected void _unmarshalTo(FileBlob target) {
        if (selection.contains(CONTENT)) {
            // target.setFile(???)
        }
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
        if (selection.contains(CONTENT)) {
            // length = map.getLong("length");
        }
    }

    public long getLength() {
        return length;
    }

    public void setLength(long length) {
        this.length = length;
    }

    public byte[] getHeader() {
        return header;
    }

    public void setHeader(byte[] header) {
        if (header == null)
            throw new NullPointerException("header");
        this.header = header;
    }

    public ImageBlob getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(ImageBlob thumbnail) {
        this.thumbnail = thumbnail;
    }

    public ImageBlob getPreview() {
        return preview;
    }

    public void setPreview(ImageBlob preview) {
        this.preview = preview;
    }

    public int getRefCount() {
        return refCount;
    }

    public void setRefCount(int refCount) {
        this.refCount = refCount;
    }

}
