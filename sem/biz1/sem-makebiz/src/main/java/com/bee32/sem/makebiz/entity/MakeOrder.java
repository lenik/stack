package com.bee32.sem.makebiz.entity;

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
import com.bee32.sem.chance.entity.Chance;
import com.bee32.sem.make.entity.Part;
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
    List<MaterialPlan> plans = new ArrayList<MaterialPlan>();
    List<DeliveryNote> deliveryNotes = new ArrayList<DeliveryNote>();

    @Override
    public void populate(Object source) {
        if (source instanceof MakeOrder)
            _populate((MakeOrder) source);
        else
            super.populate(source);
    }

    protected void _populate(MakeOrder o) {
        super._populate(o);
        customer = o.customer;
        status = o.status;
        chance = o.chance;
        tasks = new ArrayList<MakeTask>(o.tasks);
        plans = new ArrayList<MaterialPlan>(o.plans);
        verifyContext = (SingleVerifierWithNumberSupport) verifyContext.clone();
    }

    @Override
    protected void createTransients() {
        if (verifyContext == null)
            setVerifyContext(new SingleVerifierWithNumberSupport());
        else
            verifyContext.bind(this, nativeTotalProperty, "金额");
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

    @OneToMany(mappedBy = "order")
    @Cascade(CascadeType.ALL)
    public List<MaterialPlan> getPlans() {
        return plans;
    }

    public void setPlans(List<MaterialPlan> plans) {
        this.plans = plans;
    }

    @OneToMany(mappedBy = "order")
    @Cascade(CascadeType.ALL)
    public List<DeliveryNote> getDeliveryNotes() {
        return deliveryNotes;
    }

    public void setDeliveryNotes(List<DeliveryNote> deliveryNotes) {
        if (deliveryNotes == null)
            throw new NullPointerException("deliveryNotes");
        this.deliveryNotes = deliveryNotes;
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
        //计算已经在task中按排的量
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

        //计算已经在plan中按排的量
//        for (MaterialPlan plan : plans) {
//            for (MaterialPlanItem planItem : plan.getItems()) {
//                Part part =
//
//                planItem.material
//
//                BigDecimal sum = sumMap.get(planItem.part);
//                if (sum == null) {
//                    sumMap.put(taskItem.part, taskItem.getQuantity());
//                } else {
//                    sum = sum.add(taskItem.getQuantity());
//                    sumMap.put(taskItem.part, sum);
//                }
//            }
//        }

        return sumMap;
    }

    /**
     * Sum of part quantity in each delivery note item.
     *
     * @aka deliveryNoteItemListToMap
     */
    @Transient
    Map<Part, BigDecimal> getDeliveriedPartSum() {
        Map<Part, BigDecimal> sumMap = new HashMap<Part, BigDecimal>();
        for (DeliveryNote note : deliveryNotes) {
            for (DeliveryNoteItem deliveryNoteItem : note.getItems()) {
                BigDecimal sum = sumMap.get(deliveryNoteItem.part);
                if (sum == null) {
                    sumMap.put(deliveryNoteItem.part, deliveryNoteItem.getQuantity());
                } else {
                    sum = sum.add(deliveryNoteItem.getQuantity());
                    sumMap.put(deliveryNoteItem.part, sum);
                }
            }
        }
        return sumMap;
    }

    /**
     * 寻找还没有按排生产任务的产品列表
     *
     * @return Non-null list of {@link MakeOrderItem}.
     * @aka getNoCorrespondingOrderItems
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
                // 生产任务的数量小于订单的数量
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
     * 寻找还没有按排送货单的产品列表
     *
     * @return Non-null list of {@link MakeOrderItem}.
     * @aka getNoCorrespondingOrderItems
     */
    @Transient
    public List<MakeOrderItem> getNotDeliveriedItems() {
        List<MakeOrderItem> result = new ArrayList<MakeOrderItem>();
        Map<Part, BigDecimal> sumMap = getDeliveriedPartSum();

        for (MakeOrderItem orderItem : items) {
            BigDecimal sum = sumMap.get(orderItem.getPart());
            // 尚没有对应的送货单，安排全部
            if (sum == null) {
                result.add(orderItem);
                continue;
            }
            // 有对应的送货单，安排剩下的部分
            BigDecimal remaining = orderItem.getQuantity().subtract(sum);
            if (remaining.longValue() > 0) {
                // 送货单的数量小于订单的数量
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

    /**
     * 检查所有对应送货单的数量总合是否超过订单的数量
     *
     * @aka checkIfDeliveryQuantityFitOrder
     */
    @Transient
    public Map<Part, BigDecimal> getOverloadPartsOfDelivery() {
        Map<Part, BigDecimal> overloadPartsDelivery = new HashMap<Part, BigDecimal>();
        Map<Part, BigDecimal> sumMap = getDeliveriedPartSum();
        for (MakeOrderItem orderItem : items) {
            BigDecimal sum = sumMap.get(orderItem.getPart());
            if (sum != null) {
                if (sum.compareTo(orderItem.getQuantity()) > 0) {
                    BigDecimal overloaded = sum.subtract(orderItem.getQuantity());
                    // 送货单中的数量大于订单中的数量
                    overloadPartsDelivery.put(orderItem.getPart(), overloaded);
                }
            }
        }
        return overloadPartsDelivery;
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
