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
import org.primefaces.component.panel.Panel;
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
import com.bee32.sem.makebiz.util.YieldDataModel;
import com.bee32.sem.material.dto.MaterialDto;
import com.bee32.sem.world.thing.UnitConvDto;

public class YieldProgres extends EntityViewBean {

    private static final long serialVersionUID = 1L;

    ChanceDto project;

    void generateMakeOrderDetails() {
        FacesContext context = FacesContext.getCurrentInstance();
        UIViewRoot rootView = context.getViewRoot();
        Div div = (Div) rootView.findComponent(":mainForm:yieldGrid");
        div.getChildren().clear();

        Fieldset fieldset = new Fieldset();
        fieldset.setLegend("生产进度表");
        fieldset.setToggleable(true);
        fieldset.setToggleSpeed(100);

        MakeOrder order = DATA(MakeOrder.class).getUnique(MakebizCriteria.getMakeOrderByChanceId(project.getId()));
        if (null == order)
            uiLogger.warn("没有该销售机会对应的生产订单");
        else {

            MakeOrderDto orderDto = DTOs.marshal(MakeOrderDto.class, MakeOrderDto.ITEMS + MakeOrderDto.TASKS, order);

            Panel panel = new Panel();
            panel.setHeader("生产进度汇总");

            PanelGrid infoGrid = new PanelGrid();
            infoGrid.setColumns(2);

            HtmlOutputText orderTitle = new HtmlOutputText();
            orderTitle.setValue("对应生产订单:");
            infoGrid.getChildren().add(orderTitle);

            HtmlOutputText orderLabel = new HtmlOutputText();
            orderLabel.setValue(orderDto.getLabel());
            infoGrid.getChildren().add(orderLabel);

            HtmlOutputText totalPartTypeSize = new HtmlOutputText();
            totalPartTypeSize.setValue("构件类型:");
            infoGrid.getChildren().add(totalPartTypeSize);

            HtmlOutputText partsize = new HtmlOutputText();
            partsize.setValue(orderDto.getItems().size());
            infoGrid.getChildren().add(partsize);

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

            Panel detailPanel = new Panel();
            detailPanel.setHeader("生产进度信息明细");

            PanelGrid mainGrid = new PanelGrid();
            mainGrid.setColumns(stepList.size() + 2);

            HtmlOutputText empty = new HtmlOutputText();
            empty.setValue("");
            mainGrid.getChildren().add(empty);
            for (MakeStepNameDto stepName : stepList) {
                HtmlOutputText stepLabel = new HtmlOutputText();
                stepLabel.setValue(stepName.getLabel());
                mainGrid.getChildren().add(stepLabel);
            }
            HtmlOutputText yieldProgresLabel = new HtmlOutputText();
            yieldProgresLabel.setValue("构件进度");
            mainGrid.getChildren().add(yieldProgresLabel);

            List<MakeOrderItemDto> items = orderDto.getItems();

            int totalPartCount = 0;
            int productedPartCount = 0;
            for (MakeOrderItemDto item : items) {
                MaterialDto materialDto = item.getMaterial();
                UnitConvDto unitConv = materialDto.getUnitConv();
                unitConv = reload(unitConv, UnitConvDto.MAP);

                int total = item.getQuantity().intValue();
                totalPartCount = totalPartCount + total;

                String partLabelDetail = materialDto.getLabel() + " {" + item.getQuantity().intValue() + "}";
                HtmlOutputText partLabel = new HtmlOutputText();
                partLabel.setValue(partLabelDetail);
                mainGrid.getChildren().add(partLabel);

                Map<Integer, YieldDataModel> stepMap = itemMap.get(materialDto.getId());
                Integer actual = null;
                Integer actualTotal = null;
                Integer tempProductedPart = null;
                for (MakeStepNameDto stepName : stepList) {
                    YieldDataModel dataModel = stepMap.get(stepName.getId());

                    if (null == dataModel) {
                        HtmlOutputText stepValue = new HtmlOutputText();
                        stepValue.setValue("---");
                        mainGrid.getChildren().add(stepValue);
                    } else {
                        int quantity = dataModel.getQuantity();
                        if (quantity > total)
                            quantity = total;
                        if (null == actual)
                            actual = 0;
                        actual += quantity;
                        if (null == actualTotal)
                            actualTotal = 0;
                        actualTotal += total;
                        if (null == tempProductedPart)
                            tempProductedPart = 10000;
                        if (quantity < tempProductedPart)
                            tempProductedPart = quantity;

                        PanelGrid cell = new PanelGrid();
                        cell.setColumns(1);
                        cell.setStyle("border:0px;margin:0;padding:0;color:red;");

                        HtmlOutputText pieceText = new HtmlOutputText();
                        pieceText.setValue(quantity + "/" + total + "个");
                        cell.getChildren().add(pieceText);

                        Double weight = unitConv.getScale("kg");
                        if (null == weight)
                            weight = 0.0;
                        double roundWeight = Math.round(quantity * weight * 100) / 100.00;
                        HtmlOutputText weightText = new HtmlOutputText();
                        weightText.setValue(roundWeight + "kg");
                        if (roundWeight > 0.0)
                            weightText.setStyle("color:green");
                        else
                            weightText.setStyle("color:red");
                        cell.getChildren().add(weightText);

                        Double square = unitConv.getScale("m2");
                        if (null == square)
                            square = 0.0;
                        double roundSquare = Math.round(quantity * square * 100) / 100.00;
                        HtmlOutputText squareText = new HtmlOutputText();
                        squareText.setValue(roundSquare + "㎡");
                        if (roundSquare > 0.0)
                            squareText.setStyle("color:green");
                        else
                            squareText.setStyle("color:red");
                        cell.getChildren().add(squareText);

                        mainGrid.getChildren().add(cell);
                    }

                }

                if (null == tempProductedPart)
                    tempProductedPart = 0;
                productedPartCount += tempProductedPart;

                HtmlOutputText progres = new HtmlOutputText();
                if (null == actual || null == actualTotal)
                    progres.setValue("n/a");
                else {
                    double totalPercent = Math.round((double) actual / actualTotal * 100) / 100.00;
                    progres.setValue(totalPercent + "%");
                }
                mainGrid.getChildren().add(progres);

            }

            HtmlOutputText productedPartTitle = new HtmlOutputText();
            productedPartTitle.setValue("已生产构件数:");
            infoGrid.getChildren().add(productedPartTitle);

            HtmlOutputText productePartSize = new HtmlOutputText();
            productePartSize.setValue(productedPartCount);
            if (productedPartCount == 0)
                productePartSize.setStyle("color:red");
            else
                productePartSize.setStyle("color:blue");
            infoGrid.getChildren().add(productePartSize);

            HtmlOutputText totalPartTitle = new HtmlOutputText();
            totalPartTitle.setValue("总构件数:");
            infoGrid.getChildren().add(totalPartTitle);

            HtmlOutputText totalPartSize = new HtmlOutputText();
            totalPartSize.setValue(totalPartCount);
            infoGrid.getChildren().add(totalPartSize);

            HtmlOutputText totalProgresLabel = new HtmlOutputText();
            totalProgresLabel.setValue("总进度:");
            infoGrid.getChildren().add(totalProgresLabel);

            double totalProgresPercent = Math.round(productedPartCount / totalPartCount * 100) / 100.00;
            HtmlOutputText totalProgresPercentText = new HtmlOutputText();
            totalProgresPercentText.setStyle("color:blue");
            totalProgresPercentText.setValue(totalProgresPercent + "%");
            infoGrid.getChildren().add(totalProgresPercentText);

            panel.getChildren().add(infoGrid);
            detailPanel.getChildren().add(mainGrid);
            fieldset.getChildren().add(panel);
            fieldset.getChildren().add(detailPanel);
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

}