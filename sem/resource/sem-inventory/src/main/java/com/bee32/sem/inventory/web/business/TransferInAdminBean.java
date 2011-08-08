package com.bee32.sem.inventory.web.business;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.faces.event.AjaxBehaviorEvent;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.bee32.plover.criteria.hibernate.Equals;
import com.bee32.plover.criteria.hibernate.Offset;
import com.bee32.plover.criteria.hibernate.Order;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.sem.inventory.dto.StockOrderDto;
import com.bee32.sem.inventory.dto.StockOrderItemDto;
import com.bee32.sem.inventory.dto.tx.StockTransferDto;
import com.bee32.sem.inventory.entity.StockOrder;
import com.bee32.sem.inventory.entity.StockOrderSubject;
import com.bee32.sem.inventory.tx.entity.StockTransfer;
import com.bee32.sem.inventory.util.StockCriteria;
import com.bee32.sem.misc.EntityCriteria;
import com.bee32.sem.people.dto.PersonDto;
import com.bee32.sem.people.entity.Person;
import com.bee32.sem.people.util.PeopleCriteria;

@Component
@Scope("view")
public class TransferInAdminBean extends StockOrderBaseBean {

    private static final long serialVersionUID = 1L;

    private StockTransferDto stockTransfer = new StockTransferDto().create();

    private Date limitDateFrom;
    private Date limitDateTo;

    private int goNumber;
    private int count;


    private String personPattern;
    private List<PersonDto> persons;
    private PersonDto selectedPerson;


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

        subject = StockOrderSubject.XFER_IN;
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

    public int getCount() {
        count = serviceFor(StockOrder.class).count(//
                EntityCriteria.createdBetweenEx(limitDateFrom, limitDateTo), //
                StockCriteria.subjectOf(getSubject()), //
                new Equals("warehouse.id", selectedWarehouse.getId()));
        return count;
    }


    public StockTransferDto getStockTransfer() {
        return stockTransfer;
    }

    public void setStockTransfer(StockTransferDto stockTransfer) {
        this.stockTransfer = stockTransfer;
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









    public void onSwChange(AjaxBehaviorEvent e) {
        loadStockOrder(goNumber);
        loadStockLocationTree();
    }

    private void loadStockOrder(int goNumber) {
        //刷新总记录数
        getCount();

        if(goNumber < 1) goNumber = 1;
        if(goNumber > count) goNumber = count;


        stockOrder = new StockOrderDto().create();
        stockTransfer = new StockTransferDto().create();
        if (selectedWarehouse != null) {
            StockOrder firstOrder = serviceFor(StockOrder.class)
                    .getFirst(
                            //
                            new Offset(goNumber - 1), //
                            EntityCriteria.createdBetweenEx(limitDateFrom,
                                    limitDateTo), //
                            StockCriteria.subjectOf(getSubject()), //
                            new Equals("warehouse.id", selectedWarehouse
                                    .getId()), Order.desc("id"));

            if (firstOrder != null) {
                stockOrder = DTOs.marshal(StockOrderDto.class, firstOrder);

                StockTransfer t = serviceFor(StockTransfer.class)
                        .getUnique(new Equals("source.id", stockOrder.getId()));
                if(t != null) {
                    stockTransfer = DTOs.marshal(StockTransferDto.class, t);
                }
            }
        }
    }

    public void limit() {
        loadStockOrder(goNumber);
    }

    public void new_() {
        if (selectedWarehouse.getId() == null) {
            uiLogger.warn("请选择对应的仓库!");
            return;
        }

        stockTransfer = new StockTransferDto().create();
        stockOrder = new StockOrderDto().create();
        //stockOrder.setCreatedDate(new Date());
        editable = true;
    }

    public void modify() {
        if(stockOrder.getId() == null) {
            uiLogger.warn("当前没有对应的单据");
            return;
        }

        itemsNeedToRemoveWhenModify.clear();

        editable = true;
    }

    @Transactional
    public void delete() {
        serviceFor(StockTransfer.class).deleteAll(
                new Equals("source.id", stockOrder.getId()));
        //serviceFor(StockOrder.class).deleteById(stockOrder.getId());
        uiLogger.info("删除成功!");
        loadStockOrder(goNumber);
    }

    @Transactional
    public void save() {
        if(selectedWarehouse.getId().equals(stockTransfer.getDestWarehouse().getId())) {
            uiLogger.warn("调拨源仓库和目标仓库不能相同");
            return;
        }


//        if(stockOrder.getItems() != null && stockOrder.getItems().size() <= 0) {
//            uiLogger.warn("单据上至少应该有一条明细");
//            return;
//        }


        stockOrder.setSubject(subject);
        stockOrder.setWarehouse(selectedWarehouse);

        if(stockOrder.getId() == null) {
            //新增
            goNumber = 1;
        }

        //删除需要删除的item
        for(StockOrderItemDto item : itemsNeedToRemoveWhenModify) {
            serviceFor(StockOrder.class).delete(item.unmarshal());
        }

//        //保存新的stockOrder
//        serviceFor(StockOrder.class).saveOrUpdate(stockOrder.unmarshal());

        stockTransfer.setSourceWarehouse(selectedWarehouse);
        stockTransfer.setSource(stockOrder);
        StockTransfer _stockTransfer = stockTransfer.unmarshal();
        //保存stockTransfer
        serviceFor(StockTransfer.class).saveOrUpdate(_stockTransfer);


        uiLogger.info("保存成功");
        loadStockOrder(goNumber);
        editable = false;
    }

    public void cancel() {

        loadStockOrder(goNumber);
        editable = false;
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

    public void findPerson() {
        if (personPattern != null && !personPattern.isEmpty()) {

            List<Person> _persons = serviceFor(Person.class).list(//
                    // Restrictions.in("tags", new Object[] { PartyTagname.INTERNAL }), //
                    PeopleCriteria.internal(), //
                    PeopleCriteria.namedLike(personPattern));

            persons = DTOs.marshalList(PersonDto.class, _persons, true);
        }
    }

    public void choosePerson() {
        stockTransfer.setTransferredBy(selectedPerson);
    }
}
