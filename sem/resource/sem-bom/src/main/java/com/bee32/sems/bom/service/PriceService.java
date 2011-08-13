package com.bee32.sems.bom.service;


import com.bee32.sems.bom.entity.MaterialPriceStrategy;
import com.bee32.sems.bom.entity.Product;
import com.bee32.sems.material.entity.Material;

/**
 * 计算某个产品的价格
 * 属于业务类,应该划在业务模型层中的service层
 * 此处因为应用层，facade层，业务service层没有区分，所以放在了一起
 * 产品价格计算方法见模型model/bom.asta中对PriceService的说明
 */
public interface PriceService {
    /**
     * 计算产品价格
     */
    public Double getPrice(Product product) throws MaterialPriceNotFoundException;

    /**
     * 取得当前原材料价格获取策略
     */
    public MaterialPriceStrategy getMaterialPriceStrategy();

    /**
     * 结合当前原材料价格获取策略取得原材料价格
     */
    public Double getRawMaterialPrice(Material material) throws MaterialPriceNotFoundException;
}
