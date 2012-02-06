package com.bee32.sem.inventory.tx.dto;

import java.math.BigDecimal;
import java.util.List;

import javax.free.NotImplementedException;
import javax.free.Nullables;
import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.arch.util.dto.MarshalType;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.sem.inventory.dto.StockOrderDto;
import com.bee32.sem.inventory.dto.StockOrderItemDto;
import com.bee32.sem.inventory.tx.StockTakingFeat;
import com.bee32.sem.inventory.tx.entity.StockTaking;

/**
 * @see StockTakingFeat
 */
public class StockTakingDto
        extends StockJobDto<StockTaking> {

    private static final long serialVersionUID = 1L;

    public static final int JOIN = 1;

    StockOrderDto expectedOrder;
    StockOrderDto diffOrder;

    transient StockOrderDto joined;

    public StockTakingDto() {
        super();
    }

    public StockTakingDto(int fmask) {
        super(fmask);
    }

    @Override
    protected void _marshal(StockTaking source) {
        if (selection.contains(ORDERS)) {
            int orderSelection = selection.translate(//
                    ITEMS, StockOrderDto.ITEMS);
            expectedOrder = marshal(StockOrderDto.class, orderSelection, source.getExpected());
            diffOrder = marshal(StockOrderDto.class, orderSelection, source.getDiff());
        }
    }

    @Override
    protected void _unmarshalTo(StockTaking target) {
        if (selection.contains(ORDERS)) {
            // Never update: merge the expectedOrder for initialization only.
            // assert StockOrderDto expectedOrder = extracted[0];
            merge(target, "expected", expectedOrder);

            StockOrderDto diffOrder = this.diffOrder;
            if (selection.contains(JOIN)) {
                StockOrderDto joined = getJoined();
                diffOrder = extract(joined, /* extract-expects? */false, /* extract-diffs? */true)[1];
            }
            merge(target, "diff", diffOrder);
        }
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
        throw new NotImplementedException();
    }

    /**
     * 仅当初始化的时候有意义.
     */
    public StockOrderDto getExpectedOrder() {
        return expectedOrder;
    }

    public void setExpectedOrder(StockOrderDto expectedOrder) {
        if (this.expectedOrder != expectedOrder) {
            this.expectedOrder = expectedOrder;
            joined = null;
        }
    }

    public StockOrderDto getDiffOrder() {
        return diffOrder;
    }

    public void setDiffOrder(StockOrderDto diffOrder) {
        if (this.diffOrder != diffOrder) {
            this.diffOrder = diffOrder;
            joined = null;
        }
    }

    /**
     * 获取联合表。
     *
     * 联合表的 item 有 expected, actual, diff 三个数量。
     *
     * @throws IllegalStateException
     *             当尚未初始化时。
     */
    public StockOrderDto getJoined() {
        if (joined == null) {
            synchronized (this) {
                if (joined == null) {
                    this.joined = join(expectedOrder, diffOrder);
                }
            }
        }
        return joined;
    }

    StockOrderDto join(StockOrderDto expectedOrder, StockOrderDto diffOrder) {
        if (expectedOrder == null)
            throw new NullPointerException("expectedOrder");
        if (diffOrder == null)
            throw new NullPointerException("diffOrder");

        StockOrderDto joinedOrder = new StockOrderDto().populate(expectedOrder);
        joinedOrder.setLabel("【汇总】实有库存盘点清单");

        for (StockOrderItemDto expected : expectedOrder.getItems()) {
            StockOrderItemDto matchedDiff = null;

            if (diffOrder != null) {
                // search the corresponding diff item in diffOrder.
                for (StockOrderItemDto diff : diffOrder.getItems()) {
                    if (!DTOs.equals(diff.getMaterial(), expected.getMaterial()))
                        continue;
                    if (!Nullables.equals(diff.getCBatch(), expected.getCBatch()))
                        continue;
                    if (!DTOs.equals(diff.getLocation(), expected.getLocation()))
                        continue;
                    matchedDiff = diff;
                    break;
                }
                if (matchedDiff == null) {
                    StockOrderItemDto newDiff = new StockOrderItemDto().create();
                    diffOrder.addItem(matchedDiff = newDiff);
                }
            }

            StockTakingItemDto joined = new StockTakingItemDto(expected, matchedDiff);
            joined.setParent(joinedOrder);
            joinedOrder.addItem(joined);
        }
        return joinedOrder;
    }

    StockOrderDto[] extract(StockOrderDto joinedOrder, boolean extractExpects, boolean extractDiffs) {
        List<StockOrderItemDto> jv = joinedOrder.getItems();

        StockOrderDto expectedOrder = null;
        StockOrderDto diffOrder = null;

        if (extractExpects) {
            expectedOrder = new StockOrderDto().populate(joinedOrder);
            expectedOrder.marshalAs(MarshalType.SELECTION); // But should avoid to use it.
        }
        if (extractDiffs) {
            diffOrder = new StockOrderDto().populate(joinedOrder);
            diffOrder.setLabel("【自动】盘点盈亏差值");
            diffOrder.marshalAs(MarshalType.SELECTION);
        }

        for (StockOrderItemDto j : jv) {
            StockTakingItemDto joinedItem = (StockTakingItemDto) j;

            if (extractExpects) {
                StockOrderItemDto expected = new StockOrderItemDto().populate(joinedItem);
                expected.setQuantity(joinedItem.getExpected());
                expectedOrder.addItem(expected);
            }
            if (extractDiffs) {
                BigDecimal diffQuantity = joinedItem.getDiff();
                if (!BigDecimal.ZERO.equals(diffQuantity)) { // Ignore diff==0 entries.
                    StockOrderItemDto diff = new StockOrderItemDto().populate(joinedItem);
                    diff.setParent(diffOrder);
                    diff.setQuantity(diffQuantity);
                    diffOrder.addItem(diff);
                }
            }
        }

        return new StockOrderDto[] { expectedOrder, diffOrder };
    }

}
