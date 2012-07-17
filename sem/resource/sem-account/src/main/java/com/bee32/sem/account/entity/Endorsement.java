package com.bee32.sem.account.entity;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * 票据背书
 * @author jack
 *
 */
@Entity
@DiscriminatorValue("ENDO")
public class Endorsement extends NoteBalancing {

    private static final long serialVersionUID = 1L;

    public static final int BE_ENDOR_UNIT_LENGTH = 100;

    String beEndorsementUnit;

    /**
     * 被背书人
     * @return
     */
    @Column(length = BE_ENDOR_UNIT_LENGTH)
    public String getBeEndorsementUnit() {
        return beEndorsementUnit;
    }

    public void setBeEndorsementUnit(String beEndorsementUnit) {
        this.beEndorsementUnit = beEndorsementUnit;
    }
}
