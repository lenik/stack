package com.bee32.sem.salary.web;

import java.util.List;

import com.bee32.plover.faces.utils.SelectableList;
import com.bee32.plover.orm.util.DataViewBean;
import com.bee32.sem.salary.dto.BaseBonusDto;
import com.bee32.sem.salary.entity.BaseBonus;

public class SalaryDictsBean
        extends DataViewBean {

    private static final long serialVersionUID = 1L;

    List<BaseBonusDto> bonuses;

    public SelectableList<BaseBonusDto> getBonuses() {
        if (bonuses == null) {
            synchronized (this) {
                if (bonuses == null) {
                    bonuses = mrefList(BaseBonus.class, BaseBonusDto.class, 0);
                }
            }
        }
        return SelectableList.decorate(bonuses);
    }

}
