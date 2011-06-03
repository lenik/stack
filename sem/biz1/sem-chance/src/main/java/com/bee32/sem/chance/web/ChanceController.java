package com.bee32.sem.chance.web;

import com.bee32.plover.orm.ext.util.BasicEntityController;
import com.bee32.plover.orm.ext.util.DataTableDxo;
import com.bee32.sem.chance.dto.ChanceDto;
import com.bee32.sem.chance.entity.Chance;

public class ChanceController
        extends BasicEntityController<Chance, Long, ChanceDto> {

    @Override
    protected void fillDataRow(DataTableDxo tab, ChanceDto dto) {
    }

}
