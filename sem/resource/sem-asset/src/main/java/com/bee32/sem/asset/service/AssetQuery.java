package com.bee32.sem.asset.service;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Date;
import java.util.List;

import com.bee32.plover.arch.DataService;
import com.bee32.plover.criteria.hibernate.Equals;
import com.bee32.plover.criteria.hibernate.GroupPropertyProjection;
import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.plover.criteria.hibernate.Order;
import com.bee32.plover.criteria.hibernate.ProjectionList;
import com.bee32.plover.criteria.hibernate.SumProjection;
import com.bee32.plover.ox1.dict.CodeTreeBuilder;
import com.bee32.sem.asset.entity.AccountSnapshot;
import com.bee32.sem.asset.entity.AccountSnapshotItem;
import com.bee32.sem.asset.entity.AccountSubject;
import com.bee32.sem.asset.entity.AccountTicketItem;
import com.bee32.sem.asset.util.AssetCriteria;
import com.bee32.sem.people.entity.Party;
import com.bee32.sem.world.monetary.MCValue;

public class AssetQuery
        extends DataService
        implements IAssetQuery {

    @Override
    public SumNode getSummary(AssetQueryOptions options) {
        List<AccountSubject> subjects = asFor(AccountSubject.class).list(Order.asc("id"));
        CodeTreeBuilder ctb = new CodeTreeBuilder();
        ctb.setNodeClass(SumNode.class);
        ctb.learn(subjects);

        AccountSnapshot latestSnapshot = asFor(AccountSnapshot.class).getFirst(//
                AssetCriteria.latestSnapshotBefore(options.getTimestamp()));

        Date snapshotDate = null;
        if (latestSnapshot != null) {
            snapshotDate = latestSnapshot.getEndTime();
            for (AccountSnapshotItem item : asFor(AccountSnapshotItem.class).list(//
                    new Equals("snapshot", latestSnapshot))) {

                AccountTicketItem converted = new AccountTicketItem();
                converted.populate(item);
                converted.setSubject(item.getSubject());
                converted.setParty(item.getParty());
                converted.setValue(item.getValue());

                String subjectId = item.getSubject().getId();
                SumNode node = ctb.getNode(subjectId);
                node.receive(converted);
            }
        }

        ICriteriaElement selection = AssetCriteria.select(options, snapshotDate);
        ProjectionList projection = new ProjectionList(//
                new GroupPropertyProjection("value.currencyCode"), //
                new SumProjection("value.value"), //
                options.getSubjectProjection(), //
                options.getPartyProjection() //
        );

        List<Object[]> list = asFor(AccountTicketItem.class).listMisc(projection, selection);

        for (Object[] line : list) {
            int _column = 0;
            String _value_cc = (String) line[_column++];
            Currency _currency = Currency.getInstance(_value_cc);
            BigDecimal _value = (BigDecimal) line[_column++];
            AccountSubject _subject = null;
            Party _party = null;

            if (!options.isSubjectMerged())
                _subject = (AccountSubject) line[_column++];
            if (!options.isPartyMerged())
                _party = (Party) line[_column++];

            AccountTicketItem item = new AccountTicketItem();
            item.setSubject(_subject);
            item.setParty(_party);
            item.setValue(new MCValue(_currency, _value));

            SumNode node = ctb.getNode(_subject.getId());
            node.receive(item);
        }

        ctb.reduce();
        // SumNode root = ctb.getNode(options.getSubjects());
        SumNode root = ctb.getRoot();
        return root;
    }

}
