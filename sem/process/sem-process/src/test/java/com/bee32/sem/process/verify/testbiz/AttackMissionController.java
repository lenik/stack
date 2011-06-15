package com.bee32.sem.process.verify.testbiz;

import java.util.Date;

import org.springframework.web.bind.annotation.RequestMapping;

import com.bee32.icsf.principal.User;
import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.orm.web.util.DataTableDxo;
import com.bee32.sem.event.entity.Task;
import com.bee32.sem.process.SEMProcessModule;
import com.bee32.sem.process.verify.IAllowedByContext;
import com.bee32.sem.process.verify.util.VerifiableEntityController;

@RequestMapping(AttackMissionController.PREFIX + "*")
public class AttackMissionController
        extends VerifiableEntityController<AttackMission, Integer, IAllowedByContext, AttackMissionDto> {

    public static final String PREFIX = SEMProcessModule.PREFIX + "attack/";

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
    public String doPreVerify(AttackMission entity, User currentUser, TextMap request) {
        boolean allowed = request.getBoolean("allowed");
        String rejectedReason = request.getString("rejectedReason");

        entity.setVerifier(currentUser);
        entity.setAllowed(allowed);
        entity.setRejectedReason(rejectedReason);
        entity.setVerifiedDate(new Date());

        return null;
    }

    @Override
    public void doPostVerify(AttackMission entity, User currentUser, TextMap request) {
        Task task = entity.getVerifyTask();

        String editLocation = AttackMissionController.PREFIX + "editForm?id=" + entity.getId();
        task.setSeeAlsos(editLocation);
    }

}
