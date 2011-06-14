package com.bee32.sem.process.verify.typedef;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bee32.plover.arch.util.ClassUtil;
import com.bee32.plover.orm.ext.basic.EntityHelper;
import com.bee32.plover.orm.ext.basic.NotApplicableHandler;
import com.bee32.plover.orm.ext.util.BasicEntityController;
import com.bee32.plover.orm.ext.util.DataTableDxo;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.plover.servlet.mvc.ActionRequest;
import com.bee32.plover.servlet.mvc.ActionResult;
import com.bee32.sem.process.SEMProcessModule;
import com.bee32.sem.process.verify.IVerifyContext;
import com.bee32.sem.process.verify.VerifyPolicy;
import com.bee32.sem.process.verify.VerifyPolicyManager;
import com.bee32.sem.process.verify.builtin.dto.VerifyPolicyDto;
import com.bee32.sem.process.verify.service.VerifyService;
import com.bee32.sem.process.verify.util.VerifiableEntity;

@RequestMapping(VerifyPolicyPrefController.PREFIX + "*")
public class VerifyPolicyPrefController
        extends BasicEntityController<VerifyPolicyPref, String, VerifyPolicyPrefDto> {

    public static final String PREFIX = SEMProcessModule.PREFIX + "pref/";

    static Logger logger = LoggerFactory.getLogger(VerifyPolicyPrefController.class);

    @Inject
    VerifyPolicyPrefDao prefDao;

    @Inject
    VerifyService verifyService;

    public VerifyPolicyPrefController() {
        _createOTF = true;
        addHandler("createForm", NotApplicableHandler.INSTANCE);
        addHandler("create", NotApplicableHandler.INSTANCE);
    }

    @Override
    protected List<? extends VerifyPolicyPref> __list(HttpServletRequest req) {
        List<VerifyPolicyPref> prefs = new ArrayList<VerifyPolicyPref>();

        for (Class<?> verifiableType : VerifyPolicyManager.getVerifiableTypes()) {
            String typeId = VerifyPolicyPref.typeId(verifiableType);
            // prefDao.get(typeId);

            VerifyPolicyPref pref = dataManager.get(VerifyPolicyPref.class, typeId);
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
        EntityHelper<?, ?> eh = req.getHandler().getEntityHelper();

        Class<?> verifiableType = result.entity.getType();

        List<VerifyPolicyDto> candidates = new ArrayList<VerifyPolicyDto>();

        for (Class<? extends VerifyPolicy<?>> candidatePolicyType : VerifyPolicyManager.getCandidates(verifiableType)) {

            List<? extends VerifyPolicy<?>> candidatePolicies = dataManager.loadAll(candidatePolicyType);

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

        VerifyPolicy<? extends IVerifyContext> preferredPolicy = pref.getPreferredPolicy();
        assert preferredPolicy != null;

        refresh(userEntityType);
    }

    /**
     * Update all dependencies.
     */
    <E extends VerifiableEntity<? extends Number, C>, C extends IVerifyContext> //
    void refresh(Class<? extends E> userEntityType) {

        String typeName = ClassUtil.getDisplayName(userEntityType);

        for (E userEntity : dataManager.loadAll(userEntityType)) {
            if (logger.isDebugEnabled())
                logger.debug("Refresh/verify " + typeName + " [" + userEntity.getId() + "]");

            verifyService.verifyEntity(userEntity);

            // should save userEntity?
        }
    }

}
