package com.bee32.sem.makebiz.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.component.UIViewRoot;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.component.html.HtmlPanelGroup;
import javax.faces.context.FacesContext;

import org.primefaces.component.commandlink.CommandLink;
import org.primefaces.component.panel.Panel;
import org.primefaces.component.panelgrid.PanelGrid;

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

public class MakeProcessAdminBean extends DataViewBean implements ILocationConstants {

    private static final long serialVersionUID = 1L;

    Long id;
    boolean invalidateId = false;

    MakeProcessDto process;

    int goNumber;
    int count;

    public MakeProcessAdminBean() {
        goNumber = 1;
    }

    public void init() {
        if (!invalidateId) {
            id = null;   //如果没有带参数进入页面，则设id为null
        } else {
            goNumber = 1;   //如果带参数了，则只显示一个process
        }
        loadMakeProcess(goNumber);
        invalidateId = false;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
        invalidateId = true;
    }

    public MakeProcessDto getProcess() {
        return process;
    }

    public void setProcess(MakeProcessDto process) {
        this.process = process;
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

        FacesContext facesContext = FacesContext.getCurrentInstance();
        UIViewRoot root = facesContext.getViewRoot();
        Panel stepsPanel = (Panel) root.findComponent("mainForm:steps");
        if (stepsPanel == null)
            return;

        if (stepsPanel.getChildCount() > 0) {
            //先清空子元素
            stepsPanel.getChildren().clear();
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

        ((Panel)stepsPanel).getChildren().add(grid);
    }

    public int getGoNumber() {
        return goNumber;
    }

    public void setGoNumber(int goNumber) {
        this.goNumber = goNumber;
    }

    public int getCount() {
        if(id != null) return 1;
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
        MakeProcess _process;
        if (id != null) {
            _process = DATA(MakeProcess.class).get(id);
        } else {
            _process = DATA(MakeProcess.class).getFirst(new Offset(goNumber - 1));
        }
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
}
