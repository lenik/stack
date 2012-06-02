package com.bee32.plover.restful.resource;

import java.io.IOException;
import java.io.InputStream;

import javax.free.FileResolveException;
import javax.free.IFile;
import javax.free.VFS;

public class FjvfsResource
        extends AbstractResource {

    // private final String path;
    private final IFile file;

    public FjvfsResource(String path)
            throws FileResolveException {
        if (path == null)
            throw new NullPointerException("path");
        // this.path = path;
        this.file = VFS.resolveFile(path);
    }

    @Override
    public InputStream openBinary()
            throws IOException {
        return file.toSource().newInputStream();
    }

    @Override
    public String getPath() {
        String path = file.getPath().toString();

        if (path != null)
            return file.getPath().toString();
        return null;
    }

}
