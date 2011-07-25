package com.bee32.sem.chance.dto;

import java.util.List;

import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.orm.util.EntityDto;
import com.bee32.sem.chance.entity.ChanceQuotation;

public class ChanceQuotationDto
        extends EntityDto<ChanceQuotation, Long> {

    private static final long serialVersionUID = 1L;

    private String subject;
    private ChanceDto chance;
    private List<ChanceQuotationItemDto> items;
    private double amount;
    private String recommend;
    private String payment;
    private String remark;

    @Override
    protected void _marshal(ChanceQuotation source) {
        this.subject = source.getSubject();
        this.chance = mref(ChanceDto.class, source.getChance());
        this.items = marshalList(ChanceQuotationItemDto.class, source.getItems());
        this.amount = source.getAmount();
        this.recommend = source.getRecommend();
        this.payment = source.getPayment();
        this.remark = source.getRemark();
    }

    @Override
    protected void _unmarshalTo(ChanceQuotation target) {
        target.setSubject(subject);
        merge(target, "chance", chance);
        mergeList(target, "items", items);
        target.setAmount(amount);
        target.setRecommend(recommend);
        target.setPayment(payment);
        target.setRemark(remark);
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
    }

    public void addItem(ChanceQuotationItemDto qid) {
        this.items.add(qid);
    }

    public void removeItem(ChanceQuotationItemDto qid){
        this.items.remove(qid);
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

    public List<ChanceQuotationItemDto> getItems() {
        return items;
    }

    public void setItems(List<ChanceQuotationItemDto> items) {
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
