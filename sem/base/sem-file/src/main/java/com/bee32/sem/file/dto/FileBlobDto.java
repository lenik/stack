package com.bee32.sem.file.dto;

import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.orm.ext.digest.DigestEntityDto;
import com.bee32.sem.file.blob.FileBlob;
import com.bee32.sem.file.util.ImageBlob;

public class FileBlobDto
        extends DigestEntityDto<FileBlob> {

    private static final long serialVersionUID = 1L;

    public static final int CONTENT = 1;

    public FileBlobDto() {
        super();
    }

    public FileBlobDto(FileBlob source) {
        super(source);
    }

    public FileBlobDto(int selection) {
        super(selection);
    }

    public FileBlobDto(int selection, FileBlob source) {
        super(selection, source);
    }

    FileStoreDto<?> store;

    String origPath;

    long length;
    byte[] header;

    ImageBlob smallImage;
    ImageBlob mediumImage;

    @Override
    protected void _marshal(FileBlob source) {
        origPath = source.getOrigPath();
        length = source.getLength();
        header = source.getHeader();
        smallImage = source.getSmallImage();
        mediumImage = source.getMediumImage();
    }

    @Override
    protected void _unmarshalTo(FileBlob target) {
        target.setOrigPath(origPath);

        if (selection.contains(CONTENT)) {
            // target.setFile(???)
        }
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
        origPath = map.getString("origPath");

        if (selection.contains(CONTENT)) {
            // length = map.getLong("length");
        }
    }

    public FileStoreDto<?> getStore() {
        return store;
    }

    public void setStore(FileStoreDto<?> store) {
        this.store = store;
    }

    public String getOrigPath() {
        return origPath;
    }

    public void setOrigPath(String origPath) {
        this.origPath = origPath;
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
        this.header = header;
    }

    public ImageBlob getSmallImage() {
        return smallImage;
    }

    public void setSmallImage(ImageBlob smallImage) {
        this.smallImage = smallImage;
    }

    public ImageBlob getMediumImage() {
        return mediumImage;
    }

    public void setMediumImage(ImageBlob mediumImage) {
        this.mediumImage = mediumImage;
    }

}
