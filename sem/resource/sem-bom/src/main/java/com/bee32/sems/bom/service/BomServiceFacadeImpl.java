package com.bee32.sems.bom.service;

import java.util.List;

import javax.inject.Inject;

import com.bee32.icsf.access.annotation.AccessCheck;
import com.bee32.plover.arch.EnterpriseServiceFacade;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.sems.bom.dto.ComponentDTO;
import com.bee32.sems.bom.dto.MaterialPriceStrategyDTO;
import com.bee32.sems.bom.dto.ProductDTO;
import com.bee32.sems.bom.entity.*;
import com.bee32.sems.material.entity.MaterialImpl;
import com.bee32.sems.material.entity.MaterialRepository;
import com.bee32.sems.org.dao.PersonDao;
import com.bee32.sems.org.entity.Person;

public class BomServiceFacadeImpl
        extends EnterpriseServiceFacade
        implements BomServiceFacade {
    @Inject
    private BomService bomService;

    @Inject
    private ProductRepository productRepository;

    @Inject
    private ComponentRepository componentRepository;

    @Inject
    private MaterialPriceStrategyRepository materialPriceStrategyRepository;

    @Inject
    private PersonDao personRepository;

    @Inject
    private MaterialRepository materialRepository;

    @Inject
    private PriceService priceService;

    @AccessCheck
    @Override
    public ProductDTO getProduct(Long productId) {
        Product p = productRepository.get(productId);
        return new ProductDTO(p);
    }

    @AccessCheck
    @Override
    public List<ProductDTO> listProduct(Integer start, Integer count) {
        List<Product> products = productRepository.listPart(start, count);
        return DTOs.marshalList(ProductDTO.class, products);
    }

    @AccessCheck
    @Override
    public long productCount() {
        return productRepository.count();
    }

    @AccessCheck
    @Override
    public void addProduct(ProductDTO productDTO) {
        Product product = new Product();

        Person creator = null;
        if (productDTO.getCreator() != null) {
            creator = personRepository.get(productDTO.getCreator().getId());
        }

        MaterialImpl material = null;
        if (productDTO.getMaterial() != null) {
            material = (MaterialImpl) materialRepository.get(productDTO.getMaterial().getId());
        }

        product.setCreator(creator);
        product.setUpdateBy(null);

        product.setHistory(null);
        product.setMaterial(material);

        product.setDate(productDTO.getDate());
        product.setValidDateFrom(productDTO.getValidDateFrom());
        product.setValidDateTo(productDTO.getValidDateTo());

        product.setWage(productDTO.getWage());
        product.setOtherFee(productDTO.getOtherFee());
        product.setElectricityFee(productDTO.getElectricityFee());
        product.setEquipmentCost(productDTO.getEquipmentCost());

        bomService.addProduct(product);

    }

    @AccessCheck
    @Override
    public void updateProduct(ProductDTO productDTO) {
        Product product = productRepository.get(productDTO.getId());

        Person updateBy = null;
        if (productDTO.getUpdateBy() != null) {
            updateBy = personRepository.get(productDTO.getUpdateBy().getId());
        }

        Product history = null;
        if (productDTO.getHistory() != null) {
            history = productRepository.get(productDTO.getHistory().getId());
        }

        MaterialImpl material = null;
        if (productDTO.getMaterial() != null) {
            material = (MaterialImpl) materialRepository.get(productDTO.getMaterial().getId());
        }

        product.setUpdateBy(updateBy);

        product.setHistory(history);
        product.setMaterial(material);

        product.setDate(productDTO.getDate());
        product.setValidDateFrom(productDTO.getValidDateFrom());
        product.setValidDateTo(productDTO.getValidDateTo());

        product.setWage(productDTO.getWage());
        product.setOtherFee(productDTO.getOtherFee());
        product.setElectricityFee(productDTO.getElectricityFee());
        product.setEquipmentCost(productDTO.getEquipmentCost());

        bomService.updateProduct(product);
    }

    @AccessCheck
    @Override
    public void deleteProduct(Long productId) {
        Product product = productRepository.get(productId);
        bomService.delteProduct(product);
    }

    @AccessCheck
    @Override
    public void setProductHistory(Long productId, Long historyId) {
        Product product = productRepository.get(productId);
        Product history = productRepository.get(historyId);
        bomService.setHistory(product, history);

    }

    @AccessCheck
    @Override
    public ComponentDTO getComponent(Long componentId) {
        Component c = componentRepository.get(componentId);
        return new ComponentDTO(c);
    }

    @AccessCheck
    @Override
    public List<ComponentDTO> listComponent(Long productId) {
        List<Component> components = componentRepository.listByProduct(productId);
        return DTOs.marshalList(ComponentDTO.class, components);
    }

    @AccessCheck
    @Override
    public void addComponent(ComponentDTO componentDTO) {
        Component component = new Component();

        Product product = productRepository.get(componentDTO.getProductId());

        MaterialImpl material = null;
        if (componentDTO.getMaterial() != null) {
            material = (MaterialImpl) materialRepository.get(componentDTO.getMaterial().getId());
        }

        component.setDesc(componentDTO.getDesc());
        component.setMaterial(material);
        component.setProduct(product);
        component.setQuantity(componentDTO.getQuantity());
        component.setValid(componentDTO.getValid());
        component.setValidDateFrom(componentDTO.getValidDateFrom());
        component.setValidDateTo(componentDTO.getValidDateTo());

        bomService.addComponent(component);

    }

    @AccessCheck
    @Override
    public void updateComponent(ComponentDTO componentDTO) {
        Component component = componentRepository.get(componentDTO.getId());

        Product product = productRepository.get(componentDTO.getProductId());

        MaterialImpl material = null;
        if (componentDTO.getMaterial() != null) {
            material = (MaterialImpl) materialRepository.get(componentDTO.getMaterial().getId());
        }

        component.setDesc(componentDTO.getDesc());
        component.setMaterial(material);
        component.setProduct(product);
        component.setQuantity(componentDTO.getQuantity());
        component.setValid(componentDTO.getValid());
        component.setValidDateFrom(componentDTO.getValidDateFrom());
        component.setValidDateTo(componentDTO.getValidDateTo());

        bomService.updateComponent(component);

    }

    @AccessCheck
    @Override
    public void deleteComponent(Long componentId) {
        Component component = componentRepository.get(componentId);
        bomService.deleteComponent(component);
    }

    @AccessCheck
    @Override
    public MaterialPriceStrategyDTO getMaterialPriceStragegy() {
        MaterialPriceStrategy s = materialPriceStrategyRepository.get();
        return new MaterialPriceStrategyDTO(s);
    }

    @AccessCheck
    @Override
    public void saveMaterialPriceStrategy(MaterialPriceStrategyDTO materialPriceStrategyDTO) {
        MaterialPriceStrategy materialPriceStrategy = materialPriceStrategyRepository.get();
        if(materialPriceStrategy == null) {
            materialPriceStrategy = new MaterialPriceStrategy();
        }
        materialPriceStrategy.setMemo(materialPriceStrategyDTO.getMemo());
        materialPriceStrategy.setStrategy(Strategy.parseInt(materialPriceStrategyDTO.getStrategy()));

        bomService.saveMaterialPriceStrategy(materialPriceStrategy);
    }

    @Override
    public Double productPrice(ProductDTO productDTO) throws MaterialPriceNotFoundException {
        Product product = productRepository.get(productDTO.getId());
        return priceService.getPrice(product);
    }
}
