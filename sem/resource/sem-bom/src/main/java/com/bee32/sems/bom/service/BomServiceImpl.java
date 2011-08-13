package com.bee32.sems.bom.service;

import javax.inject.Inject;

import org.springframework.transaction.annotation.Transactional;

import com.bee32.plover.arch.EnterpriseService;
import com.bee32.plover.orm.dao.CommonDataManager;
import com.bee32.sems.bom.entity.Component;
import com.bee32.sems.bom.entity.MaterialPriceStrategy;
import com.bee32.sems.bom.entity.MaterialPriceStrategyRepository;
import com.bee32.sems.bom.entity.Product;
import com.bee32.sems.log.LogDesc;

public class BomServiceImpl
        extends EnterpriseService
        implements BomService {

    @Inject
    private CommonDataManager productRepository;

    @Inject
    private CommonDataManager partRepository;

    @Inject
    private MaterialPriceStrategyRepository materialPriceStrategyRepository;

    @Override
    @Transactional
    @LogDesc(actionName = "BOM设置->增加BOM")
    public Product addProduct(Product p) {
        productRepository.saveOrUpdate(p);
        return p;
    }

    @Override
    @Transactional
    @LogDesc(actionName = "BOM设置->更新BOM")
    public Product updateProduct(Product p) {
        productRepository.saveOrUpdate(p);
        return p;
    }

    @Override
    @Transactional
    @LogDesc(actionName = "BOM设置->删除BOM")
    public Product delteProduct(Product p) {
        productRepository.delete(p);
        return p;
    }

    @Override
    @Transactional
    @LogDesc(actionName = "BOM设置->设置BOM历史")
    public Product setHistory(Product product, Product history) {
        Product productFromRepo = productRepository.fetch(Product.class, product.getId());
        productFromRepo.setHistory(history);
        productRepository.saveOrUpdate(productFromRepo);
        return product;
    }

    @Override
    @Transactional
    @LogDesc(actionName = "BOM设置->增加BOM部件")
    public Component addComponent(Component component) {
        partRepository.saveOrUpdate(component);
        return component;
    }

    @Override
    @Transactional
    @LogDesc(actionName = "BOM设置->更新BOM部件")
    public Component updateComponent(Component component) {
        partRepository.saveOrUpdate(component);
        return component;
    }

    @Override
    @Transactional
    @LogDesc(actionName = "BOM设置->删除BOM部件")
    public Component deleteComponent(Component component) {
        partRepository.delete(component);
        return component;
    }

    @Override
    @Transactional
    @LogDesc(actionName = "BOM设置->物料价格获取策略设置")
    public MaterialPriceStrategy saveMaterialPriceStrategy(MaterialPriceStrategy materialPriceStrategy) {
        materialPriceStrategyRepository.save(materialPriceStrategy);
        return materialPriceStrategy;
    }

}
