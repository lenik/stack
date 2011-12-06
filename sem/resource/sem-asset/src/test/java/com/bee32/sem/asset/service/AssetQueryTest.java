package com.bee32.sem.asset.service;

import java.io.IOException;
import java.util.Date;

import javax.free.Stdio;
import javax.inject.Inject;

import org.springframework.transaction.annotation.Transactional;

import com.bee32.plover.orm.unit.Using;
import com.bee32.plover.orm.util.WiredDaoFeat;
import com.bee32.plover.ox1.dict.PoTreeBuilder;
import com.bee32.plover.test.ICoordinator;
import com.bee32.sem.asset.SEMAssetUnit;
import com.bee32.sem.asset.entity.AccountSubject;
import com.bee32.sem.asset.entity.AccountTicket;
import com.bee32.sem.asset.entity.AccountTicketItem;
import com.bee32.sem.people.SEMPeopleSamples;
import com.bee32.sem.world.monetary.MCValue;

@Using(SEMAssetUnit.class)
public class AssetQueryTest
        extends WiredDaoFeat<AssetQueryTest> {

    @Inject
    IAssetQuery query;

    @Transactional
    void prepare() {
        AccountTicket ticket1 = new AccountTicket();
        ticket1.setSerial("_AQT_T1");

        AccountTicketItem item1 = new AccountTicketItem();
        item1.setTicket(ticket1);
        item1.setSerial("_AQT_T1-1");
        item1.setSubject(AccountSubject.s100901);
        item1.setParty(SEMPeopleSamples.bugatti);
        item1.setValue(new MCValue(200));

        AccountTicketItem item2 = new AccountTicketItem();
        item2.setTicket(ticket1);
        item1.setSerial("_AQT_T1-2");
        item2.setSubject(AccountSubject.s110110);
        item2.setParty(SEMPeopleSamples.bentley);
        item2.setValue(new MCValue(300));

        AccountTicketItem item3 = new AccountTicketItem();
        item3.setTicket(ticket1);
        item1.setSerial("_AQT_T1-3");
        item3.setSubject(AccountSubject.s1102);
        item3.setParty(SEMPeopleSamples.bugatti);
        item3.setValue(new MCValue(50));

        dataManager.asFor(AccountTicket.class).saveOrUpdateAll(//
                ticket1);
        dataManager.asFor(AccountTicketItem.class).saveOrUpdateAll(//
                item1, item2, item3);
    }

    @Transactional(readOnly = true)
    void query() {
        AssetQueryOptions options = new AssetQueryOptions(new Date());
        SumNode root = query.getSummary(options);
        PoTreeBuilder.dump(Stdio.cout, root, SumNode.SumNodeFormatter.INSTANCE);
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
