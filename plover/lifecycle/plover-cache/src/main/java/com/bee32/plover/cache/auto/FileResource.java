package com.bee32.plover.cache.auto;

import java.io.File;
import java.util.List;

public abstract class FileResource
        extends CreateRuleResource<File> {

    private final File filepath;

    public FileResource(IMakeSchema cacheSchema, File file) {
        super(cacheSchema);
        if (file == null)
            throw new NullPointerException("file");
        this.filepath = file;
    }

    // IResource

    @Override
    public long getVersion() {
        return filepath.lastModified();
    }

    @Override
    protected File commit(File file) {
        if (file == null)
            cache.clearCache();
        else {
            cache.setCache(file);
            if (file != null)
                filepath.setLastModified(clock.tick());
        }
        return file;
    }

    @Override
    protected File create(List<?> resolvedDependencies)
            throws Exception {
        try {
            create(filepath, resolvedDependencies);
        } catch (Exception e) {
            throw e;
        }
        // the returned value will be ignored in cache-ref operations.
        return filepath;
    }

    protected abstract void create(File filepath, List<?> resolvedDependencies)
            throws Exception;

}
