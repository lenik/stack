package com.bee32.sem.process.verify.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bee32.icsf.login.SessionUser;
import com.bee32.icsf.principal.User;
import com.bee32.plover.orm.util.DataViewBean;
import com.bee32.plover.orm.util.EntityDto;
import com.bee32.sem.process.verify.IVerifiable;
import com.bee32.sem.process.verify.VerifyEvalState;
import com.bee32.sem.process.verify.dto.IVerifiableDto;
import com.bee32.sem.process.verify.service.IVerifyService;

public class VerifiableSupportBean
        extends DataViewBean {

    private static final long serialVersionUID = 1L;

    static final Map<VerifyEvalState, String> commandLabelMap;
    static final Map<VerifyEvalState, Boolean> commandEnabledMap;
    static {
        commandLabelMap = new HashMap<VerifyEvalState, String>();
        commandLabelMap.put(VerifyEvalState.NOT_APPLICABLE, "审核不可用");
        commandLabelMap.put(VerifyEvalState.UNKNOWN, "审核");
        commandLabelMap.put(VerifyEvalState.INVALID, "审核");
        commandLabelMap.put(VerifyEvalState.VERIFIED, "已审核");
        commandLabelMap.put(VerifyEvalState.REJECTED, "重审");
        commandLabelMap.put(VerifyEvalState.PENDING, "审核中");

        commandEnabledMap = new HashMap<VerifyEvalState, Boolean>();
        commandEnabledMap.put(VerifyEvalState.NOT_APPLICABLE, false);
        commandEnabledMap.put(VerifyEvalState.UNKNOWN, true);
        commandEnabledMap.put(VerifyEvalState.INVALID, true);
        commandEnabledMap.put(VerifyEvalState.VERIFIED, false);
        commandEnabledMap.put(VerifyEvalState.REJECTED, true);
        commandEnabledMap.put(VerifyEvalState.PENDING, false);
    }

    public String getCommandLabel(List<? extends IVerifiableDto> verifiables) {
        VerifyEvalState state = VerifyEvalState.meet(verifiables);
        String name = commandLabelMap.get(state);
        if (name == null)
            name = "审核";
        return name;
    }

    public boolean isCommandEnabled(List<? extends IVerifiableDto> verifiables) {
        VerifyEvalState state = VerifyEvalState.meet(verifiables);
        Boolean enabled = commandEnabledMap.get(state);
        return enabled == Boolean.TRUE;
    }

    public boolean isCurrentUserResponsible(List<? extends IVerifiableDto> verifiables) {
        // VerifyEvalState state = getMixedVerifyState(verifiables);
        return true;
    }

    public boolean isContextResponsible(List<? extends IVerifiableDto> verifiables) {
        if (verifiables.isEmpty())
            return false;

        IVerifyService service = ctx.bean.getBean(IVerifyService.class);
        User me = SessionUser.getInstance().getInternalUser();

        for (IVerifiableDto verifiableDto : verifiables) {
            EntityDto<?, ?> entityDto = (EntityDto<?, ?>) verifiableDto;
            IVerifiable<?> verifiable = (IVerifiable<?>) entityDto.unmarshal();
            boolean responsible = service.isResponsible(me, verifiable);
            if (!responsible)
                return false;
        }
        return true;
    }

}
