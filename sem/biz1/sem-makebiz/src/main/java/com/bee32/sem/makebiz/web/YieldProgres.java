package com.bee32.sem.makebiz.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.component.UIViewRoot;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.context.FacesContext;

import org.apache.myfaces.custom.div.Div;
import org.primefaces.component.fieldset.Fieldset;
import org.primefaces.component.panelgrid.PanelGrid;

import com.bee32.plover.orm.util.DTOs;
import com.bee32.plover.orm.util.EntityViewBean;
import com.bee32.sem.chance.dto.ChanceDto;
import com.bee32.sem.make.dto.MakeStepNameDto;
import com.bee32.sem.makebiz.dto.MakeOrderDto;
import com.bee32.sem.makebiz.dto.MakeOrderItemDto;
import com.bee32.sem.makebiz.dto.MakeProcessDto;
import com.bee32.sem.makebiz.dto.MakeStepDto;
import com.bee32.sem.makebiz.dto.MakeStepItemDto;
import com.bee32.sem.makebiz.dto.MakeTaskDto;
import com.bee32.sem.makebiz.dto.MakeTaskItemDto;
import com.bee32.sem.makebiz.entity.MakeOrder;
import com.bee32.sem.makebiz.util.MakebizCriteria;
import com.bee32.sem.material.dto.MaterialDto;

public class YieldProgres extends EntityViewBean {

    private static final long serialVersionUID = 1L;

    ChanceDto project;

    void generateMakeOrderDetails() {
        FacesContext context = FacesContext.getCurrentInstance();
        UIViewRoot rootView = context.getViewRoot();
        Div div = (Div) rootView.findComponent(":mainForm:yieldGrid");
        div.getChildren().clear();

        Fieldset fieldset = new Fieldset();
        fieldset.setLegend("Yield Detail");
        fieldset.setToggleable(true);
        fieldset.setToggleSpeed(100);
        fieldset.setRendered(true);

        MakeOrder order = DATA(MakeOrder.class).getUnique(MakebizCriteria.getMakeOrderByChanceId(project.getId()));
        if (null == order)
            uiLogger.warn("没有该销售机会对应的生产订单");
        else {
            MakeOrderDto orderDto = DTOs.marshal(MakeOrderDto.class, MakeOrderDto.ITEMS + MakeOrderDto.TASKS, order);

            List<MakeStepNameDto> stepList = new ArrayList<MakeStepNameDto>();
            Map<Long, Map<Integer, YieldDataModel>> itemMap = new HashMap<Long, Map<Integer, YieldDataModel>>();

            List<MakeTaskDto> tasks = orderDto.getTasks();
            for (MakeTaskDto task : tasks) {
                List<MakeTaskItemDto> items = task.getItems();

                for (MakeTaskItemDto taskItem : items) {

                    Long materialId = taskItem.getMaterial().getId();
                    List<MakeProcessDto> processes = taskItem.getProcesses();
                    Map<Integer, YieldDataModel> map = itemMap.get(materialId);

                    if (null == map)
                        map = new HashMap<Integer, YieldDataModel>();

                    for (MakeProcessDto process : processes) {
                        List<MakeStepDto> steps = process.getSteps();

                        for (MakeStepDto step : steps) {

                            MakeStepNameDto stepName = step.getModel().getStepName();
                            if (!stepList.contains(stepName))
                                stepList.add(stepName);

                            YieldDataModel dataModel = map.get(stepName.getId());
                            if (null == dataModel)
                                dataModel = new YieldDataModel(0, 0, 0);

                            int quantity = dataModel.getQuantity();
                            List<MakeStepItemDto> stepItems = step.getItems();
                            for (MakeStepItemDto stepItem : stepItems) {
                                int actualQuantity = stepItem.getActualQuantity().intValue();
                                quantity = quantity + actualQuantity;
                            }
                            dataModel.setQuantity(quantity);
                            map.put(stepName.getId(), dataModel);
                        }
                    }
                    itemMap.put(materialId, map);
                }
            }

            PanelGrid mainGrid = new PanelGrid();
            mainGrid.setColumns(stepList.size() + 2);

            HtmlOutputText empty = new HtmlOutputText();
            empty.setValue("");
            empty.setRendered(true);
            mainGrid.getChildren().add(empty);
            for (MakeStepNameDto stepName : stepList) {
                HtmlOutputText stepLabel = new HtmlOutputText();
                stepLabel.setValue(stepName.getLabel());
                stepLabel.setRendered(true);
                mainGrid.getChildren().add(stepLabel);
            }
            HtmlOutputText yieldProgresLabel = new HtmlOutputText();
            yieldProgresLabel.setValue("总进度");
            yieldProgresLabel.setRendered(true);
            mainGrid.getChildren().add(yieldProgresLabel);

            List<MakeOrderItemDto> items = orderDto.getItems();
            for (MakeOrderItemDto item : items) {
                MaterialDto materialDto = item.getMaterial();
                int total = item.getQuantity().intValue();

                String partLabelDetail = materialDto.getLabel() + " {" + item.getQuantity().intValue() + "}";
                HtmlOutputText partLabel = new HtmlOutputText();
                partLabel.setValue(partLabelDetail);
                partLabel.setRendered(true);
                mainGrid.getChildren().add(partLabel);

                Map<Integer, YieldDataModel> stepMap = itemMap.get(materialDto.getId());
                Integer actual = null;
                Integer actualTotal = null;
                for (MakeStepNameDto stepName : stepList) {
                    String result;
                    YieldDataModel dataModel = stepMap.get(stepName.getId());
                    if (null == dataModel)
                        result = "---";
                    else {
                        int quantity = dataModel.getQuantity();
                        if (quantity > total)
                            quantity = total;
                        if (null == actual)
                            actual = 0;
                        actual += quantity;
                        if (null == actualTotal)
                            actualTotal = 0;
                        actualTotal += total;
                        result = quantity + "/" + total;
                    }
                    HtmlOutputText stepValue = new HtmlOutputText();
                    stepValue.setValue(result);
                    stepValue.setRendered(true);
                    mainGrid.getChildren().add(stepValue);
                }

                HtmlOutputText progres = new HtmlOutputText();
                if (null == actual || null == actualTotal)
                    progres.setValue("n/a");
                else
                    progres.setValue((double) actual / actualTotal + "%");
                mainGrid.getChildren().add(progres);

            }

            fieldset.getChildren().add(mainGrid);
            div.getChildren().add(fieldset);
        }

    }

    public ChanceDto getProject() {
        return project;
    }

    public void setProject(ChanceDto project) {
        this.project = project;
        generateMakeOrderDetails();
    }

    class YieldDataModel {
        int quantity;
        int total;
        double time;

        public YieldDataModel(int quantity, int total, double time) {
            this.quantity = quantity;
            this.total = total;
            this.time = time;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public double getTime() {
            return time;
        }

        public void setTime(double time) {
            this.time = time;
        }

    }

}