package com.bee32.sem.process.base;

import java.util.Set;

import com.bee32.icsf.login.SessionUser;
import com.bee32.plover.faces.utils.FacesPartialContext;
import com.bee32.plover.orm.util.MixinnedDataAssembledContext;
import com.bee32.plover.ox1.color.MomentIntervalDto;
import com.bee32.sem.process.state.web.EntityStateService;
import com.bee32.sem.process.verify.builtin.dto.SingleVerifierSupportDto;
import com.bee32.sem.process.verify.dto.IVerifiableDto;

public abstract class ProcessEntityDto<E extends ProcessEntity>
        extends MomentIntervalDto<E>
        implements IVerifiableDto {

    private static final long serialVersionUID = 1L;

    SingleVerifierSupportDto verifyContext;

    public ProcessEntityDto() {
        super();
    }

    public ProcessEntityDto(int fmask) {
        super(fmask);
    }

    @Override
    protected void __marshal(E source) {
        super.__marshal(source);
        verifyContext = marshal(SingleVerifierSupportDto.class, source.getVerifyContext());
    }

    @Override
    protected void __unmarshalTo(E target) {
        super.__unmarshalTo(target);
        merge(target, "verifyContext", verifyContext);
    }

    @Deprecated
    @Override
    public SingleVerifierSupportDto getVerifyContext() {
        return verifyContext;
    }

    @Deprecated
    public void setVerifyContext(SingleVerifierSupportDto verifyContext) {
        if (verifyContext == null)
            throw new NullPointerException("verifyContext");
        this.verifyContext = verifyContext;
    }

    protected static class ctx
            extends MixinnedDataAssembledContext {
        public static final FacesPartialContext view = FacesPartialContext.INSTANCE;
    }

    @SuppressWarnings("deprecation")
    public boolean isStateOp() {
        Set<Integer> myImIdSet = SessionUser.getInstance().getImIdSet();

        EntityStateService entityStateService = ctx.bean.getBean(EntityStateService.class);
        Class<? extends E> entityType = getEntityType();
        Set<Integer> stateImIdSet = entityStateService.getStateImSet(entityType, getStateInt());

        for (Integer myImId : myImIdSet)
            if (stateImIdSet.contains(myImId))
                return true;

        return false;
    }

}
