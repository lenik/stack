package com.bee32.plover.orm.web;

import java.io.Serializable;

import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.orm.util.EntityDto;
import com.bee32.plover.orm.web.util.DataTableDxo;
import com.bee32.plover.orm.web.util.SearchModel;

public interface IEntityListing<E extends Entity<K>, K extends Serializable> {

    void loadEntry(E entity, EntityDto<E, K> dto);

    void fillDataRow(DataTableDxo tab, EntityDto<E, K> dto);

    void fillSearchModel(SearchModel model, TextMap request)
            throws ParseException;

}
