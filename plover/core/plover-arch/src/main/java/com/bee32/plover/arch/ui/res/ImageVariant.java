package com.bee32.plover.arch.ui.res;

import java.io.Serializable;

public class ImageVariant
        implements Comparable<ImageVariant>, Serializable {

    private static final long serialVersionUID = 1L;

    private static final int DEFAULT_HINT = 10000;

    private final String variantName;
    private final int widthHint;
    private final int heightHint;

    public ImageVariant(String variantName, int widthHint, int heightHint) {
        if (widthHint == 0)
            widthHint = DEFAULT_HINT;
        if (heightHint == 0)
            heightHint = DEFAULT_HINT;
        if (variantName == null)
            throw new NullPointerException("variantName");
        this.variantName = variantName;
        this.widthHint = widthHint;
        this.heightHint = heightHint;
    }

    @Override
    public int compareTo(ImageVariant o) {
        int cmp = variantName.compareTo(o.variantName);
        if (cmp != 0)
            return cmp;
        if ((cmp = o.widthHint - widthHint) != 0)
            return cmp;
        if ((cmp = o.heightHint - heightHint) != 0)
            return cmp;
        return 0;
    }

    public boolean implies(ImageVariant o) {
        if (o.variantName.startsWith(variantName)) {
            String oSuffix = o.variantName.substring(variantName.length());
            if (!oSuffix.isEmpty()) {
                char joinChar = oSuffix.charAt(0);
                // is punctuation?
                if (Character.isLetterOrDigit(joinChar))
                    return false;
            }
            // assert o.variantName.equals(variantName);
            if (widthHint >= o.widthHint || heightHint >= o.heightHint)
                return true;
        }
        return false;
    }

    public ImageVariant getParent() {
        // if (widthHint != 0 || heightHint != 0)
        // return new VariantKey(variantName, 0, 0);
        int minor = variantName.lastIndexOf('-');
        if (minor == -1)
            minor = variantName.lastIndexOf('.');
        if (minor != -1) {
            String minorName = variantName.substring(0, minor);
            return new ImageVariant(minorName, widthHint, heightHint);
        }
        return null;
    }

    @Override
    public int hashCode() {
        int hash = variantName.hashCode();
        hash ^= widthHint * 0x833bfde9;
        hash ^= heightHint * 0x64f08afb;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof ImageVariant))
            return false;
        ImageVariant o = (ImageVariant) obj;
        return variantName.equals(o.variantName) //
                && widthHint == o.widthHint //
                && heightHint == o.heightHint;
    }

    @Override
    public String toString() {
        return variantName + "-" + widthHint + "x" + heightHint;
    }

}
