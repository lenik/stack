package com.bee32.plover.disp.util;

import java.util.Arrays;
import java.util.Date;

import javax.free.StringArray;

public class Arrival
        implements IArrival {

    private final IArrival parent;

    private String[] consumedTokens = new String[0];

    private Object target;

    private Date expires;

    public Arrival(Object startTarget) {
        this.parent = null;
        this.target = startTarget;
    }

    public Arrival(IArrival parent, Object target, String... consumedTokens) {
        if (consumedTokens == null)
            throw new NullPointerException("consumedTokens");
        this.parent = parent;
        this.target = target;
        this.consumedTokens = consumedTokens;
    }

    @Override
    public IArrival getParent() {
        return parent;
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
    public boolean backtrace(ArrivalBacktraceCallback callback) {
        ReversedPathTokens consumedRpt = new ReversedPathTokens();

        IArrival node = this;
        while (node != null) {

            String[] tokens = node.getConsumedTokens();
            for (int i = tokens.length - 1; i >= 0; i--)
                consumedRpt.add(tokens[i]);

            if (callback.arriveBack(node, consumedRpt))
                return true;

            node = node.getParent();
        }

        return false;
    }

    @Override
    public Object getTarget() {
        return target;
    }

    public void setObject(Object object) {
        this.target = object;
    }

    @Override
    public Object getLastNonNullTarget() {
        if (target != null)
            return target;

        if (parent == null)
            return null;

        return parent.getLastNonNullTarget();
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
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Arrays.hashCode(consumedTokens);
        result = prime * result + ((expires == null) ? 0 : expires.hashCode());
        result = prime * result + ((parent == null) ? 0 : parent.hashCode());
        result = prime * result + ((target == null) ? 0 : target.hashCode());
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

        Arrival other = (Arrival) obj;

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

        if (target == null) {
            if (other.target != null)
                return false;
        } else if (!target.equals(other.target))
            return false;

        return true;
    }

    @Override
    public String toString() {
        return getConsumedPath() + " -> " + target;
    }

}
