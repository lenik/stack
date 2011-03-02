package com.bee32.plover.disp;

import java.util.Arrays;
import java.util.Date;

import javax.free.StringArray;

public class DispatchContext
        implements IDispatchContext {

    private final IDispatchContext parent;

    private static final String[] emptyStringArray = new String[0];
    private String[] consumedTokens = emptyStringArray;

    private Object reachedObject;

    private Date expires;

    public DispatchContext(Object startObject) {
        this.parent = null;
        this.reachedObject = startObject;
    }

    public DispatchContext(IDispatchContext parent) {
        if (parent == null)
            throw new NullPointerException("parent");
        this.parent = parent;
        this.expires = parent.getExpires();
    }

    public DispatchContext(IDispatchContext parent, Object reachedObject, String... consumedTokens) {
        this(parent);

        if (reachedObject == null)
            throw new NullPointerException("reachedObject");
        if (consumedTokens == null)
            throw new NullPointerException("consumedTokens");

        this.reachedObject = reachedObject;
        this.consumedTokens = consumedTokens;
    }

    @Override
    public IDispatchContext getParent() {
        return parent;
    }

    @Override
    public Object getObject() {
        return reachedObject;
    }

    public void setObject(Object object) {
        this.reachedObject = object;
    }

    @Override
    public Date getExpires() {
        return expires;
    }

    public void expires(Date afterDate) {
        if (this.expires == null)
            this.expires = afterDate;
        else {
            // Get the min date
            int cmp = this.expires.compareTo(afterDate);
            if (cmp > 0)
                this.expires = afterDate;
        }
    }

    @Override
    public String[] getConsumedTokens() {
        return consumedTokens;
    }

    public void setConsumedTokens(String[] consumedTokens) {
        this.consumedTokens = consumedTokens;
    }

    @Override
    public String getConsumedPath() {
        return StringArray.join("/", consumedTokens);
    }

    @Override
    public String toString() {
        return getConsumedPath() + " -> " + reachedObject;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Arrays.hashCode(consumedTokens);
        result = prime * result + ((expires == null) ? 0 : expires.hashCode());
        result = prime * result + ((parent == null) ? 0 : parent.hashCode());
        result = prime * result + ((reachedObject == null) ? 0 : reachedObject.hashCode());
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
        DispatchContext other = (DispatchContext) obj;
        if (!Arrays.equals(consumedTokens, other.consumedTokens))
            return false;
        if (expires == null) {
            if (other.expires != null)
                return false;
        } else if (!expires.equals(other.expires))
            return false;
        if (parent == null) {
            if (other.parent != null)
                return false;
        } else if (!parent.equals(other.parent))
            return false;
        if (reachedObject == null) {
            if (other.reachedObject != null)
                return false;
        } else if (!reachedObject.equals(other.reachedObject))
            return false;
        return true;
    }

}
