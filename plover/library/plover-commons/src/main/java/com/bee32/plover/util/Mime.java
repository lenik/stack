package com.bee32.plover.util;

import java.io.Serializable;

public class Mime
        implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String name;
    private final String preferredExtension;

    public Mime(String name, String preferredExtension) {
        if (name == null)
            throw new NullPointerException("name");
        if (preferredExtension == null)
            throw new NullPointerException("preferredExtension");
        this.name = name;
        this.preferredExtension = preferredExtension;
    }

    public String getName() {
        return name;
    }

    /**
     * The preferred extension name.
     *
     * @return The extension name, without the dot(.).
     */
    public String getPreferredExtension() {
        return preferredExtension;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((preferredExtension == null) ? 0 : preferredExtension.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Mime other = (Mime) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (preferredExtension == null) {
            if (other.preferredExtension != null)
                return false;
        } else if (!preferredExtension.equals(other.preferredExtension))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return name;
    }

    public static Mime getInstance(String contentType) {
        return Mimes.contentTypeMap.get(contentType);
    }

    public static Mime getInstanceByExtension(String extension) {
        return Mimes.extensionMap.get(extension);
    }

}
