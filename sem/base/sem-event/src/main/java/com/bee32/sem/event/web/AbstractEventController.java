package com.bee32.sem.event.web;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com.bee32.icsf.principal.dao.UserDao;
import com.bee32.icsf.principal.dto.UserDto;
import com.bee32.plover.orm.ext.util.BasicEntityController;
import com.bee32.plover.orm.ext.util.DataTableDxo;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.sem.event.dao.EventPriorityDao;
import com.bee32.sem.event.dao.EventStatusDao;
import com.bee32.sem.event.entity.Event;

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
        tab.push(event.getCategory());
        tab.push(event.getPriority());
        tab.push(event.getStatusText());
        tab.push(event.getActorName());
        tab.push(event.getSubject());
        tab.push(event.getBeginTime());
        // tab.push(event.getEndTime());

        String controlUrl = null;
        tab.push(controlUrl);
    }

    @Override
    protected void fillTemplate(Dto event) {
        event.setCategory("");
        event.setPriority(0);
        event.setState(0);
        event.setStatus(new EventStatusDto());
        event.setSubject("");
        event.setMessage("");
        event.setBeginTime(new Date());
        event.setActor(new UserDto());
    }

    protected void fillEntity(E entity, Dto dto) {
        dto.unmarshalTo(this, entity);
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
