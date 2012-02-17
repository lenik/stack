package com.bee32.sem.chance.web;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.bee32.plover.orm.annotation.ForEntity;
import com.bee32.sem.chance.dto.ChanceActionDto;
import com.bee32.sem.chance.dto.ChanceDto;
import com.bee32.sem.chance.dto.ChancePartyDto;
import com.bee32.sem.chance.dto.ChanceQuotationDto;
import com.bee32.sem.chance.dto.ChanceQuotationItemDto;
import com.bee32.sem.chance.dto.ChanceStageDto;
import com.bee32.sem.chance.entity.Chance;
import com.bee32.sem.chance.entity.ChanceAction;
import com.bee32.sem.chance.entity.ChanceStage;
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
    protected void postUpdate(UnmarshalMap uMap)
            throws Exception {
        Set<ChanceAction> updates = new HashSet<ChanceAction>();
        for (Chance _chance : uMap.<Chance> entitySet()) {
            for (ChanceActionDto action : attachSet) {
                ChanceAction _action = action.unmarshal();
                _action.setChance(_chance);
                updates.add(_action);
            }
            for (ChanceActionDto action : detachSet) {
                ChanceAction _action = action.unmarshal();
                _action.setChance(null);
                updates.add(_action);
            }
            break;
        }
        try {
            ctx.data.access(ChanceAction.class).saveOrUpdateAll(updates);
        } finally {
            attachSet.clear();
            detachSet.clear();
        }
    }

    ChanceActionDto selectedAction;
    Set<ChanceActionDto> attachSet = new HashSet<ChanceActionDto>();
    Set<ChanceActionDto> detachSet = new HashSet<ChanceActionDto>();

    public ChanceActionDto getSelectedAction() {
        return selectedAction;
    }

    public void setSelectedAction(ChanceActionDto selectedAction) {
        this.selectedAction = selectedAction;
    }

    public void addAction() {
        ChanceDto chance = getOpenedObject();
        if (selectedAction.getStage() == null) {
            ChanceStageDto initStage = new ChanceStageDto().ref(ChanceStage.INIT);
            selectedAction.setStage(initStage);
        }
        chance.addAction(selectedAction);
        attachSet.add(selectedAction);
        detachSet.remove(selectedAction);
    }

    public void removeAction() {
        selectedAction.setStage(null);
        attachSet.remove(selectedAction);
        detachSet.add(selectedAction);
    }

    ListMBean<ChancePartyDto> partiesMBean = ListMBean.fromEL(this, "openedObject.parties", ChancePartyDto.class);
    ListMBean<ChanceQuotationDto> quotationsMBean = ListMBean.fromEL(this, "openedObject.quotations",
            ChanceQuotationDto.class);
    ListMBean<ChanceQuotationItemDto> quotationItemsMBean = ListMBean.fromEL(quotationsMBean, "openedObject.items",
            ChanceQuotationItemDto.class);

    public ListMBean<ChancePartyDto> getPartiesMBean() {
        return partiesMBean;
    }

    public ListMBean<ChanceQuotationDto> getQuotationsMBean() {
        return quotationsMBean;
    }

    public ListMBean<ChanceQuotationItemDto> getQuotationItemsMBean() {
        return quotationItemsMBean;
    }

}
