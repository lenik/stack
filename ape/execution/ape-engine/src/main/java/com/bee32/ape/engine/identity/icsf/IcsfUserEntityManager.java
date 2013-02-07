package com.bee32.ape.engine.identity.icsf;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.activiti.engine.identity.Group;
import org.activiti.engine.impl.Page;
import org.activiti.engine.impl.UserQueryImpl;
import org.activiti.engine.impl.UserQueryProperty;
import org.activiti.engine.impl.persistence.entity.IdentityInfoEntity;
import org.activiti.engine.impl.persistence.entity.UserEntity;
import org.activiti.engine.impl.persistence.entity.UserEntityManager;
import org.hibernate.criterion.MatchMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;

import com.bee32.ape.engine.base.IApeActivitiAdapter;
import com.bee32.ape.engine.identity.ActivitiOrderUtils;
import com.bee32.ape.engine.identity.UserEntityUtils;
import com.bee32.icsf.login.UserPassword;
import com.bee32.icsf.principal.Principal;
import com.bee32.plover.criteria.hibernate.CriteriaComposite;
import com.bee32.plover.criteria.hibernate.Equals;
import com.bee32.plover.criteria.hibernate.InCollection;
import com.bee32.plover.criteria.hibernate.Like;
import com.bee32.plover.criteria.hibernate.Limit;
import com.bee32.plover.criteria.hibernate.Order;

public class IcsfUserEntityManager
        extends UserEntityManager
        implements IApeActivitiAdapter {

    static final Logger logger = LoggerFactory.getLogger(IcsfUserEntityManager.class);

    IIcsfTypeMapping icsfTypeMapping;

    public IcsfUserEntityManager(IIcsfTypeMapping icsfTypeMapping) {
        if (icsfTypeMapping == null)
            throw new NullPointerException("icsfTypeMapping");
        this.icsfTypeMapping = icsfTypeMapping;
    }

    @Override
    public UserEntity createNewUser(String userId) {
        return new UserEntity(userId);
    }

    /**
     * @throws DataAccessException
     */
    @Override
    public void insertUser(org.activiti.engine.identity.User user) {
        if (user == null)
            throw new NullPointerException("user");

        String icsfName = user.getId();

        Principal _principal = ctx.data.access(Principal.class).getByName(icsfName);
        if (_principal != null) {
            if (!(_principal instanceof com.bee32.icsf.principal.User))
                throw new IllegalStateException("Incompatible principal type.");
            else
                logger.error("User is already existed: " + icsfName);
            return;
        }

        com.bee32.icsf.principal.User _user = new com.bee32.icsf.principal.User();
        _user.setName(icsfName);
        _user.setFullName(user.getFirstName(), user.getLastName());
        _user.setPreferredEmail(user.getEmail());
        // _user.addPassword(user.getPassword());

        ctx.data.access(icsfUserType).save(_user);
    }

    /**
     * @throws DataAccessException
     */
    @Override
    public void updateUser(UserEntity updatedUser) {
        String name = updatedUser.getId();
        com.bee32.icsf.principal.User _user = ctx.data.access(icsfUserType).getByName(name);
        if (_user == null)
            // _user = new com.bee32.icsf.principal.User();
            throw new IllegalStateException("User isn't existed: " + updatedUser.getId());

        _user.setName(updatedUser.getId());
        _user.setFullName(updatedUser.getFirstName(), updatedUser.getLastName());
        _user.setPreferredEmail(updatedUser.getEmail());
        // updatedUser.getPassword();
        // updatedUser.getPicture();
        ctx.data.access(icsfUserType).update(_user);
    }

    /**
     * @throws DataAccessException
     */
    @Override
    public void deleteUser(String userId) {
        ctx.data.access(icsfUserType).findAndDelete(//
                new Equals("name", userId));
    }

    @Override
    public Boolean checkPassword(String userId, String password) {
        String base64 = UserPassword.encrypt(password);
        com.bee32.icsf.principal.User icsfUser = ctx.data.access(icsfUserType).getByName(userId);
        if (icsfUser == null)
            return null;

        Integer icsfUserId = icsfUser.getId();
        int matches = ctx.data.access(UserPassword.class).count(//
                new Equals("user.id", icsfUserId), //
                new Equals("_passwd", base64));
        return matches != 0;
    }

    @Override
    public long findUserCountByQueryCriteria(UserQueryImpl query) {
        CriteriaComposite criteria = compose(query);
        if (criteria == null)
            return 0L;

        return ctx.data.access(icsfUserType).count(criteria);
    }

    @Override
    public List<org.activiti.engine.identity.User> findUserByQueryCriteria(UserQueryImpl query, Page page) {
        CriteriaComposite criteria = compose(query);
        if (criteria == null)
            return Collections.emptyList();

        if (page != null) {
            Limit limit = new Limit(page.getFirstResult(), page.getMaxResults());
            criteria.add(limit);
        }
        List<com.bee32.icsf.principal.User> icsfUsers = ctx.data.access(icsfUserType).list(criteria);
        return icsfTypeMapping.convertUserList(icsfUsers);
    }

    @Override
    public UserEntity findUserById(String userId) {
        com.bee32.icsf.principal.User icsfUser = ctx.data.access(icsfUserType).getByName(userId);
        return icsfTypeMapping.convertUser(icsfUser);
    }

    /**
     * @throws DataAccessException
     */
    @Override
    public List<Group> findGroupsByUser(String userId) {
        List<Principal> icsfGroups = ctx.data.access(icsfTypeMapping.getIcsfGroupType()).list(//
                icsfTypeMapping.getMemberUsersAlias("m"), //
                new Equals("m.name", userId));
        return icsfTypeMapping.convertGroupList(icsfGroups);
    }

    @Override
    public IdentityInfoEntity findUserInfoByUserIdAndKey(String userId, String key) {
        return null;
    }

    @Override
    public List<String> findUserInfoKeysByUserIdAndType(String userId, String type) {
        List<String> keys = new ArrayList<>();
        return keys;
    }

    @Override
    public List<org.activiti.engine.identity.User> findPotentialStarterUsers(String proceDefId) {
        List<org.activiti.engine.identity.User> users = new ArrayList<>();
        return users;
    }

    public CriteriaComposite compose(UserQueryImpl query) {
        CriteriaComposite composite = new CriteriaComposite();

        String id = query.getId();
        String firstName = query.getFirstName();
        String lastName = query.getLastName();
        String firstNameLike = query.getFirstNameLike();
        String lastNameLike = query.getLastNameLike();
        String email = query.getEmail();
        String emailLike = query.getEmailLike();

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

        if (email != null) {
            // composite.add(new Equals("email", email));
        }
        if (emailLike != null) {
            // composite.add(new Like(true, "email", emailLike, MatchMode.ANYWHERE));
        }

        String groupId = query.getGroupId();
        if (groupId != null) {
            Set<Principal> imset = new HashSet<Principal>();

            String icsfRoleName = icsfTypeMapping.toIcsfGroupName(groupId);
            Principal _group = ctx.data.access(icsfTypeMapping.getIcsfGroupType()).getByName(icsfRoleName);

            if (_group != null)
                imset.addAll(_group.getImSet());

            // Role role = ctx.data.access(Role.class).getByName(groupId);
            // if (role != null)
            // imset.addAll(role.getImSet());

            Set<Integer> imIdSet = new HashSet<Integer>();
            for (Principal im : imset)
                imIdSet.add(im.getId());

            if (imIdSet.isEmpty()) {
                return null;
            } else {
                // Maybe return user duplicates for each matching group.
                // composite.add(new Alias("assignedGroups", "g"));
                // composite.add(new InCollection("g.id", imIdSet));
                composite.add(icsfTypeMapping.getUserGroupsAlias("g"));
                composite.add(new InCollection("g.id", imIdSet));
            }
        }

        // if (procDefId != null) ... // Not supported.

        String orderBy = query.getOrderBy();
        if (orderBy != null) {
            String propertyName = null;

            UserQueryProperty queryProperty = UserEntityUtils.getOrderProperty(orderBy);
            if (queryProperty == UserQueryProperty.USER_ID)
                propertyName = "name";
            else if (queryProperty == UserQueryProperty.FIRST_NAME)
                propertyName = "label";
            else if (queryProperty == UserQueryProperty.LAST_NAME)
                ;
            else if (queryProperty == UserQueryProperty.EMAIL)
                ;

            if (propertyName != null)
                if (ActivitiOrderUtils.isAscending(orderBy))
                    composite.add(Order.asc(propertyName));
                else
                    composite.add(Order.desc(propertyName));
        }

        return composite;
    }

}
