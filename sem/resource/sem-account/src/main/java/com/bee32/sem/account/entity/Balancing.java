package com.bee32.sem.account.entity;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * 票据结算
 *
 * <p lang="en">
 */
@Entity
@DiscriminatorValue("BALA")
public class Balancing
        extends NoteBalancing {

    private static final long serialVersionUID = 1L;

    public static final int COLLECTION_BANK_LENGTH = 100;

    String collectionBank;

    /**
     * 托收单位(银行)
     *
     * @return
     */
    @Column(length = COLLECTION_BANK_LENGTH)
    public String getCollectionBank() {
        return collectionBank;
    }

    public void setCollectionBank(String collectionBank) {
        this.collectionBank = collectionBank;
    }
}
