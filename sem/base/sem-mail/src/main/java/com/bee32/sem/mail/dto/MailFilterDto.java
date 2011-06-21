package com.bee32.sem.mail.dto;

import javax.free.ParseException;
import javax.free.TypeConvertException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.orm.util.EntityDto;
import com.bee32.sem.mail.entity.MailFilter;

public class MailFilterDto
        extends EntityDto<MailFilter, Integer> {

    private static final long serialVersionUID = 1L;

    String name;
    String description;

    boolean enabled;
    int order;

    String expr;

    MailFolderDto source;
    MailFolderDto target;
    String transferTo;

    int chMask;
    int chBits;

    public MailFilterDto() {
        super();
    }

    public MailFilterDto(int selection) {
        super(selection);
    }

    @Override
    protected void _marshal(MailFilter source) {
        name = source.getName();
        description = source.getDescription();

        enabled = source.isEnabled();
        order = source.getOrder();
        expr = source.getExpr();

        this.source = new MailFolderDto().marshal(source.getSource());
        target = new MailFolderDto().marshal(source.getTarget());
        transferTo = source.getTransferTo();

        chMask = source.getChMask();
        chBits = source.getChBits();
    }

    @Override
    protected void _unmarshalTo(MailFilter target) {
        target.setName(name);
        target.setDescription(description);

        target.setEnabled(enabled);
        target.setOrder(order);
        target.setExpr(expr);

        merge(target, "source", source);
        merge(target, "target", this.target);
        target.setTransferTo(transferTo);

        target.setChMask(chMask);
        target.setChBits(chBits);
    }

    @Override
    public void _parse(TextMap map)
            throws ParseException, TypeConvertException {

        name = map.getString("name");
        description = map.getString("description");

        enabled = map.getBoolean("enabled");
        order = map.getInt("order");
        expr = map.getString("expr");
        // source = map.getString("source");
        // target = map.getString("target");

        transferTo = map.getString("transferTo");
        chMask = map.getInt("chMask");
        chBits = map.getInt("chBits");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getExpr() {
        return expr;
    }

    public void setExpr(String expr) {
        this.expr = expr;
    }

    public MailFolderDto getSource() {
        return source;
    }

    public void setSource(MailFolderDto source) {
        this.source = source;
    }

    public MailFolderDto getTarget() {
        return target;
    }

    public void setTarget(MailFolderDto target) {
        this.target = target;
    }

    public String getTransferTo() {
        return transferTo;
    }

    public void setTransferTo(String transferTo) {
        this.transferTo = transferTo;
    }

    public int getChMask() {
        return chMask;
    }

    public void setChMask(int chMask) {
        this.chMask = chMask;
    }

    public int getChBits() {
        return chBits;
    }

    public void setChBits(int chBits) {
        this.chBits = chBits;
    }

}
