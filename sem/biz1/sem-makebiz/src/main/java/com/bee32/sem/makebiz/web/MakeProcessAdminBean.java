package com.bee32.sem.makebiz.web;

import com.bee32.plover.criteria.hibernate.Offset;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.plover.orm.util.DataViewBean;
import com.bee32.plover.rtx.location.ILocationConstants;
import com.bee32.sem.make.dto.MakeStepModelDto;
import com.bee32.sem.make.dto.MakeStepNameDto;
import com.bee32.sem.make.dto.PartDto;
import com.bee32.sem.makebiz.dto.MakeProcessDto;
import com.bee32.sem.makebiz.dto.MakeStepDto;
import com.bee32.sem.makebiz.entity.MakeProcess;
import com.bee32.sem.makebiz.entity.MakeStep;
import org.apache.commons.lang.StringUtils;
import org.primefaces.component.commandlink.CommandLink;
import org.primefaces.component.panel.Panel;
import org.primefaces.component.panelgrid.PanelGrid;

import javax.annotation.PostConstruct;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.component.html.HtmlPanelGroup;
import javax.faces.context.FacesContext;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MakeProcessAdminBean extends DataViewBean implements ILocationConstants {

    private static final long serialVersionUID = 1L;

    UIViewRoot viewRoot;

    MakeProcessDto process;

    int goNumber;
    int count;


    MakeStepDto currStep;

    public MakeProcessAdminBean() {
        goNumber = 1;
        loadMakeProcess(goNumber);

        //初始建一个空的MakeStepDto,为避免程序出错
        currStep = new MakeStepDto().create();

    }

    @PostConstruct
    public void init() {
        String id = ctx.view.getRequest().getParameter("id");
        if(StringUtils.isNotBlank(id)) {
            MakeProcess _process = ctx.data.getOrFail(MakeProcess.class, Long.parseLong(id));
            process = DTOs.marshal(MakeProcessDto.class, _process);
        }
    }

    public MakeProcessDto getProcess() {
        return process;
    }

    public void setProcess(MakeProcessDto process) {
        this.process = process;
    }

    private List<UIComponent> getComponentChildren(UIComponent component) {
        List<UIComponent> componentList = null;
        System.out.println(component.getId());
        if(component.getChildCount() > 0){
            for(UIComponent ui : component.getChildren()){
                componentList = getComponentChildren(ui);
            }
        }
        return componentList;
    }

    public String getComponentsList(){
        viewRoot = FacesContext.getCurrentInstance().getViewRoot();
        for(UIComponent component : viewRoot.getChildren()){
            getComponentChildren(component);
        }
        return null;
    }

    private void generateTwoDimensionTable() {
        List<PartDto> parts = new ArrayList<PartDto>();
        List<MakeStepNameDto> stepNames = new ArrayList<MakeStepNameDto>();

        Map<PartDto, Map<MakeStepNameDto, MakeStepDto>> table = new HashMap<PartDto, Map<MakeStepNameDto, MakeStepDto>>();

        List<MakeStepDto> steps = process.getSteps();
        for(MakeStepDto step: steps) {
           MakeStepModelDto stepModel = step.getModel();
           PartDto part = stepModel.getOutput();
           MakeStepNameDto stepName = stepModel.getStepName();

           if(!parts.contains(part)) parts.add(part);
           if(!stepNames.contains(stepName)) stepNames.add(stepName);

           Map<MakeStepNameDto, MakeStepDto> row = table.get(part);
           if (row == null) {
               row = new HashMap<MakeStepNameDto, MakeStepDto>();
               table.put(part, row);
           }

           MakeStepDto existedStep = row.get(stepName);
           if (existedStep == null) {
               row.put(stepName, step);
           }
        }

        //动态生成表格
        FacesContext context = FacesContext.getCurrentInstance();
        UIComponent panel = context.getViewRoot().findComponent("mainForm:steps");

        if (panel.getChildCount() > 0) {
            panel.getChildren().remove(0);
        }

        PanelGrid grid = new PanelGrid();
        grid.setColumns(stepNames.size() + 1);
        grid.setRendered(true);

        //形成表头
        HtmlPanelGroup group = new HtmlPanelGroup();
        group.setRendered(true);
        grid.getChildren().add(group);
        for(MakeStepNameDto stepName : stepNames) {
            HtmlOutputText text = new HtmlOutputText();
            text.setValue(stepName.getLabel());
            text.setRendered(true);
            grid.getChildren().add(text);
        }

        //形成表体
        for(PartDto part : parts) {
            HtmlOutputText text = new HtmlOutputText();
            text.setValue(part.getTarget().getLabel());
            text.setRendered(true);
            grid.getChildren().add(text);

            for(MakeStepNameDto stepName : stepNames) {
                MakeStepDto makeStep = table.get(part).get(stepName);
                if (makeStep != null) {
                    //首先建立一个列数为1的panelGrid
                    PanelGrid stepGrid = new PanelGrid();
                    stepGrid.setColumns(1);
                    stepGrid.setRendered(true);

                    HtmlOutputText txtRecordCount = new HtmlOutputText();
                    txtRecordCount.setValue("记录数:" + makeStep.getItems().size());
                    txtRecordCount.setRendered(true);

                    stepGrid.getChildren().add(txtRecordCount);

                    CommandLink link = new CommandLink();
                    link.setId("lnkItem" + makeStep.getId());
                    link.setValue("明细");

                    link.setOnclick("return _open('" + WEB_APP.resolveAbsolute(ctx.view.getRequest()) + "/3/15/6/3/stepItem/list.jsf?stepId=" + makeStep.getId() + "');");
                    link.setRendered(true);

                    stepGrid.getChildren().add(link);

                    grid.getChildren().add(stepGrid);
                } else {
                    HtmlOutputText textNa= new HtmlOutputText();
                    textNa.setValue("");
                    textNa.setRendered(true);
                    grid.getChildren().add(textNa);
                }
            }
        }

        ((Panel)panel).getChildren().add(grid);
    }

    public int getGoNumber() {
        return goNumber;
    }

    public void setGoNumber(int goNumber) {
        this.goNumber = goNumber;
    }

    public MakeStepDto getCurrStep() {
        return currStep;
    }

    public void setCurrStep(MakeStepDto currStep) {
        this.currStep = currStep;
    }

    public int getCount() {
        count = DATA(MakeProcess.class).count();
        return count;
    }

    private void loadMakeProcess(int pos) {
        getCount();
        goNumber = pos;

        if (pos < 1) {
            goNumber = 1;
            pos = 1;
        }

        if (goNumber > count) {
            goNumber = count;
            pos = count;
        }

        process = new MakeProcessDto().create();
        MakeProcess _process = DATA(MakeProcess.class).getFirst(new Offset(goNumber - 1));
        if (_process != null)
            process = DTOs.marshal(MakeProcessDto.class, _process);

        generateTwoDimensionTable();
    }


    public void first() {
        goNumber = 1;
        loadMakeProcess(goNumber);
    }

    public void previous() {
        goNumber--;
        if (goNumber < 1)
            goNumber = 1;
        loadMakeProcess(goNumber);
    }

    public void go() {
        if (goNumber < 1) {
            goNumber = 1;
        } else if (goNumber > count) {
            goNumber = count;
        }
        loadMakeProcess(goNumber);
    }

    public void next() {
        goNumber++;

        if (goNumber > count)
            goNumber = count;
        loadMakeProcess(goNumber);
    }

    public void last() {
        goNumber = count + 1;
        loadMakeProcess(goNumber);
    }

    public void fillStep(long stepId) {
        MakeStep _step = DATA(MakeStep.class).get(stepId);

        currStep = DTOs.marshal(MakeStepDto.class, _step);
    }

    public void save() {
        try {
            MakeStep _step = currStep.unmarshal();
            DATA(MakeStep.class).saveOrUpdate(_step);
            uiLogger.info("保存成功");
        } catch (Exception e) {
            uiLogger.error("保存出错!", e);
        }
    }


    /*************************************************************************
     * Section: MBeans
     *************************************************************************/
//    final ListMBean<PersonDto> operatorsMBean = ListMBean.fromEL(this, //
//            "currStep.operators", PersonDto.class);
//
//    class QCResultContext
//    implements IEnclosingContext, Serializable {
//
//        private static final long serialVersionUID = 1L;
//
//        @Override
//        public Object getEnclosingObject() {
//            //return currStep.getQcResult();
//        }
//    }
//
//    final ListMBean<QCResultParameterDto> qcResultParasMBean = ListMBean.fromEL(new QCResultContext(), //
//            "enclosingObject.parameters", QCResultParameterDto.class);
//
//    public ListMBean<PersonDto> getOperatorsMBean() {
//        return operatorsMBean;
//    }
//
//    public ListMBean<QCResultParameterDto> getQcResultParasMBean() {
//        return qcResultParasMBean;
//    }




}
