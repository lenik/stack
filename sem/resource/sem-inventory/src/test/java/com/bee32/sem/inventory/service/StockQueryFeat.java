package com.bee32.sem.inventory.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import com.bee32.plover.orm.unit.Using;
import com.bee32.plover.orm.util.WiredDaoFeat;
import com.bee32.plover.test.ICoordinator;
import com.bee32.sem.inventory.SEMInventorySamples;
import com.bee32.sem.inventory.SEMInventoryUnit;
import com.bee32.sem.world.monetary.FxrQueryException;

@SuppressWarnings("unused")
@Using(SEMInventoryUnit.class)
public class StockQueryFeat
        extends WiredDaoFeat<StockQueryFeat> {

    @Inject
    IStockQuery stockQuery;

    void list()
            throws FxrQueryException {
        System.out.println("----------- LIST BEGIN -----------");
        List<Long> materials = Arrays.asList( //
                SEMInventorySamples.m_glass1.getId(), //
                SEMInventorySamples.m_glue1.getId());

        StockQueryOptions sqopts = new StockQueryOptions(new Date(), false);
        sqopts.setCBatch(null, true);
        StockQueryResult actual = stockQuery.getPhysicalStock(materials, sqopts);
        StockQueryResult virtual = stockQuery.getAvailableStock(materials, sqopts);

        System.out.println(actual.dump());
        System.out.println(virtual.dump());

        BigDecimal actualTotal = actual.getNativeTotal();
        BigDecimal virtualTotal = virtual.getNativeTotal();
        System.out.println(actualTotal);
        System.out.println(virtualTotal);

        BigDecimal gunAq = stockQuery.getPhysicalStock(SEMInventorySamples.m_glass1.getId(), sqopts);
        BigDecimal gunVq = stockQuery.getAvailableStock(SEMInventorySamples.m_glass1.getId(), sqopts);

        System.out.println("----------- LIST END -----------");
    }

    public static void main(String[] args)
            throws IOException {
        new StockQueryFeat().mainLoop(new ICoordinator<StockQueryFeat>() {
            @Override
            public void main(StockQueryFeat feat)
                    throws Exception {
                System.out.println(">>> list");
                feat.list();
            }
        });
    }

}
