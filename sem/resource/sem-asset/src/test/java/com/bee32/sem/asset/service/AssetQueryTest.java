package com.bee32.sem.asset.service;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.free.Stdio;
import javax.inject.Inject;

import org.springframework.transaction.annotation.Transactional;

import com.bee32.plover.orm.unit.Using;
import com.bee32.plover.orm.util.WiredDaoFeat;
import com.bee32.plover.test.ICoordinator;
import com.bee32.sem.asset.SEMAssetUnit;
import com.bee32.sem.asset.entity.AccountSnapshot;
import com.bee32.sem.asset.entity.AccountSnapshotItem;
import com.bee32.sem.asset.entity.AccountSubjects;
import com.bee32.sem.asset.entity.AccountTicket;
import com.bee32.sem.asset.entity.AccountTicketItem;
import com.bee32.sem.people.SEMPeopleSamples;
import com.bee32.sem.world.monetary.MCValue;

@Using(SEMAssetUnit.class)
public class AssetQueryTest
        extends WiredDaoFeat<AssetQueryTest> {

    @Inject
    IAssetQuery query;

    @Inject
    SEMPeopleSamples people;
    @Inject
    AccountSubjects subjects;

    static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    static Date parseDate(String str) {
        try {
            return dateFormat.parse(str);
        } catch (ParseException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @Transactional
    void prepare() {
        AccountSnapshot snapshot1 = new AccountSnapshot();
        snapshot1.setEndTime(parseDate("2011-12-1"));
        snapshot1.setAltId("_AQT_S1");

        AccountSnapshotItem sitem1 = new AccountSnapshotItem();
        sitem1.setSnapshot(snapshot1);
        sitem1.setAltId("_AQT_S1-1");
        sitem1.setSubject(subjects.s1001);
        sitem1.setParty(people.bentley);
        sitem1.setValue(new MCValue(10000));

        AccountTicket ticket1 = new AccountTicket();
        ticket1.setAltId("_AQT_T1");

        AccountTicketItem item1 = new AccountTicketItem();
        item1.setTicket(ticket1);
        item1.setAltId("_AQT_T1-1");
        item1.setSubject(subjects.s1002);
        item1.setParty(people.bugatti);
        item1.setValue(new MCValue(200));
        item1.setEndTime(parseDate("2011-12-1"));

        AccountTicketItem item2 = new AccountTicketItem();
        item2.setTicket(ticket1);
        item2.setAltId("_AQT_T1-2");
        item2.setSubject(subjects.s1012);
        item2.setParty(people.bentley);
        item2.setValue(new MCValue(300));
        item2.setEndTime(parseDate("2011-12-2"));

        AccountTicketItem item3 = new AccountTicketItem();
        item3.setTicket(ticket1);
        item3.setAltId("_AQT_T1-3");
        item3.setSubject(subjects.s1101);
        item3.setParty(people.bugatti);
        item3.setValue(new MCValue(50));
        item3.setEndTime(parseDate("2011-12-3"));

        ctx.data.access(AccountSnapshot.class).saveOrUpdateAllByNaturalId(//
                snapshot1);
        ctx.data.access(AccountSnapshotItem.class).saveOrUpdateAllByNaturalId(//
                sitem1);
        ctx.data.access(AccountTicket.class).saveOrUpdateAllByNaturalId(//
                ticket1);
        ctx.data.access(AccountTicketItem.class).saveOrUpdateAllByNaturalId(//
                item1, item2, item3);
    }

    @Transactional(readOnly = true)
    void query() {
        AssetQueryOptions options = new AssetQueryOptions(new Date());
        options.setParties(null, true);
        SumTree tree = query.getSummary(options);
        tree.dump(Stdio.cout);
    }

    public static void main(String[] args)
            throws IOException {
        new AssetQueryTest().mainLoop(new ICoordinator<AssetQueryTest>() {
            @Override
            public void main(AssetQueryTest feat)
                    throws Exception {
                feat.prepare();
                feat.query();
            }
        });
    }

}
