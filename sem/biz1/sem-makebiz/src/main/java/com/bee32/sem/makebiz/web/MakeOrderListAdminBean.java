package com.bee32.sem.makebiz.web;

import java.util.Date;

import javax.faces.context.FacesContext;

import com.bee32.plover.criteria.hibernate.Equals;
import com.bee32.plover.orm.annotation.ForEntity;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.plover.ox1.util.CommonCriteria;
import com.bee32.sem.chance.dto.ChanceDto;
import com.bee32.sem.chance.entity.Chance;
import com.bee32.sem.makebiz.dto.MakeOrderDto;
import com.bee32.sem.makebiz.entity.MakeOrder;
import com.bee32.sem.makebiz.util.MakebizCriteria;
import com.bee32.sem.misc.SimpleEntityViewBean;

@ForEntity(MakeOrder.class)
public class MakeOrderListAdminBean
        extends SimpleEntityViewBean {

    private static final long serialVersionUID = 1L;

    public MakeOrderListAdminBean() {
        super(MakeOrder.class, MakeOrderDto.class, 0);
    }

    public void init() {
        FacesContext context = FacesContext.getCurrentInstance();
        if (!context.isPostback()) {
            String chanceIdStr = context.getExternalContext().getRequestParameterMap().get("chanceId");
            if (chanceIdStr == null) {
                clearSearchFragments();
            } else {
                Chance chance = ctx.data.getOrFail(Chance.class, Long.parseLong(chanceIdStr));
                searchChance = DTOs.marshal(ChanceDto.class, chance);
                addChanceRestriction();
            }
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
        setSearchFragment("name", "名称含有 " + searchPattern, CommonCriteria.labelledWith(searchPattern, true));
        searchPattern = null;
    }

    ChanceDto searchChance;

    public ChanceDto getSearchChance() {
        return searchChance;
    }

    public void setSearchChance(ChanceDto searchChance) {
        this.searchChance = searchChance;
    }

    public void addChanceRestriction() {
        if (searchChance != null) {
            setSearchFragment("chance", "机会为 " + searchChance.getSubject(), //
                    new Equals("chance.id", searchChance.getId()));
            searchChance = null;
        }
    }

    Date start;
    Date end;

    public void addCreatedDateRangeRestriction() {
        if (start == null) {
            uiLogger.warn("请选择开始时间！");
            return;
        }
        if (end == null) {
            uiLogger.warn("请选择结束时间！");
            return;
        }
        setSearchFragment("create-date", "创建时间范围", MakebizCriteria.dateRangeRestriction("createdDate", start, end));

    }

    public void addModefiedDateRangeRestriction() {
        if (start == null) {
            uiLogger.warn("请选择开始时间！");
            return;
        }
        if (end == null) {
            uiLogger.warn("请选择结束时间！");
            return;
        }
        setSearchFragment("modify-date", "修改时间范围", MakebizCriteria.dateRangeRestriction("lastModified", start, end));
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

}
