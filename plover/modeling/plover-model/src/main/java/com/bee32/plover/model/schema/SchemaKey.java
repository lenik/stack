package com.bee32.plover.model.schema;

import java.io.Serializable;

import com.bee32.plover.model.stereo.StereoType;

public class SchemaKey
        implements Serializable {

    private static final long serialVersionUID = 1L;

    private final StereoType stereoType;
    private final String name;

    public SchemaKey(StereoType stereoType, String name) {
        if (stereoType == null)
            throw new NullPointerException("stereoType");
        if (name == null)
            throw new NullPointerException("name");
        this.stereoType = stereoType;
        this.name = name;
    }

    public StereoType getStereoType() {
        return stereoType;
    }

    public String getName() {
        return name;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + stereoType.hashCode();
        result = prime * result + name.hashCode();
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
        SchemaKey other = (SchemaKey) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (stereoType == null) {
            if (other.stereoType != null)
                return false;
        } else if (!stereoType.equals(other.stereoType))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return stereoType + " " + name;
    }

}
