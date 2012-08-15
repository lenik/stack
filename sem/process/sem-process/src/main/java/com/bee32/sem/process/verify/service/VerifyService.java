package com.bee32.sem.process.verify.service;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.free.IllegalUsageException;
import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import com.bee32.icsf.login.SessionUser;
import com.bee32.icsf.principal.Principal;
import com.bee32.icsf.principal.User;
import com.bee32.plover.arch.DataService;
import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.plover.ox1.c.CEntity;
import com.bee32.plover.restful.resource.IObjectPageDirectory;
import com.bee32.plover.restful.resource.PageDirectory;
import com.bee32.plover.restful.resource.StandardViews;
import com.bee32.plover.rtx.location.Location;
import com.bee32.sem.event.entity.Event;
import com.bee32.sem.event.entity.EventPriorities;
import com.bee32.sem.event.entity.EventType;
import com.bee32.sem.process.verify.AbstractVerifyContext;
import com.bee32.sem.process.verify.DummyVerifyProcessHandler;
import com.bee32.sem.process.verify.IVerifiable;
import com.bee32.sem.process.verify.IVerifyPolicy;
import com.bee32.sem.process.verify.IVerifyProcessAware;
import com.bee32.sem.process.verify.IVerifyProcessHandler;
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
    @Inject
    EventPriorities eventPriorities;

    @Transactional(readOnly = true)
    @Override
    public VerifyPolicy getPreferredVerifyPolicy(Class<? extends IVerifiable<?>> entityClass) {
        VerifyPolicy preferredVerifyPolicy = policyDao.getPreferredVerifyPolicy(entityClass);
        return preferredVerifyPolicy;
    }

    @Transactional(readOnly = true)
    @Override
    public VerifyPolicyDto getPreferredVerifyPolicyDto(Class<? extends IVerifiable<?>> entityClass) {
        VerifyPolicy preferredVerifyPolicy = getPreferredVerifyPolicy(entityClass);
        VerifyPolicyDto policyDto = DTOs.marshal(VerifyPolicyDto.class, preferredVerifyPolicy);
        // return new VerifyPolicyDto(preferredVerifyPolicy);
        return policyDto;
    }

    @Transactional(readOnly = true)
    @Override
    public VerifyPolicy getVerifyPolicy(IVerifiable<?> entity) {
        VerifyPolicy verifyPolicy = policyDao.getVerifyPolicy(entity);
        return verifyPolicy;
    }

    @Transactional(readOnly = true)
    @Override
    public VerifyPolicyDto getVerifyPolicyDto(IVerifiable<?> entity) {
        VerifyPolicy verifyPolicy = getVerifyPolicy(entity);
        VerifyPolicyDto policyDto = DTOs.marshal(VerifyPolicyDto.class, verifyPolicy);
        return policyDto;
    }

    // --o IVerifyPolicy.

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

        Class<? extends Entity<?>> entityType = (Class<? extends Entity<?>>) entity.getClass();
        IVerifiable<?> verifiable = (IVerifiable<?>) entity;
        AbstractVerifyContext context = (AbstractVerifyContext) verifiable.getVerifyContext();

        IVerifyProcessHandler handler = null;
        if (verifiable instanceof IVerifyProcessAware)
            handler = ((IVerifyProcessAware) verifiable).getVerifyProcessHandler();
        if (handler == null)
            handler = DummyVerifyProcessHandler.INSTANCE;

        VerifyResult result = handler.getPreVerifiedResult();
        if (result != null)
            return result;
        if (VerifyEvalState.VERIFIED.equals(context.getVerifyEvalState()))
            return IVerifyPolicy.VERIFIED;

        handler.preVerify();
        result = verify(verifiable);
        handler.handleVerifyResult(result);

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
            User op = SessionUser.getInstance().getInternalUserOpt();

            event.setPriority(eventPriorities.HIGH);
            event.setClosed(state.isFinalized());
            event.setState(state);
            event.setActor(op);

            StringBuilder subjectBuf = new StringBuilder();
            subjectBuf.append("【作业跟踪】");
            subjectBuf.append("【" + state.getDisplayName() + "】");
            subjectBuf.append(entity.getEntryLabel());
            if (!StringUtils.isEmpty(context.getVerifyError()))
                subjectBuf.append(": " + context.getVerifyError());
            final String subject = subjectBuf.toString();
            event.setSubject(subject);

            EventHtmlTemplate template = new EventHtmlTemplate(event) {
                @Override
                protected void _pageContent() {
                    IObjectPageDirectory pageDir = PageDirectory.getPageDirectory(entity);
                    if (pageDir == null) {
                        throw new IllegalUsageException("No page for entity type: " + entity.getClass());
                    }

                    String viewType = state.isFinalized() ? StandardViews.CONTENT : StandardViews.EDIT_FORM;

                    // h3().text(subject).end();
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

            Set<Principal> responsibles = new HashSet<Principal>();
            if (state.isFinalized()) {
                responsibles.add(op);
                if (entity instanceof CEntity<?>) {
                    CEntity<?> centity = (CEntity<?>) entity;
                    User owner = centity.getOwner();
                    if (owner != null)
                        responsibles.add(owner);
                }
            } else
                responsibles.addAll(getResponsibles(verifiable));
            event.setObservers(responsibles);

            if (state.isFinalized()) {
                // TODO - send mail to owner?
            }
        }

        handler.preUpdate();
        DATA(entityType).update(entity);
        handler.postUpdate();

        return result;
    }

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
    public boolean isResponsible(Principal principal, IVerifiable<?> obj) {
        VerifyPolicy verifyPolicy = policyDao.getPreferredVerifyPolicy(obj.getClass());
        if (verifyPolicy == null)
            return false;
        Object stage = verifyPolicy.getStage(obj.getVerifyContext());
        return verifyPolicy.isResponsible(principal, stage);
    }

}
