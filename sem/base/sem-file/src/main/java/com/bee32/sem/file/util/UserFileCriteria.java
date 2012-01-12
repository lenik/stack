package com.bee32.sem.file.util;

import java.util.Collection;

import com.bee32.plover.criteria.hibernate.CriteriaSpec;
import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.plover.criteria.hibernate.LeftHand;
import com.bee32.sem.file.entity.UserFile;

public class UserFileCriteria
        extends CriteriaSpec {

    @LeftHand(UserFile.class)
    public static ICriteriaElement namedLike(String pattern) {
        if (pattern == null)
            return null;
        pattern = pattern.trim();
        if (pattern.isEmpty())
            return null;
        return or(//
                likeIgnoreCase("name", "%" + pattern + "%"), //
                likeIgnoreCase("label", "%" + pattern + "%"));
    }

    @LeftHand(UserFile.class)
    public static ICriteriaElement withAnyTagIn(Collection<Long> tags) {
        if (tags == null)
            throw new NullPointerException("tags");
        if (tags.isEmpty())
            return null;
        return compose(alias("tags", "tag"), //
                in("tag.id", tags));
    }

    @LeftHand(UserFile.class)
    public static ICriteriaElement isAttachment(boolean isAttachment) {
        if (isAttachment)
            return isNotNull("refTypeId");
        else
            return isNull("refTypeId");
    }

}
