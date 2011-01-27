package com.bee32.plover.model.stereo;

import com.bee32.plover.arch.Component;

public class StereoType
        extends Component {

    private final Class<? extends IStereoResolution> resolutionType;

    public StereoType(String name, Class<? extends IStereoResolution> resolutionType) {
        super(name);
        this.resolutionType = resolutionType;
    }

    public boolean isProperty() {
        return this == PROPERTY;
    }

    public boolean isCommand() {
        return this == COMMAND;
    }

    public boolean isLayout() {
        return this == LAYOUT;
    }

    public Class<? extends IStereoResolution> getResolutionType() {
        return resolutionType;
    }

    private static final int typeHash = StereoType.class.hashCode();

    @Override
    public int hashCode() {
        int hash = typeHash;
        if (name != null)
            hash += name.hashCode();
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        return this == obj;
    }

    public static final StereoType PROPERTY = new StereoType("Property", null);
    public static final StereoType COMMAND = new StereoType("Command", Command.class);
    public static final StereoType LAYOUT = new StereoType("Layout", Layout.class);

}
