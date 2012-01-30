package com.bee32.sem.chance.web;

import java.util.HashSet;
import java.util.Set;

import com.bee32.plover.orm.annotation.ForEntity;
import com.bee32.sem.chance.dto.ChanceActionDto;
import com.bee32.sem.chance.dto.ChanceDto;
import com.bee32.sem.chance.dto.ChancePartyDto;
import com.bee32.sem.chance.dto.ChanceStageDto;
import com.bee32.sem.chance.entity.Chance;
import com.bee32.sem.chance.entity.ChanceAction;
import com.bee32.sem.chance.entity.ChanceStage;
import com.bee32.sem.misc.SimpleEntityViewBean;
import com.bee32.sem.misc.UnmarshalMap;

@ForEntity(Chance.class)
public class ChanceBean
        extends SimpleEntityViewBean {

    private static final long serialVersionUID = 1L;

    ChanceActionDto selectedAction;
    Set<ChanceActionDto> attachSet = new HashSet<ChanceActionDto>();
    Set<ChanceActionDto> detachSet = new HashSet<ChanceActionDto>();

    ChancePartyDto selectedChanceParty;
    ChancePartyDto chanceParty;

    public ChanceBean() {
        super(Chance.class, ChanceDto.class, 0);
    }

    @Override
    protected boolean preUpdate(UnmarshalMap uMap)
            throws Exception {
        for (ChanceDto dto : uMap.<ChanceDto> dtos()) {
            if (dto.getParties().isEmpty()) {
                uiLogger.error("错误提示", "请选择机会对应的客户!");
                return false;
            }
        }
        return true;
    }

    @Override
    protected void _postUpdate(UnmarshalMap uMap)
            throws Exception {
        Set<ChanceAction> updates = new HashSet<ChanceAction>();
        for (Chance _chance : uMap.<Chance> entitySet()) {
            for (ChanceActionDto action : attachSet) {
                ChanceAction _action = action.unmarshal(this);
                _action.setChance(_chance);
                updates.add(_action);
            }
            for (ChanceActionDto action : detachSet) {
                ChanceAction _action = action.unmarshal(this);
                _action.setChance(null);
                updates.add(_action);
            }
            break;
        }
        try {
            asFor(ChanceAction.class).saveOrUpdateAll(updates);
        } finally {
            attachSet.clear();
            detachSet.clear();
        }
    }

    public ChanceActionDto getSelectedAction() {
        return selectedAction;
    }

    public void setSelectedAction(ChanceActionDto selectedAction) {
        this.selectedAction = selectedAction;
    }

    public void addAction() {
        ChanceDto chance = getActiveObject();
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

    public void createChanceParty() {
        ChanceDto chance = getActiveObject();
        chanceParty = new ChancePartyDto();
        chanceParty.setChance(chance);
    }

    public ChancePartyDto getSelectedChanceParty() {
        return selectedChanceParty;
    }

    public void setSelectedChanceParty(ChancePartyDto selectedChanceParty) {
        this.selectedChanceParty = selectedChanceParty;
    }

    public void addParty() {
        ChanceDto chance = getActiveObject();
        chance.addParty(chanceParty);
    }

    public void removeParty() {
        ChanceDto chance = getActiveObject();
        chance.removeParty(selectedChanceParty);
    }

}
