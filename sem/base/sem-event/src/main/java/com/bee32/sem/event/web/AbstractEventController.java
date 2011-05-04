package com.bee32.sem.event.web;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.free.ParseException;
import javax.free.Strings;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.criterion.Restrictions;
import org.springframework.web.servlet.ModelAndView;

import com.bee32.icsf.principal.dao.UserDao;
import com.bee32.icsf.principal.dto.UserDto;
import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.orm.ext.util.BasicEntityController;
import com.bee32.plover.orm.ext.util.DataTableDxo;
import com.bee32.plover.orm.ext.util.SearchModel;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.sem.event.dao.EventPriorityDao;
import com.bee32.sem.event.dao.EventStatusDao;
import com.bee32.sem.event.dto.AbstractEventDto;
import com.bee32.sem.event.dto.EventPriorityDto;
import com.bee32.sem.event.dto.EventStatusDto;
import com.bee32.sem.event.entity.Activity;
import com.bee32.sem.event.entity.Event;
import com.bee32.sem.event.entity.Task;

public abstract class AbstractEventController<E extends Event, Dto extends AbstractEventDto<E>>
        extends BasicEntityController<E, Long, Dto> {

    @Inject
    UserDao userDao;

    @Inject
    EventStatusDao statusDao;

    @Inject
    EventPriorityDao priorityDao;

    @Override
    protected void fillDataRow(DataTableDxo tab, Dto event) {
        String category = event.getSourceName();
        String cat = event.getCategory().getAlias();
        if (category == null)
            category = "";
        if (cat != null)
            category += "[" + cat + "]";
        tab.push(category);

        tab.push(event.getPriority());
        tab.push(event.getStatusText());
        tab.push(event.getActorName());
        tab.push(event.getSubject());
        tab.push(event.getBeginTime());
        // tab.push(event.getEndTime());

        String seeAlsos = event.getSeeAlsos();
        if (seeAlsos != null)
            seeAlsos = Strings.ellipse(seeAlsos, 10);
        tab.push(seeAlsos);
    }

    @Override
    protected void fillSearchModel(SearchModel model, TextMap request)
            throws ParseException {

        String stereo = request.getString("stereo");
        if (stereo != null) {
            Class<? extends Event> eventClass = null;
            if ("EVT".equals(stereo))
                eventClass = Event.class;
            else if ("TSK".equals(stereo))
                eventClass = Task.class;
            else if ("ACT".equals(stereo))
                eventClass = Activity.class;

            if (eventClass != null)
                model.setEntityClass(eventClass);
        }

        Boolean closed = request.getNBoolean("closed");
        if (closed != null)
            model.add(Restrictions.eq("closed", closed));

        Integer recent = request.getNInt("recent");
        if (recent != null) {
            long recentSeconds = recent * 86400L;
            Date beginDate = new Date(System.currentTimeMillis() - recentSeconds);
            model.add(Restrictions.gt("beginTime", beginDate));
        }

    }

    @Override
    protected ModelAndView _createOrEditForm(ViewData view, HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        super._createOrEditForm(view, req, resp);

        List<EventPriorityDto> priorityList = DTOs.marshalList(EventPriorityDto.class, priorityDao.list());
        List<EventStatusDto> statusList = DTOs.marshalList(EventStatusDto.class, statusDao.list());
        List<UserDto> userList = DTOs.marshalList(UserDto.class, 0, userDao.list());

        view.put("priorityList", priorityList);
        view.put("statusList", statusList);
        view.put("userList", userList);

        return view;
    }

}
