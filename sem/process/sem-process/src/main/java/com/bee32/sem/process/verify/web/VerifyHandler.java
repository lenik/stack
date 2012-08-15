package com.bee32.sem.process.verify.web;

import java.util.Set;

import javax.inject.Inject;
import javax.servlet.ServletException;

import com.bee32.icsf.login.SessionUser;
import com.bee32.icsf.principal.Principal;
import com.bee32.icsf.principal.User;
import com.bee32.plover.ajax.SuccessOrFailMessage;
import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.orm.util.EntityDto;
import com.bee32.plover.orm.web.EntityHandler;
import com.bee32.plover.servlet.mvc.ActionRequest;
import com.bee32.plover.servlet.mvc.ActionResult;
import com.bee32.sem.process.verify.IVerifiable;
import com.bee32.sem.process.verify.IVerifyContext;
import com.bee32.sem.process.verify.service.VerifyService;

public class VerifyHandler<E extends Entity<K> & IVerifiable<C>, //
/*        */K extends Number, C extends IVerifyContext, Dto extends EntityDto<E, K>>
        extends EntityHandler<E, K> {

    @Inject
    protected VerifyService verifyService;

    IVerifyHandlerHook<E> hook;

    public VerifyHandler(Class<E> entityType, IVerifyHandlerHook<E> hook) {
        super(entityType);

        this.hook = hook;
    }

    @Override
    public ActionResult _handleRequest(final ActionRequest req, ActionResult result)
            throws Exception {

        final String _id = req.getParameter("id");
        final User currentUser = SessionUser.getInstance().getInternalUser();

        SuccessOrFailMessage sof = new SuccessOrFailMessage("审核完成。") {

            @Override
            protected String eval()
                    throws ServletException {

                if (currentUser == null)
                    return "您尚未登陆。";

                K id = eh.parseRequiredId(_id);
                E entity = DATA(eh.getEntityType()).get(id);
                if (entity == null)
                    return "审核的目标对象不存在，这可能是因为有人在您审核的同时删除了该对象。";

                // C context = entity.getVerifyContext();
                Set<Principal> responsibles = verifyService.getResponsibles(entity);

                if (!currentUser.impliesOneOf(responsibles))
                    return "您不在责任人列表中，无权执行审核功能。";

                TextMap textMap = TextMap.convert(req);

                if (hook != null) {
                    String error = hook.doPreVerify(entity, currentUser, textMap);
                    if (error != null)
                        return "审核前置条件失败：" + error;
                }

                verifyService.verifyEntity(entity);

                if (hook != null)
                    hook.doPostVerify(entity, currentUser, textMap);

                return null;
            }

        };

        return sof.jsonDump(result);
    }

}
