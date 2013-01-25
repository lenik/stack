package com.bee32.sem.process.ape;

import java.util.List;

import javax.free.NotImplementedException;

import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.activiti.engine.identity.UserQuery;
import org.activiti.engine.impl.Page;
import org.activiti.engine.impl.UserQueryImpl;
import org.activiti.engine.impl.persistence.entity.IdentityInfoEntity;
import org.activiti.engine.impl.persistence.entity.UserEntity;
import org.activiti.engine.impl.persistence.entity.UserManager;
import org.springframework.dao.DataAccessException;

import com.bee32.icsf.login.UserPassword;
import com.bee32.plover.criteria.hibernate.Equals;

public class ApeUserManager
        extends UserManager
        implements IApeActivitiAdapter {

    /**
     * @throws DataAccessException
     */
    @Override
    public User createNewUser(String userId) {
        com.bee32.icsf.principal.User _user = new com.bee32.icsf.principal.User();
        _user.setName(userId);
        ctx.data.access(semUserType).save(_user);

        return ActivitiAdapters.semUser2activitiUser(_user);
    }

    /**
     * @throws DataAccessException
     */
    @Override
    public void insertUser(User user) {
        if (user == null)
            throw new NullPointerException("user");

        com.bee32.icsf.principal.User _user = new com.bee32.icsf.principal.User();
        _user.setName(user.getId());
        _user.setFullName(user.getFirstName(), user.getLastName());
        _user.setPreferredEmail(user.getEmail());
        // _user.addPassword(user.getPassword());

        ctx.data.access(semUserType).save(_user);
    }

    /**
     * @throws DataAccessException
     */
    @Override
    public void updateUser(UserEntity updatedUser) {
        String name = updatedUser.getId();
        com.bee32.icsf.principal.User _user = ctx.data.access(semUserType).getByName(name);
        if (_user == null)
            // _user = new com.bee32.icsf.principal.User();
            throw new IllegalStateException("User isn't existed: " + updatedUser.getId());

        _user.setName(updatedUser.getId());
        _user.setFullName(updatedUser.getFirstName(), updatedUser.getLastName());
        _user.setPreferredEmail(updatedUser.getEmail());
        // updatedUser.getPassword();
        // updatedUser.getPicture();
        ctx.data.access(semUserType).update(_user);
    }

    /**
     * @throws DataAccessException
     */
    @Override
    public void deleteUser(String userId) {
        ctx.data.access(semUserType).findAndDelete(//
                new Equals("name", userId));
    }

    /**
     * @throws DataAccessException
     */
    @Override
    public List<Group> findGroupsByUser(String userId) {
        ctx.data.access(semGroupType).findAndDelete(//
                );
        return super.findGroupsByUser(userId);
    }

    @Override
    public UserQuery createNewUserQuery() {
        return new ApeUserQuery();
    }

    @Override
    public Boolean checkPassword(String userId, String password) {
        com.bee32.icsf.principal.User semUser = ctx.data.access(semUserType).getByName(userId);
        Integer semUserId = semUser.getId();
        int matches = ctx.data.access(UserPassword.class).count(//
                new Equals("user.id", semUserId), //
                new Equals("passwd", password));
        return matches != 0;
    }

    @Override
    public List<User> findPotentialStarterUsers(String proceDefId) {
        ApeUserQuery query = new ApeUserQuery();
        query.potentialStarter(proceDefId);
        return query.list();
    }

    @Override
    public UserEntity findUserById(String userId) {
        com.bee32.icsf.principal.User semUser = ctx.data.access(semUserType).getByName(userId);
        return ActivitiAdapters.semUser2activitiUserEntity(semUser);
    }

    @Override
    public List<User> findUserByQueryCriteria(UserQueryImpl query, Page page) {
        return query.listPage(page.getFirstResult(), page.getMaxResults());
    }

    @Override
    public long findUserCountByQueryCriteria(UserQueryImpl query) {
        return query.count();
    }

    @Override
    public IdentityInfoEntity findUserInfoByUserIdAndKey(String userId, String key) {
        // TODO
        throw new NotImplementedException();
    }

    @Override
    public List<String> findUserInfoKeysByUserIdAndType(String userId, String type) {
        // TODO
        throw new NotImplementedException();
    }

}
