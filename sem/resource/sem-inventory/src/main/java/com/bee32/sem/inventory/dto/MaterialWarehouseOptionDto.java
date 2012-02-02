package com.bee32.sem.inventory.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.free.NotImplementedException;
import javax.free.ParseException;

import com.bee32.plover.arch.util.IdComposite;
import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.ox1.c.CEntityDto;
import com.bee32.sem.frame.ui.IEnclosedObject;
import com.bee32.sem.inventory.entity.MaterialWarehouseOption;

public class MaterialWarehouseOptionDto
        extends CEntityDto<MaterialWarehouseOption, Long>
        implements IEnclosedObject<MaterialDto> {

    private static final long serialVersionUID = 1L;
    public static final int MATERIAL = 1;

    MaterialDto material;
    StockWarehouseDto warehouse;
    BigDecimal safetyStock = new BigDecimal(1);
    int stkPeriod = 365;

    @Override
    protected void _marshal(MaterialWarehouseOption source) {
        if (selection.contains(MATERIAL))
            this.material = mref(MaterialDto.class, source.getMaterial());
        this.warehouse = mref(StockWarehouseDto.class, source.getWarehouse());
        this.safetyStock = source.getSafetyStock();
        this.stkPeriod = source.getStkPeriod();
    }

    @Override
    protected void _unmarshalTo(MaterialWarehouseOption target) {
        merge(target, "material", material);
        merge(target, "warehouse", warehouse);
        target.setSafetyStock(safetyStock);
        target.setStkPeriod(stkPeriod);
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
        throw new NotImplementedException();
    }

    @Override
    public MaterialDto getEnclosingObject() {
        return getMaterial();
    }

    @Override
    public void setEnclosingObject(MaterialDto enclosingObject) {
        setMaterial(enclosingObject);
    }

    public MaterialDto getMaterial() {
        return material;
    }

    public void setMaterial(MaterialDto material) {
        this.material = material;
    }

    public StockWarehouseDto getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(StockWarehouseDto warehouse) {
        this.warehouse = warehouse;
    }

    public BigDecimal getSafetyStock() {
        return safetyStock;
    }

    public void setSafetyStock(BigDecimal safetyStock) {
        this.safetyStock = safetyStock;
    }

    public int getStkPeriod() {
        return stkPeriod;
    }

    public void setStkPeriod(int stkPeriod) {
        this.stkPeriod = stkPeriod;
    }

    @Override
    protected Serializable naturalId() {
        return new IdComposite(naturalId(material), naturalId(warehouse));
    }

}
