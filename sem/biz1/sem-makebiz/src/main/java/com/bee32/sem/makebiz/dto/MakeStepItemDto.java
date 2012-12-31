package com.bee32.sem.makebiz.dto;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.free.NotImplementedException;
import javax.free.ParseException;

import com.bee32.plover.arch.util.IEnclosedObject;
import com.bee32.plover.arch.util.TextMap;
import com.bee32.sem.inventory.tx.dto.StockJobDto;
import com.bee32.sem.make.dto.QCResultDto;
import com.bee32.sem.makebiz.entity.MakeStepItem;
import com.bee32.sem.people.dto.OrgUnitDto;
import com.bee32.sem.people.dto.PersonDto;

public class MakeStepItemDto
    extends StockJobDto<MakeStepItem>
    implements IEnclosedObject<MakeStepDto> {

    private static final long serialVersionUID = 1L;

    public static final int OPERATORS = 1;

    //begiinDate
    MakeStepDto parent;

    BigDecimal planQuantity = new BigDecimal(0);
    BigDecimal actualQuantity = new BigDecimal(0);
    BigDecimal verifiedQuantity = new BigDecimal(0);

    Date planDeadline;
    Date actualDeadline;

    OrgUnitDto orgUnit;
    List<PersonDto> operators;
    QCResultDto qcResult;

    @Override
    public MakeStepDto getEnclosingObject() {
        return getParent();
    }

    @Override
    public void setEnclosingObject(MakeStepDto enclosingObject) {
        setParent(enclosingObject);

    }

    @Override
    protected void _marshal(MakeStepItem source) {
        parent = mref(MakeStepDto.class, source.getParent());
        planQuantity = source.getPlanQuantity();
        actualQuantity = source.getActualQuantity();
        verifiedQuantity = source.getVerifiedQuantity();
        planDeadline = source.getPlanDeadline();
        actualDeadline = source.getActualDeadline();
        orgUnit = mref(OrgUnitDto.class, source.getOrgUnit());

        if(selection.contains(OPERATORS))
            operators = mrefList(PersonDto.class, source.getOperators());
        else
            operators = Collections.emptyList();

        qcResult = mref(QCResultDto.class, source.getQcResult());
    }

    @Override
    protected void _unmarshalTo(MakeStepItem target) {
        merge(target, "parent", parent);
        target.setPlanQuantity(planQuantity);
        target.setActualQuantity(actualQuantity);
        target.setVerifiedQuantity(verifiedQuantity);
        target.setPlanDeadline(planDeadline);
        target.setActualDeadline(actualDeadline);
        merge(target, "orgUnit", orgUnit);

        if(selection.contains(OPERATORS))
            mergeList(target, "operators", operators);
        merge(target, "qcResult", qcResult);
    }

    @Override
    protected void _parse(TextMap map) throws ParseException {
        throw new NotImplementedException();

    }

    public MakeStepDto getParent() {
        return parent;
    }

    public void setParent(MakeStepDto parent) {
        this.parent = parent;
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

    public BigDecimal getVerifiedQuantity() {
        return verifiedQuantity;
    }

    public void setVerifiedQuantity(BigDecimal verifiedQuantity) {
        this.verifiedQuantity = verifiedQuantity;
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
