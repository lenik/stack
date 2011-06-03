package com.bee32.sem.chance.web;

import com.bee32.plover.orm.ext.util.BasicEntityController;
import com.bee32.plover.orm.ext.util.DataTableDxo;
import com.bee32.sem.chance.dto.ChanceActionDto;
import com.bee32.sem.chance.entity.ChanceAction;

public class ChanceActionController
        extends BasicEntityController<ChanceAction, Long, ChanceActionDto> {

    @Override
    protected void fillDataRow(DataTableDxo tab, ChanceActionDto dto) {
    }

}
