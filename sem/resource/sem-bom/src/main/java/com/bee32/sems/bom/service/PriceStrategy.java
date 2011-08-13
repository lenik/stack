package com.bee32.sems.bom.service;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.bee32.plover.arch.util.EnumAlt;
import com.bee32.plover.arch.util.NoSuchEnumException;
import com.bee32.sem.inventory.entity.Material;
import com.bee32.sem.world.monetary.FxrQueryException;
import com.bee32.sems.bom.entity.Component;

public abstract class PriceStrategy
        extends EnumAlt<String, PriceStrategy> {

    private static final long serialVersionUID = 1L;

    static final Map<String, PriceStrategy> nameMap = new HashMap<String, PriceStrategy>();
    static final Map<String, PriceStrategy> valueMap = new HashMap<String, PriceStrategy>();

    public PriceStrategy(String value, String name) {
        super(value, name);
    }

    @Override
    protected Map<String, PriceStrategy> getNameMap() {
        return nameMap;
    }

    @Override
    protected Map<String, PriceStrategy> getValueMap() {
        return valueMap;
    }

    public static Collection<PriceStrategy> values() {
        Collection<PriceStrategy> values = valueMap.values();
        return Collections.unmodifiableCollection(values);
    }

    public static PriceStrategy valueOf(String value) {
        if (value == null)
            return null;

        PriceStrategy strategy = valueMap.get(value);
        if (strategy == null)
            throw new NoSuchEnumException(PriceStrategy.class, value);

        return strategy;
    }

    public static PriceStrategy nameOf(String altName) {
        PriceStrategy strategy = nameMap.get(altName);
        if (strategy == null)
            throw new NoSuchEnumException(PriceStrategy.class, altName);
        return strategy;
    }

    /**
     * 计算零件的价格
     */
    public BigDecimal getPrice(Component product)
            throws MaterialPriceNotFoundException, FxrQueryException {

        BigDecimal total = product.getExtraCost();

        for (Component child : product.getChildren()) {
            BigDecimal childExtra = child.getExtraCost();
            BigDecimal childRaw = getPrice(child.getMaterial());

            total = total.add(childExtra);
            total = total.add(childRaw);
        }

        return total;
    }

    /**
     * 获取物料的价格
     */
    public abstract BigDecimal getPrice(Material material)
            throws MaterialPriceNotFoundException, FxrQueryException;

    /** 平均价格策略 */
    public static AveragePrice AVERAGE = new AveragePrice("AVG", "average");

    /** 最新价格策略 */
    public static LatestPrice LATEST = new LatestPrice("LTS", "latest");

}
