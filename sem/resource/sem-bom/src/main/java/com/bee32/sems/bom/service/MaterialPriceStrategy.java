package com.bee32.sems.bom.service;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bee32.plover.arch.util.EnumAlt;
import com.bee32.plover.arch.util.NoSuchEnumException;
import com.bee32.plover.orm.ext.tree.TreeCriteria;
import com.bee32.plover.orm.util.IEntityMarshalContext;
import com.bee32.sem.inventory.entity.Material;
import com.bee32.sems.bom.entity.Component;

public abstract class MaterialPriceStrategy
        extends EnumAlt<String, MaterialPriceStrategy> {

    private static final long serialVersionUID = 1L;

    static final Map<String, MaterialPriceStrategy> nameMap = new HashMap<String, MaterialPriceStrategy>();
    static final Map<String, MaterialPriceStrategy> valueMap = new HashMap<String, MaterialPriceStrategy>();

    public MaterialPriceStrategy(String value, String name) {
        super(value, name);
    }

    @Override
    protected Map<String, MaterialPriceStrategy> getNameMap() {
        return nameMap;
    }

    @Override
    protected Map<String, MaterialPriceStrategy> getValueMap() {
        return valueMap;
    }

    public BigDecimal getPrice(IEntityMarshalContext ctx, Component product)
            throws MaterialPriceNotFoundException {

        BigDecimal total = product.getExtraCost();

        List<Component> children = ctx.asFor(Component.class).list(//
                TreeCriteria.childOf(product.getId()));

        for (Component child : children) {
            BigDecimal childExtra = child.getExtraCost();
            BigDecimal childRaw = getPrice(ctx, child.getMaterial());

            total = total.add(childExtra);
            total = total.add(childRaw);
        }

        return total;
    }

    public abstract BigDecimal getPrice(IEntityMarshalContext ctx, Material material)
            throws MaterialPriceNotFoundException;

    public static Collection<MaterialPriceStrategy> values() {
        Collection<MaterialPriceStrategy> values = valueMap.values();
        return Collections.unmodifiableCollection(values);
    }

    public static MaterialPriceStrategy valueOf(String value) {
        if (value == null)
            return null;

        MaterialPriceStrategy strategy = valueMap.get(value);
        if (strategy == null)
            throw new NoSuchEnumException(MaterialPriceStrategy.class, value);

        return strategy;
    }

    public static MaterialPriceStrategy nameOf(String altName) {
        MaterialPriceStrategy strategy = nameMap.get(altName);
        if (strategy == null)
            throw new NoSuchEnumException(MaterialPriceStrategy.class, altName);
        return strategy;
    }

    public static AveragePrice AVERAGE = new AveragePrice("AVG", "average");
    public static LatestPrice LATEST = new LatestPrice("LTS", "latest");

    static MaterialPriceStrategy defaultStrategy = LATEST;

    public static MaterialPriceStrategy getDefault() {
        return defaultStrategy;
    }

    public static void setDefault(MaterialPriceStrategy defaultStrategy) {
        MaterialPriceStrategy.defaultStrategy = defaultStrategy;
    }

}
