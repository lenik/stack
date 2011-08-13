package com.bee32.sems.bom.service;

import java.util.List;

import com.bee32.sems.bom.dto.ComponentDTO;
import com.bee32.sems.bom.dto.MaterialPriceStrategyDTO;
import com.bee32.sems.bom.dto.ProductDTO;
import com.bee32.sems.bom.entity.Product;

public interface BomServiceFacade {


    public ProductDTO getProduct(Long productId);


    public List<ProductDTO> listProduct(Integer start, Integer count);


    public long productCount();


    public void addProduct(ProductDTO productDTO);


    public void updateProduct(ProductDTO productDTO);


    public void deleteProduct(Long productId);


    public void setProductHistory(Long productId, Long historyId);


    public ComponentDTO getComponent(Long componentId);


    public List<ComponentDTO> listComponent(Long productId);


    public void addComponent(ComponentDTO componentDTO);


    public void updateComponent(ComponentDTO componentDTO);


    public void deleteComponent(Long componentId);

    public void saveMaterialPriceStrategy(MaterialPriceStrategyDTO materialPriceStrategyDTO);

    public MaterialPriceStrategyDTO getMaterialPriceStragegy();

    public Double productPrice(ProductDTO productDTO) throws MaterialPriceNotFoundException;
}
