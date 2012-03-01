package com.bee32.sem.asset.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.free.IFormatter;
import javax.free.INegotiation;
import javax.free.IllegalUsageException;
import javax.free.NegotiationException;

import com.bee32.plover.ox1.dict.PoNode;
import com.bee32.sem.asset.entity.AccountSubject;
import com.bee32.sem.asset.entity.AccountTicketItem;
import com.bee32.sem.people.entity.Party;
import com.bee32.sem.world.monetary.FxrQueryException;
import com.bee32.sem.world.monetary.MCValue;

public class SumNode
        extends PoNode<AccountSubject> {

    private final List<AccountTicketItem> items = new ArrayList<AccountTicketItem>();
    private BigDecimal total = new BigDecimal(0);

    @Override
    public SumNode getParent() {
        return (SumNode) super.getParent();
    }

    public List<AccountTicketItem> getItems() {
        return items;
    }

    public void addItem(AccountTicketItem item) {
        if (item == null)
            throw new NullPointerException("item");
        item.setIndex(items.size());
        items.add(item);
    }

    public boolean removeItem(AccountTicketItem item) {
        return items.remove(item);
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public void receive(AccountTicketItem item) {
        // if (!isVirtual())
        addItem(item);

        MCValue mcv = item.getValue();
        BigDecimal nativeValue;
        try {
            nativeValue = mcv.getNativeValue(item.getBeginTime());
        } catch (FxrQueryException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
        add(nativeValue);
    }

    void add(BigDecimal value) {
        total = total.add(value);
        SumNode parent = getParent();
        if (parent == this)
            throw new IllegalUsageException("Dead-loop: " + this);
        if (parent != null)
            parent.add(value);
    }

    @Override
    public boolean isVirtual() {
        if (super.isVirtual())
            return true;
        if (!items.isEmpty())
            return false;
        if (getChildren().isEmpty())
            return true;
        return false;
    }

    public static class SumNodeFormatter
            implements IFormatter<PoNode<?>> {

        @Override
        public String format(PoNode<?> node, INegotiation n)
                throws NegotiationException {
            return format(node);
        }

        @Override
        public String format(PoNode<?> _node) {
            SumNode node = (SumNode) _node;
            List<AccountTicketItem> items = node.getItems();
            BigDecimal total = node.getTotal();

            StringBuilder sb = new StringBuilder();
            sb.append(node.getKey());
            if (node.isVirtual())
                sb.append(" (virtual)");

            sb.append(": " + total);
            // sb.append(", " + items.size() + " items");
            sb.append(" { ");
            for (AccountTicketItem item : items) {
                if (item.isTransient())
                    sb.append("#");
                sb.append(item.getSubject().getLabel());
                sb.append(":");
                Party party = item.getParty();
                sb.append(party == null ? "*" : party.getDisplayName());
                sb.append("+" + item.getValue());
                sb.append(", ");
            }
            sb.append(" }");
            return sb.toString();
        }

        public static final SumNodeFormatter INSTANCE = new SumNodeFormatter();

    }

}
