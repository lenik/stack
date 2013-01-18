package com.bee32.sem.makebiz.web;

import com.bee32.plover.criteria.hibernate.Equals;
import com.bee32.plover.orm.annotation.ForEntity;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.plover.ox1.util.CommonCriteria;
import com.bee32.sem.chance.entity.Chance;
import com.bee32.sem.makebiz.dto.MakeStepDto;
import com.bee32.sem.makebiz.dto.MakeStepItemDto;
import com.bee32.sem.makebiz.entity.MakeStep;
import com.bee32.sem.makebiz.entity.MakeStepItem;
import com.bee32.sem.misc.SimpleEntityViewBean;
import org.apache.commons.lang.StringUtils;

import javax.annotation.PostConstruct;

@ForEntity(Chance.class)
public class MakeStepItemListAdminBean
        extends SimpleEntityViewBean {

    private static final long serialVersionUID = 1L;


    public MakeStepItemListAdminBean() {
        super(MakeStepItem.class, MakeStepItemDto.class, 0);
    }

    @PostConstruct
    public void init() {
        String stepId = ctx.view.getRequest().getParameter("stepId");
        if(StringUtils.isNotBlank(stepId)) {
            MakeStep step = ctx.data.getOrFail(MakeStep.class, Long.parseLong(stepId));
            searchStep = DTOs.marshal(MakeStepDto.class, step);
            addStepRestriction();
        }
    }


    /*************************************************************************
     * Section: MBeans
     *************************************************************************/



    /*************************************************************************
     * Section: Persistence
     *************************************************************************/


    /*************************************************************************
     * Section: Search
     *************************************************************************/
    @Override
    public void addNameOrLabelRestriction() {
        setSearchFragment("name", "名称含有 " + searchPattern,
                CommonCriteria.labelledWith(searchPattern, true));
        searchPattern = null;
    }

    MakeStepDto searchStep;

    public MakeStepDto getSearchStep() {
        return searchStep;
    }

    public void setSearchStep(MakeStepDto searchStep) {
        this.searchStep = searchStep;
    }

    public void addStepRestriction() {
        if (searchStep != null) {
            setSearchFragment("step", "工艺步骤为 " + searchStep.getLabel(), //
                    new Equals("parent.id", searchStep.getId()));
            searchStep = null;
        }
    }
}
