package com.bee32.sem.inventory.util;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.collections15.Transformer;
import org.apache.commons.collections15.functors.NOPTransformer;
import org.apache.commons.collections15.map.TransformedMap;

import com.bee32.sem.inventory.dto.MaterialDto;
import com.bee32.sem.inventory.entity.Material;

public class ConsumptionMap
        extends HashMap<Number, BigDecimal> {

    private static final long serialVersionUID = 1L;

    Map<Number, Material> entityIndex = new HashMap<>();
    Map<Number, MaterialDto> dtoIndex = new HashMap<>();

    /**
     * @see #getConsumption(Number)
     */
    @Deprecated
    @Override
    public BigDecimal get(Object key) {
        return super.get(key);
    }

    /**
     * @see #consume(Material, BigDecimal)
     * @see #supply(Material, BigDecimal)
     */
    @Deprecated
    @Override
    public BigDecimal put(Number key, BigDecimal value) {
        return super.put(key, value);
    }

    public BigDecimal getConsumption(Number key) {
        BigDecimal consumption = super.get(key);
        if (consumption == null)
            consumption = BigDecimal.ZERO;
        return consumption;
    }

    public BigDecimal getConsumption(Material material) {
        return super.get(material.getId());
    }

    public BigDecimal getConsumption(MaterialDto material) {
        return super.get(material.getId());
    }

    public void consume(Number key, BigDecimal quantity) {
        if (quantity == null)
            throw new NullPointerException("quantity");
        BigDecimal consumption = super.get(key);
        if (consumption == null)
            consumption = quantity;
        else
            consumption = consumption.add(quantity);
        super.put(key, consumption);
    }

    public void consume(Material material, BigDecimal quantity) {
        consume(material.getId(), quantity);
        entityIndex.put(material.getId(), material);
    }

    public void consume(MaterialDto material, BigDecimal quantity) {
        consume(material.getId(), quantity);
        dtoIndex.put(material.getId(), material);
    }

    public void supply(Number key, BigDecimal quantity) {
        if (quantity == null)
            throw new NullPointerException("quantity");
        consume(key, quantity.negate());
    }

    public void supply(Material material, BigDecimal quantity) {
        supply(material.getId(), quantity);
        entityIndex.put(material.getId(), material);
    }

    public void supply(MaterialDto material, BigDecimal quantity) {
        supply(material.getId(), quantity);
        dtoIndex.put(material.getId(), material);
    }

    public void removeNegatives(boolean includeZero) {
        Iterator<Map.Entry<Number, BigDecimal>> iter = entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry<Number, BigDecimal> entry = iter.next();
            BigDecimal consumption = entry.getValue();
            if (includeZero) {
                if (consumption.signum() <= 0)
                    iter.remove();
            } else {
                if (consumption.signum() < 0)
                    iter.remove();
            }
        }
    }

    public Map<Number, Material> getEntityIndex() {
        return Collections.unmodifiableMap(entityIndex);
    }

    public Map<Number, MaterialDto> getDtoIndex() {
        return Collections.unmodifiableMap(dtoIndex);
    }

    class Index2Entity
            implements Transformer<Number, Material> {

        @Override
        public Material transform(Number input) {
            return entityIndex.get(input);
        }

    };

    class Index2Dto
            implements Transformer<Number, MaterialDto> {

        @Override
        public MaterialDto transform(Number input) {
            return dtoIndex.get(input);
        }

    };

    public Map<Material, BigDecimal> entityMap() {
        return TransformedMap.decorate(this, new Index2Entity(), NOPTransformer.getInstance());
    }

    public Map<MaterialDto, BigDecimal> dtoMap() {
        return TransformedMap.decorate(this, new Index2Dto(), NOPTransformer.getInstance());
    }

}
