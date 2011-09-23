package com.bee32.sem.inventory.tx;

import java.io.IOException;
import java.util.Date;

import javax.inject.Inject;

import org.springframework.context.annotation.Scope;
import org.springframework.transaction.annotation.Transactional;

import com.bee32.plover.orm.dao.CommonDataManager;
import com.bee32.plover.orm.unit.Using;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.plover.orm.util.SamplesLoader;
import com.bee32.plover.orm.util.WiredDaoFeat;
import com.bee32.plover.test.ICoordinator;
import com.bee32.sem.inventory.SEMInventorySamples;
import com.bee32.sem.inventory.SEMInventoryUnit;
import com.bee32.sem.inventory.dto.StockOrderDto;
import com.bee32.sem.inventory.dto.StockOrderItemDto;
import com.bee32.sem.inventory.entity.StockOrder;
import com.bee32.sem.inventory.service.IStockQuery;
import com.bee32.sem.inventory.service.StockQueryOptions;
import com.bee32.sem.inventory.tx.dto.StockTakingDto;
import com.bee32.sem.inventory.tx.dto.StockTakingItemDto;
import com.bee32.sem.inventory.tx.entity.StockTaking;

@Scope("prototype")
@Using(SEMInventoryUnit.class)
public class StockTakingFeat
        extends WiredDaoFeat<StockTakingFeat> {

    @Inject
    SamplesLoader samplesLoader;
    static boolean loaded;

    @Inject
    CommonDataManager dataManager;

    @Inject
    IStockQuery stockQuery;

    private long stockTakingId;

    @Transactional
    public void loadSamples() {
        if (loaded)
            return;
        samplesLoader.loadNormalSamples();
        loaded = true;
    }

    @Transactional
    public void create() {
        Date now = new Date();

        StockQueryOptions options = new StockQueryOptions(now);
        options.setWarehouse(SEMInventorySamples.rawWarehouse, false);
        options.setCBatch(null, false);
        options.setLocation(null, false);

        StockOrder actualSummary = stockQuery.getActualSummary(null, options);
        StockOrderDto expectedOrderDto = DTOs.marshal(StockOrderDto.class, -1, actualSummary);

        // 准备盘点DTO
        StockTakingDto stockTakingDto = new StockTakingDto(-1).create();
        stockTakingDto.setBeginTime(now);
        stockTakingDto.initialize(expectedOrderDto);

        StockTaking stockTaking = stockTakingDto.unmarshal();
        dataManager.asFor(StockTaking.class).save(stockTaking);

        stockTakingId = stockTaking.getId();
        dataManager.asFor(StockTaking.class).evict(stockTaking);
    }

    @Transactional(readOnly = true)
    public void dump() {
        StockTaking stockTaking = dataManager.asFor(StockTaking.class).get(stockTakingId);
        StockTakingDto stockTakingDto = DTOs.marshal(StockTakingDto.class, stockTaking);
        StockOrderDto joined = stockTakingDto.getJoined();
        for (StockOrderItemDto item : joined.getItems()) {
            StockTakingItemDto joinedItem = (StockTakingItemDto) item;
            System.out.printf("Item %-20s: expected=%6.2f  actual=%6.2f  diff=%6.2f\n", //
                    joinedItem.getMaterial().getLabel(), //
                    joinedItem.getExpected(), //
                    joinedItem.getActual(), //
                    joinedItem.getDiff());
        }
    }

    public static void main(String[] args)
            throws IOException {
        new StockTakingFeat().mainLoop(new ICoordinator<StockTakingFeat>() {
            @Override
            public void main(StockTakingFeat feat)
                    throws Exception {
                feat.loadSamples();
                feat.create();
                feat.dump();
            }
        });
    }

}
