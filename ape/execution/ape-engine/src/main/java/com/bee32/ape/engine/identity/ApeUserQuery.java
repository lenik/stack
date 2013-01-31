package com.bee32.ape.engine.identity;

import java.util.HashSet;
import java.util.Set;

import org.activiti.engine.identity.User;
import org.activiti.engine.identity.UserQuery;
import org.activiti.engine.impl.UserQueryImpl;
import org.hibernate.criterion.MatchMode;

import com.bee32.ape.engine.base.AbstractApeQuery;
import com.bee32.icsf.principal.Group;
import com.bee32.icsf.principal.Principal;
import com.bee32.plover.criteria.hibernate.Alias;
import com.bee32.plover.criteria.hibernate.CriteriaComposite;
import com.bee32.plover.criteria.hibernate.Equals;
import com.bee32.plover.criteria.hibernate.InCollection;
import com.bee32.plover.criteria.hibernate.Like;
import com.bee32.plover.orm.entity.Entity;

public class ApeUserQuery
        extends AbstractApeQuery<UserQuery, User>
        implements UserQuery {

    String id;
    String firstName;
    String firstNameLike;
    String lastName;
    String lastNameLike;
    String email;
    String emailLike;
    String groupId;
    String procDefId;

    public ApeUserQuery() {
        super(icsfUserType);
    }

    public ApeUserQuery(UserQueryImpl o) {
        super(icsfUserType, o);
        id = o.getId();
        firstName = o.getFirstName();
        firstNameLike = o.getFirstNameLike();
        lastName = o.getLastName();
        lastNameLike = o.getLastNameLike();
        email = o.getEmail();
        emailLike = o.getEmailLike();
        groupId = o.getGroupId();
        // procDefId = o.procDefId;
    }

    @Override
    public UserQuery userId(String id) {
        this.id = id;
        return this;
    }

    @Override
    public UserQuery userFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    @Override
    public UserQuery userFirstNameLike(String firstNameLike) {
        this.firstNameLike = firstNameLike;
        return this;
    }

    @Override
    public UserQuery userLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    @Override
    public UserQuery userLastNameLike(String lastNameLike) {
        this.lastNameLike = lastNameLike;
        return this;
    }

    @Override
    public UserQuery userEmail(String email) {
        this.email = email;
        return this;
    }

    @Override
    public UserQuery userEmailLike(String emailLike) {
        this.emailLike = emailLike;
        return this;
    }

    @Override
    public UserQuery memberOfGroup(String groupId) {
        this.groupId = groupId;
        return this;
    }

    @Override
    public UserQuery potentialStarter(String procDefId) {
        this.procDefId = procDefId;
        return this;
    }

    @Override
    public UserQuery orderByUserId() {
        setOrderProperty("name");
        return this;
    }

    @Override
    public UserQuery orderByUserFirstName() {
        setOrderProperty("label");
        return this;
    }

    @Override
    public UserQuery orderByUserLastName() {
        // Not supported.
        // setOrderProperty("after(label, /\\s+/)");
        return this;
    }

    @Override
    public UserQuery orderByUserEmail() {
        // Not supported.
        // setOrderProperty("emails[0]");
        return this;
    }

    @Override
    protected CriteriaComposite compose() {
        CriteriaComposite composite = new CriteriaComposite();
        if (id != null)
            composite.add(new Equals("name", id));
        if (firstName != null && lastName != null) {
            String fullName = (firstName + " " + lastName).trim();
            composite.add(new Equals("fullName", fullName));
        }
        if (firstNameLike != null)
            composite.add(new Like(true, "fullName", firstNameLike, MatchMode.ANYWHERE));
        if (lastNameLike != null)
            composite.add(new Like(true, "fullName", lastNameLike, MatchMode.ANYWHERE));
        // if (email != null)
        // composite.add(new Equals("email", email));
        // if (emailLike != null)
        // composite.add(new Like(true, "email", emailLike, MatchMode.ANYWHERE));

        if (groupId != null) {
            Set<Principal> imset = new HashSet<Principal>();

            String icsfGroupName = groupId + GROUP_EXT;
            Group group = ctx.data.access(Group.class).getByName(icsfGroupName);

            if (group != null)
                imset.addAll(group.getImSet());

            // Role role = ctx.data.access(Role.class).getByName(groupId);
            // if (role != null)
            // imset.addAll(role.getImSet());

            Set<Integer> imIdSet = new HashSet<Integer>();
            for (Principal im : imset)
                imIdSet.add(im.getId());

            // Maybe return user duplicates for each matching group.
            composite.add(new Alias("assignedGroups", "g"));
            composite.add(new InCollection("g.id", imIdSet));
        }

        if (procDefId != null) {
            // Not supported.
        }
        return composite;
    }

    @Override
    protected User icsf2activiti(Entity<?> icsfEntity) {
        com.bee32.icsf.principal.User icsfUser = (com.bee32.icsf.principal.User) icsfEntity;
        return ActivitiIdentityAdapters.icsfUser2activitiUser(icsfUser);
    }

}
