package com.bee32.sem.process.verify.builtin;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.free.NotImplementedException;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bee32.icsf.principal.IPrincipal;
import com.bee32.icsf.principal.Principal;
import com.bee32.icsf.principal.User;
import com.bee32.plover.orm.util.Alias;
import com.bee32.sem.process.verify.VerifyEvent;
import com.bee32.sem.process.verify.VerifyPolicy;
import com.bee32.sem.process.verify.VerifyResult;

/**
 * 依序审核策略
 */
@Entity
@DiscriminatorValue("NXT")
@Alias("p2next")
public class PassToNext
        extends VerifyPolicy<IPassEvents> {

    private static final long serialVersionUID = 1L;

    static Logger logger = LoggerFactory.getLogger(PassToNext.class);

    private List<PassStep> sequences;

    public PassToNext() {
        this(new ArrayList<PassStep>());
    }

    public PassToNext(List<PassStep> sequence) {
        if (sequence == null)
            throw new NullPointerException("sequence");
        this.sequences = sequence;
    }

    @OneToMany(mappedBy = "policy")
    @OrderBy("order")
    public List<PassStep> getSequences() {
        return sequences;
    }

    public void setSequences(List<PassStep> sequences) {
        this.sequences = sequences;
    }

    /**
     * TODO
     */
    @Override
    public Set<Principal> getDeclaredResponsibles(IPassEvents context) {
        int position = 0;

        if (context != null)
            position = context.getPosition();

        if (sequences == null)
            return null;

        throw new NotImplementedException();
    }

    @Override
    public VerifyResult evaluate(IPassEvents passLogs) {

        // 分析审核数据
        int stepIndex = 0; // 审核步骤
        int personIndex = 0; // 实际审核者
        while (personIndex < passLogs.size()) {
            VerifyEvent event = passLogs.getEvent(personIndex);

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
