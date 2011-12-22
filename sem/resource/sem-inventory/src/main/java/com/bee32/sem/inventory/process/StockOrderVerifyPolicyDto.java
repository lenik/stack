package com.bee32.sem.inventory.process;

import java.util.Collections;
import java.util.Map;

import com.bee32.sem.process.verify.VerifyPolicy;
import com.bee32.sem.process.verify.dto.VerifyPolicyDto;

public class StockOrderVerifyPolicyDto
        extends VerifyPolicyDto {

    private static final long serialVersionUID = 1L;

    public static final int SUBJECT_POLICY_MAP = 1;

    Map<String, VerifyPolicyDto> subjectPolicyMap;

    @Override
    protected void _marshal(VerifyPolicy _source) {
        super._marshal(_source);

        StockOrderVerifyPolicy source = (StockOrderVerifyPolicy) _source;

        if (selection.contains(SUBJECT_POLICY_MAP)) {
            Map<String, VerifyPolicy> map = source.getSubjectPolicyMap();
            // mref map...
        } else {
            subjectPolicyMap = Collections.emptyMap();
        }
    }

    @Override
    protected void _unmarshalTo(VerifyPolicy _target) {
        super._unmarshalTo(_target);

        StockOrderVerifyPolicy target = (StockOrderVerifyPolicy) _target;

        if (selection.contains(SUBJECT_POLICY_MAP)) {
            // mergeMap();
        }
    }

    public Map<String, VerifyPolicyDto> getSubjectPolicyMap() {
        return subjectPolicyMap;
    }

    public VerifyPolicyDto getSubjectPolicy(String stockOrderSubjectName) {
        VerifyPolicyDto subjectPolicy = subjectPolicyMap.get(stockOrderSubjectName);
        if (subjectPolicy == null)
            subjectPolicy = new VerifyPolicyDto().ref();
        return subjectPolicy;
    }

    public void setSubjectPolicy(String stockOrderSubjectName, VerifyPolicyDto subjectPolicy) {
        subjectPolicyMap.put(stockOrderSubjectName, subjectPolicy);
    }

}
