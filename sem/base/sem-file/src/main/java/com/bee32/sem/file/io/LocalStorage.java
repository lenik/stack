package com.bee32.sem.file.io;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import javax.free.IFile;
import javax.free.JavaioFile;
import javax.free.SystemProperties;

/**
 *
 *
 * <p lang="en">
 * Local Storage
 */
public class LocalStorage
        implements Serializable {

    private static final long serialVersionUID = 1L;

    File contextFile;
    IFile context;

    public LocalStorage(String homedir)
            throws IOException {
        if (homedir == null)
            throw new NullPointerException("homedir");

        contextFile = new File(homedir);
        if (!contextFile.exists()) {
            if (!contextFile.mkdirs())
                throw new IOException("Failed to create directory: " + homedir);
        }

        context = new JavaioFile(contextFile);
    }

    public IFile getContext() {
        return context;
    }

    public IFile resolveHash(String hash)
            throws IOException {
        if (hash == null)
            throw new NullPointerException("base");

        if (hash.length() < 32)
            throw new IllegalArgumentException("Illegal hash name: " + hash);

        StringBuffer mbase = new StringBuffer();

        // mbase.append(hash.substring(0, 3));
        // mbase.append(SystemProperties.getFileSeparator());
        // mbase.append(hash.substring(3, 6));
        // mbase.append(SystemProperties.getFileSeparator());
        // mbase.append(hash.substring(6));
        mbase.append(hash.substring(0, 2));
        mbase.append(SystemProperties.getFileSeparator());
        mbase.append(hash.substring(2));

        File file = new File(contextFile, mbase.toString());
        return new JavaioFile(file);
    }

    /**
     * The default file home is: $HOME/.bee32/files
     */
    public static final String DEFAULT_FILE_HOME;
    static {
        String userHome = SystemProperties.getUserHome();
        File fileHome = new File(userHome, ".bee32/files");
        DEFAULT_FILE_HOME = fileHome.getPath();
    }

    /**
     * The local file home directory is determined by following in order:
     * <ul>
     * <li>Environ: BEE32_FILE_HOME
     * <li>Java property: bee32.file.home
     * <li>The static constant #DEFAULT_FILE_HOME is used.
     * </ul>
     */

    public static final LocalStorage INSTANCE;
    static {
        String homedir = DEFAULT_FILE_HOME;

        String property = System.getProperty("bee32.file.home");
        if (property != null)
            homedir = property;

        String environ = System.getenv("BEE32_FILE_HOME");
        if (environ != null)
            homedir = environ;

        if (homedir == null)
            throw new IllegalStateException("Bee32 file home dir isn't specified in any way.");

        try {
            INSTANCE = new LocalStorage(homedir);
        } catch (IOException e) {
            throw new Error(e.getMessage(), e);
        }
    }

}
