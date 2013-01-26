package com.bee32.sem.makebiz.web;

import java.io.Serializable;

import javax.faces.context.FacesContext;

import com.bee32.plover.arch.util.IEnclosingContext;
import com.bee32.plover.criteria.hibernate.Equals;
import com.bee32.plover.orm.annotation.ForEntity;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.plover.ox1.util.CommonCriteria;
import com.bee32.sem.frame.ui.ListMBean;
import com.bee32.sem.make.dto.MakeStepModelDto;
import com.bee32.sem.make.dto.QCResultParameterDto;
import com.bee32.sem.make.web.ChooseQCSpecParameterDialogBean;
import com.bee32.sem.makebiz.dto.MakeStepDto;
import com.bee32.sem.makebiz.dto.MakeStepItemDto;
import com.bee32.sem.makebiz.entity.MakeStep;
import com.bee32.sem.makebiz.entity.MakeStepItem;
import com.bee32.sem.misc.SimpleEntityViewBean;
import com.bee32.sem.people.dto.PersonDto;

@ForEntity(MakeStepItem.class)
public class MakeStepItemListAdminBean
        extends SimpleEntityViewBean {

    private static final long serialVersionUID = 1L;

    public MakeStepItemListAdminBean() {
        super(MakeStepItem.class, MakeStepItemDto.class, MakeStepItemDto.OPERATORS);
    }

    public void init() {
        FacesContext context = FacesContext.getCurrentInstance();
        if (!context.isPostback()) {
            String stepIdStr = context.getExternalContext().getRequestParameterMap().get("stepId");
            if(stepIdStr == null) {
                clearSearchFragments();
            } else {
                MakeStep step = ctx.data.getOrFail(MakeStep.class, Long.parseLong(stepIdStr));
                searchStep = DTOs.marshal(MakeStepDto.class, step);
                addStepRestriction();
            }
        }
    }

    public void addQcSpecRestriction() {
        if (!DTOs.isNull(searchStep)) {
            MakeStepModelDto model = searchStep.getModel();
            BEAN(ChooseQCSpecParameterDialogBean.class).addQcSpecRestriction(model.getQcSpec().getId());
        }
    }

    /*************************************************************************
     * Section: MBeans
     *************************************************************************/
    final ListMBean<PersonDto> operatorsMBean = ListMBean.fromEL(this, //
            "openedObject.operators", PersonDto.class);

    class QCResultContext
            implements IEnclosingContext, Serializable {
        private static final long serialVersionUID = 1L;

        @Override
        public Object getEnclosingObject() {
            return ((MakeStepItemDto) getOpenedObject()).getQcResult();
        }
    }

    final ListMBean<QCResultParameterDto> qcResultParasMBean = ListMBean.fromEL(new QCResultContext(), //
            "enclosingObject.parameters", QCResultParameterDto.class);

    public ListMBean<PersonDto> getOperatorsMBean() {
        return operatorsMBean;
    }

    public ListMBean<QCResultParameterDto> getQcResultParasMBean() {
        return qcResultParasMBean;
    }

    /*************************************************************************
     * Section: Persistence
     *************************************************************************/

    @Override
    public void showCreateForm() {
        // 新建makeStepItem时，设置parent为从工艺流转单上跳转过来时对应的MakeStep
        super.showCreateForm();
        MakeStepItemDto _stepItem = ((MakeStepItemDto) getOpenedObject());
        _stepItem.setParent(searchStep);
    }

    /*************************************************************************
     * Section: Search
     *************************************************************************/
    @Override
    public void addNameOrLabelRestriction() {
        setSearchFragment("name", "名称含有 " + searchPattern, CommonCriteria.labelledWith(searchPattern, true));
        searchPattern = null;
    }

    MakeStepDto searchStep;

    public MakeStepDto getSearchStep() {
        return searchStep;
    }

    public void setSearchStep(MakeStepDto searchStep) {
        this.searchStep = searchStep;
    }

    public void addStepRestriction() {
        if (searchStep != null) {
            setSearchFragment("step",
                    "工艺步骤为  " + (searchStep.getModel().getStepName().getLabel()), //
                    new Equals("parent.id", searchStep.getId()));
        }
    }
}
