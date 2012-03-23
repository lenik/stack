package com.bee32.sem.make.web;

import java.util.List;

import com.bee32.plover.faces.utils.SelectableList;
import com.bee32.plover.orm.util.DataViewBean;
import com.bee32.sem.make.dto.MakeStepNameDto;
import com.bee32.sem.make.entity.MakeStepName;

public class BomDictsBean
        extends DataViewBean {

    private static final long serialVersionUID = 1L;

    List<MakeStepNameDto> stepNames;


    public SelectableList<MakeStepNameDto> getStepNames() {
        if (stepNames == null) {
            synchronized (this) {
                if (stepNames == null) {
                    stepNames = mrefList(MakeStepName.class, MakeStepNameDto.class, 0);
                }
            }
        }
        return SelectableList.decorate(stepNames);
    }

}
