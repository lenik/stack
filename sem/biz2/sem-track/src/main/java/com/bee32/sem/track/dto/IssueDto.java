package com.bee32.sem.track.dto;

import java.util.Collections;
import java.util.List;

import javax.free.NotImplementedException;
import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.orm.entity.CopyUtils;
import com.bee32.sem.chance.dto.ChanceDto;
import com.bee32.sem.file.dto.UserFileDto;
import com.bee32.sem.inventory.dto.StockOrderDto;
import com.bee32.sem.mail.dto.MessageDto;
import com.bee32.sem.mail.entity.Message;
import com.bee32.sem.track.entity.Issue;
import com.bee32.sem.track.entity.IssueState;

public class IssueDto
        extends MessageDto {

    private static final long serialVersionUID = 1L;

    public static final int ATTACHMENTS = 1;
    public static final int OBSERVERS = 2;

    IssueState state;
    int priority;

    String tags = "";
    String commitish;

    List<UserFileDto> attachments;
    List<IssueObserverDto> observers;

    ChanceDto chance;
    StockOrderDto stockOrder;

    boolean contentEditable = true;

    @Override
    protected void _copy() {
        super._copy();
        observers = CopyUtils.copyList(observers);
    }

    @Override
    protected void _marshal(Message _source) {
        super._marshal(_source);
        Issue source = (Issue) _source;

        priority = source.getPriority();
        tags = source.getTags();
        commitish = source.getCommitish();

        if (selection.contains(ATTACHMENTS))
            attachments = marshalList(UserFileDto.class, source.getAttachments());
        else
            attachments = Collections.emptyList();

        if (selection.contains(OBSERVERS))
            observers = marshalList(IssueObserverDto.class, source.getObservers());
        else
            observers = Collections.emptyList();

        if (source.getChance() != null)
            chance = mref(ChanceDto.class, source.getChance());
        if (source.getStockOrder() != null)
            stockOrder = mref(StockOrderDto.class, source.getStockOrder());
    }

    @Override
    protected void _unmarshalTo(Message _target) {
        super._unmarshalTo(_target);
        Issue target = (Issue) _target;

        target.setPriority(priority);
        target.setTags(tags);
        target.setCommitish(commitish);

        if (selection.contains(ATTACHMENTS))
            mergeList(target, "attachments", attachments);
        if (selection.contains(OBSERVERS))
            mergeList(target, "observers", observers);

        merge(target, "chance", chance);
        merge(target, "order", stockOrder);
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
        throw new NotImplementedException();
    }

    public String getStateName() {
        return state.getName();
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getCommitish() {
        return commitish;
    }

    public void setCommitish(String commitish) {
        this.commitish = commitish;
    }

    public List<UserFileDto> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<UserFileDto> attachments) {
        this.attachments = attachments;
    }

    public boolean isContentEditable() {
        return contentEditable;
    }

    public void setContentEditable(boolean contentEditable) {
        this.contentEditable = contentEditable;
    }

    public List<IssueObserverDto> getObservers() {
        return observers;
    }

    public void setObservers(List<IssueObserverDto> observers) {
        this.observers = observers;
    }

    public String getChanceSubject() {
        if (chance == null)
            return "无";
        else
            return chance.getSubject();
    }

    public String getOrderSubject() {
        if (stockOrder == null)
            return "无";
        else
            return stockOrder.getSubject().getDisplayName();
    }

    public ChanceDto getChance() {
        return chance;
    }

    public void setChance(ChanceDto chance) {
        this.chance = chance;
    }

    public StockOrderDto getStockOrder() {
        return stockOrder;
    }

    public void setStockOrder(StockOrderDto stockOrder) {
        this.stockOrder = stockOrder;
    }

}
