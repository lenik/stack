package com.bee32.sem.chance.dto;

import java.util.Date;
import java.util.List;

import javax.free.ParseException;

import com.bee32.icsf.principal.User;
import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.orm.ext.color.GreenEntityDto;
import com.bee32.sem.chance.entity.Chance;
import com.bee32.sem.chance.entity.ChanceCategory;
import com.bee32.sem.chance.entity.ChanceSource;
import com.bee32.sem.chance.entity.ChanceStage;

public class ChanceDto
        extends GreenEntityDto<Chance, Long> {

    private static final long serialVersionUID = 1L;

    public static final int PARTIES = 1;
    public static final int ACTIONS = 2;

    private User owner;
    private ChanceCategory category;
    private ChanceSource source;
    private String subject;
    private String content;

    private Date createDate;

    private List<ChancePartyDto> parties;
    private List<ChanceActionDto> actions;

    ChanceStage stage;

    public ChanceDto() {
        super(PARTIES + ACTIONS);
    }

    public ChanceDto(Chance source) {
        super(PARTIES + ACTIONS, source);
    }

    public ChanceDto(int selection, Chance source) {
        super(selection, source);
    }

    public ChanceDto(int selection) {
        super(selection);
    }

    @Override
    protected void _marshal(Chance source) {
        this.owner = source.getOwner();
        this.category = source.getCategory();
        this.source = source.getSource();
        this.subject = source.getSubject();
        this.content = source.getContent();
        this.createDate = source.getCreateDate();

        if (selection.contains(PARTIES))
            this.parties = marshalList(ChancePartyDto.class, source.getParties());
        if (selection.contains(ACTIONS))
            this.actions = marshalList(ChanceActionDto.class, source.getActions());
    }

    @Override
    protected void _unmarshalTo(Chance target) {
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public ChanceCategory getCategory() {
        return category;
    }

    public void setCategory(ChanceCategory category) {
        this.category = category;
    }

    public ChanceSource getSource() {
        return source;
    }

    public void setSource(ChanceSource source) {
        this.source = source;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public List<ChancePartyDto> getParties() {
        return parties;
    }

    public void setParties(List<ChancePartyDto> parties) {
        this.parties = parties;
    }

    public List<ChanceActionDto> getActions() {
        return actions;
    }

    public void setActions(List<ChanceActionDto> actions) {
        this.actions = actions;
    }

    public ChanceStage getStage() {
        return stage;
    }

    public void setStage(ChanceStage stage) {
        this.stage = stage;
    }

}
