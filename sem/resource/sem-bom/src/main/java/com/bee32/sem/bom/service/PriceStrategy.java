package com.bee32.sem.bom.service;

import java.math.BigDecimal;
import java.util.Collection;

import com.bee32.plover.arch.util.EnumAlt;
import com.bee32.sem.bom.entity.Part;
import com.bee32.sem.bom.entity.PartItem;
import com.bee32.sem.inventory.entity.Material;
import com.bee32.sem.world.monetary.FxrQueryException;

public abstract class PriceStrategy
        extends EnumAlt<Character, PriceStrategy> {

    private static final long serialVersionUID = 1L;

    public PriceStrategy(char value, String name) {
        super(value, name);
    }

    public static PriceStrategy forName(String name) {
        return forName(PriceStrategy.class, name);
    }

    public static Collection<PriceStrategy> values() {
        return values(PriceStrategy.class);
    }

    public static PriceStrategy valueOf(Character value) {
        return valueOf(PriceStrategy.class, value);
    }

    public static PriceStrategy valueOf(char value) {
        return valueOf(new Character(value));
    }

    /**
     * 计算零件的价格
     */
    public BigDecimal getPrice(Part part)
            throws MaterialPriceNotFoundException, FxrQueryException {

        BigDecimal total = part.getExtraCost();

        for (PartItem child : part.getChildren()) {
            Part _part = child.getPart();
            Material _material = child.getMaterial();

            BigDecimal _quantity = child.getQuantity();
            BigDecimal _total = new BigDecimal(0);

            if (_part != null) {
                BigDecimal _extra = _part.getExtraCost();
                _total = _total.add(_extra);
            } else if (_material != null) {
                BigDecimal _raw = getPrice(_material);
                _total = _total.add(_raw);
            }

            _total = _total.multiply(_quantity);
            total = total.add(_total);
        }

        return total;
    }

    /**
     * 获取物料的价格
     */
    public abstract BigDecimal getPrice(Material material)
            throws MaterialPriceNotFoundException, FxrQueryException;

    /** 平均价格策略 */
    public static AveragePrice AVERAGE = new AveragePrice('m', "average");

    /** 最新价格策略 */
    public static LatestPrice LATEST = new LatestPrice('z', "latest");

}
