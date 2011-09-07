package com.bee32.sem.file.util;

import java.util.Collection;

import org.zkoss.lang.Strings;

import com.bee32.plover.criteria.hibernate.CriteriaSpec;
import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.plover.criteria.hibernate.LeftHand;
import com.bee32.sem.file.entity.UserFile;

public class UserFileCriteria
        extends CriteriaSpec {

    @LeftHand(UserFile.class)
    public static ICriteriaElement withAnyTagIn(Collection<Long> tags) {
        if (tags == null)
            throw new NullPointerException("tags");
        if (tags.isEmpty())
            return null;
        return compose(alias("tags", "tag"), //
                in("tag.id", tags));
    }

    public static ICriteriaElement isAttachment(boolean isAttachment) {
        if (isAttachment)
            return isNotNull("refTypeId");
        else
            return isNull("refTypeId");
    }

    public static ICriteriaElement switchOwner(int ownerId) {
        if (ownerId == 0)
            return null;
        else
            return equals("owner.id", ownerId);
    }

    public static ICriteriaElement patternMatch(String pattern) {
        if (Strings.isEmpty(pattern))
            return null;
        else {
            return or(likeIgnoreCase("name", "%" + pattern + "%"), likeIgnoreCase("label", "%" + pattern + "%"));
        }
    }
}
