package demo.policy;

import java.util.ArrayList;
import java.util.List;

import com.seccaproject.sems.security.verify.AbstractVerifyPolicy;
import com.seccaproject.sems.security.verify.BadVerifyDataException;
import com.seccaproject.sems.security.verify.IVerifiableObject;
import com.seccaproject.sems.security.verify.IVerifyData;
import com.seccaproject.sems.security.verify.VerifyException;



import demo.apps.dom.Person;

/**
 * 依序审核策略
 */
public class SequenceVerifyPolicy
        extends AbstractVerifyPolicy {

    /**
     * 审核步骤
     */
    public static class VerifyStep {

        /** 要求审核者的部门属性 */
        public String dept;

        /** 要求审核者的脚色属性 */
        public String role;

        /** 步骤可选 */
        public boolean optional;

        public VerifyStep(String dept, String role, boolean optional) {
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

    public static class VerifyData
            extends ArrayList<Person>
            implements IVerifyData {

        private static final long serialVersionUID = 1L;

        public VerifyData() {
        }

        public VerifyData(List<Person> persons) {
            if (persons == null)
                throw new NullPointerException("persons");
            this.addAll(persons);
        }

        public void reset() {
            this.clear();
        }

        public void addPerson(Person person) {
            if (person == null)
                throw new NullPointerException("person");
            this.add(person);
        }

        @Override
        public String toString() {
            String list = null;
            for (Person person : this) {
                if (list == null)
                    list = "";
                else
                    list += ", ";
                list += person.getName();
            }
            return "Verified by " + list + " (" + this.size() + " persons)";
        }

    }

    private List<VerifyStep> sequence;

    public SequenceVerifyPolicy(List<VerifyStep> sequence) {
        this.sequence = sequence;
    }

    @Override
    public void verify(IVerifiableObject verifiableObject)
            throws VerifyException, BadVerifyDataException {
        if (verifiableObject == null)
            throw new NullPointerException("verifiableObject");

        Object _data = verifiableObject.getVerifyData();
        if (_data == null)
            throw new VerifyException("尚未审核。");

        if (!(_data instanceof VerifyData))
            throw new BadVerifyDataException(VerifyData.class, _data);

        // 分析审核数据
        VerifyData data = (VerifyData) _data;
        int stepIndex = 0; // 审核步骤
        int personIndex = 0; // 实际审核者
        while (personIndex < data.size()) {
            Person actualPerson = data.get(personIndex);
            if (stepIndex == sequence.size()) {
                // 次序中要求的步骤均已经实现审核，忽略多余的审核数据。
                break;
            }
            VerifyStep step = sequence.get(stepIndex);
            boolean stepVerified = true;
            if (step.dept != null) {
                // 要求部门
                if (!step.dept.equals(actualPerson.getDept()))
                    stepVerified = false;
            }
            if (step.role != null) {
                // 要求角色
                if (!step.role.equals(actualPerson.getRole()))
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
