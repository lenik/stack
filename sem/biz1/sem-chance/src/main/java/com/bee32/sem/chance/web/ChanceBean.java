package com.bee32.sem.chance.web;

import java.util.List;

import com.bee32.plover.arch.util.dto.Fmask;
import com.bee32.plover.criteria.hibernate.Or;
import com.bee32.plover.orm.annotation.ForEntity;
import com.bee32.plover.orm.util.Identities;
import com.bee32.plover.orm.util.RefsDiff;
import com.bee32.plover.ox1.util.CommonCriteria;
import com.bee32.sem.chance.dto.ChanceActionDto;
import com.bee32.sem.chance.dto.ChanceDto;
import com.bee32.sem.chance.dto.ChancePartyDto;
import com.bee32.sem.chance.dto.ChanceQuotationDto;
import com.bee32.sem.chance.dto.ChanceQuotationItemDto;
import com.bee32.sem.chance.entity.Chance;
import com.bee32.sem.chance.entity.ChanceAction;
import com.bee32.sem.chance.util.ChanceCriteria;
import com.bee32.sem.frame.ui.ListMBean;
import com.bee32.sem.misc.SimpleEntityViewBean;
import com.bee32.sem.misc.UnmarshalMap;

@ForEntity(Chance.class)
public class ChanceBean
        extends SimpleEntityViewBean {

    private static final long serialVersionUID = 1L;

    public ChanceBean() {
        super(Chance.class, ChanceDto.class, 0);
    }

    @Override
    protected boolean postValidate(List<?> dtos) {
        for (Object dto : dtos) {
            ChanceDto chance = (ChanceDto) dto;
            if (chance.getParties().isEmpty()) {
                uiLogger.error("错误提示", "请选择机会对应的客户!");
                return false;
            }
        }
        return true;
    }

    @Override
    protected Integer getFmaskOverride(int saveFlags) {
        return Fmask.F_MORE & ~ChanceDto.ACTIONS;
    }

    @Override
    protected boolean preUpdate(UnmarshalMap uMap)
            throws Exception {
        UnmarshalMap dActions = uMap.deltaMap("actions");
        dActions.setLabel("关联行动");
        dActions.setEntityClass(ChanceAction.class);

        for (Chance _chance : uMap.<Chance> entitySet()) {
            ChanceDto chance = uMap.getSourceDto(_chance);
            RefsDiff diff = Identities.compare(_chance.getActions(), chance.getActions());
            for (ChanceAction _detached : diff.<ChanceAction> leftOnly()) {
                _detached.setChance(null);
                dActions.put(_detached, null);
            }
            for (ChanceActionDto attached : diff.<ChanceActionDto> rightOnly()) {
                ChanceAction _attached = attached.unmarshal();
                _attached.setChance(_chance);
                dActions.put(_attached, attached);
            }
        }
        return true;
    }

    public void addNameOrLabelRestriction() {
        addSearchFragment("名称含有 " + searchPattern, Or.of(//
                CommonCriteria.labelledWith(searchPattern, true), //
                ChanceCriteria.subjectLike(searchPattern, true)));
        searchPattern = null;
    }

    ListMBean<ChancePartyDto> partiesMBean = ListMBean.fromEL(this, "openedObject.parties", ChancePartyDto.class);
    ListMBean<ChanceActionDto> actionsMBean = ListMBean.fromEL(this, "openedObject.actions", ChanceActionDto.class);
    ListMBean<ChanceQuotationDto> quotationsMBean = ListMBean.fromEL(this, "openedObject.quotations",
            ChanceQuotationDto.class);
    ListMBean<ChanceQuotationItemDto> quotationItemsMBean = ListMBean.fromEL(quotationsMBean, "openedObject.items",
            ChanceQuotationItemDto.class);

    public ListMBean<ChancePartyDto> getPartiesMBean() {
        return partiesMBean;
    }

    public ListMBean<ChanceActionDto> getActionsMBean() {
        return actionsMBean;
    }

    public ListMBean<ChanceQuotationDto> getQuotationsMBean() {
        return quotationsMBean;
    }

    public ListMBean<ChanceQuotationItemDto> getQuotationItemsMBean() {
        return quotationItemsMBean;
    }

}
