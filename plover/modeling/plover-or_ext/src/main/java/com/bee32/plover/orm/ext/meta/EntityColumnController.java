package com.bee32.plover.orm.ext.meta;

import org.springframework.web.bind.annotation.RequestMapping;

import com.bee32.plover.orm.ext.PloverORMExtModule;
import com.bee32.plover.orm.web.basic.BasicEntityController;
import com.bee32.plover.orm.web.util.DataTableDxo;

@RequestMapping(EntityColumnController.PREFIX)
public class EntityColumnController
        extends BasicEntityController<EntityColumn, Integer, EntityColumnDto> {

    public static final String PREFIX = PloverORMExtModule.PREFIX + "column/";

    @Override
    protected void fillDataRow(DataTableDxo tab, EntityColumnDto dto) {
    }

}
