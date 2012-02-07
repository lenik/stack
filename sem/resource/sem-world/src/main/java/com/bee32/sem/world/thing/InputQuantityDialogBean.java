package com.bee32.sem.world.thing;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bee32.plover.faces.utils.SelectableList;
import com.bee32.plover.orm.util.DataViewBean;
import com.bee32.plover.ox1.config.DecimalConfig;

public class InputQuantityDialogBean
        extends DataViewBean
        implements DecimalConfig {

    private static final long serialVersionUID = 1L;

    static Logger logger = LoggerFactory.getLogger(InputQuantityDialogBean.class);

    String header = "Input quantity in specific unit...";

    static final char UNIT = 'u';
    static final char VIEW = 'v';
    char mode = UNIT;

    UnitDto unitUnit;
    BigDecimal unitQuantity;

    UnitDto viewUnit;
    BigDecimal viewQuantity;
    UnitConvDto unitConv;

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        if (header == null)
            throw new NullPointerException("header");
        this.header = header;
    }

    public String getMode() {
        return String.valueOf(mode);
    }

    public void setMode(String mode) {
        if (mode == null)
            return; // throw new NullPointerException("mode");
        if (mode.equals("u"))
            this.mode = UNIT;
        else if (mode.equals("v"))
            this.mode = VIEW;
        else
            throw new IllegalArgumentException("Bad mode: " + mode);
    }

    public BigDecimal getQuantity() {
        if (mode == UNIT)
            return getUnitQuantity();
        else
            return getViewQuantity();
    }

    public void setQuantity(BigDecimal quantity) {
        if (mode == UNIT)
            setUnitQuantity(quantity);
        else
            setViewQuantity(quantity);
    }

    public UnitDto getUnitUnit() {
        return unitUnit;
    }

    public void setUnitUnit(UnitDto unitUnit) {
        this.unitUnit = unitUnit;
    }

    public BigDecimal getUnitQuantity() {
        return unitQuantity;
    }

    public void setUnitQuantity(BigDecimal unitQuantity) {
        if (unitQuantity == null)
            throw new NullPointerException("unitQuantity");
        this.unitQuantity = unitQuantity;
    }

    public SelectableList<UnitDto> getViewUnits() {
        List<UnitDto> viewUnits = new ArrayList<UnitDto>();
        if (unitConv != null && !unitConv.isNull()) {
            Map<UnitDto, Double> scaleMap = unitConv.getScaleMap();
            if (scaleMap == null) {
                logger.warn("scaleMap is null: " + unitConv);
            } else {
                viewUnits.addAll(scaleMap.keySet());
            }
        }
        return SelectableList.decorate(viewUnits);
    }

    public UnitDto getViewUnit() {
        return viewUnit;
    }

    public void setViewUnit(UnitDto viewUnit) {
        this.viewUnit = viewUnit;
    }

    public String getViewUnitId() {
        UnitDto viewUnit = getViewUnit();
        if (viewUnit == null)
            return "";
        else
            return viewUnit.getId();
    }

    public BigDecimal getViewQuantity() {
        return viewQuantity;
    }

    public void setViewQuantity(BigDecimal viewQuantity) {
        if (viewQuantity == null)
            throw new NullPointerException("viewQuantity");
        this.viewQuantity = viewQuantity;
    }

    public UnitConvDto getUnitConv() {
        return unitConv;
    }

    public void setUnitConv(UnitConvDto unitConv) {
        if (unitConv != null && unitConv.isNull())
            unitConv = null;
        this.unitConv = unitConv;
    }

    BigDecimal getViewScale() {
        Double viewScale = unitConv.getScale(viewUnit.getId());
        if (viewScale == null || viewScale.doubleValue() == Double.NaN) {
            uiLogger.error("换算单位的换算率没有定义");
            return null;
        }

        if (viewScale.doubleValue() == 0) {
            uiLogger.error("换算率为0：请检查并修复换算表：" + unitConv);
            return null;
        }
        return BigDecimal.valueOf(viewScale);
    }

    public void toUnitQuantity() {
        unitQuantity = toUnitQuantity(viewQuantity);
    }

    public BigDecimal toUnitQuantity(BigDecimal viewQuantity) {
        // 没有指定换算表，则不换算
        if (unitConv == null)
            return viewQuantity;

        // 没有选择换算单位，则不换算
        if (viewUnit == null)
            return viewQuantity;

        // 换算率不可用
        BigDecimal viewScale = getViewScale();
        if (viewScale == null)
            return null;

        // 主单位数量 = 换算单位数量 / 换算率
        BigDecimal unitQuantity = viewQuantity.divide(viewScale, QTY_ITEM_SCALE, RoundingMode.HALF_UP);
        return unitQuantity;
    }

    public void toViewQuantity() {
        viewQuantity = toViewQuantity(unitQuantity);
    }

    public BigDecimal toViewQuantity(BigDecimal unitQuantity) {
        if (unitConv == null || viewUnit == null) {
            // 没有指定换算表，或没有选择换算单位，则不换算
            return unitQuantity;
        }

        BigDecimal viewScale = getViewScale();
        if (viewScale == null)
            return null;

        // 换算单位 = 主单位数量 * 换算率
        BigDecimal viewQuantity = unitQuantity.multiply(viewScale, QTY_ITEM_CONTEXT);
        return viewQuantity;
    }

}
