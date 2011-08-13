package com.bee32.sems.bom.service;

import java.util.*;

import javax.inject.Inject;

import com.bee32.plover.arch.EnterpriseService;
import com.bee32.sems.bom.entity.*;
import com.bee32.sems.inventory.entity.Warehouse;
import com.bee32.sems.inventory.entity.WarehouseRepository;
import com.bee32.sems.inventory.service.InventoryQueryService;
import com.bee32.sems.inventory.service.InventorySetupService;
import com.bee32.sems.material.entity.Material;

public class PriceServiceImpl extends EnterpriseService implements PriceService {

    @Inject
    private ProductRepository productRepository;

    @Inject
    private ComponentRepository componentRepository;

    @Inject
    private MaterialPriceStrategyRepository materialPriceStrategyRepository;

    @Inject
    private InventoryQueryService inventoryQueryService;

    @Inject
    private WarehouseRepository warehouseRepository;


    @Override
    public Double getPrice(Product product) throws MaterialPriceNotFoundException {
        double totalPrice = product.getPricePart();

        List<Component> components = componentRepository.listByProduct(product.getId());

        for(Component comp : components) {

            Product subProduct = productRepository.findByMaterial(comp.getMaterial());

            if(subProduct != null) {
                totalPrice += getPrice(subProduct);
            } else {
                totalPrice += getRawMaterialPrice(comp.getMaterial()) * comp.getQuantity();
            }
        }

        return totalPrice;
    }

    @Override
    public MaterialPriceStrategy getMaterialPriceStrategy() {
        return materialPriceStrategyRepository.get();
    }

    @Override
    public Double getRawMaterialPrice(Material material) throws MaterialPriceNotFoundException {
        List<Long> materialIds = new ArrayList<Long>();
        materialIds.add(material.getId());

        List<Warehouse> warehouses = warehouseRepository.list();
        SortedMap<Date, Double> sortedPrices = new TreeMap<Date, Double>();
        for(Warehouse w : warehouses) {
            List<Object[]> ledgerList = inventoryQueryService.queryStockLedger(w.getId(), materialIds);
            for(Object[] row : ledgerList) {
                sortedPrices.put((Date)row[12], (Double)row[10]);   //12为入库日期,10为单价
            }
        }

        if(sortedPrices.size() <= 0)
            throw new MaterialPriceNotFoundException();

        MaterialPriceStrategy materialPriceStrategy = getMaterialPriceStrategy();
        //默认如果没有设置原材料价格获取策略，则为取最新价格
        Strategy strategy = Strategy.NEWESTPRICE;
        if(materialPriceStrategy != null) {
            strategy = materialPriceStrategy.getStrategy();
        }

        double rawMaterialPrice = 0;

        switch(strategy) {
            case NEWESTPRICE:
                rawMaterialPrice = sortedPrices.get(sortedPrices.lastKey());
                break;
            case AVGPRICE:
                double sumPrice = 0;
                for(Double eachPrice : sortedPrices.values()) {
                    sumPrice += eachPrice;
                }
                rawMaterialPrice = sumPrice / sortedPrices.size();
                break;
        }

        return rawMaterialPrice;
    }
}
