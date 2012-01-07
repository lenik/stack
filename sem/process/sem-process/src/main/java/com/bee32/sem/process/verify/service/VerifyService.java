package com.bee32.sem.process.verify.service;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.free.IllegalUsageException;
import javax.inject.Inject;

import org.springframework.transaction.annotation.Transactional;

import com.bee32.icsf.login.SessionUser;
import com.bee32.icsf.principal.Principal;
import com.bee32.plover.arch.DataService;
import com.bee32.plover.arch.util.ClassUtil;
import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.plover.restful.resource.IObjectPageDirectory;
import com.bee32.plover.restful.resource.PageDirectory;
import com.bee32.plover.restful.resource.StandardViews;
import com.bee32.plover.rtx.location.Location;
import com.bee32.sem.event.entity.Event;
import com.bee32.sem.event.entity.EventPriority;
import com.bee32.sem.event.entity.EventType;
import com.bee32.sem.process.verify.AbstractVerifyContext;
import com.bee32.sem.process.verify.IVerifiable;
import com.bee32.sem.process.verify.VerifyContextAccessor;
import com.bee32.sem.process.verify.VerifyEvalState;
import com.bee32.sem.process.verify.VerifyException;
import com.bee32.sem.process.verify.VerifyPolicy;
import com.bee32.sem.process.verify.VerifyPolicyDao;
import com.bee32.sem.process.verify.VerifyResult;
import com.bee32.sem.process.verify.dto.VerifyPolicyDto;

public class VerifyService
        extends DataService
        implements IVerifyService {

    @Inject
    VerifyPolicyDao policyDao;

    @Transactional(readOnly = true)
    @Override
    public VerifyPolicyDto getPreferredVerifyPolicy(Class<? extends IVerifiable<?>> entityClass) {
        VerifyPolicy preferredVerifyPolicy = policyDao.getPreferredVerifyPolicy(entityClass);
        VerifyPolicyDto policyDto = DTOs.marshal(VerifyPolicyDto.class, preferredVerifyPolicy);
        // return new VerifyPolicyDto(preferredVerifyPolicy);
        return policyDto;
    }

    @Transactional(readOnly = true)
    @Override
    public VerifyPolicyDto getVerifyPolicy(IVerifiable<?> entity) {
        VerifyPolicy verifyPolicy = policyDao.getVerifyPolicy(entity);
        VerifyPolicyDto policyDto = DTOs.marshal(VerifyPolicyDto.class, verifyPolicy);
        return policyDto;
    }

    // --o IVerifyPolicy.

    @Transactional(readOnly = true)
    @Override
    public Set<Principal> getResponsibles(IVerifiable<?> obj) {
        VerifyPolicy verifyPolicy = policyDao.getPreferredVerifyPolicy(obj.getClass());

        if (verifyPolicy == null)
            return new HashSet<Principal>();

        return verifyPolicy.getResponsibles(obj.getVerifyContext());
    }

    @Transactional(readOnly = true)
    @Override
    public boolean isVerified(IVerifiable<?> obj) {
        VerifyPolicy verifyPolicy = policyDao.getPreferredVerifyPolicy(obj.getClass());
        if (verifyPolicy == null)
            return false;
        return verifyPolicy.isVerified(obj.getVerifyContext());
    }

    @Transactional(readOnly = true)
    @Override
    public void assertVerified(IVerifiable<?> obj)
            throws VerifyException {
        VerifyPolicy verifyPolicy = policyDao.requirePreferredVerifyPolicy(obj.getClass());
        verifyPolicy.assertVerified(obj.getVerifyContext());
    }

    @Transactional(readOnly = true)
    @Override
    public VerifyResult verify(IVerifiable<?> obj) {
        if (obj == null)
            throw new NullPointerException("context");

        VerifyPolicy verifyPolicy = policyDao.getPreferredVerifyPolicy(obj.getClass());

        VerifyResult result;
        if (verifyPolicy == null)
            result = VerifyResult.n_a("不可用");
        else
            result = verifyPolicy.verify(obj.getVerifyContext());

        return result;
    }

    @Transactional
    public VerifyResult verifyEntity(final Entity<?> entity) {
        if (entity == null)
            throw new NullPointerException("entity");

        if (!(entity instanceof IVerifiable))
            throw new IllegalUsageException("Not a verifiable entity: " + entity.getClass());

        IVerifiable<?> verifiable = (IVerifiable<?>) entity;
        // Do verification.
        VerifyResult result = verify(verifiable);

        AbstractVerifyContext context = (AbstractVerifyContext) verifiable.getVerifyContext();
        VerifyContextAccessor.setVerifyState(context, result.getState());
        VerifyContextAccessor.setVerifyError(context, result.getMessage());
        VerifyContextAccessor.setVerifyEvalDate(context, new Date());

        Event event = context.getVerifyEvent();
        final VerifyEvalState state = result.getState();
        switch (state.getType()) {
        case VerifyEvalState.INIT:
            event = null;
            break;
        case VerifyEvalState.RUNNING:
            if (event == null)
                event = new Event(this, EventType.TASK);
            else
                event.setType(EventType.TASK);
            break;
        case VerifyEvalState.END:
            if (event == null)
                event = new Event(this, EventType.EVENT);
            else
                event.setType(EventType.EVENT);
            break;
        }
        context.setVerifyEvent(event);

        if (event != null) {
            event.setPriority(EventPriority.HIGH);
            event.setClosed(state.isFinalized());
            event.setState(state);

            event.setActor(SessionUser.getInstance().getInternalUserOpt());

            String entityName = ClassUtil.getParameterizedTypeName(entity) + " [" + entity.getId() + "]";

            String subject = "【作业跟踪】【审核】" + entityName;
            event.setSubject(subject);

            EventHtmlTemplate template = new EventHtmlTemplate(event) {
                @Override
                protected void _pageContent() {
                    IObjectPageDirectory pageDir = PageDirectory.getPageDirectory(entity);
                    String viewType = state.isFinalized() ? StandardViews.CONTENT : StandardViews.EDIT_FORM;

                    p().text("点击下面链接进入相关操作：");
                    ul();
                    {
                        Map<String, Object> viewParams = new HashMap<String, Object>();
                        viewParams.put("id", entity.getId());
                        List<Location> locations = pageDir.getPagesForView(viewType, viewParams);
                        for (Location location : locations) {
                            String href = location.resolveAbsolute(getRequest());
                            String hint = state.isFinalized() ? "查看" : "管理";
                            a().href(href).text(hint + ": " + href).end();
                        }
                    }
                    end(2);
                }
            };
            event.setMessage(template.make());

            event.setBeginTime(new Date()); //
            if (state.isFinalized())
                event.setEndTime(context.getVerifyEvalDate());
            else
                event.setEndTime(null);

            event.setRef(entity);

            Set<Principal> responsibles = new HashSet<Principal>(getResponsibles(verifiable));
            event.setObservers(responsibles);
        }

        return result;
    }

}
