package com.bee32.sem.inventory.process;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.bee32.sem.inventory.entity.StockOrderSubject;
import com.bee32.sem.process.verify.VerifyPolicy;
import com.bee32.sem.process.verify.dto.VerifyPolicyDto;

public class StockOrderVerifyPolicyDto extends VerifyPolicyDto {

    private static final long serialVersionUID = 1L;

    public static final int SUBJECT_POLICY_MAP = 1;

    List<StockOrderSubjectPolicyEntry> entries;

    @Override
    protected void _marshal(VerifyPolicy _source) {
        super._marshal(_source);

        StockOrderVerifyPolicy source = (StockOrderVerifyPolicy) _source;

        if (selection.contains(SUBJECT_POLICY_MAP)) {
            Map<String, VerifyPolicy> map = source.getSubjectPolicyMap();
            entries = new ArrayList<StockOrderSubjectPolicyEntry>();
            for (Entry<String, VerifyPolicy> _entry : map.entrySet()) {
                String _subject = _entry.getKey();
                VerifyPolicy _policy = _entry.getValue();
                StockOrderSubject subject = StockOrderSubject.forValue(_subject);
                VerifyPolicyDto policy = mref(VerifyPolicyDto.class, _policy);
                StockOrderSubjectPolicyEntry entry = new StockOrderSubjectPolicyEntry(
                        subject, policy);
                entries.add(entry);
            }
        } else {
            entries = Collections.emptyList();
        }
    }

    @Override
    protected void _unmarshalTo(VerifyPolicy _target) {
        super._unmarshalTo(_target);

        StockOrderVerifyPolicy target = (StockOrderVerifyPolicy) _target;

        if (selection.contains(SUBJECT_POLICY_MAP)) {
            Map<String, VerifyPolicy> map = target.getSubjectPolicyMap();
            map.clear();
            for (StockOrderSubjectPolicyEntry entry : entries) {
                String _subject = entry.getSubject().getValue();
                VerifyPolicy _policy = entry.getPolicy().unmarshal();
                map.put(_subject, _policy);
            }
        }
    }

    public List<StockOrderSubjectPolicyEntry> getEntries() {
        return entries;
    }

    public void addEntry(StockOrderSubjectPolicyEntry entry) {
        entries.add(entry);
    }

    public void removeEntry(StockOrderSubjectPolicyEntry entry) {
        entries.remove(entry);
    }
}
