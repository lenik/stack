package com.bee32.sem.inventory.web.business;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.faces.event.AjaxBehaviorEvent;

import org.springframework.transaction.annotation.Transactional;

import com.bee32.plover.criteria.hibernate.Equals;
import com.bee32.plover.criteria.hibernate.IsNull;
import com.bee32.plover.criteria.hibernate.Offset;
import com.bee32.plover.criteria.hibernate.Order;
import com.bee32.plover.orm.annotation.ForEntity;
import com.bee32.plover.orm.annotation.TypeParameter;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.plover.ox1.util.CommonCriteria;
import com.bee32.sem.inventory.dto.StockOrderDto;
import com.bee32.sem.inventory.dto.StockOrderItemDto;
import com.bee32.sem.inventory.dto.StockWarehouseDto;
import com.bee32.sem.inventory.entity.StockOrder;
import com.bee32.sem.inventory.entity.StockOrderSubject;
import com.bee32.sem.inventory.tx.dto.StockTransferDto;
import com.bee32.sem.inventory.tx.entity.StockTransfer;
import com.bee32.sem.inventory.util.StockCriteria;
import com.bee32.sem.people.dto.PersonDto;
import com.bee32.sem.people.entity.Person;
import com.bee32.sem.people.util.PeopleCriteria;

@ForEntity(value = StockOrder.class, parameters = @TypeParameter(name = "_subject", value = "XFRI"))
public class TransferInAdminBean
        extends StockOrderBaseBean {

    private static final long serialVersionUID = 1L;

    private StockOrderSubject subjectOut = null;

    private StockOrderDto stockOrderOut = new StockOrderDto().ref();

    private StockTransferDto stockTransfer = new StockTransferDto().create();
    private StockTransferDto stockTransferOut = new StockTransferDto().create();

    private StockOrderItemDto orderItemOut = new StockOrderItemDto().create().ref();
    private StockOrderItemDto orderItemIn = new StockOrderItemDto().create().ref();
    private StockOrderItemDto selectedItemIn;

    private Date limitDateFrom;
    private Date limitDateTo;

    private int goNumber;
    private int count;

    private int goNumberOut;
    private int countOut;

    private String personPattern;
    private List<PersonDto> persons;
    private PersonDto selectedPerson;

    private boolean transferring; // 是否在拨入状态

    public TransferInAdminBean() {
        Calendar c = Calendar.getInstance();
        // 取这个月的第一天
        c.set(Calendar.DAY_OF_MONTH, 1);
        limitDateFrom = c.getTime();

        // 最这个月的最后一天
        c.add(Calendar.MONTH, 1);
        c.add(Calendar.DAY_OF_MONTH, -1);
        limitDateTo = c.getTime();

        goNumber = 1;

        goNumberOut = 1;

        subject = StockOrderSubject.XFER_IN;
        subjectOut = StockOrderSubject.XFER_OUT;

        transferring = false;
    }

    public StockOrderSubject getSubjectOut() {
        return subjectOut;
    }

    public void setSubjectOut(StockOrderSubject subjectOut) {
        this.subjectOut = subjectOut;
    }

    public StockOrderDto getStockOrderOut() {
        return stockOrderOut;
    }

    public void setStockOrderOut(StockOrderDto stockOrderOut) {
        this.stockOrderOut = stockOrderOut;
    }

    public Date getLimitDateFrom() {
        return limitDateFrom;
    }

    public void setLimitDateFrom(Date limitDateFrom) {
        this.limitDateFrom = limitDateFrom;
    }

    public Date getLimitDateTo() {
        return limitDateTo;
    }

    public void setLimitDateTo(Date limitDateTo) {
        this.limitDateTo = limitDateTo;
    }

    public int getGoNumber() {
        return goNumber;
    }

    public void setGoNumber(int goNumber) {
        this.goNumber = goNumber;
    }

    public int getGoNumberOut() {
        return goNumberOut;
    }

    public void setGoNumberOut(int goNumberOut) {
        this.goNumberOut = goNumberOut;
    }

    public int getCount() {
        count = serviceFor(StockOrder.class).count(//
                CommonCriteria.createdBetweenEx(limitDateFrom, limitDateTo), //
                StockCriteria.subjectOf(getSubject()), //
                new Equals("warehouse.id", selectedWarehouse.getId()));
        return count;
    }

    public int getCountOut() {
        countOut = serviceFor(StockTransfer.class).count(//
                new Equals("destWarehouse.id", selectedWarehouse.getId()), new IsNull("dest.id"));
        return countOut;
    }

    public StockTransferDto getStockTransfer() {
        return stockTransfer;
    }

    public void setStockTransfer(StockTransferDto stockTransfer) {
        this.stockTransfer = stockTransfer;
    }

    public StockTransferDto getStockTransferOut() {
        return stockTransferOut;
    }

    public void setStockTransferOut(StockTransferDto stockTransferOut) {
        this.stockTransferOut = stockTransferOut;
    }

    public String getPersonPattern() {
        return personPattern;
    }

    public void setPersonPattern(String personPattern) {
        this.personPattern = personPattern;
    }

    public List<PersonDto> getPersons() {
        return persons;
    }

    public void setPersons(List<PersonDto> persons) {
        this.persons = persons;
    }

    public PersonDto getSelectedPerson() {
        return selectedPerson;
    }

    public void setSelectedPerson(PersonDto selectedPerson) {
        this.selectedPerson = selectedPerson;
    }

    public String getCreatorOut() {
        if (stockOrderOut == null)
            return "";
        else
            return stockOrderOut.getOwnerDisplayName();
    }

    public List<StockOrderItemDto> getItemsOut() {
        if (stockOrderOut == null)
            return null;
        return stockOrderOut.getItems();
    }

    public boolean isTransferring() {
        return transferring;
    }

    public void setTransferring(boolean transferring) {
        this.transferring = transferring;
    }

    public StockOrderItemDto getOrderItemOut() {
        return orderItemOut;
    }

    public void setOrderItemOut(StockOrderItemDto orderItemOut) {
        this.orderItemOut = orderItemOut;
    }

    public StockOrderItemDto getOrderItemIn() {
        return orderItemIn;
    }

    public void setOrderItemIn(StockOrderItemDto orderItemIn) {
        this.orderItemIn = orderItemIn;
    }

    public StockOrderItemDto getSelectedItemIn() {
        return selectedItemIn;
    }

    public void setSelectedItemIn(StockOrderItemDto selectedItemIn) {
        this.selectedItemIn = selectedItemIn;
    }

    public void onSwChange(AjaxBehaviorEvent e) {
        loadStockOrder(goNumber);
        loadStockOrderOut(goNumberOut);
        loadStockLocationTree();
    }

    private void loadStockOrder(int position) {
        // 刷新总记录数
        getCount();

        goNumber = position;

        if (position < 1) {
            goNumber = 1;
            position = 1;
        }
        if (goNumber > count) {
            goNumber = count;
            position = count;
        }

        stockOrder = new StockOrderDto().create();
        stockTransfer = new StockTransferDto().create();
        if (selectedWarehouse != null) {
            StockOrder firstOrder = serviceFor(StockOrder.class).getFirst( //
                    new Offset(position - 1), //
                    CommonCriteria.createdBetweenEx(limitDateFrom, limitDateTo), //
                    StockCriteria.subjectOf(getSubject()), //
                    new Equals("warehouse.id", selectedWarehouse.getId()),
                    Order.asc("id"));

            if (firstOrder != null) {
                stockOrder = DTOs.marshal(StockOrderDto.class, firstOrder);

                StockTransfer t = serviceFor(StockTransfer.class).getUnique(new Equals("dest.id", stockOrder.getId()));
                if (t != null) {
                    stockTransfer = DTOs.marshal(StockTransferDto.class, t);
                }
            }
        }
    }

    private void loadStockOrderOut(int position) {
        // 刷新总记录数
        getCountOut();

        goNumberOut = position;

        if (position < 1) {
            goNumberOut = 1;
            position = 1;
        }
        if (goNumberOut > countOut) {
            goNumberOut = countOut;
            position = countOut;
        }

        stockOrderOut = new StockOrderDto().create();
        stockTransferOut = new StockTransferDto().create();
        if (selectedWarehouse != null) {

            StockTransfer firstTransfer = serviceFor(StockTransfer.class).getFirst(new Offset(position - 1),
                    new Equals("destWarehouse.id", selectedWarehouse.getId()), new IsNull("dest.id"));

            if (firstTransfer != null) {
                stockTransferOut = DTOs.marshal(StockTransferDto.class, firstTransfer);

                StockOrder o = serviceFor(StockOrder.class).getUnique(
                        new Equals("id", stockTransferOut.getSource().getId()));
                if (o != null) {
                    stockOrderOut = DTOs.marshal(StockOrderDto.class, o);
                }
            }
        }
    }

    public void limit() {
        loadStockOrder(goNumber);
    }

    @Transactional
    public void delete() {
        // 若加入事务标记后，清空StockTransfer.dest后，不会马上反应到数据库中，
        // 导致StockOrder被引用，不能删除，会出错
        try {
            StockTransfer t = serviceFor(StockTransfer.class).getUnique(
                    new Equals("dest.id", stockOrder.getId()));
            if (t != null) {
                t.setDest(null);
                serviceFor(StockTransfer.class).saveOrUpdate(t);
            }
            serviceFor(StockOrder.class).delete(stockOrder.unmarshal());

            uiLogger.info("删除成功!");
            loadStockOrder(goNumber);
        } catch (Exception e) {
            uiLogger.warn("删除失败,错误信息:" + e.getMessage());
        }
    }

    public void first() {
        goNumber = 1;
        loadStockOrder(goNumber);
    }

    public void previous() {
        goNumber--;
        if (goNumber < 1)
            goNumber = 1;
        loadStockOrder(goNumber);
    }

    public void go() {
        if (goNumber < 1) {
            goNumber = 1;
        } else if (goNumber > count) {
            goNumber = count;
        }
        loadStockOrder(goNumber);
    }

    public void next() {
        goNumber++;

        if (goNumber > count)
            goNumber = count;
        loadStockOrder(goNumber);
    }

    public void last() {
        goNumber = count + 1;
        loadStockOrder(goNumber);
    }

    public void firstOut() {
        goNumberOut = 1;
        loadStockOrderOut(goNumberOut);
    }

    public void previousOut() {
        goNumberOut--;
        if (goNumberOut < 1)
            goNumberOut = 1;
        loadStockOrderOut(goNumberOut);
    }

    public void goOut() {
        if (goNumberOut < 1) {
            goNumberOut = 1;
        } else if (goNumberOut > countOut) {
            goNumberOut = countOut;
        }
        loadStockOrderOut(goNumberOut);
    }

    public void nextOut() {
        goNumberOut++;

        if (goNumberOut > countOut)
            goNumberOut = countOut;
        loadStockOrderOut(goNumberOut);
    }

    public void lastOut() {
        goNumberOut = countOut + 1;
        loadStockOrderOut(goNumberOut);
    }

    public void transferInStart() {
        if (countOut <= 0) {
            uiLogger.warn("没有可以拨入的单据");
            return;
        }

        stockOrder = new StockOrderDto().create();
        stockOrder.setSubject(subject);
        stockTransfer.setSourceWarehouse(stockTransferOut.getSourceWarehouse());

        editable = true;
        transferring = true;
    }

    @Transactional
    public void transferInDone() {
        // if(stockOrder.getItems() != null && stockOrder.getItems().size() <= 0) {
        // uiLogger.warn("单据上至少应该有一条明细");
        // return;
        // }

        stockOrder.setWarehouse(selectedWarehouse);

        stockTransferOut.setDestWarehouse(selectedWarehouse);
        stockTransferOut.setDest(stockOrder);
        StockTransfer _stockTransferOut = stockTransferOut.unmarshal();
        // 保存stockTransferOut
        try {
            serviceFor(StockTransfer.class).saveOrUpdate(_stockTransferOut);

            uiLogger.info("拨入成功");

            loadStockOrder(count + 1);
            loadStockOrderOut(goNumberOut);

            editable = false;
            transferring = false;
        } catch (Exception e) {
            uiLogger.warn("拨入失败,错误信息:" + e.getMessage());
        }
    }

    public void cancelTransferIn() {
        loadStockOrder(goNumber);
        loadStockOrderOut(goNumberOut);

        editable = false;
        transferring = false;
    }

    public void findPerson() {
        if (personPattern != null && !personPattern.isEmpty()) {

            List<Person> _persons = serviceFor(Person.class).list(//
                    // Restrictions.in("tags", new Object[] { PartyTagname.INTERNAL }), //
                    PeopleCriteria.internal(), //
                    PeopleCriteria.namedLike(personPattern));

            persons = DTOs.mrefList(PersonDto.class, _persons);
        }
    }

    public void choosePerson() {
        stockTransfer.setTransferredBy(selectedPerson);
    }

    public void newItemIn() {
        orderItemIn = new StockOrderItemDto().create();
        orderItemIn.setMaterial(orderItemOut.getMaterial());
        orderItemIn.setBatch(orderItemOut.getBatch());
        orderItemIn.setExpirationDate(orderItemOut.getExpirationDate());
        orderItemIn.setPrice(orderItemOut.getPrice());
    }

    public void deleteItemIn() {
        if (selectedItemIn == null) {
            uiLogger.warn("请选择要删除的拨入项目");
            return;
        }

        stockOrder.getItems().remove(selectedItemIn);
    }

    public void saveItemIn() {
        orderItemIn.setParent(stockOrder);
        stockOrder.getItems().add(orderItemIn);
    }

    @Override
    public StockOrderItemDto getOrderItem_() {
        return orderItemIn;
    }

    @Override
    public StockWarehouseDto getSelectedWarehouse_() {
        return selectedWarehouse;
    }
}
