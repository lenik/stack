package com.bee32.plover.ox1.dict;

import java.io.Serializable;

import org.springframework.web.bind.annotation.RequestMapping;

import com.bee32.plover.orm.web.basic.BasicEntityController;
import com.bee32.plover.orm.web.util.DataTableDxo;
import com.bee32.plover.ox1.PloverOx1Module;

@RequestMapping(CommonDictController.PREFIX + "/**")
public class CommonDictController<E extends DictEntity<K>, K extends Serializable, Dto extends DictEntityDto<E, K>>
        extends BasicEntityController<E, K, Dto> {

    public static final String PREFIX = PloverOx1Module.PREFIX + "/1";
    public static final String PREFIX_ = PREFIX + "/";

    @Override
    protected void fillDataRow(DataTableDxo tab, Dto dto) {
        tab.push(dto.getLabel());
        tab.push(dto.getDescription());
    }

}
