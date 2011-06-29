package com.bee32.sem.chance.dto;

import java.util.List;

import javax.free.ParseException;

import com.bee32.icsf.principal.dto.UserDto;
import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.orm.util.EntityDto;
import com.bee32.sem.chance.entity.QuotationInvoice;
import com.bee32.sem.people.dto.PartyDto;

public class QuotationInvoiceDto
        extends EntityDto<QuotationInvoice, Long> {

    private static final long serialVersionUID = 1L;

    private UserDto owner;
    private String subject;
    private ChanceDto chance;
    private List<PartyDto> parties;
    private List<QuotationItemDto> items;
    private double amount;
    private String recommend;
    private String payment;
    private String remark;

    @Override
    protected void _marshal(QuotationInvoice source) {
    }

    @Override
    protected void _unmarshalTo(QuotationInvoice target) {
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
    }

    public UserDto getOwner() {
        return owner;
    }

    public void setOwner(UserDto owner) {
        this.owner = owner;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public ChanceDto getChance() {
        return chance;
    }

    public void setChance(ChanceDto chance) {
        this.chance = chance;
    }

    public List<PartyDto> getParties() {
        return parties;
    }

    public void setParties(List<PartyDto> parties) {
        this.parties = parties;
    }

    public List<QuotationItemDto> getItems() {
        return items;
    }

    public void setItems(List<QuotationItemDto> items) {
        this.items = items;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getRecommend() {
        return recommend;
    }

    public void setRecommend(String recommend) {
        this.recommend = recommend;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}
