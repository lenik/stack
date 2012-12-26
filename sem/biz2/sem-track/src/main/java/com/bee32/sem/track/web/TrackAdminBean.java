package com.bee32.sem.track.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;

import com.bee32.plover.faces.utils.SelectableList;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.plover.orm.util.EntityViewBean;
import com.bee32.sem.track.dto.IssueDto;
import com.bee32.sem.track.entity.Issue;
import com.bee32.sem.track.entity.IssueState;
import com.bee32.sem.track.util.IssueCriteria;

public class TrackAdminBean
        extends EntityViewBean {

    private static final long serialVersionUID = 1L;

    public String globalState = "";

    public TrackAdminBean() {
    }

    public void applyAndToogle() {
        IssueDto selection = (IssueDto) getSingleSelection();
        FacesContext context = FacesContext.getCurrentInstance();

        try {
            context.getExternalContext().redirect("issue-content.jsf?id=" + selection.getId());
        } catch (IOException e) {
            uiLogger.error("页面重定向错误", e);
        }

    }

    public void gotoCreateView()
            throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("issue-content.jsf");
    }

    public void backToList()
            throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("index-rich.jsf");
    }

    public SelectableList<IssueDto> getIssues() {
        List<Issue> list = new ArrayList<Issue>();
        if (globalState == null || globalState.isEmpty()) {
            list = DATA(Issue.class).list();
        } else {
            list = DATA(Issue.class).list(IssueCriteria.getIssueByState(globalState.charAt(0)));
        }
        List<IssueDto> dtoList = DTOs.marshalList(IssueDto.class, IssueDto.REPLIES, list);
        return SelectableList.decorate(dtoList);
    }

    public SelectableList<IssueState> getIssueStates() {
        List<IssueState> list = new ArrayList<IssueState>(IssueState.values());
        return SelectableList.decorate(list);
    }

    public String getGlobalState() {
        return globalState;
    }

    public void setGlobalState(String globalState) {
        this.globalState = globalState;
    }

}
