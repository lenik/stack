package com.bee32.sem.people.web;

import java.util.List;

import javax.annotation.PostConstruct;

import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bee32.icsf.principal.User;
import com.bee32.icsf.principal.dto.UserDto;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.plover.orm.util.EntityViewBean;

@Component
@Scope("view")
public class PermissionAdminBean extends EntityViewBean {

    private static final long serialVersionUID = 1L;

    private UserDto selectedUser;


    @PostConstruct
    public void init() {

    }

    public UserDto getSelectedUser() {
        return selectedUser;
    }

    public void setSelectedUser(UserDto selectedUser) {
        this.selectedUser = selectedUser;
    }

    public List<UserDto> getUsers() {
        List<User> users = getDataManager().loadAll(User.class);
        List<UserDto> userDtos = DTOs.marshalList(UserDto.class, users);

        return userDtos;
    }




    public void onRowSelectUser(SelectEvent event) {
    }

    public void onRowUnselectUser(UnselectEvent event) {
    }

}
