package com.bee32.sem.chance.web;

import java.util.List;

import com.bee32.plover.faces.utils.SelectableList;
import com.bee32.plover.orm.util.DataViewBean;
import com.bee32.sem.chance.dto.*;
import com.bee32.sem.chance.entity.*;

public class ChanceDictsBean
        extends DataViewBean {

    private static final long serialVersionUID = 1L;

    List<ChanceCategoryDto> categories;
    List<ChanceSourceTypeDto> sourceTypes;
    List<ChancePriorityDto> priorities;
    List<ChanceActionStyleDto> actionStyles;
    List<ChanceStageDto> stages;
    List<ProcurementMethodDto> methods;
    List<PurchaseRegulationDto> regulations;

    public SelectableList<ChanceCategoryDto> getCategories() {
        if (categories == null) {
            synchronized (this) {
                if (categories == null) {
                    categories = mrefList(ChanceCategory.class, ChanceCategoryDto.class, 0);
                }
            }
        }
        return SelectableList.decorate(categories);
    }

    public SelectableList<ChanceSourceTypeDto> getSourceTypes() {
        if (sourceTypes == null) {
            synchronized (this) {
                if (sourceTypes == null) {
                    sourceTypes = mrefList(ChanceSourceType.class, ChanceSourceTypeDto.class, 0);
                }
            }
        }
        return SelectableList.decorate(sourceTypes);
    }

    public SelectableList<ChancePriorityDto> getPriorities() {
        if (priorities == null) {
            synchronized (this) {
                if (priorities == null) {
                    priorities = mrefList(ChancePriority.class, ChancePriorityDto.class, 0);
                }
            }
        }
        return SelectableList.decorate(priorities);
    }

    public SelectableList<ChanceActionStyleDto> getActionStyles() {
        if (actionStyles == null) {
            synchronized (this) {
                if (actionStyles == null) {
                    actionStyles = mrefList(ChanceActionStyle.class, ChanceActionStyleDto.class, 0);
                }
            }
        }
        return SelectableList.decorate(actionStyles);
    }

    public SelectableList<ChanceStageDto> getStages() {
        if (stages == null) {
            synchronized (this) {
                if (stages == null) {
                    stages = mrefList(ChanceStage.class, ChanceStageDto.class, 0);
                }
            }
        }
        return SelectableList.decorate(stages);
    }

    public SelectableList<ProcurementMethodDto> getMethods() {
        if (methods == null) {
            synchronized (this) {
                if (methods == null) {
                    methods = mrefList(ProcurementMethod.class, ProcurementMethodDto.class, 0);
                }
            }
        }
        return SelectableList.decorate(methods);
    }

    public SelectableList<PurchaseRegulationDto> getRegulations() {
        if (regulations == null) {
            synchronized (this) {
                if (regulations == null) {
                    regulations = mrefList(PurchaseRegulation.class, PurchaseRegulationDto.class, 0);
                }
            }
        }
        return SelectableList.decorate(regulations);
    }
}
