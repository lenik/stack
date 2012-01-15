package com.bee32.plover.web.faces.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;

import org.apache.myfaces.application.ActionListenerImpl;

import com.bee32.plover.web.faces.view.ViewBean;

public class ActionBean
        extends ViewBean {

    private static final long serialVersionUID = 1L;

    List<String> actions = new ArrayList<String>();

    public void clear() {
        actions.clear();
    }

    public void action() {
        actions.add("action");
    }

    public void action(Object arg) {
        actions.add("action: " + arg);
    }

    public void action_va(Object... args) {
        actions.add("action_va: " + Arrays.asList(args).toString());
    }

    public void actionListener() {
        actions.add("actionListener");
    }

    public void actionListener(Object arg) {
        actions.add("actionListener: " + arg);
    }

    public void actionListener2() {
        actions.add("actionListener2");
    }

    public void actionListener_va(Object... args) {
        actions.add("actionListener_va: " + Arrays.asList(args).toString());
    }

    public List<String> getActions() {
        actions.add("getActions");
        return actions;
    }

    public void setActions(List<String> actions) {
        actions.add("setActions");
        this.actions = actions;
    }

    public ActionListener getListener1() {
        return new ActionListenerImpl() {

            @Override
            public void processAction(ActionEvent actionEvent)
                    throws AbortProcessingException {
                actions.add("listener1.processAction: " + actionEvent);
            }

        };
    }

    public ActionListener getListener2() {
        return new ActionListenerImpl() {

            @Override
            public void processAction(ActionEvent actionEvent)
                    throws AbortProcessingException {
                actions.add("listener2.processAction: " + actionEvent);
            }

        };
    }

    public void setVar(Object value) {
        String type = value == null ? "(null-class)" : value.getClass().getCanonicalName();
        actions.add("setVar: " + value + " as " + type);
    }

}
