package com.bee32.ape.engine.identity;

import java.util.HashSet;
import java.util.Set;

import org.activiti.engine.identity.User;
import org.activiti.engine.impl.UserQueryImpl;
import org.activiti.engine.impl.UserQueryProperty;
import org.activiti.engine.impl.persistence.entity.UserEntity;
import org.hibernate.criterion.MatchMode;

import com.bee32.ape.engine.base.IAppCtxAccess.ctx;
import com.bee32.icsf.principal.Principal;
import com.bee32.icsf.principal.Role;
import com.bee32.plover.criteria.hibernate.Alias;
import com.bee32.plover.criteria.hibernate.CriteriaComposite;
import com.bee32.plover.criteria.hibernate.Equals;
import com.bee32.plover.criteria.hibernate.InCollection;
import com.bee32.plover.criteria.hibernate.Like;

public class UserEntityUtils
        extends ActivitiEntityUtils {

    public static void populate(UserEntity dst, UserEntity src) {
        dst.setId(src.getId());
        dst.setRevision(src.getRevision());

        dst.setFirstName(src.getFirstName());
        dst.setLastName(src.getLastName());
        dst.setPassword(src.getPassword());
        dst.setEmail(src.getEmail());

        dst.setPicture(src.getPicture());
    }

    /**
     * Match the user part.
     *
     * groupId and proceDefId is not included.
     */
    public static boolean match(User user, UserQueryImpl query) {
        if (notMatch(query.getId(), user.getId()))
            return false;

        if (notMatch(query.getFirstName(), user.getFirstName()))
            return false;
        if (notLike(query.getFirstNameLike(), user.getFirstName()))
            return false;
        if (notMatch(query.getLastName(), user.getLastName()))
            return false;
        if (notLike(query.getLastNameLike(), user.getLastName()))
            return false;

        if (notMatch(query.getEmail(), user.getEmail()))
            return false;
        if (notLike(query.getEmailLike(), user.getEmail()))
            return false;

        return true;
    }

    public static UserQueryProperty getOrderProperty(String orderBy) {
        if (orderBy == null)
            return null;
        String column = ActivitiOrderUtils.getOrderColumn(orderBy);

        if (UserQueryProperty.USER_ID.getName().equals(column))
            return UserQueryProperty.USER_ID;

        if (UserQueryProperty.FIRST_NAME.getName().equals(column))
            return UserQueryProperty.FIRST_NAME;

        if (UserQueryProperty.LAST_NAME.getName().equals(column))
            return UserQueryProperty.LAST_NAME;

        if (UserQueryProperty.EMAIL.getName().equals(column))
            return UserQueryProperty.EMAIL;

        return null;
    }

    public static CriteriaComposite compose(UserQueryImpl query) {
        CriteriaComposite composite = new CriteriaComposite();

        String id = query.getId();
        String firstName = query.getFirstName();
        String lastName = query.getLastName();
        String firstNameLike = query.getFirstNameLike();
        String lastNameLike = query.getLastNameLike();
        String email = query.getEmail();

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

            String icsfRoleName = groupId + ROLE_EXT;
            Role group = ctx.data.access(icsfRoleType).getByName(icsfRoleName);

            if (group != null)
                imset.addAll(group.getImSet());

            // Role role = ctx.data.access(Role.class).getByName(groupId);
            // if (role != null)
            // imset.addAll(role.getImSet());

            Set<Integer> imIdSet = new HashSet<Integer>();
            for (Principal im : imset)
                imIdSet.add(im.getId());

            // Maybe return user duplicates for each matching group.
            // composite.add(new Alias("assignedGroups", "g"));
            // composite.add(new InCollection("g.id", imIdSet));
            composite.add(new Alias("assignedRoles", "r"));
            composite.add(new InCollection("r.id", imIdSet));
        }

        // if (procDefId != null) ... // Not supported.
        return composite;
    }

}
