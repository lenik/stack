package com.bee32.sem.asset.service;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Date;
import java.util.List;

import com.bee32.plover.criteria.hibernate.Equals;
import com.bee32.plover.criteria.hibernate.GroupPropertyProjection;
import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.plover.criteria.hibernate.LessThan;
import com.bee32.plover.criteria.hibernate.ProjectionList;
import com.bee32.plover.criteria.hibernate.SumProjection;
import com.bee32.sem.asset.entity.AccountSnapshot;
import com.bee32.sem.asset.entity.AccountSnapshotItem;
import com.bee32.sem.asset.entity.AccountSubject;
import com.bee32.sem.asset.entity.AccountTicket;
import com.bee32.sem.asset.entity.AccountTicketItem;
import com.bee32.sem.asset.util.AssetCriteria;
import com.bee32.sem.people.entity.Party;
import com.bee32.sem.world.monetary.MCValue;

public class AssetQuery
        extends AbstractAssetQuery {

    @Override
    public AccountTicket getSummary(ICriteriaElement selection, AssetQueryOptions options) {

        AccountSnapshot latestSnapshot = asFor(AccountSnapshot.class).getFirst(//
                AssetCriteria.latestSnapshotBefore(options.getTimestamp()));

        ICriteriaElement dateCriterion = null;
        if (latestSnapshot != null) {
            Date snapshotDate = latestSnapshot.getEndTime();
            dateCriterion = new LessThan("beginTime", snapshotDate);

            for (AccountSnapshotItem item : asFor(AccountSnapshotItem.class).list(//
                    new Equals("snapshot", latestSnapshot))) {
                // Merge the initial.
            }
        }

        ProjectionList projection = new ProjectionList(//
                new GroupPropertyProjection("value.currency"), //
                new SumProjection("value.value"), //
                options.getSubjectProjection(), //
                options.getPartyProjection() //
        );

        List<Object[]> list = asFor(AccountTicketItem.class).listMisc(projection, selection);

        AccountTicket all = new AccountTicket();

        int index = 0;
        for (Object[] line : list) {
            int _column = 0;
            Currency _value_cc = (Currency) line[_column++];
            BigDecimal _value = (BigDecimal) line[_column++];
            AccountSubject _subject = null;
            Party _party = null;

            if (!options.isSubjectMerged())
                _subject = (AccountSubject) line[_column++];
            if (!options.isPartyMerged())
                _party = (Party) line[_column++];

            AccountTicketItem item = new AccountTicketItem();
            item.setIndex(index++);
            item.setSubject(_subject);
            item.setParty(_party);
            item.setValue(new MCValue(_value_cc, _value));

            all.addItem(item);
        }

        return all;
    }
}
