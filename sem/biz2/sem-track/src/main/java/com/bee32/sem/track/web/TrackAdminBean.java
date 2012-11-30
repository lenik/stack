package com.bee32.sem.track.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.primefaces.context.RequestContext;

import com.bee32.icsf.login.SessionUser;
import com.bee32.icsf.principal.UserDto;
import com.bee32.plover.faces.utils.SelectableList;
import com.bee32.plover.orm.entity.IEntityAccessService;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.plover.orm.util.EntityViewBean;
import com.bee32.sem.track.dto.IssueDto;
import com.bee32.sem.track.dto.IssueReplyDto;
import com.bee32.sem.track.entity.Issue;
import com.bee32.sem.track.entity.IssueReply;
import com.bee32.sem.track.entity.IssueState;
import com.bee32.sem.track.util.IssueCriteria;

public class TrackAdminBean
        extends EntityViewBean {

    private static final long serialVersionUID = 1L;

    public IssueReplyDto reply = new IssueReplyDto().create();
    public String globalState = "";

    public TrackAdminBean() {
        generatorOpenedObject();
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

    void generatorOpenedObject() {
        IssueDto issueDto;
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        String param = request.getParameter("id");
        if (param == null || param.isEmpty()) {
            issueDto = new IssueDto().create();
            UserDto current = SessionUser.getInstance().getUser();
            issueDto.setOwner(current);
        } else {
            long issueId = Long.parseLong(param);
            Issue issue = DATA(Issue.class).getUnique(IssueCriteria.getUniqueById(issueId));
            issueDto = DTOs.marshal(IssueDto.class, IssueDto.REPLIES, issue);
            issueDto.setContentEditable(false);
        }
        setOpenedObject(issueDto);
    }

    public void doComment() {
        IssueDto openedObject = (IssueDto) getOpenedObject();
        UserDto user = SessionUser.getInstance().getUser();
        IEntityAccessService<IssueReply, Long> replyData = DATA(IssueReply.class);
        IEntityAccessService<Issue, Long> issueData = DATA(Issue.class);
        IssueReplyDto marshaled;
        Issue IssueEntity;

        reply.setIssue(openedObject);
        reply.setReplier(user);
        IssueReply replyEntity = reply.unmarshal();
        replyData.saveOrUpdate(replyEntity);
        marshaled = DTOs.marshal(IssueReplyDto.class, replyEntity);
        openedObject.getReplies().add(marshaled);
        openedObject.setStateValue(reply.getStateValue().charAt(0));
        IssueEntity = openedObject.unmarshal();
        issueData.saveOrUpdate(IssueEntity);

        RequestContext.getCurrentInstance().execute("clearContent()");
    }

    public void deleteReply(Object obj) {
        IssueDto openedObject = getOpenedObject();
        IssueReplyDto dto = (IssueReplyDto) obj;
        DATA(IssueReply.class).deleteByKey(dto.getId());
        openedObject.getReplies().remove(dto);
    }

    public void doSaveIssue() {
        IssueDto issueDto = (IssueDto) getOpenedObject();
        Issue entity = issueDto.unmarshal();
        DATA(Issue.class).saveOrUpdate(entity);
        issueDto = DTOs.marshal(IssueDto.class, IssueDto.REPLIES, entity);
        setOpenedObject(issueDto);
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

    public IssueReplyDto getReply() {
        return reply;
    }

    public void setReply(IssueReplyDto reply) {
        this.reply = reply;
    }

    public String getGlobalState() {
        return globalState;
    }

    public void setGlobalState(String globalState) {
        this.globalState = globalState;
    }

}
