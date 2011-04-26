package com.bee32.sem.process.verify.typedef;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.bee32.plover.orm.dao.CommonDataManager;
import com.bee32.plover.orm.ext.util.BasicEntityController;
import com.bee32.plover.orm.ext.util.DataTableDxo;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.sem.process.SEMProcessModule;
import com.bee32.sem.process.verify.VerifyPolicy;
import com.bee32.sem.process.verify.VerifyPolicyManager;
import com.bee32.sem.process.verify.builtin.web.VerifyPolicyDto;

@RequestMapping(VerifyPolicyPrefController.PREFIX + "*")
public class VerifyPolicyPrefController
        extends BasicEntityController<VerifyPolicyPref, String, VerifyPolicyPrefDto> {

    public static final String PREFIX = SEMProcessModule.PREFIX + "pref/";

    @Inject
    VerifyPolicyPrefDao prefDao;

    @Inject
    CommonDataManager dataManager;

    public VerifyPolicyPrefController() {
        _createOTF = true;
    }

    @Override
    protected List<? extends VerifyPolicyPref> __list() {
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
    protected void fillTemplate(VerifyPolicyPrefDto dto) {
        dto.setPreferredPolicy(new VerifyPolicyDto());
        dto.setDescription("");
    }

    @Override
    protected ModelAndView _createOrEditForm(ViewData view, HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        super._createOrEditForm(view, req, resp);

        Class<?> verifiableType = view.entity.getType();

        List<VerifyPolicyDto> candidates = new ArrayList<VerifyPolicyDto>();

        for (Class<? extends VerifyPolicy<?>> candidatePolicyType : VerifyPolicyManager.getCandidates(verifiableType)) {

            List<? extends VerifyPolicy<?>> candidatePolicies = dataManager.loadAll(candidatePolicyType);

            for (VerifyPolicyDto candidate : DTOs.marshalList(VerifyPolicyDto.class, candidatePolicies))
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

}
