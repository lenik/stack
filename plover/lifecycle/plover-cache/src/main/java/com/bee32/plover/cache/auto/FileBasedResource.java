package com.bee32.plover.cache.auto;

import java.io.File;
import java.io.IOException;

import javax.free.CreateException;

/**
 * File based resource constructs itself from a single specific file on the fly.
 */
@Deprecated
public abstract class FileBasedResource<T>
        extends CreateRuleResource<T> {

    protected final File file;

    public FileBasedResource(IMakeSchema cacheSchema, File file) {
        super(cacheSchema);

        if (!cacheSchema.getClock().isSystemClock())
            throw new IllegalArgumentException("Require system clock to work with.");

        if (file == null)
            throw new NullPointerException("file");
        this.file = file;
    }

    @Override
    public long getVersion() {
        return file.lastModified();
    }

    @Override
    public T commit(T obj) {
        try {
            dump(file, obj);
            return super.commit(obj);
        } catch (IOException e) {
            throw new RuntimeException(e); // XXX
        }
    }

    protected abstract T load(File in)
            throws IOException, CreateException;

    protected abstract void dump(File file, T obj)
            throws IOException;

}
