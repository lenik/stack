package com.bee32.sem.process.verify.util;

import java.util.Date;

import com.bee32.plover.arch.util.PropertyAccessor;
import com.bee32.plover.orm.util.EntityDto;
import com.bee32.plover.orm.util.IUnmarshalContext;
import com.bee32.sem.event.EventState;
import com.bee32.sem.event.dto.TaskDto;
import com.bee32.sem.event.entity.Task;
import com.bee32.sem.process.verify.IVerifyContext;
import com.bee32.sem.process.verify.builtin.dto.VerifyPolicyDto;

public abstract class VerifiableEntityDto<E extends VerifiableEntityBean<K, ? extends IVerifyContext>, K extends Number>
        extends EntityDto<E, K> {

    private static final long serialVersionUID = 1L;

    private EventState verifyState;
    private String verifyError;
    private Date verifyEvalDate;
    private TaskDto verifyTask;

    private VerifyPolicyDto verifyPolicy;

    public VerifiableEntityDto() {
        super();
    }

    public VerifiableEntityDto(E source) {
        super(source);
    }

    public VerifiableEntityDto(int selection) {
        super(selection);
    }

    public VerifiableEntityDto(E source, int selection) {
        super(source, selection);
    }

    @Override
    protected void __marshal(E source) {
        super.__marshal(source);

        verifyState = source.getVerifyState();
        verifyError = source.getVerifyError();
        verifyEvalDate = source.getVerifyEvalDate();

        verifyTask = new TaskDto().ref(source.getVerifyTask());
    }

    @Override
    protected void __unmarshalTo(IUnmarshalContext context, E target) {
        super.__unmarshalTo(context, target);

        // target.setVerifyState(verifyState);
        // target.setVerifyError(verifyError);
        // target.setVerifyEvalDate(verifyEvalDate);

        with(context, target).unmarshal(verifyTaskProperty, verifyTask);
    }

    public EventState getVerifyState() {
        return verifyState;
    }

    public void setVerifyState(EventState verifyState) {
        this.verifyState = verifyState;
    }

    public String getVerifyError() {
        return verifyError;
    }

    public void setVerifyError(String verifyError) {
        this.verifyError = verifyError;
    }

    public Date getVerifyEvalDate() {
        return verifyEvalDate;
    }

    public void setVerifyEvalDate(Date verifyEvalDate) {
        this.verifyEvalDate = verifyEvalDate;
    }

    public VerifyPolicyDto getVerifyPolicy() {
        return verifyPolicy;
    }

    public void setVerifyPolicy(VerifyPolicyDto verifyPolicy) {
        this.verifyPolicy = verifyPolicy;
    }

    final PropertyAccessor<E, Task> verifyTaskProperty = new PropertyAccessor<E, Task>(Task.class) {

        @Override
        public Task get(E entity) {
            return entity.getVerifyTask();
        }

        @Override
        public void set(E entity, Task verifyTask) {
            entity.setVerifyTask(verifyTask);
        }

    };

}
