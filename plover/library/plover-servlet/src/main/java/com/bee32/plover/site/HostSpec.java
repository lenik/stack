package com.bee32.plover.site;

import java.io.Serializable;

import javax.free.Nullables;

public final class HostSpec
        implements Serializable {

    private static final long serialVersionUID = 1L;

    // static final int DEFAULT_PORT = 80;

    private String hostName;
    private Integer port;

    public HostSpec(String hostName, Integer port) {
        this.hostName = hostName;
        this.port = port;
    }

    public static HostSpec parse(String spec) {
        int colon = spec.indexOf(':');
        String hostName;
        Integer port = null;
        if (colon == -1) {
            hostName = spec;
            port = null; // DEFAULT_PORT;
        } else {
            hostName = spec.substring(0, colon);
            String _port = spec.substring(colon + 1);
            if (_port.equals("*"))
                port = null;
            else
                port = Integer.parseInt(_port);
        }
        return new HostSpec(hostName, port);
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public boolean isWild() {
        return port == null;
    }

    @Override
    public String toString() {
        if (port == null)
            return hostName + ":*";
        return hostName + ":" + port;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((hostName == null) ? 0 : hostName.hashCode());
        result = prime * result + ((port == null) ? 0 : port.hashCode());
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
        HostSpec other = (HostSpec) obj;
        if (!Nullables.equals(hostName, other.hostName))
            return false;
        if (!Nullables.equals(port, other.port))
            return false;
        return true;
    }

    public static String getHostName(String hostSpec) {
        return HostSpec.parse(hostSpec).hostName;
    }

}
