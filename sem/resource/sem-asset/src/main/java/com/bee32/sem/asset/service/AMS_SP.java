package com.bee32.sem.asset.service;

import javax.free.Nullables;

import com.bee32.sem.asset.entity.AccountSubject;
import com.bee32.sem.asset.entity.AccountTicketItem;
import com.bee32.sem.people.entity.Party;

public class AMS_SP
        implements IAssetMergeStrategy {

    private static final long serialVersionUID = 1L;

    static class Key {

        final AccountSubject subject;
        final Party party;

        public Key(AccountSubject subject, Party party) {
            this.subject = subject;
            this.party = party;
        }

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof Key))
                return false;

            Key o = (Key) obj;

            if (!Nullables.equals(subject, o.subject))
                return false;

            if (!Nullables.equals(party, o.party))
                return false;

            return true;
        }

        @Override
        public int hashCode() {
            int hash = 0;
            if (subject != null)
                hash = hash * 17 + subject.hashCode();
            if (party != null)
                hash = hash * 17 + party.hashCode();
            return hash;
        }

    }

    @Override
    public Object getMergeKey(AccountTicketItem item) {
        Key mergeKey = new Key(item.getSubject(), item.getParty());
        return mergeKey;
    }

    public static final AMS_SP INSTANCE = new AMS_SP();

}
