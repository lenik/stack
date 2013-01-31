package com.bee32.ape.engine.identity;

import java.util.List;

import javax.free.NotImplementedException;

import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.activiti.engine.identity.UserQuery;
import org.activiti.engine.impl.Page;
import org.activiti.engine.impl.UserQueryImpl;
import org.activiti.engine.impl.persistence.entity.IdentityInfoEntity;
import org.activiti.engine.impl.persistence.entity.UserEntity;
import org.activiti.engine.impl.persistence.entity.UserEntityManager;
import org.springframework.dao.DataAccessException;

import com.bee32.ape.engine.base.IApeActivitiAdapter;
import com.bee32.icsf.login.UserPassword;
import com.bee32.plover.criteria.hibernate.Equals;

public class ApeUserEntityManager
        extends UserEntityManager
        implements IApeActivitiAdapter {

    /**
     * @throws DataAccessException
     */
    @Override
    public User createNewUser(String userId) {
        com.bee32.icsf.principal.User _user = new com.bee32.icsf.principal.User();
        _user.setName(userId);
        ctx.data.access(icsfUserType).save(_user);

        return ActivitiIdentityAdapters.icsfUser2activitiUser(_user);
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

    /**
     * @throws DataAccessException
     */
    @Override
    public List<Group> findGroupsByUser(String userId) {
        ctx.data.access(icsfGroupType).findAndDelete(//
                );
        return super.findGroupsByUser(userId);
    }

    @Override
    public UserQuery createNewUserQuery() {
        return new ApeUserQuery();
    }

    @Override
    public Boolean checkPassword(String userId, String password) {
        String base64 = UserPassword.encrypt(password);
        com.bee32.icsf.principal.User icsfUser = ctx.data.access(icsfUserType).getByName(userId);
        Integer icsfUserId = icsfUser.getId();
        int matches = ctx.data.access(UserPassword.class).count(//
                new Equals("user.id", icsfUserId), //
                new Equals("_passwd", base64));
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
        com.bee32.icsf.principal.User icsfUser = ctx.data.access(icsfUserType).getByName(userId);
        return ActivitiIdentityAdapters.icsfUser2activitiUser(icsfUser);
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
