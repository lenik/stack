package com.bee32.plover.orm.ext.basic;

import java.io.Serializable;

import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.orm.ext.util.DataTableDxo;
import com.bee32.plover.orm.ext.util.SearchModel;
import com.bee32.plover.orm.util.EntityDto;

public interface IEntityListing<E extends Entity<K>, K extends Serializable> {

    void loadEntry(E entity, EntityDto<E, K> dto);

    void fillDataRow(DataTableDxo tab, EntityDto<E, K> dto);

    void fillSearchModel(SearchModel model, TextMap request)
            throws ParseException;

}
