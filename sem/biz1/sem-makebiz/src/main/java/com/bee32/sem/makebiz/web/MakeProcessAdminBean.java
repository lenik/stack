package com.bee32.sem.makebiz.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.component.html.HtmlPanelGrid;
import javax.faces.component.html.HtmlPanelGroup;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionListener;

import org.primefaces.component.commandbutton.CommandButton;

import com.bee32.plover.criteria.hibernate.Offset;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.plover.orm.util.DataViewBean;
import com.bee32.sem.make.dto.MakeStepModelDto;
import com.bee32.sem.make.dto.MakeStepNameDto;
import com.bee32.sem.make.dto.PartDto;
import com.bee32.sem.makebiz.dto.MakeProcessDto;
import com.bee32.sem.makebiz.dto.MakeStepDto;
import com.bee32.sem.makebiz.entity.MakeProcess;

public class MakeProcessAdminBean extends DataViewBean {

    private static final long serialVersionUID = 1L;

    UIViewRoot viewRoot;

    MakeProcessDto process;

    int goNumber;
    int count;


    public MakeProcessAdminBean() {
        goNumber = 1;
        loadMakeProcess(goNumber);

    }

	private List<UIComponent> getComponentChildren(UIComponent component){
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
        UIComponent panelGroup = context.getViewRoot().findComponent("mainForm:panelGroup");

        if (panelGroup.getChildCount() > 0) {
            panelGroup.getChildren().remove(0);
        }

        HtmlPanelGrid grid = new HtmlPanelGrid();
        grid.setColumns(stepNames.size() + 1);
        grid.setBorder(1);
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
                    CommandButton button = new CommandButton();
                    button.setValue("输入工艺数据");

                    //ActionListener listener = new ActionListener();

                    //button.addActionListener(listener);

                    button.setRendered(true);
                    grid.getChildren().add(button);
                } else {
                    HtmlOutputText textNa= new HtmlOutputText();
                    textNa.setValue("");
                    textNa.setRendered(true);
                    grid.getChildren().add(textNa);
                }
            }
        }

        ((HtmlPanelGroup)panelGroup).getChildren().add(grid);
    }

    public int getGoNumber() {
        return goNumber;
    }

    public void setGoNumber(int goNumber) {
        this.goNumber = goNumber;
    }

    public int getCount() {
        count = ctx.data.access(MakeProcess.class).count();
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
        MakeProcess _process = ctx.data.access(MakeProcess.class).getFirst(new Offset(goNumber - 1));
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
