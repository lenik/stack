package com.bee32.sem.process.verify.testbiz;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.RequestMapping;

import com.bee32.icsf.principal.dto.UserDto;
import com.bee32.plover.orm.ext.util.BasicEntityController;
import com.bee32.plover.orm.ext.util.DataTableDxo;
import com.bee32.sem.process.SEMProcessModule;
import com.bee32.sem.process.verify.service.VerifyPolicyService;

@RequestMapping(AttackMissionController.PREFIX + "*")
public class AttackMissionController
        extends BasicEntityController<AttackMission, Integer, AttackMissionDto> {

    public static final String PREFIX = SEMProcessModule.PREFIX + "attack/";

    @Inject
    VerifyPolicyService verifyPolicyService;

    @Override
    protected void fillDataRow(DataTableDxo tab, AttackMissionDto dto) {
        tab.push(dto.getTarget());
        tab.push(dto.getVerificationState());
        tab.push(dto.getVerifier().getDisplayName());
        tab.push(dto.getRejectReason());
        tab.push(dto.getVerifiedDate());
    }

    @Override
    protected void fillTemplate(AttackMissionDto dto) {
        dto.setTarget("");
        dto.setVerifier(new UserDto());
    }

}
