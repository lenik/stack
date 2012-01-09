package com.bee32.sem.process.verify.web;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.bee32.plover.orm.util.DTOs;
import com.bee32.plover.ox1.typePref.TypeInfo;
import com.bee32.sem.misc.SimpleEntityViewBean;
import com.bee32.sem.process.verify.IVerifiable;
import com.bee32.sem.process.verify.IVerifyPolicy;
import com.bee32.sem.process.verify.VerifyPolicy;
import com.bee32.sem.process.verify.VerifyPolicyManager;
import com.bee32.sem.process.verify.dto.VerifyPolicyDto;
import com.bee32.sem.process.verify.preference.VerifyPolicyPref;
import com.bee32.sem.process.verify.preference.VerifyPolicyPrefDto;

public class VerifyPolicyPrefBean
        extends SimpleEntityViewBean {

    private static final long serialVersionUID = 1L;

    private List<? extends VerifyPolicyDto> candidates;

    public VerifyPolicyPrefBean() {
        super(VerifyPolicyPref.class, VerifyPolicyPrefDto.class, 0);
    }

    public void refreshCandidates() {
        candidates = null;
    }

    public List<? extends VerifyPolicyDto> getCandidates() {
        if (candidates == null) {
            VerifyPolicyPrefDto pref = getActiveObject();
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

    static List<TypeInfo> verifiableTypes;

    public synchronized List<TypeInfo> getVerifiableTypes() {
        if (verifiableTypes == null)
            verifiableTypes = TypeInfo.getEntityTypes(IVerifiable.class);
        return verifiableTypes;
    }

}
