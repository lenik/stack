package com.bee32.sem.process.verify.testbiz;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.bee32.icsf.principal.dao.UserDao;
import com.bee32.icsf.principal.dto.UserDto;
import com.bee32.plover.orm.ext.util.BasicEntityController;
import com.bee32.plover.orm.ext.util.DataTableDxo;
import com.bee32.plover.orm.ext.util.EntityAction;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.sem.process.SEMProcessModule;
import com.bee32.sem.process.verify.builtin.dto.VerifyPolicyDto;
import com.bee32.sem.process.verify.service.VerifyService;

@RequestMapping(AttackMissionController.PREFIX + "*")
public class AttackMissionController
        extends BasicEntityController<AttackMission, Integer, AttackMissionDto> {

    public static final String PREFIX = SEMProcessModule.PREFIX + "attack/";

    @Inject
    VerifyService verifyService;

    @Inject
    UserDao userDao;

    @Override
    protected void doAction(EntityAction action, AttackMission entity, AttackMissionDto dto, Object... args) {
        super.doAction(action, entity, dto, args);

        switch (action.getType()) {
        case LOAD:
            VerifyPolicyDto verifyPolicy = verifyService.getVerifyPolicy(entity);
            dto.setVerifyPolicy(verifyPolicy);
            break;

        case SAVE:
            // Do the verification and all.
            verifyService.verifyEntity(entity);
            // dto.setVerifyState(result.getState());
            // dto.setVerifyError(result.getMessage());
            break;
        }
    }

    @Override
    protected ModelAndView _createOrEditForm(ViewData view, HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        super._createOrEditForm(view, req, resp);

        List<UserDto> users = DTOs.marshalList(UserDto.class, userDao.list());
        view.put("users", users);

        return view;
    }

    @Override
    protected void fillDataRow(DataTableDxo tab, AttackMissionDto dto) {
        tab.push(dto.getTarget());

        tab.push(dto.getVerifier().getDisplayName());
        tab.push(dto.getVerifiedDate());
        tab.push(dto.getRejectedReason());

        tab.push(dto.getVerifyState().getDisplayName());
        tab.push(dto.getVerifiedDate());
    }

}
