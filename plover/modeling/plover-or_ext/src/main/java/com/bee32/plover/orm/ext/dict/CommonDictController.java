package com.bee32.plover.orm.ext.dict;

import java.io.Serializable;

import org.springframework.web.bind.annotation.RequestMapping;

import com.bee32.plover.orm.ext.PloverORMExtModule;
import com.bee32.plover.orm.web.basic.GenericEntityController;
import com.bee32.plover.orm.web.util.DataTableDxo;

@RequestMapping(CommonDictController.PREFIX + "*")
public class CommonDictController<E extends DictEntity<K>, K extends Serializable, Dto extends DictEntityDto<E, K>>
        extends GenericEntityController<E, K, Dto> {

    public static final String PREFIX = PloverORMExtModule.PREFIX + "1/";

    @Override
    protected void fillDataRow(DataTableDxo tab, Dto dto) {
        // XXX tab.push(dto.getName());
        tab.push(dto.getLabel());
        tab.push(dto.getDescription());
        tab.next();
    }

}
