package com.bee32.sem.process.verify.builtin.web;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;

import com.bee32.plover.orm.util.DTOs;
import com.bee32.plover.orm.web.basic.BasicEntityController;
import com.bee32.plover.orm.web.util.DataTableDxo;
import com.bee32.plover.servlet.mvc.ActionRequest;
import com.bee32.plover.servlet.mvc.ActionResult;
import com.bee32.sem.process.SEMProcessModule;
import com.bee32.sem.process.verify.VerifyPolicy;
import com.bee32.sem.process.verify.builtin.SingleVerifierRankedPolicy;
import com.bee32.sem.process.verify.builtin.dto.SingleVerifierLevelDto;
import com.bee32.sem.process.verify.builtin.dto.SingleVerifierRankedPolicyDto;
import com.bee32.sem.process.verify.dto.VerifyPolicyDto;

@RequestMapping(SingleVerifierRankedPolicyController.PREFIX + "/*")
public class SingleVerifierRankedPolicyController
        extends BasicEntityController<SingleVerifierRankedPolicy, Integer, SingleVerifierRankedPolicyDto> {

    public static final String PREFIX = SEMProcessModule.PREFIX + "/v1x";

    @Override
    protected void fillDataRow(DataTableDxo tab, SingleVerifierRankedPolicyDto multiLevel) {
        tab.push(multiLevel.getLabel());
        tab.push(multiLevel.getDescription());

        int max = 3;
        StringBuilder limits = null;
        for (SingleVerifierLevelDto range : multiLevel.getLevels()) {
            if (max <= 0) {
                limits.append(", etc.");
                break;
            }

            if (limits == null)
                limits = new StringBuilder();
            else
                limits.append(", ");

            limits.append(range.getLimit());

            max--;
        }
        tab.push(limits == null ? "" : limits.toString());
    }

    @Override
    protected void fillFormExtra(ActionRequest req, ActionResult result) {
        List<VerifyPolicy> _policies = ctx.data.access(VerifyPolicy.class).list();
        List<VerifyPolicyDto> policies = DTOs.marshalList(VerifyPolicyDto.class, _policies);
        result.put("policies", policies);
    }

}
