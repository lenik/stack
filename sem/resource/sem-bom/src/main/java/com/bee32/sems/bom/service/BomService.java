package com.bee32.sems.bom.service;

import com.bee32.sems.bom.entity.Component;
import com.bee32.sems.bom.entity.MaterialPriceStrategy;
import com.bee32.sems.bom.entity.Product;

public interface BomService {


    public Product addProduct(Product p);


    public Product updateProduct(Product p);


    public Product delteProduct(Product p);


    public Product setHistory(Product product, Product history);


    public Component addComponent(Component component);


    public Component updateComponent(Component component);


    public Component deleteComponent(Component component);


    public MaterialPriceStrategy saveMaterialPriceStrategy(MaterialPriceStrategy materialPriceStrategy);

}
