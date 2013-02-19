package com.bee32.ape.engine.identity.icsf;

import java.util.List;

import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.activiti.engine.impl.persistence.entity.GroupEntity;
import org.activiti.engine.impl.persistence.entity.UserEntity;

import com.bee32.icsf.principal.Principal;
import com.bee32.plover.criteria.hibernate.ICriteriaElement;

public interface IIcsfTypeMapping {

    Class<? extends Principal> getIcsfGroupType();

    Principal newIcsfGroup();

    String toIcsfGroupName(String plainGroupName);

    String toPlainGroupName(String icsfGroupName);

    ICriteriaElement getMemberUsersAlias(String alias);

    ICriteriaElement getUserGroupsAlias(String alias);

    UserEntity convertUser(com.bee32.icsf.principal.User icsfUser);

    List<User> convertUserList(List<com.bee32.icsf.principal.User> icsfUsers);

    GroupEntity convertGroup(Principal icsfGroup);

    List<Group> convertGroupList(List<? extends Principal> icsfGroups);

}
