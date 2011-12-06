package com.bee32.sem.purchase.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

import org.springframework.transaction.annotation.Transactional;

import com.bee32.plover.arch.DataService;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.sem.inventory.dto.MaterialDto;
import com.bee32.sem.inventory.dto.MaterialWarehouseOptionDto;
import com.bee32.sem.inventory.dto.StockOrderDto;
import com.bee32.sem.inventory.dto.StockOrderItemDto;
import com.bee32.sem.inventory.entity.Material;
import com.bee32.sem.inventory.entity.StockItemList;
import com.bee32.sem.inventory.entity.StockOrder;
import com.bee32.sem.inventory.entity.StockOrderSubject;
import com.bee32.sem.inventory.service.IStockQuery;
import com.bee32.sem.inventory.service.StockQueryOptions;
import com.bee32.sem.people.dto.OrgDto;
import com.bee32.sem.purchase.dto.MaterialPlanDto;
import com.bee32.sem.purchase.dto.MaterialPlanItemDto;
import com.bee32.sem.purchase.dto.OrderHolderDto;
import com.bee32.sem.purchase.dto.PlanOrderDto;
import com.bee32.sem.purchase.dto.PurchaseAdviceDto;
import com.bee32.sem.purchase.dto.PurchaseRequestDto;
import com.bee32.sem.purchase.dto.PurchaseRequestItemDto;
import com.bee32.sem.purchase.entity.OrderHolder;

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
               requestItem.setPlanQuantity(requestQuantity);    //从物料计划计算采购请求量时，把计划采购量首先设置为和计算量相同

               requestItems.add(requestItem);
           }
        }

        if(requestItems.size() > 0)
            return requestItems;
        return null;
    }



    /**
     * 根据采购请求和所选择的仓库id,自动生成采购入库单
     */
    @Transactional
    public void genTakeInStockOrder(PurchaseRequestDto purchaseRequest)
            throws NoPurchaseAdviceException, AdviceHaveNotVerifiedException, TakeInStockOrderAlreadyGeneratedException {

        //检测purchaseReqeust是否已经生成过采购入库单
        if (purchaseRequest.getOrderHolders() != null && purchaseRequest.getOrderHolders().size() > 0) {
            throw new TakeInStockOrderAlreadyGeneratedException();
        }

        //检测有没有询价和审核采购建议
        for(PurchaseRequestItemDto item : purchaseRequest.getItems()) {
            PurchaseAdviceDto advice = item.getPurchaseAdvice();
            if(advice == null) {
                throw new NoPurchaseAdviceException();
            }

            //TODO verify check
//            if(!advice.isVerified()) {
//                throw new AdviceHaveNotVerifiedException();
//            }
        }

        Map<String, List<PurchaseRequestItemDto>> splitMap //
            = new HashMap<String, List<PurchaseRequestItemDto>>();

        //按仓库和供应商区分要生成的采购入库单
        for(PurchaseRequestItemDto item : purchaseRequest.getItems()) {
            //以warehouseId和对应供应商的id组合成   x&y 的形式
            StringBuilder builder = new StringBuilder();
            builder.append(item.getWarehouseId());
            builder.append("&");
            builder.append(item.getPurchaseAdvice().getPreferredInquiry().getParty().getId());

            if (!splitMap.keySet().contains(builder.toString())) {
                List<PurchaseRequestItemDto> newItemList = new ArrayList<PurchaseRequestItemDto>();
                splitMap.put(builder.toString(), newItemList);
            }

            List<PurchaseRequestItemDto> itemList = splitMap.get(builder.toString());

            itemList.add(item);
        }


        //3.按仓库生成subject为takeIn的StockOrder->StockOrderItem*
        for(List<PurchaseRequestItemDto> itemList : splitMap.values()) {

            StockOrderDto stockOrder = new StockOrderDto().create();
            stockOrder.setSubject(StockOrderSubject.TAKE_IN);
            stockOrder.setLabel("从采购请求生成的入库单");
            stockOrder.setOrg((OrgDto) itemList.get(0).getPurchaseAdvice().getPreferredInquiry().getParty());

            for(PurchaseRequestItemDto item : itemList) {
                StockOrderItemDto stockOrderItem = new StockOrderItemDto().create();

                stockOrderItem.setMaterial(item.getMaterial());
                stockOrderItem.setQuantity(item.getQuantity());
                stockOrderItem.setPrice(item.getPurchaseAdvice().getPreferredInquiry().getPrice());
                stockOrderItem.setDescription("由采购请求生成的入库明细项目");

                stockOrder.addItem(stockOrderItem);

                //保存以上StockOrder*
                StockOrder _stockOrder = stockOrder.unmarshal();
                asFor(StockOrder.class).saveOrUpdate(_stockOrder);

                //填充并保存OrderHolder,以在PurchaseRequest和StockOrder之间建立关联
                OrderHolderDto orderHolder = new OrderHolderDto().create();
                orderHolder.setPurchaseRequest(purchaseRequest);
                orderHolder.setStockOrder(DTOs.marshal(StockOrderDto.class, _stockOrder)); //重新marshal，以便dto中包含id

                asFor(OrderHolder.class).saveOrUpdate(orderHolder.unmarshal());
            }
        }
    }
}
