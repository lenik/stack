package com.bee32.sem.people.web;

import com.bee32.plover.orm.ext.util.BasicEntityController;
import com.bee32.plover.orm.ext.util.DataTableDxo;
import com.bee32.sem.people.dto.PartyDto;
import com.bee32.sem.people.entity.Party;

public class PartyController
        extends BasicEntityController<Party, Long, PartyDto> {

    @Override
    protected void fillDataRow(DataTableDxo tab, PartyDto dto) {
    }

}
