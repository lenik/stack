package com.bee32.sem.track.dto;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.free.NotImplementedException;
import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.model.validation.core.NLength;
import com.bee32.plover.orm.entity.CopyUtils;
import com.bee32.plover.util.TextUtil;
import com.bee32.sem.chance.dto.ChanceDto;
import com.bee32.sem.file.dto.UserFileDto;
import com.bee32.sem.inventory.dto.StockOrderDto;
import com.bee32.sem.mail.dto.MessageDto;
import com.bee32.sem.track.entity.Issue;
import com.bee32.sem.track.entity.IssueState;

public class IssueDto
        extends MessageDto<Issue, IssueDto> {

    private static final long serialVersionUID = 1L;

    public static final int ATTACHMENTS = 1;
    public static final int HREFS = 2;
    public static final int REPLIES = 4;
    public static final int OBSERVERS = 16;
    public static final int CC_GROUPS = 32;
    public static final int EXT_MISC = 64;

    private Date dueDate;
    private Date endDate;

    private String tags = "";
    private String commitish;

    private List<UserFileDto> attachments;
    private List<IssueHrefDto> hrefs;
    private List<IssueObserverDto> observers;
    private List<IssueCcGroupDto> ccGroups;
    private List<IssueReplyDto> replies;

    private ChanceDto chance;
    private StockOrderDto stockOrder;

    @Override
    protected void _copy() {
        super._copy();
        observers = CopyUtils.copyList(observers);
        ccGroups = CopyUtils.copyList(ccGroups);
    }

    @Override
    protected void _marshal(Issue source) {
        super._marshal(source);

        dueDate = source.getDueDate();
        endDate = source.getEndDate();

        tags = source.getTags();
        commitish = source.getCommitish();

        if (selection.contains(ATTACHMENTS))
            attachments = marshalList(UserFileDto.class, source.getAttachments());
        else
            attachments = Collections.emptyList();

        if (selection.contains(REPLIES))
            replies = marshalList(IssueReplyDto.class, source.getReplies());
        else
            replies = Collections.emptyList();

        if (selection.contains(OBSERVERS))
            observers = marshalList(IssueObserverDto.class, source.getObservers());
        else
            observers = Collections.emptyList();

        if (selection.contains(CC_GROUPS))
            ccGroups = marshalList(IssueCcGroupDto.class, source.getCcGroups());
        else
            ccGroups = Collections.emptyList();

        if (selection.contains(EXT_MISC)) {
            chance = mref(ChanceDto.class, source.getChance());
            stockOrder = mref(StockOrderDto.class, source.getStockOrder());
        }
    }

    @Override
    protected void _unmarshalTo(Issue target) {
        super._unmarshalTo(target);

        target.setDueDate(dueDate);
        target.setEndDate(endDate);
        target.setTags(tags);
        target.setCommitish(commitish);

        if (selection.contains(ATTACHMENTS))
            mergeList(target, "attachments", attachments);
        if (selection.contains(HREFS))
            mergeList(target, "hrefs", hrefs);

        if (selection.contains(REPLIES))
            mergeList(target, "replies", replies);

        if (selection.contains(OBSERVERS))
            mergeList(target, "observers", observers);
        if (selection.contains(CC_GROUPS))
            mergeList(target, "ccGroups", ccGroups);

        if (selection.contains(EXT_MISC)) {
            merge(target, "chance", chance);
            merge(target, "order", stockOrder);
        }
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
        throw new NotImplementedException();
    }

    public IssueState getIssueState() {
        int state = getState();
        IssueState issueState = IssueState.forValue((char) state);
        return issueState;
    }

    public void setIssueState(IssueState issueState) {
        int state = issueState.getValue();
        setState(state);
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
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

    public List<UserFileDto> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<UserFileDto> attachments) {
        if (attachments == null)
            throw new NullPointerException("attachments");
        this.attachments = attachments;
    }

    public List<IssueHrefDto> getHrefs() {
        return hrefs;
    }

    public void setHrefs(List<IssueHrefDto> hrefs) {
        if (hrefs == null)
            throw new NullPointerException("hrefs");
        this.hrefs = hrefs;
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
