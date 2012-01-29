package com.bee32.sem.chance.web;

import java.util.List;

import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.plover.orm.util.DataViewBean;
import com.bee32.plover.orm.util.EntityDto;
import com.bee32.plover.web.faces.utils.SelectableList;
import com.bee32.sem.chance.dto.ChanceActionStyleDto;
import com.bee32.sem.chance.dto.ChanceCategoryDto;
import com.bee32.sem.chance.dto.ChanceSourceTypeDto;
import com.bee32.sem.chance.dto.ChanceStageDto;
import com.bee32.sem.chance.entity.ChanceActionStyle;
import com.bee32.sem.chance.entity.ChanceCategory;
import com.bee32.sem.chance.entity.ChanceSourceType;
import com.bee32.sem.chance.entity.ChanceStage;

public class ChanceDictsBean
        extends DataViewBean {

    private static final long serialVersionUID = 1L;

    SelectableList<ChanceCategoryDto> categories;
    SelectableList<ChanceSourceTypeDto> sourceTypes;
    SelectableList<ChanceActionStyleDto> actionStyles;
    SelectableList<ChanceStageDto> stages;

    <E extends Entity<?>, D extends EntityDto<E, ?>> //
    SelectableList<D> mrefList(Class<E> entityClass, Class<D> dtoClass, int fmask, ICriteriaElement... criteriaElements) {
        List<E> entities = asFor(entityClass).list(criteriaElements);
        List<D> dtos = DTOs.mrefList(dtoClass, entities);
        SelectableList<D> list = SelectableList.decorate(dtos);
        return list;
    }

    public SelectableList<ChanceCategoryDto> getCategories() {
        if (categories == null) {
            synchronized (this) {
                if (categories == null) {
                    categories = mrefList(ChanceCategory.class, ChanceCategoryDto.class, 0);
                }
            }
        }
        return categories;
    }

    public SelectableList<ChanceSourceTypeDto> getSourceTypes() {
        if (sourceTypes == null) {
            synchronized (this) {
                if (sourceTypes == null) {
                    sourceTypes = mrefList(ChanceSourceType.class, ChanceSourceTypeDto.class, 0);
                }
            }
        }
        return sourceTypes;
    }

    public SelectableList<ChanceActionStyleDto> getActionStyles() {
        if (actionStyles == null) {
            synchronized (this) {
                if (actionStyles == null) {
                    actionStyles = mrefList(ChanceActionStyle.class, ChanceActionStyleDto.class, 0);
                }
            }
        }
        return actionStyles;
    }

    public SelectableList<ChanceStageDto> getStages() {
        if (stages == null) {
            synchronized (this) {
                if (stages == null) {
                    stages = mrefList(ChanceStage.class, ChanceStageDto.class, 0);
                }
            }
        }
        return stages;
    }

}
