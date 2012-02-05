package com.bee32.sem.process.verify.web;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bee32.plover.arch.util.ClassUtil;
import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.plover.orm.util.EntityDto;
import com.bee32.plover.ox1.typePref.TypeInfo;
import com.bee32.sem.misc.SimpleEntityViewBean;
import com.bee32.sem.misc.UnmarshalMap;
import com.bee32.sem.process.verify.IVerifiable;
import com.bee32.sem.process.verify.IVerifyPolicy;
import com.bee32.sem.process.verify.VerifyPolicy;
import com.bee32.sem.process.verify.VerifyPolicyManager;
import com.bee32.sem.process.verify.dto.VerifyPolicyDto;
import com.bee32.sem.process.verify.preference.VerifyPolicyPref;
import com.bee32.sem.process.verify.preference.VerifyPolicyPrefDto;
import com.bee32.sem.process.verify.service.IVerifyService;

public class VerifyPolicyPrefBean
        extends SimpleEntityViewBean {

    private static final long serialVersionUID = 1L;

    static Logger logger = LoggerFactory.getLogger(VerifyPolicyPrefBean.class);

    public VerifyPolicyPrefBean() {
        super(VerifyPolicyPref.class, VerifyPolicyPrefDto.class, 0);
    }

    // editForm

    static List<TypeInfo> verifiableTypes;
    List<? extends VerifyPolicyDto> candidates;

    public static final int LAZY = 0;
    public static final int EAGER = 1;
    public static final int SKIP = 2;
    int mode = LAZY;

    public synchronized List<TypeInfo> getVerifiableTypes() {
        if (verifiableTypes == null)
            verifiableTypes = TypeInfo.getEntityTypes(IVerifiable.class);
        return verifiableTypes;
    }

    public void refreshCandidates() {
        candidates = null;
    }

    public List<? extends VerifyPolicyDto> getCandidates() {
        if (candidates == null) {
            VerifyPolicyPrefDto pref = getOpenedObject();
            if (pref == null)
                return Collections.emptyList();
            Class<?> verifiableType = pref.getType();
            candidates = getCandidates(verifiableType);
        }
        return candidates;
    }

    protected List<? extends VerifyPolicyDto> getCandidates(Class<?> verifiableType) {
        if (verifiableType == null)
            return Collections.emptyList();
        // assert IVerifiable.class.isAssignableFrom(verifiableType);

        List<VerifyPolicyDto> candidates = new ArrayList<VerifyPolicyDto>();
        for (Class<? extends IVerifyPolicy> candidatePolicyType : VerifyPolicyManager.getCandidates(verifiableType)) {
            // Include only entities in the candidate list
            if (!VerifyPolicy.class.isAssignableFrom(candidatePolicyType))
                continue;

            @SuppressWarnings("unchecked")
            Class<? extends VerifyPolicy> persistedType = (Class<? extends VerifyPolicy>) candidatePolicyType;

            List<? extends VerifyPolicy> candidatePolicies = asFor(persistedType).list();

            for (VerifyPolicyDto candidate : DTOs.mrefList(VerifyPolicyDto.class, candidatePolicies))
                candidates.add(candidate);
        }
        return candidates;
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    // Save/Apply

    @Override
    protected boolean preUpdate(UnmarshalMap uMap)
            throws Exception {
        if (mode == SKIP)
            return true;
        for (Entry<Entity<?>, EntityDto<?, ?>> entry : uMap.entrySet()) {
            Entity<?> entity = entry.getKey();
            if (!(entity instanceof VerifyPolicyPref))
                continue;

            VerifyPolicyPref pref = (VerifyPolicyPref) entity;
            VerifyPolicyPrefDto prefDto = (VerifyPolicyPrefDto) entry.getValue();

            if (!prefDto.isPreferredPolicyChanged())
                if (mode != EAGER)
                    continue;

            Class<? extends Entity<?>> userEntityType;
            userEntityType = (Class<? extends Entity<?>>) pref.getType();

            assert IVerifiable.class.isAssignableFrom(userEntityType);

            VerifyPolicy preferredPolicy = pref.getPreferredPolicy();
            assert preferredPolicy != null;

            reverifyEntities(userEntityType);
        }
        return true;
    }

    /**
     * Update all dependencies.
     */
    void reverifyEntities(Class<? extends Entity<?>> userEntityType) {
        // Refresh on non-verifiable entities do nothing.
        if (!IVerifiable.class.isAssignableFrom(userEntityType))
            // logger.warn?
            return;

        String typeName = ClassUtil.getTypeName(userEntityType);

        for (Entity<?> userEntity : asFor(userEntityType).list()) {
            if (logger.isDebugEnabled())
                logger.debug("Refresh/verify " + typeName + " [" + userEntity.getId() + "]");

            IVerifyService verifyService = getBean(IVerifyService.class);
            verifyService.verifyEntity(userEntity);
            // should save userEntity?
        }
    }

}
