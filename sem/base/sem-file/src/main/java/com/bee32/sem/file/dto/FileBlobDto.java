package com.bee32.sem.file.dto;

import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.orm.ext.dict.DigestNameDictDto;
import com.bee32.plover.orm.util.IUnmarshalContext;
import com.bee32.sem.file.entity.FileBlob;

public class FileBlobDto
        extends DigestNameDictDto<FileBlob> {

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

    @Override
    protected void _marshal(FileBlob source) {
    }

    @Override
    protected void _unmarshalTo(IUnmarshalContext context, FileBlob target) {
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
    }

}
