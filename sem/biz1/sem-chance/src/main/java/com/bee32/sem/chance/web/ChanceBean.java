package com.bee32.sem.chance.web;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.bee32.plover.criteria.hibernate.Or;
import com.bee32.plover.orm.annotation.ForEntity;
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
    protected boolean preUpdate(UnmarshalMap uMap)
            throws Exception {
        UnmarshalMap actionUMap = uMap.getSubMap("actions");
        actionUMap.setLabel("关联行动");
        actionUMap.setEntityClass(ChanceAction.class);
        for (Chance _chance : uMap.<Chance> entitySet()) {
            for (ChanceActionDto attachAction : attachSet) {
                ChanceAction _action = attachAction.unmarshal();
                _chance.addAction(_action);
                actionUMap.put(_action, attachAction);
            }
            for (ChanceActionDto detachAction : detachSet) {
                ChanceAction _action = detachAction.unmarshal();
                _chance.removeAction(_action);
                actionUMap.put(_action, detachAction);
            }
        }
        attachSet.clear();
        detachSet.clear();
        return true;
    }

    Set<ChanceActionDto> attachSet = new HashSet<ChanceActionDto>();
    Set<ChanceActionDto> detachSet = new HashSet<ChanceActionDto>();

    public void setChosenAction(ChanceActionDto attachAction) {
        if (attachAction == null)
            return;
        ChanceDto chance = getOpenedObject();
        chance.addAction(attachAction);
        attachSet.add(attachAction);
        detachSet.remove(attachAction);
    }

    public void detachAction() {
        ChanceActionDto detachAction = actionsMBean.getSelection();
        if (detachAction == null)
            return;
        ChanceDto chance = getOpenedObject();
        chance.removeAction(detachAction);
        attachSet.remove(detachAction);
        detachSet.add(detachAction);
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
