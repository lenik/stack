package com.bee32.sem.process.verify.typedef;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.bee32.plover.arch.util.ClassUtil;
import com.bee32.plover.orm.ext.util.BasicEntityController;
import com.bee32.plover.orm.ext.util.DataTableDxo;
import com.bee32.plover.orm.ext.util.EntityAction;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.sem.process.SEMProcessModule;
import com.bee32.sem.process.verify.IVerifyContext;
import com.bee32.sem.process.verify.VerifyPolicy;
import com.bee32.sem.process.verify.VerifyPolicyManager;
import com.bee32.sem.process.verify.builtin.dto.AbstractVerifyPolicyDto;
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
        tab.push(dto.getPreferredPolicy() == null ? null : dto.getPreferredPolicy().getName());
        tab.push(dto.getDescription());
    }

    @Override
    protected ModelAndView _createOrEditForm(ViewData view, HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        super._createOrEditForm(view, req, resp);

        Class<?> verifiableType = view.entity.getType();

        List<AbstractVerifyPolicyDto> candidates = new ArrayList<AbstractVerifyPolicyDto>();

        for (Class<? extends VerifyPolicy<?>> candidatePolicyType : VerifyPolicyManager.getCandidates(verifiableType)) {

            List<? extends VerifyPolicy<?>> candidatePolicies = dataManager.loadAll(candidatePolicyType);

            for (AbstractVerifyPolicyDto candidate : DTOs.marshalList(AbstractVerifyPolicyDto.class, candidatePolicies))
                candidates.add(candidate);
        }

        view.put("candidates", candidates);

        return view;
    }

    // Not-Applicables

    @Override
    protected ModelAndView _createForm(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        return notApplicable(req, resp);
    }

    @Override
    protected ModelAndView _create(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        return notApplicable(req, resp);
    }

    // Action.

    @Override
    protected//
    void doAction(EntityAction action, VerifyPolicyPref entity, VerifyPolicyPrefDto dto, Object... args) {
        super.doAction(action, entity, dto, args);

        switch (action.getType()) {
        case SAVE:
            // Update all dependencies
            Class<? extends VerifiableEntity<? extends Number, IVerifyContext>> userEntityType;
            userEntityType = (Class<? extends VerifiableEntity<? extends Number, IVerifyContext>>) entity.getType();

            assert IVerifyContext.class.isAssignableFrom(userEntityType);

            VerifyPolicy<? extends IVerifyContext> preferredPolicy = entity.getPreferredPolicy();
            assert preferredPolicy != null;

            refresh(userEntityType);

            break;
        }
    }

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
