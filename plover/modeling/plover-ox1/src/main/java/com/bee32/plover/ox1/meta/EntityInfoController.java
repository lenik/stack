package com.bee32.plover.ox1.meta;

import org.springframework.web.bind.annotation.RequestMapping;

import com.bee32.plover.orm.web.basic.BasicEntityController;
import com.bee32.plover.orm.web.util.DataTableDxo;
import com.bee32.plover.ox1.PloverOx1Module;

@RequestMapping(EntityInfoController.PREFIX)
public class EntityInfoController
        extends BasicEntityController<EntityInfo, String, EntityInfoDto> {

    public static final String PREFIX = PloverOx1Module.PREFIX + "meta/";

    @Override
    protected void fillDataRow(DataTableDxo tab, EntityInfoDto dto) {
    }

}
