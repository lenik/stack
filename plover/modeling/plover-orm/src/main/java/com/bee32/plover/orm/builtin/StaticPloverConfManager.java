package com.bee32.plover.orm.builtin;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

public class StaticPloverConfManager
        implements IPloverConfManager {

    final Map<String, String> configs;

    public StaticPloverConfManager() {
        configs = new HashMap<String, String>();
    }

    @Override
    public PloverConfDto getConf(String section, String key) {
        String value = configs.get(key(section, key));
        if (value == null)
            return null;
        PloverConfDto conf = new PloverConfDto();
        conf.setSection(section);
        conf.setKey(key);
        conf.setValue(value);
        return conf;
    }

    @Override
    public String get(String section, String key) {
        return configs.get(key(section, key));
    }

    public void set(String section, String key, String value) {
        configs.put(key(section, key), value);
    }

    @Override
    public void set(String section, String key, String value, String description) {
        configs.put(key(section, key), value);
    }

    @Override
    public void remove(String section, String key) {
        configs.remove(key(section, key));
    }

    @Override
    public Map<String, PloverConfDto> getSection(String section) {
        String prefix = section + "::";
        int nprefix = prefix.length();
        Map<String, PloverConfDto> map = new TreeMap<>();
        for (Entry<String, String> entry : configs.entrySet()) {
            if (!entry.getKey().startsWith(prefix))
                continue;
            String key = entry.getKey().substring(nprefix);
            String value = entry.getValue();
            PloverConfDto conf = new PloverConfDto();
            conf.setSection(section);
            conf.setKey(key);
            conf.setValue(value);
            map.put(key, conf);
        }
        return map;
    }

    @Override
    public void removeSection(String section) {
        String prefix = section + "::";
        Iterator<Entry<String, String>> iterator = configs.entrySet().iterator();
        while (iterator.hasNext()) {
            Entry<String, String> entry = iterator.next();
            if (entry.getKey().startsWith(prefix)) {
                iterator.remove();
            }
        }
    }

    static String key(String section, String key) {
        return section + "::" + key;
    }

    static final StaticPloverConfManager instance = new StaticPloverConfManager();

    public static StaticPloverConfManager getInstance() {
        return instance;
    }

}
