package com.bee32.sem.purchase.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

import com.bee32.plover.arch.DataService;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.sem.inventory.dto.MaterialDto;
import com.bee32.sem.inventory.dto.MaterialWarehouseOptionDto;
import com.bee32.sem.inventory.dto.StockOrderItemDto;
import com.bee32.sem.inventory.entity.Material;
import com.bee32.sem.inventory.entity.StockItemList;
import com.bee32.sem.inventory.service.IStockQuery;
import com.bee32.sem.inventory.service.StockQueryOptions;
import com.bee32.sem.purchase.dto.MaterialPlanDto;
import com.bee32.sem.purchase.dto.MaterialPlanItemDto;
import com.bee32.sem.purchase.dto.PlanOrderDto;
import com.bee32.sem.purchase.dto.PurchaseRequestDto;
import com.bee32.sem.purchase.dto.PurchaseRequestItemDto;

public class PurchaseService extends DataService {
    @Inject
    private IStockQuery stockQuery;

    /**
     * 根据传入的物料计划，结合当前库存和物料的安全库存，计算所需要采购的量
     *
     * 注：如果同一物料分布在多个仓库，此处目前数量合并计算
     */
    public List<PurchaseRequestItemDto> calcMaterialRequirement(PurchaseRequestDto purchaseRequest, List<MaterialPlanDto> plans) {
        Map<MaterialDto, BigDecimal> need = new HashMap<MaterialDto, BigDecimal>();
        Map<MaterialDto, BigDecimal> alreadyHave = new HashMap<MaterialDto, BigDecimal>();

        for(MaterialPlanDto p : plans) {

            for(MaterialPlanItemDto item : p.getItems()) {
                BigDecimal needQuantity = need.get(item.getMaterial());
                if(needQuantity != null) {
                    need.put(item.getMaterial(), item.getQuantity().add(needQuantity));
                } else {
                    need.put(item.getMaterial(), item.getQuantity());
                }
            }

            List<PlanOrderDto> orders = p.getPlanOrders();
            if(orders != null) {
                for(PlanOrderDto order : orders) {
                    for(StockOrderItemDto item : order.getItems()) {
                        BigDecimal alreadyHaveQuantity = alreadyHave.get(item.getMaterial());
                        if(alreadyHaveQuantity != null) {
                            alreadyHave.put(item.getMaterial(), item.getQuantity().add(alreadyHaveQuantity));
                        } else {
                            alreadyHave.put(item.getMaterial(), item.getQuantity());
                        }
                    }

                }
            }
        }


        List<PurchaseRequestItemDto> requestItems = new ArrayList<PurchaseRequestItemDto>();

        Set<MaterialDto> materialSet =  need.keySet();
        for(MaterialDto m : materialSet) {
           //BigDecimal alreadyHaveQuantity = alreadyHave.get(m);
           BigDecimal needQuantity = need.get(m);


           //取得物料的安全库存
           Material _m = asFor(Material.class).get(m.getId());
           m = DTOs.marshal(MaterialDto.class, MaterialDto.OPTIONS, _m);
           BigDecimal safetyQuantity = new BigDecimal(0);
           List<MaterialWarehouseOptionDto> options = m.getOptions();
           if(options != null) {
               for(MaterialWarehouseOptionDto option : options) {
                   safetyQuantity = safetyQuantity.add(option.getSafetyStock());
               }
           }

           //取得实际库存
           Calendar c = Calendar.getInstance();
           c.set(Calendar.HOUR_OF_DAY, 23);
           c.set(Calendar.MINUTE, 59);
           c.set(Calendar.SECOND, 59);
           c.set(Calendar.MILLISECOND, 999);

           StockQueryOptions opts = new StockQueryOptions(c.getTime());
           opts.setCBatch(null, false);
           opts.setLocation(null, false);
           opts.setWarehouse(null, false);

           List<Material> ms = new ArrayList<Material>();
           ms.add(_m);
           StockItemList list = stockQuery.getActualSummary(ms, opts);
           List<StockOrderItemDto> queryResult = DTOs.marshalList(StockOrderItemDto.class, list.getItems());
           BigDecimal actualQuantity = new BigDecimal(0);
           if(queryResult != null && queryResult.size() > 0) {
               for(StockOrderItemDto item : queryResult) {
                   actualQuantity = actualQuantity.add(item.getQuantity());
               }
           }

           //计算需要采购的数量
           BigDecimal requestQuantity = new BigDecimal(0);
           if(needQuantity.compareTo(actualQuantity.subtract(safetyQuantity)) <= 0) {
               //不用采购
               requestQuantity = null;
           } else {
               requestQuantity = needQuantity.subtract(actualQuantity.subtract(safetyQuantity));
           }


           if(requestQuantity != null) {
               PurchaseRequestItemDto requestItem = new PurchaseRequestItemDto().create();
               requestItem.setMaterial(m);
               requestItem.setPurchaseRequest(purchaseRequest);
               requestItem.setQuantity(requestQuantity);

               requestItems.add(requestItem);
           }
        }

        if(requestItems.size() > 0)
            return requestItems;
        return null;
    }
}
