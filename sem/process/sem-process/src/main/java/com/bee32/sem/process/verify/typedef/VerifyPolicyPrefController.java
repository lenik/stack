package com.bee32.sem.process.verify.typedef;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bee32.plover.arch.util.ClassUtil;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.plover.orm.web.EntityHandler;
import com.bee32.plover.orm.web.EntityHelper;
import com.bee32.plover.orm.web.basic.BasicEntityController;
import com.bee32.plover.orm.web.util.DataTableDxo;
import com.bee32.plover.servlet.mvc.ActionRequest;
import com.bee32.plover.servlet.mvc.ActionResult;
import com.bee32.sem.process.SEMProcessModule;
import com.bee32.sem.process.verify.IVerifyContext;
import com.bee32.sem.process.verify.VerifyPolicy;
import com.bee32.sem.process.verify.VerifyPolicyManager;
import com.bee32.sem.process.verify.builtin.dto.VerifyPolicyDto;
import com.bee32.sem.process.verify.service.VerifyService;
import com.bee32.sem.process.verify.util.VerifiableEntity;

@RequestMapping(VerifyPolicyPrefController.PREFIX + "/*")
public class VerifyPolicyPrefController
        extends BasicEntityController<VerifyPolicyPref, String, VerifyPolicyPrefDto> {

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

        // XXX _createOTF = true;
        addHandler("createForm", EntityHandler.NOT_APPLICABLE);
        addHandler("create", EntityHandler.NOT_APPLICABLE);
    }

// XXX @Override
    protected List<? extends VerifyPolicyPref> __list(HttpServletRequest req) {
        List<VerifyPolicyPref> prefs = new ArrayList<VerifyPolicyPref>();

        for (Class<?> verifiableType : VerifyPolicyManager.getVerifiableTypes()) {
            String typeId = VerifyPolicyPref.typeId(verifiableType);
            // prefDao.get(typeId);

            VerifyPolicyPref pref = asFor(VerifyPolicyPref.class).get(typeId);
            if (pref == null) {
                pref = new VerifyPolicyPref();
                pref.setType(verifiableType);
            }

            prefs.add(pref);
        }
        return prefs;
    }

    @Override
    protected void fillDataRow(DataTableDxo tab, VerifyPolicyPrefDto dto) {
        tab.push(dto.getDisplayName());
        tab.push(dto.getPreferredPolicy().getLabel());
        tab.push(dto.getDescription());
    }

    @Override
    protected void fillFormExtra(ActionRequest req, ActionResult result) {
        // TODO req.getAttribute(EntityHandler.ENTITY_HELPER_ATTRIBUTE);
        EntityHelper<?, ?> eh = null; // XXX req.getHandler().getEntityHelper();

        Class<?> verifiableType = null; // XXX result.entity.getType();

        List<VerifyPolicyDto> candidates = new ArrayList<VerifyPolicyDto>();

        for (Class<? extends VerifyPolicy> candidatePolicyType : VerifyPolicyManager.getCandidates(verifiableType)) {

            List<? extends VerifyPolicy> candidatePolicies = dataManager.asFor(candidatePolicyType).list();

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

        Class<? extends VerifiableEntity<? extends Number, IVerifyContext>> userEntityType;
        userEntityType = (Class<? extends VerifiableEntity<? extends Number, IVerifyContext>>) pref.getType();

        assert IVerifyContext.class.isAssignableFrom(userEntityType);

        VerifyPolicy preferredPolicy = pref.getPreferredPolicy();
        assert preferredPolicy != null;

        refresh(userEntityType);
    }

    /**
     * Update all dependencies.
     */
    <E extends VerifiableEntity<? extends Number, C>, C extends IVerifyContext> //
    void refresh(Class<? extends E> userEntityType) {

        String typeName = ClassUtil.getDisplayName(userEntityType);

        for (E userEntity : asFor(userEntityType).list()) {
            if (logger.isDebugEnabled())
                logger.debug("Refresh/verify " + typeName + " [" + userEntity.getId() + "]");

            verifyService.verifyEntity(userEntity);

            // should save userEntity?
        }
    }

}
