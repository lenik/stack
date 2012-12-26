package com.bee32.sem.track.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.primefaces.context.RequestContext;

import com.bee32.icsf.login.SessionUser;
import com.bee32.icsf.principal.Principal;
import com.bee32.icsf.principal.User;
import com.bee32.icsf.principal.UserDto;
import com.bee32.icsf.principal.Users;
import com.bee32.plover.faces.utils.SelectableList;
import com.bee32.plover.orm.entity.IEntityAccessService;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.plover.orm.util.EntityViewBean;
import com.bee32.plover.servlet.util.ThreadServletContext;
import com.bee32.sem.track.dto.IssueDto;
import com.bee32.sem.track.dto.IssueFavDto;
import com.bee32.sem.track.dto.IssueObserverDto;
import com.bee32.sem.track.dto.IssueReplyDto;
import com.bee32.sem.track.entity.Issue;
import com.bee32.sem.track.entity.IssueFav;
import com.bee32.sem.track.entity.IssueReply;
import com.bee32.sem.track.entity.IssueState;
import com.bee32.sem.track.util.IssueCriteria;

public class IssueAdminBean
        extends EntityViewBean {

    private static final long serialVersionUID = 1L;

    public IssueReplyDto reply = new IssueReplyDto().create();
    public int favNum;
    public boolean fav;
    public boolean editing = false;
    public boolean operatable;
    public List<IssueObserverDto> availableObservers;
    public IssueObserverDto openedObserver = new IssueObserverDto().create();

    IssueAdminBean() {
        generatorOpenedObject();
        generateAvailableObservers();
    }

    void generatorOpenedObject() {
        IssueDto issueDto;
        HttpServletRequest request = ThreadServletContext.getRequest();
        String param = request.getParameter("id");
        UserDto currentUser = SessionUser.getInstance().getUser();

        if (param == null || param.isEmpty()) {
            issueDto = new IssueDto().create();
            issueDto.setOwner(currentUser);
            fav = false;
            favNum = 0;
            operatable = true;
        } else {
            long issueId = Long.parseLong(param);
            Issue issue = DATA(Issue.class).getUnique(IssueCriteria.getUniqueById(issueId));
            issueDto = DTOs.marshal(IssueDto.class, IssueDto.REPLIES + IssueDto.OBSERVERS, issue);
            List<IssueObserverDto> observers = issueDto.getObservers();
            IssueFav issueFav = DATA(IssueFav.class).getUnique(
                    IssueCriteria.getUniqueByIssueAndUser(issueId, currentUser.getId()));

            if (issueFav == null)
                fav = false;
            else
                fav = true;
            for (IssueObserverDto observer : observers) {
                if (observer.getUser().getId() == currentUser.getId())
                    if (observer.isManager())
                        operatable = true;
            }

            Principal user = currentUser.unmarshal();
            boolean implies = user.implies(BEAN(Users.class).adminRole);
            if (implies)
                operatable = true;

            favNum = DATA(IssueFav.class).count(IssueCriteria.getObserverCount(issueId));
            issueDto.setContentEditable(false);
        }
        setOpenedObject(issueDto);
    }

    public void attention(boolean favFlag) {
        UserDto userDto = SessionUser.getInstance().getUser();
        IssueDto openedIssue = (IssueDto) getOpenedObject();
        if (!favFlag) {
            IssueFavDto favorite = new IssueFavDto().create();
            favorite.setUser(userDto);
            favorite.setIssue(openedIssue);
            IssueFav entity = favorite.unmarshal();
            DATA(IssueFav.class).save(entity);
            fav = true;
            favNum++;
        } else {
            IssueFav favorite = DATA(IssueFav.class).getUnique(
                    IssueCriteria.getUniqueByIssueAndUser(openedIssue.getId(), userDto.getId()));
            DATA(IssueFav.class).delete(favorite);
            fav = false;
            favNum--;
        }
        starToogle();
    }

    void starToogle() {
        RequestContext requestContext = RequestContext.getCurrentInstance();
        if (fav)
            requestContext.execute("$(\".favoritestar\").css('background-position', '0 -40px');");
        else
            requestContext.execute("$(\".favoritestar\").css('background-position', '0 0px');");
    }

    void generateAvailableObservers() {
        availableObservers = new ArrayList<IssueObserverDto>();
        IssueDto oo = (IssueDto) getOpenedObject();
        List<IssueObserverDto> observers = oo.getObservers();
        List<User> list = DATA(User.class).list();
        List<UserDto> dtoList = DTOs.marshalList(UserDto.class, list);
        for (UserDto user : dtoList) {
            IssueObserverDto observer = new IssueObserverDto().create();
            observer.setIssue(oo);
            observer.setUser(user);
            if (observers.contains(observer))
                observer.setSelected(true);
            availableObservers.add(observer);
        }
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
// issueDto = DTOs.marshal(IssueDto.class, IssueDto.REPLIES + IssueDto.OBSERVERS, entity);
// setOpenedObject(issueDto);
    }

    public void deselectObserver(IssueObserverDto observer) {
        IssueDto issueDto = (IssueDto) getOpenedObject();
        issueDto.getObservers().remove(observer);
    }

    public void deEditing(){
        IssueDto issueDto = (IssueDto) getOpenedObject();
        //TODO
    }

    public void testSetProterty() {
        if (openedObserver.getUser().getId() == null)
            uiLogger.warn("please select an observer");
        else {
            List<IssueObserverDto> list = new ArrayList<IssueObserverDto>();
            IssueDto openedObject = (IssueDto) getOpenedObject();
            List<IssueObserverDto> observers = openedObject.getObservers();

            observers.add(openedObserver);
            for (IssueObserverDto iod : availableObservers) {
                if (iod.getUser().getId() == openedObserver.getUser().getId())
                    iod.setSelected(true);
                list.add(iod);
            }

            availableObservers = list;
        }
    }

    public void startEditing() {
        IssueDto oo = (IssueDto) getOpenedObject();
        editing = true;
        oo.setContentEditable(true);
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

    public int getFavNum() {
        return favNum;
    }

    public void setFavNum(int favNum) {
        this.favNum = favNum;
    }

    public boolean isFav() {
        return fav;
    }

    public void setFav(boolean fav) {
        this.fav = fav;
    }

    public boolean isEditing() {
        return editing;
    }

    public void setEditing(boolean editing) {
        this.editing = editing;
    }

    public boolean isOperatable() {
        return operatable;
    }

    public void setOperatable(boolean operatable) {
        this.operatable = operatable;
    }

    public List<IssueObserverDto> getAvailableObservers() {
        return availableObservers;
    }

    public IssueObserverDto getOpenedObserver() {
        return openedObserver;
    }

    public void setOpenedObserver(IssueObserverDto openedObserver) {
        this.openedObserver = openedObserver;
    }

}
