package com.bee32.sem.process.verify.testbiz;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.bee32.icsf.principal.dao.UserDao;
import com.bee32.icsf.principal.dto.UserDto;
import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.orm.ext.util.DataTableDxo;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.sem.process.SEMProcessModule;
import com.bee32.sem.process.verify.IAllowedByContext;
import com.bee32.sem.process.verify.util.VerifiableEntityController;

@RequestMapping(AttackMissionController.PREFIX + "*")
public class AttackMissionController
        extends VerifiableEntityController<AttackMission, Integer, IAllowedByContext, AttackMissionDto> {

    public static final String PREFIX = SEMProcessModule.PREFIX + "attack/";

    @Inject
    UserDao userDao;

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

    @Override
    protected String doPreVerify(AttackMission entity, TextMap request) {
        boolean allowed = request.getBoolean("allowed");
        String rejectedReason = request.getString("rejectedReason");

        entity.setAllowed(allowed);
        entity.setRejectedReason(rejectedReason);
        entity.setVerifiedDate(new Date());

        return null;
    }

}
