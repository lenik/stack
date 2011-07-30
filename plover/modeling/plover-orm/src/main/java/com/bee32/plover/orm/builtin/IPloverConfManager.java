package com.bee32.plover.orm.builtin;

public interface IPloverConfManager {

    PloverConfDto getConf(String key);

    String getConfValue(String key);

    String getConfDescription(String key);

    void setConf(String key, String value);

    void setConf(String key, String value, String description);

    void removeConf(String key);

}
