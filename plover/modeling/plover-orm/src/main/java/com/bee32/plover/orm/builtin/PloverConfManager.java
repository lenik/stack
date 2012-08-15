package com.bee32.plover.orm.builtin;

import static com.bee32.plover.orm.builtin.PloverConfCriteria.selector;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.transaction.annotation.Transactional;

import com.bee32.plover.arch.DataService;
import com.bee32.plover.criteria.hibernate.Equals;
import com.bee32.plover.orm.util.DTOs;

@Transactional(readOnly = true)
public class PloverConfManager
        extends DataService
        implements IPloverConfManager {

    @Override
    public PloverConfDto getConf(String section, String key) {
        PloverConf conf = DATA(PloverConf.class).getUnique(selector(section, key));
        PloverConfDto dto = DTOs.marshal(PloverConfDto.class, conf);
        return dto;
    }

    @Override
    public String get(String section, String key) {
        PloverConf conf = DATA(PloverConf.class).getUnique(selector(section, key));
        if (conf == null)
            return null;
        else
            return conf.getValue();
    }

    @Transactional
    @Override
    public void set(String section, String key, String value) {
        set(section, key, value, null);
    }

    @Transactional
    @Override
    public void set(String section, String key, String value, String description) {
        PloverConf entry = DATA(PloverConf.class).getUnique(selector(section, key));
        if (entry == null) {
            entry = new PloverConf(section, key, value, description);
        } else {
            entry.setValue(value);
            if (description != null)
                entry.setDescription(description);
        }
        DATA(PloverConf.class).saveOrUpdate(entry);
    }

    @Transactional
    @Override
    public void remove(String section, String key) {
        DATA(PloverConf.class).findAndDelete(selector(section, key));
    }

    @Override
    public Map<String, PloverConfDto> getSection(String section) {
        if (section == null)
            throw new NullPointerException("section");
        List<PloverConf> list = DATA(PloverConf.class).list(new Equals("section", section));
        List<PloverConfDto> dtos = DTOs.marshalList(PloverConfDto.class, list);
        Map<String, PloverConfDto> map = new TreeMap<String, PloverConfDto>();
        for (PloverConfDto dto : dtos)
            map.put(dto.getKey(), dto);
        return map;
    }

    @Transactional
    @Override
    public void removeSection(String section) {
        if (section == null)
            throw new NullPointerException("section");
        DATA(PloverConf.class).findAndDelete(selector(section));
    }

}
