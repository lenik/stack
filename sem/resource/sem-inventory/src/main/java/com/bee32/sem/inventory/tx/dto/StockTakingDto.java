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
import com.bee32.sem.inventory.entity.StockOrderSubject;
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

    transient StockOrderDto unionOrder;

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
                StockOrderDto union = getUnionOrder();
                diffOrder = extract(union, /* extract-expects? */false, /* extract-diffs? */true)[1];
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
            unionOrder = null;
        }
    }

    public StockOrderDto getDiffOrder() {
        return diffOrder;
    }

    public void setDiffOrder(StockOrderDto diffOrder) {
        if (this.diffOrder != diffOrder) {
            this.diffOrder = diffOrder;
            unionOrder = null;
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
    public StockOrderDto getUnionOrder() {
        if (unionOrder == null) {
            synchronized (this) {
                if (unionOrder == null) {
                    this.unionOrder = union(expectedOrder, diffOrder);
                }
            }
        }
        return unionOrder;
    }

    StockOrderDto union(StockOrderDto expectedOrder, StockOrderDto diffOrder) {
        if (expectedOrder == null)
            throw new NullPointerException("expectedOrder");
        if (diffOrder == null)
            throw new NullPointerException("diffOrder");

        StockOrderDto unionOrder = new StockOrderDto().populate(expectedOrder);
        unionOrder.setSubject(StockOrderSubject.CACHE);
        unionOrder.setLabel("【汇总】实有库存盘点清单");

        for (StockOrderItemDto expected : expectedOrder.getItems()) {
            StockOrderItemDto foundDiff = null;

            if (diffOrder != null) {
                // search the corresponding diff item in diffOrder.
                for (StockOrderItemDto diff : diffOrder.getItems()) {
                    if (!DTOs.equals(diff.getMaterial(), expected.getMaterial()))
                        continue;
                    if (!Nullables.equals(diff.getCBatch(), expected.getCBatch()))
                        continue;
                    if (!DTOs.equals(diff.getLocation(), expected.getLocation()))
                        continue;
                    foundDiff = diff;
                    break;
                }
                if (foundDiff == null) {
                    StockOrderItemDto newDiff = new StockOrderItemDto().create();
                    diffOrder.addItem(newDiff);
                    foundDiff = newDiff;
                }
            }

            StockItemUnion unionItem = new StockItemUnion(expected, foundDiff);
            unionItem.setParent(unionOrder);
            unionItem.setImportant(false);
            unionOrder.addItem(unionItem);
        }
        return unionOrder;
    }

    StockOrderDto[] extract(StockOrderDto unionOrder, boolean extractExpects, boolean extractDiffs) {
        List<StockOrderItemDto> jv = unionOrder.getItems();

        StockOrderDto expectedOrder = null;
        StockOrderDto diffOrder = null;

        if (extractExpects) {
            expectedOrder = new StockOrderDto().populate(unionOrder);
            expectedOrder.marshalAs(MarshalType.SELECTION); // But should avoid to use it.
        }
        if (extractDiffs) {
            diffOrder = new StockOrderDto().populate(unionOrder);
            diffOrder.setSubject(StockOrderSubject.STKD);
            diffOrder.setLabel("【自动】盘点盈亏差值");
            diffOrder.marshalAs(MarshalType.SELECTION);
        }

        for (StockOrderItemDto j : jv) {
            StockItemUnion unionItem = (StockItemUnion) j;

            if (extractExpects) {
                StockOrderItemDto expected = new StockOrderItemDto().populate(unionItem);
                expected.setQuantity(unionItem.getExpected());
                expectedOrder.addItem(expected);
            }
            if (extractDiffs) {
                BigDecimal diffQuantity = unionItem.getDiff();
                if (!BigDecimal.ZERO.equals(diffQuantity)) { // Ignore diff==0 entries.
                    StockOrderItemDto diff = new StockOrderItemDto().populate(unionItem);
                    diff.setParent(diffOrder);
                    diff.setQuantity(diffQuantity);
                    diffOrder.addItem(diff);
                }
            }
        }

        return new StockOrderDto[] { expectedOrder, diffOrder };
    }

}
