package com.bee32.sem.track.dto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.free.NotImplementedException;
import javax.free.ParseException;

import com.bee32.icsf.principal.PrincipalDto;
import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.model.validation.core.NLength;
import com.bee32.plover.orm.entity.CopyUtils;
import com.bee32.plover.util.TextUtil;
import com.bee32.sem.chance.dto.ChanceDto;
import com.bee32.sem.inventory.dto.StockOrderDto;
import com.bee32.sem.mail.dto.MessageDto;
import com.bee32.sem.track.entity.Issue;
import com.bee32.sem.track.util.IssueState;
import com.bee32.sem.track.util.IssueType;

public class IssueDto
        extends MessageDto<Issue, IssueDto> {

    private static final long serialVersionUID = 1L;

    public static final int HREFS = 1;
    public static final int ATTACHMENTS = 2;
    public static final int COUNTER = 4;

    public static final int OBSERVERS = 16;
    public static final int CC_GROUPS = 32;
    public static final int EXT_MISC = 64;

    public static final int REPLIES = 0x0100_0000; // EXT, not included in F_MORE.

    private IssueType type;
    private Date dueDate;
    private Date endTime;

    private String tags = "";
    private String commitish;

    private List<IssueHrefDto> hrefs;
    private List<IssueAttachmentDto> attachments;
    private List<IssueObserverDto> observers;
    private List<IssueObserverDto> ccGroups;
    private List<IssueReplyDto> replies;
    private IssueCounterDto counter;

    private ChanceDto chance;
    private StockOrderDto stockOrder;

    @Override
    protected void _copy() {
        super._copy();
        observers = CopyUtils.copyList(observers);
    }

    @Override
    protected void _marshal(Issue source) {
        super._marshal(source);

        type = source.getType();
        dueDate = source.getDueDate();
        endTime = source.getEndTime();

        tags = source.getTags();
        commitish = source.getCommitish();

        if (selection.contains(HREFS))
            hrefs = marshalList(IssueHrefDto.class, source.getHrefs());
        else
            hrefs = Collections.emptyList();

        if (selection.contains(ATTACHMENTS))
            attachments = marshalList(IssueAttachmentDto.class, source.getAttachments());
        else
            attachments = Collections.emptyList();

        if (selection.contains(OBSERVERS)) {
            observers = new ArrayList<IssueObserverDto>();
            ccGroups = new ArrayList<IssueObserverDto>();
            for (IssueObserverDto dto : marshalList(IssueObserverDto.class, source.getObservers())) {
                PrincipalDto observer = dto.getObserver();
                if (observer.isUser())
                    observers.add(dto);
                else
                    ccGroups.add(dto);
            }
        } else {
            observers = Collections.emptyList();
            ccGroups = Collections.emptyList();
        }

        if (selection.contains(REPLIES))
            replies = marshalList(IssueReplyDto.class, source.getReplies());
        else
            replies = Collections.emptyList();

        if (selection.contains(COUNTER))
            counter = marshal(IssueCounterDto.class, source.getCounter());

        if (selection.contains(EXT_MISC)) {
            chance = mref(ChanceDto.class, source.getChance());
            stockOrder = mref(StockOrderDto.class, source.getStockOrder());
        }
    }

    @Override
    protected void _unmarshalTo(Issue target) {
        super._unmarshalTo(target);

        target.setType(type);
        target.setDueDate(dueDate);
        target.setEndTime(endTime);
        target.setTags(tags);
        target.setCommitish(commitish);

        if (selection.contains(ATTACHMENTS))
            mergeList(target, "attachments", attachments);
        if (selection.contains(HREFS))
            mergeList(target, "hrefs", hrefs);

        if (selection.contains(REPLIES))
            mergeList(target, "replies", replies);

        if (selection.contains(OBSERVERS)) {
            List<IssueObserverDto> list = new ArrayList<IssueObserverDto>();
            list.addAll(observers);
            list.addAll(ccGroups);
            mergeList(target, "observers", list);
        }

        if (selection.contains(EXT_MISC)) {
            merge(target, "chance", chance);
            merge(target, "stockOrder", stockOrder);
        }
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
        throw new NotImplementedException();
    }

    public IssueState getState() {
        int stateInt = getStateInt();
        IssueState state = IssueState.forValue((char) stateInt);
        return state;
    }

    public void setState(IssueState issueState) {
        int stateInt = issueState.getValue();
        setStateInt(stateInt);
    }

    public char getTypeChar() {
        return type.getValue();
    }

    public void setTypeChar(char typeChar) {
        type = IssueType.forValue(typeChar);
    }

    public IssueType getType() {
        return type;
    }

    public void setType(IssueType type) {
        if (type == null)
            throw new NullPointerException("type");
        this.type = type;
    }

    public void switchType() {
        type = type.next();
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endDate) {
        this.endTime = endDate;
    }

    @NLength(max = Issue.TAGS_LENGTH)
    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    @NLength(max = Issue.COMMITISH_LENGTH)
    public String getCommitish() {
        return commitish;
    }

    public void setCommitish(String commitish) {
        this.commitish = TextUtil.normalizeSpace(commitish);
    }

    public List<IssueHrefDto> getHrefs() {
        return hrefs;
    }

    public void setHrefs(List<IssueHrefDto> hrefs) {
        if (hrefs == null)
            throw new NullPointerException("hrefs");
        this.hrefs = hrefs;
    }

    public List<IssueAttachmentDto> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<IssueAttachmentDto> attachments) {
        if (attachments == null)
            throw new NullPointerException("attachments");
        this.attachments = attachments;
    }

    public List<IssueReplyDto> getReplies() {
        return replies;
    }

    public void setReplies(List<IssueReplyDto> replies) {
        if (replies == null)
            throw new NullPointerException("replies");
        this.replies = replies;
    }

    public List<IssueObserverDto> getObservers() {
        return observers;
    }

    public void setObservers(List<IssueObserverDto> observers) {
        if (observers == null)
            throw new NullPointerException("observers");
        this.observers = observers;
    }

    public List<IssueObserverDto> getCcGroups() {
        return ccGroups;
    }

    public void setCcGroups(List<IssueObserverDto> ccGroups) {
        if (ccGroups == null)
            throw new NullPointerException("ccGroups");
        this.ccGroups = ccGroups;
    }

    public IssueCounterDto getCounter() {
        return counter;
    }

    public void setCounter(IssueCounterDto counter) {
        this.counter = counter;
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
