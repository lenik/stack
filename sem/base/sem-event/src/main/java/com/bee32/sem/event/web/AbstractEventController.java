package com.bee32.sem.event.web;

import java.util.Date;
import java.util.List;

import javax.free.ParseException;
import javax.free.Strings;
import javax.inject.Inject;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.plover.orm.web.basic.BasicEntityController;
import com.bee32.plover.orm.web.util.DataTableDxo;
import com.bee32.plover.orm.web.util.SearchModel;
import com.bee32.plover.ox1.principal.UserDao;
import com.bee32.plover.ox1.principal.UserDto;
import com.bee32.plover.servlet.mvc.ActionRequest;
import com.bee32.plover.servlet.mvc.ActionResult;
import com.bee32.sem.event.dao.EventPriorityDao;
import com.bee32.sem.event.dao.EventStatusDao;
import com.bee32.sem.event.dto.AbstractEventDto;
import com.bee32.sem.event.dto.EventPriorityDto;
import com.bee32.sem.event.dto.EventStatusDto;
import com.bee32.sem.event.entity.Activity;
import com.bee32.sem.event.entity.Event;
import com.bee32.sem.event.entity.Task;
import com.bee32.sem.event.util.EventCriteria;

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
        String cat = event.getCategory().getLabel();
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
            model.add(EventCriteria.closed(closed));

        Integer recent = request.getNInt("recent");
        if (recent != null) {
            long recentSeconds = recent * 86400L;
            Date beginDate = new Date(System.currentTimeMillis() - recentSeconds);
            model.add(EventCriteria.beganFrom(beginDate));
        }

    }

    @Override
    protected void fillFormExtra(ActionRequest req, ActionResult result) {
        List<EventPriorityDto> priorityList = DTOs.marshalList(EventPriorityDto.class, priorityDao.list());
        List<EventStatusDto> statusList = DTOs.marshalList(EventStatusDto.class, statusDao.list());
        List<UserDto> userList = DTOs.marshalList(UserDto.class, 0, userDao.list());

        result.put("priorityList", priorityList);
        result.put("statusList", statusList);
        result.put("userList", userList);
    }

}
