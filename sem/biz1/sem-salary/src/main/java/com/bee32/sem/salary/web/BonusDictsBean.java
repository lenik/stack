package com.bee32.sem.salary.web;

import java.util.List;

import javax.faces.model.SelectItem;

import com.bee32.plover.faces.utils.SelectableList;
import com.bee32.plover.orm.util.DataViewBean;
import com.bee32.sem.salary.dto.BaseBonusDto;
import com.bee32.sem.salary.entity.BaseBonus;

public class BonusDictsBean
        extends DataViewBean {

    private static final long serialVersionUID = 1L;

    List<BaseBonusDto> bonuses;
    List<SelectItem> allBonus;

    public BonusDictsBean() {
        bonuses = mrefList(BaseBonus.class, BaseBonusDto.class, 0);
    }

    public List<SelectItem> getAllBonus() {
        if (allBonus == null) {
            synchronized (this) {
                if (allBonus == null) {
                    for (BaseBonusDto bonus : bonuses) {
                    }
                }
            }
        }
        return SelectableList.decorate(allBonus);
    }
}
