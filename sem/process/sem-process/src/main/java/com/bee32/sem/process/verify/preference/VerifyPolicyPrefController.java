package com.bee32.sem.process.verify.preference;

import java.util.ArrayList;
import java.util.List;

import javax.free.ParseException;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bee32.plover.arch.util.ClassUtil;
import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.plover.orm.util.ITypeAbbrAware;
import com.bee32.plover.orm.web.EntityHandler;
import com.bee32.plover.orm.web.IEntityListing;
import com.bee32.plover.orm.web.basic.BasicEntityController;
import com.bee32.plover.orm.web.basic.DataHandler;
import com.bee32.plover.orm.web.util.DataTableDxo;
import com.bee32.plover.servlet.mvc.ActionRequest;
import com.bee32.plover.servlet.mvc.ActionResult;
import com.bee32.sem.process.SEMProcessModule;
import com.bee32.sem.process.verify.IVerifiable;
import com.bee32.sem.process.verify.IVerifyPolicy;
import com.bee32.sem.process.verify.VerifyPolicy;
import com.bee32.sem.process.verify.VerifyPolicyManager;
import com.bee32.sem.process.verify.dto.VerifyPolicyDto;
import com.bee32.sem.process.verify.service.VerifyService;

@RequestMapping(VerifyPolicyPrefController.PREFIX + "/*")
public class VerifyPolicyPrefController
        // > TypePrefController ?
        extends BasicEntityController<VerifyPolicyPref, String, VerifyPolicyPrefDto>
        implements ITypeAbbrAware {

    public static final String PREFIX = SEMProcessModule.PREFIX + "/pref";

    static Logger logger = LoggerFactory.getLogger(VerifyPolicyPrefController.class);

    @Inject
    VerifyPolicyPrefDao prefDao;

    @Inject
    VerifyService verifyService;

    @Override
    protected void initController()
            throws BeansException {
        super.initController();

        // _createOTF = true;
        addHandler("createForm", EntityHandler.NOT_APPLICABLE);
        addHandler("create", EntityHandler.NOT_APPLICABLE);
        addHandler("data", new PrefDataHandler(VerifyPolicyPref.class, impl));
    }

    static class PrefDataHandler
            extends DataHandler<VerifyPolicyPref, String> {

        public PrefDataHandler(Class<VerifyPolicyPref> entityType, IEntityListing<VerifyPolicyPref, String> listing) {
            super(entityType, listing);
        }

        @Override
        protected List<? extends VerifyPolicyPref> __list(HttpServletRequest req)
                throws ParseException, ServletException {
            List<VerifyPolicyPref> prefs = new ArrayList<VerifyPolicyPref>();

            for (Class<?> verifiableType : VerifyPolicyManager.getVerifiableTypes()) {
                String typeId = ABBR.abbr(verifiableType);
                // prefDao.get(typeId);

                VerifyPolicyPref pref = DATA(VerifyPolicyPref.class).get(typeId);
                if (pref == null) {
                    pref = new VerifyPolicyPref();
                    pref.setType(verifiableType);
                }

                prefs.add(pref);
            }
            return prefs;
        }

    }

    @Override
    protected void fillDataRow(DataTableDxo tab, VerifyPolicyPrefDto dto) {
        tab.push(dto.getDisplayName());
        tab.push(dto.getPreferredPolicy().getLabel());
        tab.push(dto.getDescription());
    }

    @Override
    protected void fillFormExtra(ActionRequest req, ActionResult result) {
        String typeId = req.getParameter("id");
        Class<?> verifiableType;
        try {
            verifiableType = ABBR.expand(typeId);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
        assert IVerifiable.class.isAssignableFrom(verifiableType);

        List<VerifyPolicyDto> candidates = new ArrayList<VerifyPolicyDto>();
        for (Class<? extends IVerifyPolicy> candidatePolicyType : VerifyPolicyManager.getCandidates(verifiableType)) {
            // Include only entities in the candidate list
            if (!VerifyPolicy.class.isAssignableFrom(candidatePolicyType))
                continue;

            @SuppressWarnings("unchecked")
            Class<? extends VerifyPolicy> persistedType = (Class<? extends VerifyPolicy>) candidatePolicyType;

            List<? extends VerifyPolicy> candidatePolicies = DATA(persistedType).list();

            for (VerifyPolicyDto candidate : DTOs.marshalList(VerifyPolicyDto.class, candidatePolicies))
                candidates.add(candidate);
        }

        result.put("candidates", candidates);
    }

    /**
     * Update all dependencies.
     *
     * This actually does:
     *
     * <pre>
     * refresh(pref.getType())
     * </pre>
     */
    @Override
    protected void saveForm(VerifyPolicyPref pref, VerifyPolicyPrefDto prefDto) {
        super.saveForm(pref, prefDto);

        Class<? extends Entity<?>> userEntityType;
        userEntityType = (Class<? extends Entity<?>>) pref.getType();

        assert IVerifiable.class.isAssignableFrom(userEntityType);

        VerifyPolicy preferredPolicy = pref.getPreferredPolicy();
        assert preferredPolicy != null;

        refresh(userEntityType);
    }

    /**
     * Update all dependencies.
     */
    void refresh(Class<? extends Entity<?>> userEntityType) {
        // Refresh on non-verifiable entities do nothing.
        if (!IVerifiable.class.isAssignableFrom(userEntityType))
            // logger.warn?
            return;

        String typeName = ClassUtil.getTypeName(userEntityType);

        for (Entity<?> userEntity : DATA(userEntityType).list()) {
            if (logger.isDebugEnabled())
                logger.debug("Refresh/verify " + typeName + " [" + userEntity.getId() + "]");

            verifyService.verifyEntity(userEntity);
            // should save userEntity?
        }
    }

}
