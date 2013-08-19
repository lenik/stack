package com.bee32.sem.inventory.process;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.MapKeyColumn;
import javax.persistence.Transient;

import com.bee32.icsf.principal.Principal;
import com.bee32.sem.inventory.entity.StockOrderSubject;
import com.bee32.sem.process.verify.ForVerifyContext;
import com.bee32.sem.process.verify.IVerifyContext;
import com.bee32.sem.process.verify.VerifyPolicy;
import com.bee32.sem.process.verify.VerifyResult;
import com.bee32.sem.process.verify.util.Tuple;

/**
 * 库存单据审核策略
 *
 * <p lang="en">
 * Stock Order Verify Policy
 */
@Entity
@DiscriminatorValue("STO")
@ForVerifyContext(IStockOrderVerifyContext.class)
public class StockOrderVerifyPolicy
        extends VerifyPolicy {

    private static final long serialVersionUID = 1L;

    Map<String, VerifyPolicy> subjectPolicyMap = new HashMap<String, VerifyPolicy>();

    @Override
    public void populate(Object source) {
        if (source instanceof StockOrderVerifyPolicy)
            _populate((StockOrderVerifyPolicy) source);
        else
            super.populate(source);
    }

    protected void _populate(StockOrderVerifyPolicy o) {
        super._populate(o);
        subjectPolicyMap = new HashMap<String, VerifyPolicy>(o.subjectPolicyMap);
    }

    @ManyToMany
    @JoinTable(name = "StockOrderSubjectPolicy")
    @MapKeyColumn(name = "subject", nullable = false)
    public Map<String, VerifyPolicy> getSubjectPolicyMap() {
        return subjectPolicyMap;
    }

    public void setSubjectPolicyMap(Map<String, VerifyPolicy> subjectPolicyMap) {
        if (subjectPolicyMap == null)
            throw new NullPointerException("subjectPolicyMap");
        this.subjectPolicyMap = subjectPolicyMap;
    }

    public VerifyPolicy getSubjectPolicy(StockOrderSubject subject) {
        return subjectPolicyMap.get(subject.getValue());
    }

    public void setSubjectPolicy(StockOrderSubject subject, VerifyPolicy subjectPolicy) {
        subjectPolicyMap.put(subject.getValue(), subjectPolicy);
    }

    @Transient
    @Override
    public Set<?> getPredefinedStages() {
        Set<Object> predefinedStages = new HashSet<Object>();
        for (Entry<String, VerifyPolicy> entry : subjectPolicyMap.entrySet()) {
            String subjectValue = entry.getKey();
            VerifyPolicy targetPolicy = entry.getValue();
            for (Object targetStage : targetPolicy.getPredefinedStages()) {
                Tuple predefinedStage = new Tuple(subjectValue, targetStage);
                predefinedStages.add(predefinedStage);
            }
        }
        return predefinedStages;
    }

    @Override
    public Tuple getStage(IVerifyContext _context) {
        IStockOrderVerifyContext context = checkedCast(IStockOrderVerifyContext.class, _context);
        StockOrderSubject subject = context.getSubject();
        VerifyPolicy subjectPolicy = subjectPolicyMap.get(subject);
        Object subjectStage = subjectPolicy.getStage(context);
        return new Tuple(subject.getValue(), subjectStage);
    }

    @Override
    public Set<Principal> getStageResponsibles(Object stage) {
        Tuple tuple = (Tuple) stage;
        String subjectValue = tuple.getFactor(0);
        StockOrderSubject subject = StockOrderSubject.forValue(subjectValue);
        VerifyPolicy subjectPolicy = subjectPolicyMap.get(subject);
        Object subjectStage = tuple.getFactor(1);
        return subjectPolicy.getStageResponsibles(subjectStage);
    }

    @Override
    public VerifyResult evaluate(IVerifyContext _context) {
        IStockOrderVerifyContext context = checkedCast(IStockOrderVerifyContext.class, _context);

        StockOrderSubject subject = context.getSubject();
        VerifyPolicy subjectPolicy = getSubjectPolicy(subject);

        /** 当 subject 对应的审核策略没有配置时，返回未知。 */
        if (subjectPolicy == null) {
            return VerifyResult.n_a("库存 subject 对应的审核策略尚未配置：" + subject.getDisplayName());
        }

        /** 否则利用 subject 对应的策略。 */
        return subjectPolicy.evaluate(context);
    }

}
