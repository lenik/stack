package com.bee32.sem.process.verify.util;

import javax.inject.Inject;

import com.bee32.icsf.principal.User;
import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.orm.web.basic.BasicEntityController;
import com.bee32.sem.process.verify.IVerifyContext;
import com.bee32.sem.process.verify.builtin.dto.VerifyPolicyDto;
import com.bee32.sem.process.verify.service.VerifyService;

public abstract class VerifiableEntityController<E extends VerifiableEntity<K, C>, //
/*        */K extends Number, C extends IVerifyContext, Dto extends VerifiableEntityDto<E, K>>
        extends BasicEntityController<E, K, Dto> {

    @Inject
    protected VerifyService verifyService;

    @Override
    protected void loadForm(E entity, Dto dto) {
        super.loadForm(entity, dto);
        VerifyPolicyDto verifyPolicy = verifyService.getVerifyPolicy(entity);
        dto.setVerifyPolicy(verifyPolicy);
    }

    @Override
    protected void saveForm(E entity, Dto dto) {
        super.saveForm(entity, dto);
        // Do the verification and all.
        verifyService.verifyEntity(entity);
        // dto.setVerifyState(result.getState());
        // dto.setVerifyError(result.getMessage());
    }

    /**
     * 准备实体中的和审核有关的上下文参数。
     *
     * @return 成功返回 <code>null</code>，否则返回错误消息。
     */
    protected abstract String doPreVerify(E entity, User currentUser, TextMap request);

    /**
     * 审核完成后的设置。
     */
    protected abstract void doPostVerify(E entity, User currentUser, TextMap request);

}
