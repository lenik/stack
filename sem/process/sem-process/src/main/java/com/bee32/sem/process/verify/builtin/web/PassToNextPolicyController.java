package com.bee32.sem.process.verify.builtin.web;

import org.springframework.web.bind.annotation.RequestMapping;

import com.bee32.plover.orm.web.basic.BasicEntityController;
import com.bee32.plover.orm.web.util.DataTableDxo;
import com.bee32.sem.process.SEMProcessModule;
import com.bee32.sem.process.verify.builtin.PassToNextPolicy;
import com.bee32.sem.process.verify.builtin.dto.PassStepDto;
import com.bee32.sem.process.verify.builtin.dto.PassToNextPolicyDto;

@RequestMapping(PassToNextPolicyController.PREFIX + "/*")
public class PassToNextPolicyController
        extends BasicEntityController<PassToNextPolicy, Integer, PassToNextPolicyDto> {

    public static final String PREFIX = SEMProcessModule.PREFIX + "/p2next";

    @Override
    protected void fillDataRow(DataTableDxo tab, PassToNextPolicyDto dto) {
        tab.push(dto.getLabel());
        tab.push(dto.getDescription());

        int max = 3;
        StringBuilder responsibles = null;
        for (PassStepDto seq : dto.getSequences()) {
            if (max <= 0) {
                responsibles.append(", etc.");
                break;
            }

            if (responsibles == null)
                responsibles = new StringBuilder();
            else
                responsibles.append(", ");

            responsibles.append(seq.getResponsible());

            max--;
        }
        tab.push(responsibles == null ? "" : responsibles.toString());
    }

}
