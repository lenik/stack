package com.bee32.sem.inventory.web.settings;

import java.util.Collection;

import com.bee32.plover.orm.annotation.ForEntity;
import com.bee32.sem.inventory.entity.StockOrderSubject;
import com.bee32.sem.inventory.process.StockOrderSubjectPolicyEntry;
import com.bee32.sem.inventory.process.StockOrderVerifyPolicy;
import com.bee32.sem.inventory.process.StockOrderVerifyPolicyDto;
import com.bee32.sem.misc.SimpleEntityViewBean;
import com.bee32.sem.process.verify.dto.VerifyPolicyDto;

@ForEntity(StockOrderVerifyPolicy.class)
public class StockOrderVerifyPolicyBean
        extends SimpleEntityViewBean {

    private static final long serialVersionUID = 1L;

    StockOrderSubjectPolicyEntry selectedEntry;
    StockOrderSubjectPolicyEntry entry;

    public StockOrderVerifyPolicyBean() {
        super(StockOrderVerifyPolicy.class, StockOrderVerifyPolicyDto.class, 0);
    }

    public void newEntry() {
        VerifyPolicyDto policy = new VerifyPolicyDto().ref();
        entry = new StockOrderSubjectPolicyEntry(StockOrderSubject.TAKE_IN, policy);
    }

    public void addEntry() {
        StockOrderVerifyPolicyDto policy = getActiveObject();
        policy.addEntry(entry);
        entry = null;
    }

    public void removeEntry() {
        StockOrderVerifyPolicyDto policy = getActiveObject();
        policy.removeEntry(selectedEntry);
    }

    // Properties

    public StockOrderSubjectPolicyEntry getSelectedEntry() {
        return selectedEntry;
    }

    public void setSelectedEntry(StockOrderSubjectPolicyEntry selectedEntry) {
        this.selectedEntry = selectedEntry;
    }

    public StockOrderSubjectPolicyEntry getEntry() {
        return entry;
    }

    public void setEntry(StockOrderSubjectPolicyEntry entry) {
        this.entry = entry;
    }

    public Collection<StockOrderSubject> getSubjects() {
        return StockOrderSubject.getVirtuals();
    }

}
