package com.bee32.sem.inventory.process;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.free.NotImplementedException;
import javax.persistence.CascadeType;
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

/**
 * 库存单据审核策略
 */
@ForVerifyContext(IStockOrderVerifyContext.class)
@Entity
public class StockOrderVerifyPolicy
        extends VerifyPolicy {

    private static final long serialVersionUID = 1L;

    Map<String, VerifyPolicy> subjectPolicyMap = new HashMap<String, VerifyPolicy>();

    @ManyToMany(cascade = CascadeType.ALL)
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

    @Transient
    public VerifyPolicy getSubjectPolicy(StockOrderSubject subject) {
        return subjectPolicyMap.get(subject);
    }

    @Override
    public Set<Principal> getDeclaredResponsibles(IVerifyContext context) {
        throw new NotImplementedException();
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
