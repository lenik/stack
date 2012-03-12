package com.bee32.sem.makebiz.web;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bee32.plover.criteria.hibernate.Equals;
import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.sem.frame.ui.ListMBean;
import com.bee32.sem.makebiz.dto.MakeOrderDto;
import com.bee32.sem.makebiz.dto.MakeOrderItemDto;
import com.bee32.sem.makebiz.entity.MakeOrder;
import com.bee32.sem.misc.ChooseEntityDialogBean;
import com.bee32.sem.people.dto.PartyDto;

public class ChooseMakeOrderDialogBean
        extends ChooseEntityDialogBean {

    private static final long serialVersionUID = 1L;

    static Logger logger = LoggerFactory.getLogger(ChooseMakeOrderDialogBean.class);

    PartyDto customer = new PartyDto().ref(); // null.

    public ChooseMakeOrderDialogBean() {
        super(MakeOrder.class, MakeOrderDto.class, 0);
    }

    @Override
    protected void composeBaseRestrictions(List<ICriteriaElement> elements) {
        super.composeBaseRestrictions(elements);
        if (!customer.isNull()) {
            elements.add(new Equals("customer.id", customer.getId()));
        }
    }

    public PartyDto getCustomer() {
        return customer;
    }

    public void setCustomer(PartyDto customer) {
        if (customer == null)
            customer = new PartyDto().ref();
        this.customer = customer;
        // refreshRowCount();
    }

    final ListMBean<MakeOrderItemDto> itemsMBean = ListMBean.fromEL(this, "openedObject.items", MakeOrderItemDto.class);

    public ListMBean<MakeOrderItemDto> getItemsMBean() {
        return itemsMBean;
    }

}
