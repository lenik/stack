package com.bee32.sem.chance.web;

import com.bee32.plover.orm.annotation.ForEntity;
import com.bee32.sem.chance.dto.ChanceDto;
import com.bee32.sem.chance.dto.ChancePartyDto;
import com.bee32.sem.chance.entity.Chance;
import com.bee32.sem.chance.entity.ChanceAction;
import com.bee32.sem.chance.entity.ChanceStage;
import com.bee32.sem.misc.SimpleEntityViewBean;
import com.bee32.sem.misc.UnmarshalMap;

@ForEntity(Chance.class)
public class ChanceBean
        extends SimpleEntityViewBean {

    private static final long serialVersionUID = 1L;

    public ChanceBean() {
        super(Chance.class, ChanceDto.class, 0);
    }

    public void attachActions() {
        if (!actions.isSelected())
            return;

        dto.addAction(actions.getSelection());

        Chance _chance = dto.unmarshal();
        try {
            for (ChanceAction _action : _chance.getActions()) {
                if (_action.getChance() == null)
                    _action.setChance(_chance);
                if (_action.getStage() == null)
                    _action.setStage(ChanceStage.INIT);
            }

            serviceFor(ChanceAction.class).saveOrUpdateAll(_chance.getActions());
            uiLogger.info("提示", "关联成功");
        } catch (Exception e) {
            uiLogger.error("错误提示", "关联失败", e);
        }
    }

    public void detachAction() {
        if (selectedAction ==null) {
            uiLogger.error("错误提示:", "请选择行动记录!");
            return;
        }
        ChanceAction chanceAction = dto.getSelectedAction().unmarshal();
        chanceAction.setChance(null);
        chanceAction.setStage(null);
        try {
            serviceFor(ChanceAction.class).save(chanceAction);
            dto.deleteAction(dto.getSelectedAction());
            dto.setSelectedAction(null);
// actions.deselect();
            uiLogger.info("反关联成功");
        } catch (Exception e) {
            uiLogger.error("反关联失败", e);
        }
    }

    public void addChanceParty() {
        if (!parties.isSelected())
            return;
        ChancePartyDto chancePartyDto = new ChancePartyDto().create();
        chancePartyDto.setChance(dto);
        chancePartyDto.setParty(parties.getSelection());
        chancePartyDto.setRole("普通客户");
        dto.addParty(chancePartyDto);
    }

    public void editCustomerRole() {
        roleRendered = !roleRendered;
    }

    public void dropCustomer() {
        ChancePartyDto selectedParty = dto.getSelectedParty();
        dto.removeParty(selectedParty);
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

}
