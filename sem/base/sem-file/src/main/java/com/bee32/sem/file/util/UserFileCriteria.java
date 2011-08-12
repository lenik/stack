package com.bee32.sem.file.util;

import java.util.Collection;

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

}
