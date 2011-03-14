package com.bee32.sem.process.verify.builtin;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bee32.icsf.principal.IPrincipal;
import com.bee32.icsf.principal.IUserPrincipal;
import com.bee32.icsf.principal.Principal;
import com.bee32.sem.process.verify.ContextClass;
import com.bee32.sem.process.verify.VerifyPolicy;

/**
 * 依序审核策略
 */
@ContextClass(ICurrentStep.class)
@Entity
@DiscriminatorValue("p2n")
public class PassToNext
        extends VerifyPolicy<ICurrentStep, PassLog> {

    private static final long serialVersionUID = 1L;

    static Logger logger = LoggerFactory.getLogger(PassToNext.class);

    private List<PassStep> sequence;

    public PassToNext() {
        super(PassLog.class);
        this.sequence = new ArrayList<PassStep>();
    }

    public PassToNext(List<PassStep> sequence) {
        super(PassLog.class);

        if (sequence == null)
            throw new NullPointerException("sequence");

        this.sequence = sequence;
    }

    @Override
    public Collection<? extends Principal> getDeclaredResponsibles(ICurrentStep context) {
        int stepIndex = context.getCurrentStepIndex();
        if (sequence == null)
            return null;
        return null;
    }

    @Override
    public String checkState(ICurrentStep context, PassLog passLog) {
        if (passLog == null)
            return "尚未审核。";

        // 分析审核数据
        int stepIndex = 0; // 审核步骤
        int personIndex = 0; // 实际审核者
        while (personIndex < passLog.size()) {
            IUserPrincipal actualPerson = passLog.get(personIndex);
            if (stepIndex == sequence.size()) {
                // 次序中要求的步骤均已经实现审核，忽略多余的审核数据。
                break;
            }

            PassStep step = sequence.get(stepIndex);

            boolean stepVerified = true;

            IPrincipal responsible = step.getResponsible();
            assert responsible != null;

            if (actualPerson.implies(responsible))
                stepVerified = false;

            if (stepVerified) {
                stepIndex++;
                personIndex++;
            } else if (step.optional) {
                // 跳过可选步骤 （这里存在一个歧义划分，demo中忽略了。）
                stepIndex++;
            } else {
                logger.warn("忽略了在步骤（" + step + "）中无效的审核者：" + actualPerson);
                personIndex++;
            }
        }

        // 分析完成。
        if (stepIndex < sequence.size()) {
            PassStep nextStepWanted = sequence.get(stepIndex);
            return "审核未完成，下一步应该是 " + nextStepWanted;
        }

        return null;
    }

    public List<PassStep> getSequence() {
        return sequence;
    }

    public void setSequence(List<PassStep> sequence) {
        this.sequence = sequence;
    }

}
