package com.bee32.ape.engine.identity;

public class PropertiesStringBuilder {

    StringBuilder sb = new StringBuilder();

    public void put(String key, Object value) {
        if (value == null)
            return;
        if (sb.length() != 0)
            sb.append(", ");
        sb.append(key + " = " + value);
    }

    @Override
    public String toString() {
        return sb.toString();
    }

}
