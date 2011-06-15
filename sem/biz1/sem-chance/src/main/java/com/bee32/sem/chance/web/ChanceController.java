package com.bee32.sem.chance.web;

import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.orm.web.basic.BasicEntityController;
import com.bee32.plover.orm.web.util.DataTableDxo;
import com.bee32.plover.orm.web.util.SearchModel;
import com.bee32.sem.chance.dto.ChanceDto;
import com.bee32.sem.chance.entity.Chance;

public class ChanceController
        extends BasicEntityController<Chance, Long, ChanceDto> {

    @Override
    protected void fillSearchModel(SearchModel model, TextMap request)
            throws ParseException {
        super.fillSearchModel(model, request);
    }

    @Override
    protected void fillDataRow(DataTableDxo tab, ChanceDto dto) {
    }

}
