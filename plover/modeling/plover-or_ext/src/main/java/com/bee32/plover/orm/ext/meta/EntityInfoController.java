package com.bee32.plover.orm.ext.meta;

import org.springframework.web.bind.annotation.RequestMapping;

import com.bee32.plover.orm.ext.PloverORMExtModule;
import com.bee32.plover.orm.web.basic.BasicEntityController;
import com.bee32.plover.orm.web.util.DataTableDxo;

@RequestMapping(EntityInfoController.PREFIX)
public class EntityInfoController
        extends BasicEntityController<EntityInfo, String, EntityInfoDto> {

    public static final String PREFIX = PloverORMExtModule.PREFIX + "meta/";

    @Override
    protected void fillDataRow(DataTableDxo tab, EntityInfoDto dto) {
    }

}
