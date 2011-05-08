package com.bee32.sem.file.entity;

import java.io.IOException;

import javax.free.IFile;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import com.bee32.plover.orm.ext.dict.NameDict;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "stereo", length = 4)
@DiscriminatorValue("?")
public abstract class FileStore
        extends NameDict {

    private static final long serialVersionUID = 1L;

    public abstract IFile resolve(String path)
            throws IOException;

}
