package com.bee32.ape.engine.identity;

import org.activiti.engine.identity.Group;
import org.activiti.engine.impl.GroupQueryImpl;
import org.activiti.engine.impl.GroupQueryProperty;
import org.activiti.engine.impl.persistence.entity.GroupEntity;

import com.bee32.ape.engine.base.IAppCtxAccess.ctx;
import com.bee32.icsf.principal.User;
import com.bee32.plover.criteria.hibernate.Alias;
import com.bee32.plover.criteria.hibernate.CriteriaComposite;
import com.bee32.plover.criteria.hibernate.Equals;
import com.bee32.plover.criteria.hibernate.Like;
import com.bee32.plover.criteria.hibernate.Order;

public class GroupEntityUtils
        extends ActivitiEntityUtils {

    public static void populate(GroupEntity dst, GroupEntity src) {
        dst.setId(src.getId());
        dst.setRevision(src.getRevision());
        dst.setName(src.getName());
        dst.setType(src.getType());
    }

    /**
     * Match the group info.
     *
     * The member users and process info isn't matched.
     */
    public static boolean match(Group group, GroupQueryImpl query) {
        if (notMatch(query.getId(), group.getId()))
            return false;
        if (notMatch(query.getName(), group.getName()))
            return false;
        if (notLike(query.getNameLike(), group.getName()))
            return false;
        if (notMatch(query.getType(), group.getType()))
            return false;
        return true;
    }

    public static GroupQueryProperty getOrderProperty(String orderBy) {
        if (orderBy == null)
            return null;
        String column = ActivitiOrderUtils.getOrderColumn(orderBy);

        if (GroupQueryProperty.GROUP_ID.getName().equals(column))
            return GroupQueryProperty.GROUP_ID;

        if (GroupQueryProperty.NAME.getName().equals(column))
            return GroupQueryProperty.NAME;

        if (GroupQueryProperty.TYPE.getName().equals(column))
            return GroupQueryProperty.TYPE;

        return null;
    }

    public static CriteriaComposite compose(GroupQueryImpl query) {
        CriteriaComposite composite = new CriteriaComposite();

        String id = query.getId();
        String name = query.getName();
        String nameLike = query.getNameLike();
        String type = query.getType();

        if (id != null)
            composite.add(new Equals("name", id + ROLE_EXT));

        if (name != null)
            composite.add(new Equals("label", name));
        if (nameLike != null)
            composite.add(new Like(true, "label", name));

        if (type != null)
            ;

        if (memberUserId != null) {
            User user = ctx.data.access(icsfUserType).getByName(memberUserId);
            int userId = user == null ? -1 : user.getId();

            // composite.add(new Alias("memberUsers", "u"));
            composite.add(new Alias("responsibleUsers", "u"));
            composite.add(new Equals("u.id", userId));
        }

        String orderProperty = getOrderProperty();
        if (orderProperty != null) {
            Order order;
            if (isReverseOrder())
                order = Order.desc(orderProperty);
            else
                order = Order.asc(orderProperty);
            composite.add(order);
        }

        return composite;
    }

}
