package com.bee32.sem.process.verify.builtin;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bee32.icsf.principal.IPrincipal;
import com.bee32.icsf.principal.Principal;
import com.bee32.icsf.principal.User;
import com.bee32.plover.orm.entity.CloneUtils;
import com.bee32.sem.process.verify.ForVerifyContext;
import com.bee32.sem.process.verify.IVerifyContext;
import com.bee32.sem.process.verify.VerifyEvent;
import com.bee32.sem.process.verify.VerifyPolicy;
import com.bee32.sem.process.verify.VerifyResult;

/**
 * 依序审核策略
 */
@Entity
@DiscriminatorValue("P2N")
@ForVerifyContext(IPassEvents.class)
public class PassToNextPolicy
        extends VerifyPolicy {

    private static final long serialVersionUID = 1L;

    static Logger logger = LoggerFactory.getLogger(PassToNextPolicy.class);

    private List<PassStep> sequences;

    public PassToNextPolicy() {
        this(new ArrayList<PassStep>());
    }

    public PassToNextPolicy(List<PassStep> sequence) {
        if (sequence == null)
            throw new NullPointerException("sequence");
        this.sequences = sequence;
    }

    @Override
    public void populate(Object source) {
        if (source instanceof PassToNextPolicy)
            _populate((PassToNextPolicy) source);
        else
            super.populate(source);
    }

    protected void _populate(PassToNextPolicy o) {
        super._populate(o);
        sequences = CloneUtils.cloneList(o.sequences);
    }

    @OneToMany(mappedBy = "policy", orphanRemoval = true)
    @OrderBy("order")
    @Cascade(CascadeType.ALL)
    public List<PassStep> getSequences() {
        return sequences;
    }

    public void setSequences(List<PassStep> sequences) {
        this.sequences = sequences;
    }

    @Transient
    @Override
    public Set<?> getPredefinedStages() {
        Set<Integer> positions = new HashSet<Integer>();
        int n = sequences.size();
        for (int position = 0; position <= n; position++)
            positions.add(position);
        return positions;
    }

    @Override
    public Integer getStage(IVerifyContext context) {
        IPassEvents passEvents = (IPassEvents) context;
        return passEvents.getPosition();
    }

    @Override
    public Set<Principal> getStageResponsibles(Object stage) {
        int position = (Integer) stage;

        if (sequences == null)
            return null;

        PassStep step = sequences.get(position);
        Set<Principal> responsibles = new HashSet<Principal>();
        responsibles.add(step.getResponsible());
        return responsibles;
    }

    @Override
    public VerifyResult evaluate(IVerifyContext context) {
        IPassEvents passEvents = checkedCast(IPassEvents.class, context);

        // 分析审核数据
        int stepIndex = 0; // 审核步骤
        int personIndex = 0; // 实际审核者
        while (personIndex < passEvents.size()) {
            VerifyEvent event = passEvents.getEvent(personIndex);

            if (stepIndex == sequences.size()) {
                // 次序中要求的步骤均已经实现审核，忽略多余的审核数据。
                break;
            }

            PassStep step = sequences.get(stepIndex);
            User stepUser = event.getUser();

            IPrincipal responsible = step.getResponsible();
            assert responsible != null;

            if (stepUser.implies(responsible)) {
                stepIndex++;
                personIndex++;
            } else {
                if (step.optional) {
                    // 跳过可选步骤 （这里存在一个歧义划分，demo中忽略了。）
                    stepIndex++;
                } else {
                    logger.warn("忽略了在步骤（" + step + "）中无效的审核者：" + stepUser);
                    personIndex++;
                }
            }

        }

        // 分析完成。
        if (stepIndex < sequences.size()) {
            PassStep nextStepWanted = sequences.get(stepIndex);
            return VerifyResult.pending("审核未完成，下一步应该是 " + nextStepWanted);
        }

        return null;
    }

}
