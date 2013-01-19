package com.bee32.sem.makebiz.web;

import com.bee32.plover.arch.util.IEnclosingContext;
import com.bee32.plover.criteria.hibernate.Equals;
import com.bee32.plover.orm.annotation.ForEntity;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.plover.ox1.util.CommonCriteria;
import com.bee32.sem.chance.entity.Chance;
import com.bee32.sem.frame.ui.ListMBean;
import com.bee32.sem.make.dto.QCResultParameterDto;
import com.bee32.sem.makebiz.dto.MakeStepDto;
import com.bee32.sem.makebiz.dto.MakeStepItemDto;
import com.bee32.sem.makebiz.entity.MakeStep;
import com.bee32.sem.makebiz.entity.MakeStepItem;
import com.bee32.sem.misc.SimpleEntityViewBean;
import com.bee32.sem.people.dto.PersonDto;
import org.apache.commons.lang.StringUtils;

import javax.annotation.PostConstruct;
import java.io.Serializable;

@ForEntity(Chance.class)
public class MakeStepItemListAdminBean
        extends SimpleEntityViewBean {

    private static final long serialVersionUID = 1L;


    public MakeStepItemListAdminBean() {
        super(MakeStepItem.class, MakeStepItemDto.class, MakeStepItemDto.OPERATORS);
    }

    @PostConstruct
    public void init() {
        String stepId = ctx.view.getRequest().getParameter("stepId");
        if(StringUtils.isNotBlank(stepId)) {
            MakeStep step = ctx.data.getOrFail(MakeStep.class, Long.parseLong(stepId));
            searchStep = DTOs.marshal(MakeStepDto.class, step);
            addStepRestriction();
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
            return ((MakeStepItemDto)getOpenedObject()).getQcResult();
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
        //新建makeStepItem时，设置parent为从工艺流转单上跳转过来时对应的MakeStep
        ((MakeStepItemDto)getOpenedObject()).setParent(searchStep);
        super.showCreateForm();

    }

    /*************************************************************************
     * Section: Search
     *************************************************************************/
    @Override
    public void addNameOrLabelRestriction() {
        setSearchFragment("name", "名称含有 " + searchPattern,
                CommonCriteria.labelledWith(searchPattern, true));
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
            setSearchFragment("step", "工艺步骤为 " + searchStep.getLabel() + (searchStep.getModel().getStepName().getLabel()), //
                    new Equals("parent.id", searchStep.getId()));
            searchStep = null;
        }
    }
}
