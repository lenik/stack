package com.bee32.sem.file.entity;

import java.io.File;
import java.io.IOException;

import javax.free.IFile;
import javax.free.JavaioFile;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Transient;

import com.bee32.sem.file.util.VariableType;


@Entity
@DiscriminatorValue("LO")
public class LocalFileStore
        extends FileStore {

    private static final long serialVersionUID = 1L;

    VariableType prefixType;

    String path;

    @Enumerated(EnumType.STRING)
    public VariableType getPrefixType() {
        return prefixType;
    }

    public void setPrefixType(VariableType nameType) {
        this.prefixType = nameType;
    }

    @Basic(optional = false)
    @Column(length = 30, nullable = false)
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    /**
     * The same as {@link #getName()}.
     *
     * @throws IOException
     *             If failed to create the home dir.
     */
    @Transient
    public File getOrCreateHome()
            throws IOException {
        String varName = getName();
        if (varName == null)
            throw new IllegalStateException("The (var-)name of LocalFileStore wasn't set");

        String path = getPath();
        if (path == null)
            throw new NullPointerException("Path wasn't set");

        String prefixPath = getPrefixType().expand(varName);
        File prefixFile = new File(prefixPath);

        File home = prefixFile;
        if (!path.isEmpty())
            home = new File(prefixFile, path);
        else
            home = prefixFile;

        if (!home.exists()) {
            if (!home.mkdirs())
                throw new IOException("Failed to create home dir or its parents");
        }
        return home;
    }

    @Override
    public IFile resolve(String path)
            throws IOException {
        File file = new File(getOrCreateHome(), path);
        return new JavaioFile(file);
    }

}
