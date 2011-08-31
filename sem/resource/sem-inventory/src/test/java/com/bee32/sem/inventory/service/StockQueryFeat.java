package com.bee32.sem.inventory.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.springframework.context.annotation.Scope;

import com.bee32.plover.orm.unit.Using;
import com.bee32.plover.orm.util.SamplesLoaderActivator;
import com.bee32.plover.orm.util.WiredDaoFeat;
import com.bee32.plover.test.ICoordinator;
import com.bee32.sem.inventory.SEMInventorySamples;
import com.bee32.sem.inventory.SEMInventoryUnit;
import com.bee32.sem.inventory.entity.Material;
import com.bee32.sem.inventory.entity.StockItemList;
import com.bee32.sem.world.monetary.FxrQueryException;

@Scope("prototype")
@Using(SEMInventoryUnit.class)
public class StockQueryFeat
        extends WiredDaoFeat<StockQueryFeat> {

    @Inject
    IStockQuery stockQuery;

    @Inject
    SamplesLoaderActivator samplesLoaderActivator;

    void list()
            throws FxrQueryException {
        System.out.println("----------- LIST BEGIN -----------");
        List<Material> materials = Arrays.asList( //
                SEMInventorySamples.gundam, //
                SEMInventorySamples.cskdp);

        Date date = new Date();

        StockItemList actual = stockQuery.getActualSummary(date, materials, null, null);
        StockItemList virtual = stockQuery.getVirtualSummary(date, materials, null, null);

        System.out.println(actual.dump());
        System.out.println(virtual.dump());

        BigDecimal actualTotal = actual.getNativeTotal();
        BigDecimal virtualTotal = virtual.getNativeTotal();
        System.out.println(actualTotal);
        System.out.println(virtualTotal);

        BigDecimal gunAq = stockQuery.getActualQuantity(date, SEMInventorySamples.gundam, null, null);
        BigDecimal gunVq = stockQuery.getVirtualQuantity(date, SEMInventorySamples.gundam, null, null);

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
