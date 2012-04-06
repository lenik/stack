package com.bee32.sem.makebiz.web;

import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.component.html.HtmlInputText;
import javax.faces.component.html.HtmlPanelGrid;
import javax.faces.component.html.HtmlPanelGroup;
import javax.faces.context.FacesContext;

import com.bee32.plover.orm.util.EntityViewBean;

public class MakeProcessAdminBean extends EntityViewBean {

    private static final long serialVersionUID = 1L;

    UIViewRoot viewRoot;



    public MakeProcessAdminBean() {
	FacesContext context = FacesContext.getCurrentInstance();
        UIComponent panelGroup = context.getViewRoot().findComponent("panelGroup");

        int inputIndex = 0;

        int rows = 5;
        int cols = 5;

        HtmlPanelGrid grid = new HtmlPanelGrid();
        grid.setColumns(cols);
        grid.setRendered(true);

        for(int i = 0; i < rows; i++) {
		for(int j = 0; j < cols; j++) {
                HtmlInputText input = new HtmlInputText();
                input.setId("input" + (inputIndex++));
                input.setValue("input " + inputIndex);
                input.setRendered(true);
			grid.getChildren().add(input);
		}
        }

        ((HtmlPanelGroup)panelGroup).getChildren().add(grid);
        System.out.print("test");

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

}
