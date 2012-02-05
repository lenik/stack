package com.bee32.sem.mail.dto;

import javax.free.ParseException;
import javax.free.TypeConvertException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.ox1.color.UIEntityDto;
import com.bee32.sem.mail.entity.MailFilter;

public class MailFilterDto
        extends UIEntityDto<MailFilter, Integer> {

    private static final long serialVersionUID = 1L;

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

    public MailFilterDto(int fmask) {
        super(fmask);
    }

    @Override
    protected void _marshal(MailFilter source) {
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

        enabled = map.getBoolean("enabled");
        order = map.getInt("order");
        expr = map.getString("expr");
        // source = map.getString("source");
        // target = map.getString("target");

        transferTo = map.getString("transferTo");
        chMask = map.getInt("chMask");
        chBits = map.getInt("chBits");
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
