package com.bee32.plover.util;

import java.io.Serializable;

public class Mime
        implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String contentType;
    private final String preferredExtension;

    public Mime(String contentType, String preferredExtension) {
        if (contentType == null)
            throw new NullPointerException("contentType");
        if (preferredExtension == null)
            throw new NullPointerException("preferredExtension");
        this.contentType = contentType;
        this.preferredExtension = preferredExtension;
    }

    public String getContentType() {
        return contentType;
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
        result = prime * result + ((contentType == null) ? 0 : contentType.hashCode());
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

        if (contentType == null) {
            if (other.contentType != null)
                return false;
        } else if (!contentType.equals(other.contentType))
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
        return contentType;
    }

    public static Mime getInstance(String contentType) {
        return Mimes.contentTypeMap.get(contentType);
    }

    public static Mime getInstanceByExtension(String extension) {
        if (extension == null)
            return null;
        extension = extension.toLowerCase();
        return Mimes.extensionMap.get(extension);
    }

}
