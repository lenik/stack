package com.bee32.sem.process.verify.impl;

import java.util.List;

import com.bee32.icsf.principal.IGroupPrincipal;
import com.bee32.icsf.principal.IRolePrincipal;
import com.bee32.icsf.principal.IUserPrincipal;
import com.bee32.sem.process.verify.AbstractVerifyPolicy;
import com.bee32.sem.process.verify.BadVerifyDataException;
import com.bee32.sem.process.verify.IVerifiable;
import com.bee32.sem.process.verify.VerifyException;
import com.bee32.sem.process.verify.VerifyState;

/**
 * 依序审核策略
 */
public class PassToNext
        extends AbstractVerifyPolicy {

    private static final long serialVersionUID = 1L;

    /**
     * 审核步骤
     */
    public static class VerifyStep {

        /** 要求审核者的部门属性 */
        public IGroupPrincipal dept;

        /** 要求审核者的脚色属性 */
        public IRolePrincipal role;

        /** 步骤可选 */
        public boolean optional;

        public VerifyStep(IGroupPrincipal dept, IRolePrincipal role, boolean optional) {
            this.dept = dept;
            this.role = role;
            this.optional = optional;
        }

        @Override
        public String toString() {
            String description = optional ? "Optional-Step" : "Must-Step";
            if (dept != null)
                description += " Dept(" + dept + ")";
            if (role != null)
                description += " Role(" + role + ")";
            return description;
        }

    }

    private List<VerifyStep> sequence;

    public PassToNext(List<VerifyStep> sequence) {
        this.sequence = sequence;
    }

    @Override
    public void verify(IVerifiable verifiableObject)
            throws VerifyException, BadVerifyDataException {
        if (verifiableObject == null)
            throw new NullPointerException("verifiableObject");

        VerifyState state = verifiableObject.getVerifyState();
        if (state == null)
            throw new VerifyException("尚未审核。");

        if (!(state instanceof PassLog))
            throw new BadVerifyDataException(PassLog.class, state);

        // 分析审核数据
        PassLog passLog = (PassLog) state;
        int stepIndex = 0; // 审核步骤
        int personIndex = 0; // 实际审核者
        while (personIndex < passLog.size()) {
            IUserPrincipal actualPerson = passLog.get(personIndex);
            if (stepIndex == sequence.size()) {
                // 次序中要求的步骤均已经实现审核，忽略多余的审核数据。
                break;
            }
            VerifyStep step = sequence.get(stepIndex);
            boolean stepVerified = true;
            if (step.dept != null) {
                // 要求部门
                for (IGroupPrincipal group : actualPerson.getAssignedGroups())
                    if (!group.implies(step.dept))
                        stepVerified = false;
            }
            if (Boolean.FALSE)
                if (step.role != null) {
                    // 要求角色
                    IRolePrincipal actualRole = actualPerson.getAssignedRoles().iterator().next();
                    if (!actualRole.implies(step.role))
                        stepVerified = false;
                }
            if (stepVerified) {
                stepIndex++;
                personIndex++;
            } else if (step.optional) {
                // 跳过可选步骤 （这里存在一个歧义划分，demo中忽略了。）
                stepIndex++;
            } else {
                System.err.println("忽略了在步骤（" + step + "）中无效的审核者：" + actualPerson);
                personIndex++;
            }
        }

        // 分析完成。
        if (stepIndex < sequence.size()) {
            VerifyStep nextStepWanted = sequence.get(stepIndex);
            throw new VerifyException("审核未完成，下一步应该是 " + nextStepWanted);
        }
    }
}
