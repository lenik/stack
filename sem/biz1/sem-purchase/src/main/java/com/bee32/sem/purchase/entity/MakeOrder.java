package com.bee32.sem.purchase.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.bee32.plover.arch.bean.BeanPropertyAccessor;
import com.bee32.plover.arch.bean.IPropertyAccessor;
import com.bee32.plover.ox1.config.DecimalConfig;
import com.bee32.sem.bom.entity.Part;
import com.bee32.sem.chance.entity.Chance;
import com.bee32.sem.people.entity.Party;
import com.bee32.sem.process.verify.IVerifiable;
import com.bee32.sem.process.verify.builtin.ISingleVerifierWithNumber;
import com.bee32.sem.process.verify.builtin.SingleVerifierWithNumberSupport;
import com.bee32.sem.world.thing.AbstractItemList;

/**
 * 生产订单
 */
@Entity
@SequenceGenerator(name = "idgen", sequenceName = "make_order_seq", allocationSize = 1)
public class MakeOrder
        extends AbstractItemList<MakeOrderItem>
        implements DecimalConfig, IVerifiable<ISingleVerifierWithNumber> {

    private static final long serialVersionUID = 1L;

    public static final int STATUS_LENGTH = 30;

    Party customer;
    String status;
    Chance chance;

    List<MakeTask> tasks = new ArrayList<MakeTask>();

    public MakeOrder() {
        setVerifyContext(new SingleVerifierWithNumberSupport());
    }

    @ManyToOne(optional = false)
    public Party getCustomer() {
        return customer;
    }

    public void setCustomer(Party customer) {
        if (customer == null)
            throw new NullPointerException("customer");
        this.customer = customer;
    }

    @Column(length = STATUS_LENGTH)
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @OneToMany(mappedBy = "order")
    @Cascade(CascadeType.ALL)
    public List<MakeTask> getTasks() {
        return tasks;
    }

    public void setTasks(List<MakeTask> tasks) {
        if (tasks == null)
            throw new NullPointerException("tasks");
        this.tasks = tasks;
    }

    @OneToOne
    public Chance getChance() {
        return chance;
    }

    public void setChance(Chance chance) {
        this.chance = chance;
    }

    /**
     * Sum of part quantity in each task item.
     *
     * @aka taskItemListToMap
     */
    @Transient
    Map<Part, BigDecimal> getArrangedPartSum() {
        Map<Part, BigDecimal> sumMap = new HashMap<Part, BigDecimal>();
        for (MakeTask task : tasks) {
            for (MakeTaskItem taskItem : task.getItems()) {
                BigDecimal sum = sumMap.get(taskItem.part);
                if (sum == null) {
                    sumMap.put(taskItem.part, taskItem.getQuantity());
                } else {
                    sum = sum.add(taskItem.getQuantity());
                    sumMap.put(taskItem.part, sum);
                }
            }
        }
        return sumMap;
    }

    /**
     * 找寻还没有按排生产任务的产品列表
     *
     * @return Non-null list of {@link MakeOrderItem}.
     * @aka getNoCorrespondingTaskItems
     */
    @Transient
    public List<MakeOrderItem> getNotArrangedItems() {
        List<MakeOrderItem> result = new ArrayList<MakeOrderItem>();
        Map<Part, BigDecimal> sumMap = getArrangedPartSum();

        for (MakeOrderItem orderItem : items) {
            BigDecimal sum = sumMap.get(orderItem.getPart());
            // 尚没有对应的生产任务，生产全部
            if (sum == null) {
                result.add(orderItem);
                continue;
            }
            // 有对应的生产任务，生产剩下的部分
            BigDecimal remaining = orderItem.getQuantity().subtract(sum);
            if (remaining.longValue() > 0) {
                // 生产任务的数量小订单的数量
                MakeOrderItem remainingItem = new MakeOrderItem();
                remainingItem.setParent(this);
                remainingItem.setPart(orderItem.getPart());
                remainingItem.setQuantity(remaining);
                remainingItem.setExternalProductName(orderItem.getExternalProductName());
                remainingItem.setExternalModelSpec(orderItem.getExternalModelSpec());
                result.add(remainingItem);
            }
        }
        return result;
    }

    /**
     * 检查所有对应生产任务单的数量总合是否超过订单的数量
     *
     * @aka checkIfTaskQuantityFitOrder
     */
    @Transient
    public Map<Part, BigDecimal> getOverloadParts() {
        Map<Part, BigDecimal> overloadParts = new HashMap<Part, BigDecimal>();
        Map<Part, BigDecimal> sumMap = getArrangedPartSum();
        for (MakeOrderItem orderItem : items) {
            BigDecimal sum = sumMap.get(orderItem.getPart());
            if (sum != null) {
                if (sum.compareTo(orderItem.getQuantity()) > 0) {
                    BigDecimal overloaded = sum.subtract(orderItem.getQuantity());
                    // 生产任务中的数量大于订单中的数量
                    overloadParts.put(orderItem.getPart(), overloaded);
                }
            }
        }
        return overloadParts;
    }

    public static final IPropertyAccessor<BigDecimal> nativeTotalProperty = BeanPropertyAccessor.access(//
            MakeOrder.class, "nativeTotal");

    SingleVerifierWithNumberSupport verifyContext;

    @Embedded
    @Override
    public SingleVerifierWithNumberSupport getVerifyContext() {
        return verifyContext;
    }

    public void setVerifyContext(SingleVerifierWithNumberSupport verifyContext) {
        this.verifyContext = verifyContext;
        verifyContext.bind(this, nativeTotalProperty, "金额");
    }

}
