package com.bee32.sem.process.verify.impl;

import java.util.ArrayList;
import java.util.List;

import com.bee32.icsf.principal.IUserPrincipal;
import com.bee32.sem.process.verify.VerifyState;

public class PassLog
        extends VerifyState {

    private static final long serialVersionUID = 1L;

    private List<IUserPrincipal> users;

    public PassLog() {
        this.users = new ArrayList<IUserPrincipal>();
    }

    public PassLog(List<IUserPrincipal> persons) {
        if (persons == null)
            throw new NullPointerException("persons");
        this.users = persons;
    }

    public void passBy(IUserPrincipal person) {
        if (person == null)
            throw new NullPointerException("person");
        users.add(person);
    }

    public int size() {
        return users.size();
    }

    public void clear() {
        users.clear();
    }

    public IUserPrincipal get(int index) {
        return users.get(index);
    }

    @Override
    public String toString() {
        String list = null;
        for (IUserPrincipal user : users) {
            if (list == null)
                list = "";
            else
                list += ", ";
            list += user.getName();
        }
        return "Verified by " + list + " (" + users.size() + " persons)";
    }

}
