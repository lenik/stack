package com.bee32.sem.process.verify.builtin.web;

import java.util.ArrayList;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.RequestMapping;

import com.bee32.icsf.principal.dao.PrincipalDao;
import com.bee32.plover.orm.ext.util.BasicEntityController;
import com.bee32.plover.orm.ext.util.DataTableDxo;
import com.bee32.sem.process.SEMProcessModule;
import com.bee32.sem.process.verify.builtin.PassToNext;
import com.bee32.sem.process.verify.builtin.dao.PassStepDao;

@RequestMapping(PassToNextController.PREFIX + "*")
public class PassToNextController
        extends BasicEntityController<PassToNext, Integer, PassToNextDto> {

    public static final String PREFIX = SEMProcessModule.PREFIX + "p2next/";

    @Inject
    PassStepDao seqDao;

    @Inject
    PrincipalDao principalDao;

    public PassToNextController() {
        _dtoSelection = PassToNextDto.SEQUENCES;
    }

    @Override
    protected void fillDataRow(DataTableDxo tab, PassToNextDto dto) {
        tab.push(dto.getName());
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

    @Override
    protected void fillTemplate(PassToNextDto dto) {
        dto.setName("");
        dto.setDescription("");
        dto.setSequences(new ArrayList<PassStepDto>());
    }

}
