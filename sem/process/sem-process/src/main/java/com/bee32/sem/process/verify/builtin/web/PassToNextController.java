package com.bee32.sem.process.verify.builtin.web;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.RequestMapping;

import com.bee32.icsf.principal.Principal;
import com.bee32.icsf.principal.dao.PrincipalDao;
import com.bee32.plover.orm.ext.util.BasicEntityController;
import com.bee32.plover.orm.ext.util.DataTableDxo;
import com.bee32.sem.process.SEMProcessModule;
import com.bee32.sem.process.verify.builtin.PassStep;
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

    @Override
    protected void fillEntity(PassToNext entity, PassToNextDto dto) {
        entity.setName(dto.name);
        entity.setDescription(dto.description);

        List<PassStep> sequences = new ArrayList<PassStep>();
        for (PassStepDto seq : dto.getSequences()) {
            int order = seq.getOrder();
            long responsibleId = seq.getResponsible().getId();
            boolean optional = seq.isOptional();

            Principal responsible = principalDao.load(responsibleId);

            PassStep seqEntity = new PassStep(entity, order, responsible, optional);

            sequences.add(seqEntity);
        }
        entity.setSequences(sequences);
    }

}
