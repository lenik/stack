package com.bee32.sem.process.verify.web;

import javax.inject.Inject;

import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.orm.util.EntityDto;
import com.bee32.plover.orm.web.basic.BasicEntityController;
import com.bee32.sem.process.verify.IVerifiable;
import com.bee32.sem.process.verify.IVerifyContext;
import com.bee32.sem.process.verify.dto.IVerifiableDto;
import com.bee32.sem.process.verify.dto.VerifyContextDto;
import com.bee32.sem.process.verify.dto.VerifyPolicyDto;
import com.bee32.sem.process.verify.service.VerifyService;

public abstract class VerifiableEntityController<E extends Entity<K> & IVerifiable<C>, //
/*        */K extends Number, C extends IVerifyContext, Dto extends EntityDto<? super E, K> & IVerifiableDto>
        extends BasicEntityController<E, K, Dto>
        implements IVerifyHandlerHook<E> {

    @Inject
    protected VerifyService verifyService;

    @Override
    protected void loadForm(E entity, Dto dto) {
        super.loadForm(entity, dto);
        VerifyPolicyDto verifyPolicy = verifyService.getVerifyPolicy(entity);
        VerifyContextDto<?> contextDto = dto.getVerifyContext();
        contextDto.setVerifyPolicy(verifyPolicy);
    }

    @Override
    protected void saveForm(E entity, Dto dto) {
        super.saveForm(entity, dto);
        // Do the verification and all.
        verifyService.verifyEntity(entity);
        // dto.setVerifyState(result.getState());
        // dto.setVerifyError(result.getMessage());
    }

}
