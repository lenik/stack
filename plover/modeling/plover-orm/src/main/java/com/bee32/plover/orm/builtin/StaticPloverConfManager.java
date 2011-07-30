package com.bee32.plover.orm.builtin;

import java.util.HashMap;
import java.util.Map;

public class StaticPloverConfManager
        implements IPloverConfManager {

    final Map<String, String> configs;

    public StaticPloverConfManager() {
        configs = new HashMap<String, String>();
    }

    @Override
    public PloverConfDto getConf(String key) {
        String value = configs.get(key);
        PloverConfDto conf = new PloverConfDto();
        conf.setId(key);
        conf.setValue(value);
        return conf;
    }

    @Override
    public String getConfValue(String key) {
        return configs.get(key);
    }

    @Override
    public String getConfDescription(String key) {
        return null;
    }

    @Override
    public void setConf(String key, String value) {
        configs.put(key, value);
    }

    @Override
    public void setConf(String key, String value, String description) {
        configs.put(key, value);
    }

    @Override
    public void removeConf(String key) {
        configs.remove(key);
    }

    static final StaticPloverConfManager instance = new StaticPloverConfManager();

    public static StaticPloverConfManager getInstance() {
        return instance;
    }

}
