package com.bee32.sem.makebiz.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.free.ParseException;

import org.apache.commons.lang.NotImplementedException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.ox1.color.MomentIntervalDto;
import com.bee32.sem.make.dto.MakeStepModelDto;
import com.bee32.sem.make.dto.PartDto;
import com.bee32.sem.make.dto.QCResultDto;
import com.bee32.sem.makebiz.entity.MakeStep;
import com.bee32.sem.people.dto.OrgUnitDto;
import com.bee32.sem.people.dto.PersonDto;

public class MakeStepDto
    extends MomentIntervalDto<MakeStep> {

    private static final long serialVersionUID = 1L;

    public static final int OPERATORS = 1;

    MakeProcessDto parent;

    // Behavior as <model.part, model.processOrder>.
    MakeStepModelDto model;
    PartDto part; // @Redundant for grouping.

    BigDecimal planQuantity;
    BigDecimal actualQuantity;

    Date planDeadline;
    Date actualDeadline;

    OrgUnitDto orgUnit;
    List<PersonDto> operators;
    QCResultDto qcResult;

    @Override
    protected void _marshal(MakeStep source) {
        parent = mref(MakeProcessDto.class, source.getParent());

        model = mref(MakeStepModelDto.class, source.getModel());
        part = mref(PartDto.class, source.getPart());

        planQuantity = source.getPlanQuantity();
        actualQuantity = source.getActualQuantity();

        planDeadline = source.getPlanDeadline();
        actualDeadline = source.getActualDeadline();

        orgUnit = mref(OrgUnitDto.class, source.getOrgUnit());
        if (selection.contains(OPERATORS))
            operators = mrefList(PersonDto.class, source.getOperators());
        qcResult = marshal(QCResultDto.class, source.getQcResult());

    }

    @Override
    protected void _unmarshalTo(MakeStep target) {
        merge(target, "parent", parent);

        merge(target, "model", model);
        merge(target, "part", part);

        target.setPlanQuantity(planQuantity);
        target.setActualQuantity(actualQuantity);

        target.setPlanDeadline(planDeadline);
        target.setActualDeadline(actualDeadline);

        merge(target, "orgUnit", orgUnit);
        if (selection.contains(OPERATORS))
            mergeList(target, "operators", operators);
        merge(target, "qcResult", qcResult);

    }

    @Override
    protected void _parse(TextMap map) throws ParseException {
        throw new NotImplementedException();

    }

    public MakeProcessDto getParent() {
        return parent;
    }

    public void setParent(MakeProcessDto parent) {
        this.parent = parent;
    }

    public MakeStepModelDto getModel() {
        return model;
    }

    public void setModel(MakeStepModelDto model) {
        this.model = model;
    }

    public PartDto getPart() {
        return part;
    }

    public void setPart(PartDto part) {
        this.part = part;
    }

    public BigDecimal getPlanQuantity() {
        return planQuantity;
    }

    public void setPlanQuantity(BigDecimal planQuantity) {
        this.planQuantity = planQuantity;
    }

    public BigDecimal getActualQuantity() {
        return actualQuantity;
    }

    public void setActualQuantity(BigDecimal actualQuantity) {
        this.actualQuantity = actualQuantity;
    }

    public Date getPlanDeadline() {
        return planDeadline;
    }

    public void setPlanDeadline(Date planDeadline) {
        this.planDeadline = planDeadline;
    }

    public Date getActualDeadline() {
        return actualDeadline;
    }

    public void setActualDeadline(Date actualDeadline) {
        this.actualDeadline = actualDeadline;
    }

    public OrgUnitDto getOrgUnit() {
        return orgUnit;
    }

    public void setOrgUnit(OrgUnitDto orgUnit) {
        this.orgUnit = orgUnit;
    }

    public List<PersonDto> getOperators() {
        return operators;
    }

    public void setOperators(List<PersonDto> operators) {
        this.operators = operators;
    }

    public QCResultDto getQcResult() {
        return qcResult;
    }

    public void setQcResult(QCResultDto qcResult) {
        this.qcResult = qcResult;
    }



}
