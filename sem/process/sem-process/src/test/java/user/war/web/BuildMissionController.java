package user.war.web;

import java.util.Date;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;

import user.war.WarModule;
import user.war.dto.BuildMissionDto;
import user.war.entity.BuildMission;

import com.bee32.icsf.login.SessionUser;
import com.bee32.icsf.principal.User;
import com.bee32.icsf.principal.UserDto;
import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.plover.orm.web.util.DataTableDxo;
import com.bee32.plover.servlet.mvc.ActionRequest;
import com.bee32.plover.servlet.mvc.ActionResult;
import com.bee32.sem.event.entity.Event;
import com.bee32.sem.process.verify.VerifyPolicy;
import com.bee32.sem.process.verify.builtin.ISingleVerifierWithNumber;
import com.bee32.sem.process.verify.builtin.SingleVerifierSupport;
import com.bee32.sem.process.verify.builtin.dto.SingleVerifierSupportDto;
import com.bee32.sem.process.verify.web.VerifiableEntityController;

@RequestMapping(BuildMissionController.PREFIX + "*")
public class BuildMissionController
        extends VerifiableEntityController<BuildMission, Integer, ISingleVerifierWithNumber, BuildMissionDto> {

    public static final String PREFIX = WarModule.PREFIX_ + "build/";

    @Override
    protected void fillDataRow(DataTableDxo tab, BuildMissionDto dto) {
        tab.push(dto.getTarget());
        tab.push(dto.getMoney());

        SingleVerifierSupportDto sv = dto.getVerifyContext();
        tab.push(sv.getVerifier1().getDisplayName());
        tab.push(sv.getVerifiedDate1());
        tab.push(sv.getRejectedReason1());

        tab.push(sv.getVerifyEvalState().getDisplayName());
        tab.push(sv.getVerifiedDate1());
    }

    @Override
    protected void fillFormExtra(ActionRequest req, ActionResult result) {
        List<UserDto> users = DTOs.marshalList(UserDto.class, 0, ctx.data.access(User.class).list());
        result.put("users", users);

        BuildMission entity = (BuildMission) result.getModelMap().get("entity");
        User user = SessionUser.getInstance().getInternalUser();
        boolean responsible = verifyService.isResponsible(user, entity);
        result.put("responsibleTest", responsible);

        VerifyPolicy policy = verifyService.getPreferredVerifyPolicy(BuildMission.class);
        if (policy != null) {
            Object stage = policy.getStage(entity.getVerifyContext());
            result.put("stage", stage);
        }
    }

    @Override
    public String doPreVerify(BuildMission entity, User currentUser, TextMap request) {
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
    public void doPostVerify(BuildMission entity, User currentUser, TextMap request) {
        Event event = entity.getVerifyContext().getVerifyEvent();

        String editLocation = BuildMissionController.PREFIX + "editForm?id=" + entity.getId();
        event.setSeeAlsos(editLocation);
    }

}
