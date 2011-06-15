package com.bee32.sem.sandbox;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import com.bee32.plover.orm.ext.dict.DictEntityDto;

public class UIHelper {

    public static List<SelectItem> selectItemsFromDict(Iterable<? extends DictEntityDto<?, ?>> dictEntries) {
        List<SelectItem> items = new ArrayList<SelectItem>();

        for (DictEntityDto<?, ?> entry : dictEntries) {
            SelectItem item = new SelectItem(entry.getId(), entry.getLabel());
            items.add(item);
        }

        return items;
    }

}
