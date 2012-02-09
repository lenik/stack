package com.bee32.sem.inventory.web.business;

import java.math.BigDecimal;

import com.bee32.plover.orm.annotation.ForEntity;
import com.bee32.plover.orm.annotation.TypeParameter;
import com.bee32.sem.inventory.dto.StockOrderDto;
import com.bee32.sem.inventory.dto.StockOrderItemDto;
import com.bee32.sem.inventory.entity.StockOrder;
import com.bee32.sem.inventory.entity.StockOrderSubject;
import com.bee32.sem.inventory.tx.dto.StockTransferDto;
import com.bee32.sem.inventory.tx.entity.StockTransfer;
import com.bee32.sem.inventory.util.StockJobStepping;

@ForEntity(value = StockOrder.class, parameters = @TypeParameter(name = "_subject", value = "XFRI"))
public class StockOrderBean_XFER_IN
        extends AbstractStockOrderBean {

    private static final long serialVersionUID = 1L;

    StockOrderItemDto selectedSourceItem;
    StockOrderItemDto destItem;

    public StockOrderBean_XFER_IN() {
        subject = StockOrderSubject.XFER_IN;
    }

    @Override
    protected boolean configJobStepping(StockJobStepping stepping) {
        stepping.setJobClass(StockTransfer.class);
        stepping.setJobDtoClass(StockTransferDto.class);
        stepping.setInitiatorProperty("source");
        stepping.setInitiatorColumn("s1");
        stepping.setBindingProperty("dest");
        stepping.setBindingColumn("s2");
        return true;
    }

    public StockOrderItemDto getSelectedSourceItem() {
        return selectedSourceItem;
    }

    public void setSelectedSourceItem(StockOrderItemDto selectedSourceItem) {
        this.selectedSourceItem = selectedSourceItem;
    }

    public StockOrderItemDto getDestItem() {
        return destItem;
    }

    public void setDestItem(StockOrderItemDto destItem) {
        this.destItem = destItem;
    }

    public void copyItem() {
        if (selectedSourceItem == null) {
            uiLogger.error("没有选定调出单上的项目。");
            return;
        }
        // StockTransferDto job = stepping.getOpenedObject();
        StockOrderDto destOrder = getOpenedObject();
        destItem = new StockOrderItemDto().create();
        destItem.setParent(destOrder);
        destItem.populate(selectedSourceItem);
        destItem.setLocation(null);
        destItem.setQuantity(BigDecimal.ZERO);
    }

    public void addDestItem() {
        if (destItem == null)
            return;
        StockOrderDto destOrder = getOpenedObject();
        destOrder.addItem(destItem);
        destItem.setParent(destOrder);
        // XXX generated tmp id here.
        destItem.setId(-destOrder.getItems().size() - 1L);
        destItem = null;
    }

}
