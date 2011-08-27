package com.bee32.plover.ox1.meta;

import org.springframework.web.bind.annotation.RequestMapping;

import com.bee32.plover.orm.web.basic.BasicEntityController;
import com.bee32.plover.orm.web.util.DataTableDxo;
import com.bee32.plover.ox1.PloverOx1Module;

@RequestMapping(EntityColumnController.PREFIX)
public class EntityColumnController
        extends BasicEntityController<EntityColumn, Integer, EntityColumnDto> {

    public static final String PREFIX = PloverOx1Module.PREFIX + "column/";

    @Override
    protected void fillDataRow(DataTableDxo tab, EntityColumnDto dto) {
    }

}
