package com.bee32.sem.process.verify.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import com.bee32.icsf.principal.IPrincipal;
import com.bee32.plover.model.stage.IModelStage;
import com.bee32.plover.model.stage.ModelLoadException;
import com.bee32.plover.model.stage.ModelStageException;
import com.bee32.sem.process.verify.AbstractVerifyPolicy;
import com.bee32.sem.process.verify.BadVerifyDataException;
import com.bee32.sem.process.verify.IVerifiable;
import com.bee32.sem.process.verify.VerifyException;
import com.bee32.sem.process.verify.VerifyState;

/**
 * 由任一管理员审核策略。
 */
public class AllowList
        extends AbstractVerifyPolicy {

    private static final long serialVersionUID = 1L;

    private final Collection<IPrincipal> allowList;

    public AllowList(IPrincipal singleManager) {
        this.allowList = new ArrayList<IPrincipal>(1);
        this.allowList.add(singleManager);
    }

    public AllowList(IPrincipal... managers) {
        if (managers == null)
            throw new NullPointerException("managers");
        this.allowList = Arrays.asList(managers);
    }

    public AllowList(Collection<IPrincipal> managers) {
        if (managers == null)
            throw new NullPointerException("managers");
        this.allowList = managers;
    }

    public void verify(IVerifiable verifiableObject)
            throws VerifyException, BadVerifyDataException {
        if (verifiableObject == null)
            throw new NullPointerException("verifiableObject");

        VerifyState state = verifiableObject.getVerifyState();
        if (state == null)
            throw new VerifyException("尚未审核。");

        if (!(state instanceof AllowState))
            throw new BadVerifyDataException(AllowState.class, state);

        AllowState allowState = (AllowState) state;

        if (!allowList.contains(allowState.getAllowedBy()))
            throw new VerifyException("无效的审核人：" + allowState.getAllowedBy());
    }

    @Override
    public void stage(IModelStage stage)
            throws ModelStageException {
        super.stage(stage);
    }

    @Override
    public void reload(IModelStage stage)
            throws ModelLoadException {
        super.reload(stage);
    }

}
