package com.bee32.sem.account.dto;

import com.bee32.plover.model.validation.core.NLength;
import com.bee32.sem.account.entity.Endorsement;
import com.bee32.sem.account.entity.NoteBalancing;

public class EndorsementDto extends NoteBalancingDto {

    private static final long serialVersionUID = 1L;

    String beEndorsementUnit;

    @Override
    protected void _marshal(NoteBalancing source) {
        super._marshal(source);

        Endorsement o = (Endorsement)source;

        beEndorsementUnit = o.getBeEndorsementUnit();
    }

    @Override
    protected void _unmarshalTo(NoteBalancing target) {
        super._unmarshalTo(target);

        Endorsement o = (Endorsement)target;

        o.setBeEndorsementUnit(beEndorsementUnit);
    }

    @NLength(min = 2,  max = Endorsement.BE_ENDOR_UNIT_LENGTH)
    public String getBeEndorsementUnit() {
        return beEndorsementUnit;
    }

    public void setBeEndorsementUnit(String beEndorsementUnit) {
        this.beEndorsementUnit = beEndorsementUnit;
    }
}
