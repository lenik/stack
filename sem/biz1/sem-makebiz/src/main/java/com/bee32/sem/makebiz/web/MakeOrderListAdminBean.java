package com.bee32.sem.makebiz.web;

import javax.faces.context.FacesContext;

import com.bee32.plover.criteria.hibernate.Equals;
import com.bee32.plover.orm.annotation.ForEntity;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.plover.ox1.util.CommonCriteria;
import com.bee32.sem.chance.dto.ChanceDto;
import com.bee32.sem.chance.entity.Chance;
import com.bee32.sem.makebiz.dto.MakeOrderDto;
import com.bee32.sem.makebiz.entity.MakeOrder;
import com.bee32.sem.misc.SimpleEntityViewBean;

@ForEntity(MakeOrder.class)
public class MakeOrderListAdminBean
        extends SimpleEntityViewBean {

    private static final long serialVersionUID = 1L;

    public MakeOrderListAdminBean() {
        super(MakeOrder.class, MakeOrderDto.class, 0);
    }

    public void init() {
        String chanceIdStr = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("chanceId");
        if(chanceIdStr == null) {
            clearSearchFragments();
        } else {
            Chance chance = ctx.data.getOrFail(Chance.class, Long.parseLong(chanceIdStr));
            searchChance = DTOs.marshal(ChanceDto.class, chance);
            addChanceRestriction();
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
}
