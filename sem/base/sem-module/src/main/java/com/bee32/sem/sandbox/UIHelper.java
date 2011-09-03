package com.bee32.sem.sandbox;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import com.bee32.plover.arch.util.EnumAlt;
import com.bee32.plover.orm.util.FacesContextSupport2;
import com.bee32.plover.ox1.c.CEntity;
import com.bee32.plover.ox1.c.CEntityDto;
import com.bee32.plover.ox1.dict.NameDictDto;

public class UIHelper
        extends FacesContextSupport2 {

    public static List<SelectItem> selectItemsFromDict(Iterable<? extends NameDictDto<?>> dictEntries) {
        List<SelectItem> items = new ArrayList<SelectItem>();

        for (NameDictDto<?> entry : dictEntries) {
            SelectItem item = new SelectItem(entry.getDisplayId(), entry.getLabel());
            items.add(item);
        }

        return items;
    }

    public static List<SelectItem> selectItemsFromDict2(Iterable<? extends NameDictDto<?>> dictEntries) {
        List<SelectItem> items = new ArrayList<SelectItem>();

        for (NameDictDto<?> entry : dictEntries) {
            SelectItem item = new SelectItem(entry.getDisplayId(), entry.getLabel() + " (" + entry.getName() + ")");
            items.add(item);
        }

        return items;
    }

    public static List<SelectItem> selectItemsFromEnum(Iterable<? extends EnumAlt<?, ?>> enums) {
        List<SelectItem> items = new ArrayList<SelectItem>();

        for (EnumAlt<?, ?> entry : enums) {
            Object value = entry.getValue();
            String label = entry.getLabel();
            SelectItem item = new SelectItem(value, label);
            items.add(item);
        }

        return items;
    }

    public static <E extends CEntity<?>, D extends CEntityDto<E, ?>> //
    ZLazyDataModel<E, D> buildLazyDataModel(EntityDataModelOptions<E, D> options) {
        return new ZLazyDataModel<E, D>(options);
    }

    public static <T> ListHolder<T> selectable(List<T> list) {
        if (list == null)
            throw new NullPointerException("list");
        return new ListHolder<T>(list);
    }

}
