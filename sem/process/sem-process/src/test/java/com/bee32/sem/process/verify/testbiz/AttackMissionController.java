package com.bee32.sem.process.verify.testbiz;

import java.util.Date;

import org.springframework.web.bind.annotation.RequestMapping;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.orm.web.util.DataTableDxo;
import com.bee32.plover.ox1.principal.User;
import com.bee32.sem.event.entity.Event;
import com.bee32.sem.process.SEMProcessModule;
import com.bee32.sem.process.verify.builtin.ISingleVerifier;
import com.bee32.sem.process.verify.builtin.SingleVerifierSupport;
import com.bee32.sem.process.verify.builtin.dto.SingleVerifierSupportDto;
import com.bee32.sem.process.verify.web.VerifiableEntityController;

@RequestMapping(AttackMissionController.PREFIX + "*")
public class AttackMissionController
        extends VerifiableEntityController<AttackMission, Integer, ISingleVerifier, AttackMissionDto> {

    public static final String PREFIX = SEMProcessModule.PREFIX_ + "attack/";

    @Override
    protected void fillDataRow(DataTableDxo tab, AttackMissionDto dto) {
        tab.push(dto.getTarget());

        SingleVerifierSupportDto sv = dto.getVerifyContext();
        tab.push(sv.getVerifier1().getDisplayName());
        tab.push(sv.getVerifiedDate1());
        tab.push(sv.getRejectedReason1());

        tab.push(sv.getVerifyState().getDisplayName());
        tab.push(sv.getVerifiedDate1());
    }

    @Override
    public String doPreVerify(AttackMission entity, User currentUser, TextMap request) {
        boolean allowed = request.getBoolean("allowed");
        String rejectedReason = request.getString("rejectedReason");

        SingleVerifierSupport sv = entity.getVerifyContext();
        sv.setVerifier1(currentUser);
        sv.setAccepted1(allowed);
        sv.setRejectedReason1(rejectedReason);
        sv.setVerifiedDate1(new Date());

        return null;
    }

    @Override
    public void doPostVerify(AttackMission entity, User currentUser, TextMap request) {
        Event event = entity.getVerifyContext().getVerifyEvent();

        String editLocation = AttackMissionController.PREFIX + "editForm?id=" + entity.getId();
        event.setSeeAlsos(editLocation);
    }

}
