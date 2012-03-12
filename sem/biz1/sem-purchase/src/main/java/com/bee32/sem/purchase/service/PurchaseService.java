package com.bee32.sem.purchase.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import com.bee32.plover.arch.DataService;
import com.bee32.plover.arch.util.IdComposite;
import com.bee32.plover.criteria.hibernate.InCollection;
import com.bee32.plover.orm.entity.IdUtils;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.sem.inventory.dto.MaterialDto;
import com.bee32.sem.inventory.dto.StockOrderDto;
import com.bee32.sem.inventory.dto.StockOrderItemDto;
import com.bee32.sem.inventory.dto.StockWarehouseDto;
import com.bee32.sem.inventory.entity.MaterialWarehouseOption;
import com.bee32.sem.inventory.entity.StockOrderSubject;
import com.bee32.sem.inventory.entity.StockWarehouse;
import com.bee32.sem.inventory.service.IStockQuery;
import com.bee32.sem.inventory.service.StockQueryOptions;
import com.bee32.sem.inventory.service.StockQueryResult;
import com.bee32.sem.inventory.util.ConsumptionMap;
import com.bee32.sem.makebiz.dto.MaterialPlanDto;
import com.bee32.sem.people.dto.OrgDto;
import com.bee32.sem.purchase.dto.PurchaseRequestDto;
import com.bee32.sem.purchase.dto.PurchaseRequestItemDto;
import com.bee32.sem.purchase.dto.PurchaseTakeInDto;

public class PurchaseService
        extends DataService {

    @Inject
    IStockQuery stockQuery;

    /**
     *
     * 根据传入的物料计划，结合当前库存和物料的安全库存，计算所需要采购的量
     *
     * 注：如果同一物料分布在多个仓库，此处目前数量合并计算
     */
    public List<PurchaseRequestItemDto> calcMaterialRequirement(List<MaterialPlanDto> plans) {
        // 计算需求
        ConsumptionMap cmap = new ConsumptionMap();
        for (MaterialPlanDto materialPlan : plans)
            materialPlan.declareConsumption(cmap);

        // 用当前库存余量（不考虑锁定）去抵消需求
        Collection<MaterialDto> materials = cmap.getDtoIndex().values();
        if (materials.isEmpty())
            return Collections.emptyList();

        List<Long> materialIds = IdUtils.getDtoIdList(materials);
        StockQueryOptions queryOptions = new StockQueryOptions(new Date(), true);
        StockQueryResult queryResult = stockQuery.getPhysicalStock(materialIds, queryOptions);
        queryResult.declareSupply(cmap);

        // 抵消后的数量为采购需求数量，再考虑安全库存：
        ConsumptionMap safetyMap = new ConsumptionMap();
        List<MaterialWarehouseOption> _mwopts = ctx.data.access(MaterialWarehouseOption.class).list(
                new InCollection("material.id", materialIds));
        for (MaterialWarehouseOption _mwopt : _mwopts)
            safetyMap.consume(_mwopt.getMaterial(), _mwopt.getSafetyStock());

        // 生成列表
        List<PurchaseRequestItemDto> requestItems = new ArrayList<>();
        for (MaterialDto material : materials) {
            BigDecimal needQuantity = cmap.getConsumption(material);

            // 采购 至少要满足安全库存
            BigDecimal safetyStock = safetyMap.getConsumption(material);
            if (safetyStock == null)
                safetyStock = new BigDecimal(0);
            needQuantity = needQuantity.max(safetyStock);

            // 计算需要采购的数量
            BigDecimal requestQuantity = needQuantity;
            if (requestQuantity != null) {
                PurchaseRequestItemDto requestItem = new PurchaseRequestItemDto().create();
                requestItem.setMaterial(material);

                // 从物料计划计算采购请求量时，把计划采购量首先设置为和计算量相同
                requestItem.setRequiredQuantity(requestQuantity);
                requestItem.setQuantity(requestQuantity);

                requestItems.add(requestItem);
            }
        }
        return requestItems;
    }

    /**
     * 根据采购请求和所选择的仓库,自动生成采购入库单
     */
    public void generateTakeInStockOrders(PurchaseRequestDto purchaseRequest) {

        // 先用map保存用户输入的warehouseId，因为后面的reload(purchaseRequest)会导致这些warehouseIds丢失
        Map<Long, Integer> itemWarehouseMap = new HashMap<Long, Integer>();
        for (PurchaseRequestItemDto requestItem : purchaseRequest.getItems()) {
            itemWarehouseMap.put(requestItem.getId(), requestItem.getDestWarehouse().getId());
        }

        Map<Object, List<PurchaseRequestItemDto>> splitMap = new HashMap<>();

        // 按仓库和供应商区分要生成的采购入库单
        for (PurchaseRequestItemDto item : purchaseRequest.getItems()) {
            OrgDto preferredSupplier = item.getAcceptedInquiry().getSupplier();
            // 以warehouseId和对应供应商的id组合成 x&y 的形式
            IdComposite key = new IdComposite(item.getDestWarehouse().getId(), preferredSupplier.getId());

            if (!splitMap.containsKey(key)) {
                List<PurchaseRequestItemDto> newItemList = new ArrayList<>();
                splitMap.put(key, newItemList);
            }

            List<PurchaseRequestItemDto> itemList = splitMap.get(key);
            itemList.add(item);
        }

        // 3.按仓库生成subject为takeIn的StockOrder->StockOrderItem*
        for (List<PurchaseRequestItemDto> itemList : splitMap.values()) {
            OrgDto preferredSupplier = itemList.get(0).getAcceptedInquiry().getSupplier();

            StockWarehouse warehouse = ctx.data.access(StockWarehouse.class).lazyLoad(
                    itemWarehouseMap.get(itemList.get(0).getId()));

            StockOrderDto takeInOrder = new StockOrderDto().create();
            takeInOrder.setOrg(preferredSupplier);
            takeInOrder.setWarehouse(DTOs.marshal(StockWarehouseDto.class, warehouse));
            takeInOrder.setSubject(StockOrderSubject.TAKE_IN);
            takeInOrder.setLabel("从采购请求[" + purchaseRequest.getLabel() + "]生成的入库单");

            for (PurchaseRequestItemDto item : itemList) {
                StockOrderItemDto orderItem = new StockOrderItemDto().create();
                orderItem.setParent(takeInOrder);

                orderItem.setMaterial(item.getMaterial());
                orderItem.setQuantity(item.getQuantity());
                orderItem.setPrice(item.getAcceptedInquiry().getPrice());
                orderItem.setDescription("由采购请求[" + purchaseRequest.getLabel() + "]生成的入库明细项目");


                takeInOrder.addItem(orderItem);
            }

            PurchaseTakeInDto takeIn = new PurchaseTakeInDto().create();
            takeInOrder.setJob(takeIn);
            takeIn.setPurchaseRequest(purchaseRequest);
            takeIn.setStockOrder(takeInOrder);
            purchaseRequest.addTakeIn(takeIn);
        }
    }

}
