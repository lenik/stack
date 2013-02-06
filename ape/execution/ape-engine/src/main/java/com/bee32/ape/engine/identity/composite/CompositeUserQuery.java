package com.bee32.ape.engine.identity.composite;

import org.activiti.engine.identity.User;
import org.activiti.engine.identity.UserQuery;

public class CompositeUserQuery
        extends AbstractCompositeQuery<UserQuery, User>
        implements UserQuery {

    private static final long serialVersionUID = 1L;

    public CompositeUserQuery() {
        super();
    }

    public CompositeUserQuery(CompositeUserQuery o) {
        super(o);
    }

    @Override
    public CompositeUserQuery userId(String id) {
        for (UserQuery item : this)
            item.userId(id);
        return this;
    }

    @Override
    public CompositeUserQuery userFirstName(String firstName) {
        for (UserQuery item : this)
            item.userFirstName(firstName);
        return this;
    }

    @Override
    public CompositeUserQuery userFirstNameLike(String firstNameLike) {
        for (UserQuery item : this)
            item.userFirstNameLike(firstNameLike);
        return this;
    }

    @Override
    public CompositeUserQuery userLastName(String lastName) {
        for (UserQuery item : this)
            item.userLastName(lastName);
        return this;
    }

    @Override
    public CompositeUserQuery userLastNameLike(String lastNameLike) {
        for (UserQuery item : this)
            item.userLastNameLike(lastNameLike);
        return this;
    }

    @Override
    public CompositeUserQuery userEmail(String email) {
        for (UserQuery item : this)
            item.userEmail(email);
        return this;
    }

    @Override
    public CompositeUserQuery userEmailLike(String emailLike) {
        for (UserQuery item : this)
            item.userEmailLike(emailLike);
        return this;
    }

    @Override
    public CompositeUserQuery memberOfGroup(String groupId) {
        for (UserQuery item : this)
            item.memberOfGroup(groupId);
        return this;
    }

    @Override
    public CompositeUserQuery potentialStarter(String procDefId) {
        for (UserQuery item : this)
            item.potentialStarter(procDefId);
        return this;
    }

    @Override
    public CompositeUserQuery orderByUserId() {
        for (UserQuery item : this)
            item.orderByUserId();
        return this;
    }

    @Override
    public CompositeUserQuery orderByUserFirstName() {
        for (UserQuery item : this)
            item.orderByUserFirstName();
        return this;
    }

    @Override
    public CompositeUserQuery orderByUserLastName() {
        for (UserQuery item : this)
            item.orderByUserLastName();
        return this;
    }

    @Override
    public CompositeUserQuery orderByUserEmail() {
        for (UserQuery item : this)
            item.orderByUserEmail();
        return this;
    }

}
