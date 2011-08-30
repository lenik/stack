package com.bee32.sem.inventory.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.bee32.plover.inject.cref.Import;
import com.bee32.plover.orm.unit.Using;
import com.bee32.plover.orm.util.WiredDaoTestCase;
import com.bee32.plover.test.FeaturePlayer;
import com.bee32.sem.inventory.SEMInventorySamples;
import com.bee32.sem.inventory.SEMInventoryUnit;
import com.bee32.sem.inventory.entity.Material;

@Service
@Scope("prototype")
@Lazy
@Import(WiredDaoTestCase.class)
@Using(SEMInventoryUnit.class)
public class StockQueryTest
        extends FeaturePlayer<StockQueryTest>
// extends WiredDaoTestCase
{

    @Inject
    IStockQuery stockQuery;

    @Override
    protected void main(StockQueryTest player)
            throws Exception {
        List<Material> materials = Arrays.asList( //
                SEMInventorySamples.gundam, //
                SEMInventorySamples.cskdp);

        Date date = new Date();

        Map<Material, BigDecimal> actual = stockQuery.getActualQuantity(date, materials, null, null);
        Map<Material, BigDecimal> virtual = stockQuery.getVirtualQuantity(date, materials, null, null);

        System.out.println(actual);
        System.out.println(virtual);
    }

    public static void main(String[] args)
            throws IOException {
        new StockQueryTest().mainLoop();
    }

}
