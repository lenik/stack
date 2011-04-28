package com.bee32.sem.process.verify.util;

import java.io.IOException;
import java.util.Set;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.bee32.icsf.principal.IUserPrincipal;
import com.bee32.icsf.principal.Principal;
import com.bee32.plover.ajax.SuccessOrFailMessage;
import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.orm.ext.util.BasicEntityController;
import com.bee32.plover.orm.ext.util.EntityAction;
import com.bee32.sem.process.verify.IVerifyContext;
import com.bee32.sem.process.verify.builtin.dto.VerifyPolicyDto;
import com.bee32.sem.process.verify.service.VerifyService;
import com.bee32.sem.user.util.SessionLoginInfo;

public abstract class VerifiableEntityController<E extends VerifiableEntityBean<K, C>, //
/*        */K extends Number, C extends IVerifyContext, Dto extends VerifiableEntityDto<E, K>>
        extends BasicEntityController<E, K, Dto> {

    @Inject
    protected VerifyService verifyService;

    @Override
    protected void doAction(EntityAction action, E entity, Dto dto, Object... args) {
        super.doAction(action, entity, dto, args);

        switch (action.getType()) {
        case LOAD:
            VerifyPolicyDto verifyPolicy = verifyService.getVerifyPolicy(entity);
            dto.setVerifyPolicy(verifyPolicy);
            break;

        case SAVE:
            // Do the verification and all.
            verifyService.verifyEntity(entity);
            // dto.setVerifyState(result.getState());
            // dto.setVerifyError(result.getMessage());
            break;
        }
    }

    @RequestMapping("verify.htm")
    public ModelAndView verify(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        return _verify(req, resp);
    }

    protected ModelAndView _verify(final HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        final String _id = req.getParameter("id");
        final IUserPrincipal currentUser = SessionLoginInfo.getCurrentUser(req.getSession());

        return new SuccessOrFailMessage("审核完成。") {

            @Override
            protected String eval()
                    throws ServletException {

                K id = parseRequiredId(_id);

                E entity = dataManager.get(getEntityType(), id);

                if (entity == null)
                    return "审核的目标对象不存在，这可能是因为有人在您审核的同时删除了该对象。";

                Set<Principal> responsibles = verifyService.getDeclaredResponsibles(entity);

                if (!currentUser.impliesOneOf(responsibles))
                    return "您不在责任人列表中，无权执行审核功能。";

                String error = doPreVerify(entity, TextMap.convert(req));
                if (error != null)
                    return "审核前置条件失败：" + error;

                verifyService.verifyEntity(entity);

                return null;
            }

        }.jsonDump(resp);
    }

    protected abstract String doPreVerify(E entity, TextMap request);

}
