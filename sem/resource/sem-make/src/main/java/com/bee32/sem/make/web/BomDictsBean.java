package com.bee32.sem.make.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.model.SelectItem;

import com.bee32.plover.faces.utils.SelectableList;
import com.bee32.plover.orm.util.DataViewBean;
import com.bee32.sem.make.dto.MakeStepNameDto;
import com.bee32.sem.make.entity.MakeStepName;
import com.bee32.sem.misc.LazyDTOMap;

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



    /**
     * TODO - This is a temporary solution for the absence of fr:select.
     */
    Map<Integer, MakeStepNameDto> stepNameMap = new LazyDTOMap<Integer, MakeStepNameDto>(MakeStepName.class,
            MakeStepNameDto.class, 0);

    public MakeStepNameDto getStepName(int id) {
        MakeStepNameDto stepName = stepNameMap.get(id);
        if (stepName == null)
            stepName = new MakeStepNameDto().ref();
        return stepName;
    }

    public List<SelectItem> getStepNameSelectItems() {
        List<SelectItem> selectItems = new ArrayList<SelectItem>();
        for (MakeStepNameDto stepName : getStepNames()) {
            Integer id = stepName.getId();
            String name = stepName.getLabel();
            selectItems.add(new SelectItem(id, name));
        }
        return selectItems;
    }

}
