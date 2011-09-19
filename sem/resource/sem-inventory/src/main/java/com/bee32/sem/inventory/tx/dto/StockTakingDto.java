package com.bee32.sem.inventory.tx.dto;

import java.util.ArrayList;
import java.util.List;

import javax.free.NotImplementedException;
import javax.free.Nullables;
import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.sem.inventory.dto.StockOrderDto;
import com.bee32.sem.inventory.dto.StockOrderItemDto;
import com.bee32.sem.inventory.tx.entity.StockTaking;

/**
 * 初始化的时候：
 * <ul>
 * <li>setExpected(expectedOrder)
 * </ul>
 * <p>
 * 编辑的时候：
 * <ul>
 * </ul>
 */
public class StockTakingDto
        extends StockJobDto<StockTaking> {

    private static final long serialVersionUID = 1L;

    StockOrderDto expectedOrder;
    StockOrderDto joined; // transient

    Long _expectedOrderId;
    Long _diffOrderId;

    public StockTakingDto() {
        super();
    }

    public StockTakingDto(int selection) {
        super(selection);
    }

    @Override
    protected void _marshal(StockTaking source) {
        if (selection.contains(ORDERS)) {
            int orderSelection = selection.translate(//
                    ITEMS, StockOrderDto.ITEMS);
            StockOrderDto expectedOrder = marshal(StockOrderDto.class, orderSelection, source.getExpected());
            StockOrderDto diffOrder = marshal(StockOrderDto.class, orderSelection, source.getDiff());

            _expectedOrderId = expectedOrder.getId();
            _diffOrderId = diffOrder.getId();

            joined = join(expectedOrder, diffOrder);
        }
    }

    @Override
    protected void _unmarshalTo(StockTaking target) {
        if (selection.contains(ORDERS)) {

            StockOrderDto[] extracted = extract(joined);

            // Never update: merge the expectedOrder for initialization only.
            // StockOrderDto expectedOrder = extracted[0];
            if (expectedOrder != null)
                merge(target, "expected", expectedOrder);

            StockOrderDto diffOrder = extracted[1];
            merge(target, "diff", diffOrder);
        }
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
        throw new NotImplementedException();
    }

    /**
     * 仅当初始化的时候有意义，后面编辑的时候返回 <code>null</code>。
     */
    public StockOrderDto getExpectedOrder() {
        return expectedOrder;
    }

    /**
     * 初始化帐面数量。
     *
     * 只需初始化一次，后面编辑的时候不必再调用本方法。
     *
     * @see #getJoined()
     */
    public void initialize(StockOrderDto expectedOrder) {
        this.expectedOrder = expectedOrder;
        if (expectedOrder != null)
            join(expectedOrder, null);
    }

    /**
     * 获取联合表。
     *
     * @throws IllegalStateException
     *             当尚未初始化时。
     */
    public StockOrderDto getJoined() {
        if (joined == null)
            throw new IllegalStateException("No joined order: not initialized, yet.");
        return joined;
    }

    StockOrderDto join(StockOrderDto expectedOrder, StockOrderDto diffOrder) {
        StockOrderDto joinedOrder = new StockOrderDto(expectedOrder);
        for (StockOrderItemDto expected : expectedOrder.getItems()) {
            StockOrderItemDto matchedDiff = null;

            // search the corresponding diff item in diffOrder.
            for (StockOrderItemDto diff : diffOrder.getItems()) {
                if (!Nullables.equals(diff.getMaterial(), expected.getMaterial()))
                    continue;
                if (!Nullables.equals(diff.getCBatch(), expected.getCBatch()))
                    continue;
                if (!Nullables.equals(diff.getLocation(), expected.getLocation()))
                    continue;
                matchedDiff = diff;
                break;
            }
            StockTakingItem joined = new StockTakingItem(expected, matchedDiff);
            joinedOrder.addItem(joined);
        }
        return joinedOrder;
    }

    StockOrderDto[] extract(StockOrderDto joinedOrder) {
        StockOrderDto expectedOrder = new StockOrderDto(joinedOrder);
        StockOrderDto diffOrder = new StockOrderDto(joinedOrder);

        List<StockOrderItemDto> joinedItems = joined.getItems();

        if (expectedOrder != null) {
        }

        StockOrderDto expected = new StockOrderDto(joined);
        List<StockOrderItemDto> expectedItems = new ArrayList<StockOrderItemDto>(joinedItems.size());
        for (StockOrderItemDto _item : joinedItems) {
            StockTakingItem joinedItem = (StockTakingItem) _item;

            StockOrderItemDto expectedItem = new StockOrderItemDto(joinedItem);
            expectedItem.setQuantity(joinedItem.getExpected());
            expectedItems.add(expectedItem);

        }
        expected.setItems(expectedItems);

        return new StockOrderDto[] { expectedOrder, diffOrder };
    }

}
