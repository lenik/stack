package com.bee32.sem.inventory.dto;

import java.util.Date;
import java.util.List;

import javax.free.Dates;
import javax.free.IllegalUsageException;
import javax.free.ParseException;

import com.bee32.plover.arch.util.IEnclosedObject;
import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.orm.entity.EntityUtil;
import com.bee32.plover.servlet.util.ThreadHttpContext;
import com.bee32.plover.site.SiteInstance;
import com.bee32.sem.inventory.SEMInventoryCustomization;
import com.bee32.sem.inventory.entity.AbstractStockOrder;
import com.bee32.sem.inventory.entity.StockItemState;
import com.bee32.sem.inventory.entity.StockOrderItem;
import com.bee32.sem.inventory.entity.StockOrderSubject;
import com.bee32.sem.inventory.util.BatchArray;
import com.bee32.sem.inventory.util.BatchArrayEntry;
import com.bee32.sem.material.dto.MaterialDto;
import com.bee32.sem.material.dto.MaterialPreferredLocationDto;
import com.bee32.sem.material.dto.StockLocationDto;
import com.bee32.sem.world.thing.AbstractItemDto;

public class StockOrderItemDto
        extends AbstractItemDto<StockOrderItem>
        implements IEnclosedObject<StockOrderDto> {

    private static final long serialVersionUID = 1L;

    StockOrderDto parent;
    MaterialDto material;
    BatchArray batchArray;
    Date expirationDate;
    StockLocationDto location;
    char stateChar;

    public StockOrderItemDto() {
        super();
    }

    public StockOrderItemDto(int fmask) {
        super(fmask);
    }

    @Override
    protected boolean isNegated() {
        StockOrderDto parent = getParent();
        if (parent == null || parent.isNull()) {
            throw new IllegalStateException(
                    "StockOrderItem's parent isn't set. you must call setParent before setQuantity");
        }

        StockOrderSubject subject = parent.getSubject();
        return subject.isNegative();
    }

    @Override
    protected void _copy() {
        batchArray = batchArray.clone();
    }

    @Override
    protected void _marshal(StockOrderItem source) {
        AbstractStockOrder<?> _parent = source.getParent();

        boolean tryBest = false;
        if (tryBest) {
            Class<?> parentClass = _parent == null ? AbstractStockOrder.class : _parent.getClass();
            Class<? extends StockOrderDto> parentDtoType = (Class<? extends StockOrderDto>) EntityUtil
                    .getDtoType(parentClass);
            parent = mref(parentDtoType, _parent);
        } else {
            parent = mref(StockOrderDto.class, _parent);
        }

        material = mref(MaterialDto.class, source.getMaterial());
        batchArray = BatchArray.expandCopy(source.getBatchArray());

        expirationDate = source.getExpirationDate();
        location = mref(StockLocationDto.class, source.getLocation());
    }

    @Override
    protected void _unmarshalTo(StockOrderItem target) {
        if (parent.isNull())
            throw new IllegalUsageException("StockOrderItem.parent isn't set");

        merge(target, "parent", parent);
        merge(target, "material", material);
        target.setBatchArray(batchArray.reduce());
        target.setExpirationDate(expirationDate);
        merge(target, "location", location);
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
        map.getString("batch");
        map.getString("state");
    }

    @Override
    public StockOrderDto getEnclosingObject() {
        return getParent();
    }

    @Override
    public void setEnclosingObject(StockOrderDto order) {
        setParent(order);

        boolean inputNew = false;
        StockOrderSubject subject = order.getSubject();
        if (subject.equals(StockOrderSubject.TAKE_IN))
            inputNew = true;
        if (subject.equals(StockOrderSubject.FACTORY_IN))
            inputNew = true;

        if (inputNew) {
            SiteInstance site = ThreadHttpContext.getSiteInstance();
            Boolean autoBatch = site.getBooleanProperty(SEMInventoryCustomization.AUTO_BATCH_KEY);
            if (autoBatch == Boolean.TRUE) {
                String date = Dates.YYYY_MM_DD.format(new Date());
                batchArray.setBatch0(date);
            }
        }
    }

    public StockOrderDto getParent() {
        return parent;
    }

    public void setParent(StockOrderDto parent) {
        if (parent == null)
            throw new NullPointerException("parent");
        this.parent = parent;
    }

    @Override
    protected Date getFxrDate() {
        return parent.getBeginTime();
    }

    public MaterialDto getMaterial() {
        return material;
    }

    public void setMaterial(MaterialDto material) {
        if (material == null)
            throw new NullPointerException("material");
        this.material = material;

        // Auto apply preferred location.
        if (location == null || location.isNull()) {
            if (!material.selection.contains(MaterialDto.PREFERRED_LOCATIONS))
                material = ctx.data.reload(material, MaterialDto.PREFERRED_LOCATIONS);
            List<MaterialPreferredLocationDto> preferredLocations = material.getPreferredLocations();
            if (!preferredLocations.isEmpty())
                this.location = preferredLocations.get(0).getLocation();
        }
    }

    public BatchArrayEntry[] getBatches() {
        return batchArray.getEntries();
    }

    public BatchArray getBatchArray() {
        return batchArray;
    }

    public void setBatchArray(BatchArray batchArray) {
        if (batchArray == null)
            throw new NullPointerException("batchArray");
        this.batchArray = batchArray;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public StockLocationDto getLocation() {
        return location;
    }

    public void setLocation(StockLocationDto location) {
        this.location = location;
    }

    public StockItemState getStockItemState() {
        int state = getState();
        return StockItemState.forValue(state);
    }

    public void setState(StockItemState stockItemState) {
        if (stockItemState == null)
            throw new NullPointerException("stockItemState");
        int state = stockItemState.getValue();
        setState(state);
    }

    public String getStateText() {
        StockItemState state = getStockItemState();
        return state.getDisplayName();
    }

}
