package com.bee32.sem.inventory.process;

import java.io.Serializable;
import java.util.Map.Entry;

import com.bee32.sem.inventory.entity.StockOrderSubject;
import com.bee32.sem.process.verify.dto.VerifyPolicyDto;

public class StockOrderSubjectPolicyEntry implements
        Entry<StockOrderSubject, VerifyPolicyDto>, Serializable {

    private static final long serialVersionUID = 1L;

    StockOrderSubject subject;
    VerifyPolicyDto policy;

    public StockOrderSubjectPolicyEntry(StockOrderSubject subject,
            VerifyPolicyDto policy) {
        this.subject = subject;
        this.policy = policy;
    }

    public StockOrderSubject getSubject() {
        return subject;
    }

    public void setSubject(StockOrderSubject subject) {
        this.subject = subject;
    }

    public VerifyPolicyDto getPolicy() {
        return policy;
    }

    public void setPolicy(VerifyPolicyDto policy) {
        this.policy = policy;
    }

    @Override
    public StockOrderSubject getKey() {
        return subject;
    }

    @Override
    public VerifyPolicyDto getValue() {
        return policy;
    }

    @Override
    public VerifyPolicyDto setValue(VerifyPolicyDto value) {
        VerifyPolicyDto old = getPolicy();
        setPolicy(value);
        return old;
    }

    public String getSubjectValue() {
        return subject.getDisplayName();
    }

    public void setSubjectValue(String value) {
        subject = StockOrderSubject.valueOf(value);
    }

}
