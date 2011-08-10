package com.bee32.plover.orm.builtin;

import javax.inject.Inject;

import org.springframework.transaction.annotation.Transactional;

import com.bee32.plover.arch.DataService;
import com.bee32.plover.orm.util.DTOs;

@Transactional(readOnly = true)
public class PloverConfManager
        extends DataService
        implements IPloverConfManager {

    @Inject
    PloverConfDao dao;

    public PloverConfDto getConf(String key) {
        PloverConf conf = dao.get(key);
        return DTOs.marshal(PloverConfDto.class, conf);
    }

    public String getConfValue(String key) {
        PloverConf conf = dao.get(key);
        if (conf == null)
            return null;
        else
            return conf.getValue();
    }

    public String getConfDescription(String key) {
        PloverConf conf = dao.get(key);
        if (conf == null)
            return null;
        else
            return conf.getDescription();
    }

    @Transactional
    public void setConf(String key, String value) {
        PloverConf conf = dao.get(key);
        if (conf == null)
            conf = new PloverConf(key, value);
        else
            conf.setValue(value);
        dao.saveOrUpdate(conf);
    }

    @Transactional
    public void setConf(String key, String value, String description) {
        PloverConf conf = dao.get(key);
        if (conf == null)
            conf = new PloverConf(key, value);
        else
            conf.setValue(value);
        conf.setDescription(description);
        dao.saveOrUpdate(conf);
    }

    @Transactional
    public void removeConf(String key) {
        dao.deleteByKey(key);
    }

}
