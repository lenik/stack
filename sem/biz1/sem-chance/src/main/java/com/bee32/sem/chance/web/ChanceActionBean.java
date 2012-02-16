package com.bee32.sem.chance.web;

import com.bee32.icsf.login.SessionUser;
import com.bee32.icsf.principal.UserDto;
import com.bee32.plover.orm.annotation.ForEntity;
import com.bee32.sem.chance.dto.ChanceActionDto;
import com.bee32.sem.chance.dto.ChanceStageDto;
import com.bee32.sem.chance.entity.Chance;
import com.bee32.sem.chance.entity.ChanceAction;
import com.bee32.sem.misc.SimpleEntityViewBean;
import com.bee32.sem.misc.UnmarshalMap;
import com.bee32.sem.people.dto.PartyDto;

@ForEntity(ChanceAction.class)
public class ChanceActionBean
        extends SimpleEntityViewBean {

    private static final long serialVersionUID = 1L;

    PartyDto selectedParty;
    UserDto selectedPartner;

    public ChanceActionBean() {
        super(ChanceAction.class, ChanceActionDto.class, 0);
    }

    @Override
    protected boolean preUpdate(UnmarshalMap uMap)
            throws Exception {
        for (ChanceActionDto action : uMap.<ChanceActionDto> dtos()) {
            action.setActor(SessionUser.getInstance().getUser());
            ChanceStageDto stage = action.getStage();
            Long chanceId = action.getChance().getId();
            if (chanceId == null && !stage.isNull()) {
                uiLogger.error("错误提示", "必须先选择机会,才能设置机会阶段");
                return false;
            }
        }
        return true;
    }

    @Override
    protected void postUpdate(UnmarshalMap uMap)
            throws Exception {
        for (ChanceAction _action : uMap.<ChanceAction> entitySet()) {
            Chance _chance = _action.getChance();
            if (_chance != null) {
                _chance.addAction(_action);
                ctx.data.access(Chance.class).saveOrUpdate(_chance);
            }
        }
    }

    public PartyDto getSelectedParty() {
        return selectedParty;
    }

    public void setSelectedParty(PartyDto selectedParty) {
        this.selectedParty = selectedParty;
    }

    public void addParty() {
        ChanceActionDto action = getOpenedObject();
        action.addParty(selectedParty);
    }

    public UserDto getSelectedPartner() {
        return selectedPartner;
    }

    public void setSelectedPartner(UserDto selectedPartner) {
        this.selectedPartner = selectedPartner;
    }

    public void addPartner() {
        ChanceActionDto action = getOpenedObject();
        action.addPartner(selectedPartner);
    }

}
