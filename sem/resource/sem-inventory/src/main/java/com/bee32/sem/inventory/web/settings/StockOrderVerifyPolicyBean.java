package com.bee32.sem.inventory.web.settings;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.faces.model.SelectItem;

import com.bee32.plover.orm.annotation.ForEntity;
import com.bee32.sem.inventory.entity.StockOrderSubject;
import com.bee32.sem.inventory.process.StockOrderSubjectPolicyEntry;
import com.bee32.sem.inventory.process.StockOrderVerifyPolicy;
import com.bee32.sem.inventory.process.StockOrderVerifyPolicyDto;
import com.bee32.sem.misc.SimpleEntityViewBean;
import com.bee32.sem.process.verify.builtin.SingleVerifierPolicy;
import com.bee32.sem.process.verify.builtin.dto.SingleVerifierPolicyDto;
import com.bee32.sem.process.verify.dto.VerifyPolicyDto;
import com.bee32.sem.process.verify.web.ChooseVerifyPolicyDialogListener;

@ForEntity(SingleVerifierPolicy.class)
public class StockOrderVerifyPolicyBean
        extends SimpleEntityViewBean {

    private static final long serialVersionUID = 1L;

    StockOrderSubjectPolicyEntry entry;

    public StockOrderVerifyPolicyBean() {
        super(StockOrderVerifyPolicy.class, StockOrderVerifyPolicyDto.class, 0);

        VerifyPolicyDto policy = new SingleVerifierPolicyDto().create();
        entry = new StockOrderSubjectPolicyEntry(StockOrderSubject.TAKE_IN, policy);
    }

    public Object getChooseVerifyPolicyDialogListener() {
        return new ChooseVerifyPolicyDialogListener() {
            @Override
            protected void select(List<?> selection) {
                for (Object item : selection)
                    setVerifyPolicy((VerifyPolicyDto) item);
            }
        };
    }

    public void setVerifyPolicy(VerifyPolicyDto policy) {
        entry.setPolicy(policy);
    }

    public StockOrderSubjectPolicyEntry getEntry() {
        return entry;
    }

    public void setEntry(StockOrderSubjectPolicyEntry entry) {
        this.entry = entry;
    }

    public void addEntry() {
        StockOrderVerifyPolicyDto policy = getActiveObject();
        policy.addEntry(entry);
    }

    public void removeEntry() {
        StockOrderVerifyPolicyDto policy = getActiveObject();
        policy.removeEntry(entry);
    }

    public void newEntry() {
        // 临时性的创建一个dto,用户在操作界面上操作后会使用用户选择的dto对此进行覆盖
        VerifyPolicyDto policy = new SingleVerifierPolicyDto().create();
        entry = new StockOrderSubjectPolicyEntry(StockOrderSubject.TAKE_IN, policy);
    }

    public List<SelectItem> getSubjects() {
        List<SelectItem> subjects = new ArrayList<SelectItem>();

        Collection<StockOrderSubject> _subjects = StockOrderSubject.values();
        for (StockOrderSubject _subject : _subjects) {
            SelectItem item = new SelectItem();
            item.setValue(_subject.getValue());
            item.setLabel(_subject.getDisplayName());
            subjects.add(item);
        }

        return subjects;
    }

}
