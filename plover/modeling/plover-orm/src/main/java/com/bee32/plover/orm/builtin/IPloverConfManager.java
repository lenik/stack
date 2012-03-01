package com.bee32.plover.orm.builtin;

import java.util.Map;

public interface IPloverConfManager {

    PloverConfDto getConf(String section, String key);

    String get(String section, String key);

    void set(String section, String key, String value);

    void set(String section, String key, String value, String description);

    void remove(String section, String key);

    Map<String, PloverConfDto> getSection(String section);

    void removeSection(String section);

}
