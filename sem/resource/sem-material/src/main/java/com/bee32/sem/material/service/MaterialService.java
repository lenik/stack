package com.bee32.sem.material.service;

import com.bee32.plover.arch.DataService;
import com.bee32.sem.material.dto.MaterialAttributeDto;
import com.bee32.sem.material.dto.MaterialDto;
import com.bee32.sem.material.dto.MaterialPreferredLocationDto;
import com.bee32.sem.material.dto.MaterialPriceDto;
import com.bee32.sem.material.dto.MaterialWarehouseOptionDto;

public class MaterialService extends DataService {

    public MaterialDto copyMaterial(MaterialDto material) {
        MaterialDto newMaterial = new MaterialDto().create();
        newMaterial.setCategory(material.getCategory());
        newMaterial.setLabel(material.getLabel());
        newMaterial.setUnit(material.getUnit());
        newMaterial.setBarCode(material.getBarCode());
        newMaterial.setModelSpec(material.getModelSpec());
        newMaterial.setDescription(material.getDescription());

        for(MaterialAttributeDto attr : material.getAttributes()) {
            MaterialAttributeDto newAttr = new MaterialAttributeDto().create();
            newAttr.setMaterial(newMaterial);
            newAttr.setName(attr.getName());
            newAttr.setValue(attr.getValue());

            newMaterial.addAttribute(newAttr);
        }

        for(MaterialPriceDto price : material.getPrices()) {
            MaterialPriceDto newPrice = new MaterialPriceDto().create();
            newPrice.setPrice(price.getPrice());

            newMaterial.addPrice(newPrice);
        }

        newMaterial.setUnitConv(material.getUnitConv());

        for(MaterialPreferredLocationDto location : material.getPreferredLocations()) {
            MaterialPreferredLocationDto newLocation = new MaterialPreferredLocationDto().create();
            newLocation.setMaterial(newMaterial);
            newLocation.setLocation(location.getLocation());

            newMaterial.addPreferredLocation(newLocation);
        }

        for(MaterialWarehouseOptionDto option : material.getOptions()) {
            MaterialWarehouseOptionDto newOption = new MaterialWarehouseOptionDto().create();
            newOption.setMaterial(newMaterial);
            newOption.setWarehouse(option.getWarehouse());
            newOption.setSafetyStock(option.getSafetyStock());
            newOption.setStkPeriod(option.getStkPeriod());

            newMaterial.addOption(newOption);
        }


        return newMaterial;
    }
}
