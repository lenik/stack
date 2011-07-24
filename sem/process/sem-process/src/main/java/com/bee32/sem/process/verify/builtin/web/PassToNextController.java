package com.bee32.sem.process.verify.builtin.web;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.RequestMapping;

import com.bee32.plover.orm.web.basic.BasicEntityController;
import com.bee32.plover.orm.web.util.DataTableDxo;
import com.bee32.sem.process.SEMProcessModule;
import com.bee32.sem.process.verify.builtin.PassToNextPolicy;
import com.bee32.sem.process.verify.builtin.dao.PassStepDao;
import com.bee32.sem.process.verify.builtin.dto.PassStepDto;
import com.bee32.sem.process.verify.builtin.dto.PassToNextDto;

@RequestMapping(PassToNextController.PREFIX + "*")
public class PassToNextController
        extends BasicEntityController<PassToNextPolicy, Integer, PassToNextDto> {

    public static final String PREFIX = SEMProcessModule.PREFIX + "p2next/";

    @Inject
    PassStepDao seqDao;

    @Override
    protected void fillDataRow(DataTableDxo tab, PassToNextDto dto) {
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
