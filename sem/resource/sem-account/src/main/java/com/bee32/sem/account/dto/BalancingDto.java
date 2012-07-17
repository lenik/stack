package com.bee32.sem.account.dto;

import com.bee32.plover.model.validation.core.NLength;
import com.bee32.sem.account.entity.Balancing;
import com.bee32.sem.account.entity.NoteBalancing;

public class BalancingDto extends NoteBalancingDto {

    private static final long serialVersionUID = 1L;

    String collectionBank;

    @Override
    protected void _marshal(NoteBalancing source) {
        super._marshal(source);

        Balancing o = (Balancing)source;

        collectionBank = o.getCollectionBank();
    }

    @Override
    protected void _unmarshalTo(NoteBalancing target) {
        super._unmarshalTo(target);

        Balancing o = (Balancing)target;

        o.setCollectionBank(collectionBank);
    }

    @NLength(min = 2, max = Balancing.COLLECTION_BANK_LENGTH)
    public String getCollectionBank() {
        return collectionBank;
    }

    public void setCollectionBank(String collectionBank) {
        this.collectionBank = collectionBank;
    }


}
