package com.bee32.sem.file.dto;

import com.bee32.plover.orm.ext.dict.NameDictDto;
import com.bee32.sem.file.entity.FileStore;

public abstract class FileStoreDto<E extends FileStore>
        extends NameDictDto<E> {

    private static final long serialVersionUID = 1L;

    public FileStoreDto() {
        super();
    }

    public FileStoreDto(int selection) {
        super(selection);
    }

}
