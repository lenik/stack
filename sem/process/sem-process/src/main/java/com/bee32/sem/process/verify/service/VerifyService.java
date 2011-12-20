package com.bee32.sem.process.verify.service;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.free.IllegalUsageException;
import javax.inject.Inject;

import org.springframework.transaction.annotation.Transactional;

import com.bee32.icsf.login.SessionUser;
import com.bee32.plover.arch.DataService;
import com.bee32.plover.arch.util.ClassUtil;
import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.plover.ox1.principal.Principal;
import com.bee32.plover.ox1.principal.User;
import com.bee32.sem.event.entity.EventPriority;
import com.bee32.sem.event.entity.Task;
import com.bee32.sem.process.verify.AbstractVerifyContext;
import com.bee32.sem.process.verify.IVerifiable;
import com.bee32.sem.process.verify.IVerifyContext;
import com.bee32.sem.process.verify.VerifyContextAccessor;
import com.bee32.sem.process.verify.VerifyException;
import com.bee32.sem.process.verify.VerifyPolicy;
import com.bee32.sem.process.verify.VerifyPolicyDao;
import com.bee32.sem.process.verify.VerifyResult;
import com.bee32.sem.process.verify.VerifyState;
import com.bee32.sem.process.verify.dto.VerifyPolicyDto;

public class VerifyService
        extends DataService
        implements IVerifyService {

    @Inject
    VerifyPolicyDao policyDao;

    @Transactional(readOnly = true)
    @Override
    public VerifyPolicyDto getPreferredVerifyPolicy(Class<? extends IVerifyContext> entityClass) {
        VerifyPolicy preferredVerifyPolicy = policyDao.getPreferredVerifyPolicy(entityClass);
        VerifyPolicyDto policyDto = DTOs.marshal(VerifyPolicyDto.class, preferredVerifyPolicy);
        // return new VerifyPolicyDto(preferredVerifyPolicy);
        return policyDto;
    }

    @Transactional(readOnly = true)
    @Override
    public <E extends Entity<?> & IVerifiable<?>> VerifyPolicyDto getVerifyPolicy(E entity) {
        VerifyPolicy verifyPolicy = policyDao.getVerifyPolicy(entity);
        VerifyPolicyDto policyDto = DTOs.marshal(VerifyPolicyDto.class, verifyPolicy);
        return policyDto;
    }

    // --o IVerifyPolicy.

    @Transactional(readOnly = true)
    @Override
    public Set<Principal> getDeclaredResponsibles(IVerifyContext context) {
        VerifyPolicy verifyPolicy = policyDao.getPreferredVerifyPolicy(context.getClass());

        if (verifyPolicy == null)
            return new HashSet<Principal>();

        return verifyPolicy.getDeclaredResponsibles(context);
    }

    @Transactional(readOnly = true)
    @Override
    public boolean isVerified(IVerifyContext context) {
        VerifyPolicy verifyPolicy = policyDao.getPreferredVerifyPolicy(context.getClass());
        if (verifyPolicy == null)
            return false;
        return verifyPolicy.isVerified(context);
    }

    @Transactional(readOnly = true)
    @Override
    public void assertVerified(IVerifyContext context)
            throws VerifyException {
        VerifyPolicy verifyPolicy = policyDao.requirePreferredVerifyPolicy(context.getClass());
        verifyPolicy.assertVerified(context);
    }

    @Transactional(readOnly = true)
    @Override
    public VerifyResult verify(IVerifyContext context) {
        if (context == null)
            throw new NullPointerException("context");

        VerifyPolicy verifyPolicy = policyDao.getPreferredVerifyPolicy(context.getClass());

        VerifyResult result;
        if (verifyPolicy == null)
            result = VerifyResult.n_a("不可用");
        else
            result = verifyPolicy.verify(context);

        return result;
    }

    @Transactional
    public VerifyResult verifyEntity(Entity<?> entity) {
        if (entity == null)
            throw new NullPointerException("entity");

        if (!(entity instanceof IVerifiable))
            throw new IllegalUsageException("Not a verifiable entity: " + entity.getClass());

        IVerifiable<?> verifiable = (IVerifiable<?>) entity;

        User currentUser = SessionUser.getInstance().getInternalUser();

        IVerifyContext _context = verifiable.getVerifyContext();

        // Do verification.
        VerifyResult result = verify(_context);

        AbstractVerifyContext context = (AbstractVerifyContext) _context;
        VerifyContextAccessor.setVerifyState(context, result.getState());
        VerifyContextAccessor.setVerifyError(context, result.getMessage());
        VerifyContextAccessor.setVerifyEvalDate(context, new Date());

        Task verifyTask = context.getVerifyTask();
        {
            if (verifyTask == null) {
                verifyTask = new Task();
                context.setVerifyTask(verifyTask);
            }

            verifyTask.setSourceClass(VerifyPolicy.class);
            verifyTask.setPriority(EventPriority.HIGH);

            VerifyState state = result.getState();
            verifyTask.setClosed(state.isClosed());
            verifyTask.setState(state);

            verifyTask.setActor(null); // session current user.

            String entityName = ClassUtil.getDisplayName(entity.getClass()) + " [" + entity.getId() + "]";

            String subject = "【作业跟踪】【审核】" + entityName;
            String message = "（无可用内容）";

            verifyTask.setSubject(subject);
            verifyTask.setMessage(message);
            verifyTask.setBeginTime(new Date()); //

            if (state.isClosed())
                verifyTask.setEndTime(context.getVerifyEvalDate());
            else
                verifyTask.setEndTime(null);

            verifyTask.setRef(entity);

            Set<Principal> responsibles = new HashSet<Principal>(getDeclaredResponsibles(context));
            verifyTask.setObservers(responsibles);
        }

        return result;
    }

}
