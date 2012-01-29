package com.bee32.sem.people.util;

import org.hibernate.criterion.MatchMode;

import com.bee32.plover.criteria.hibernate.CriteriaElement;
import com.bee32.plover.criteria.hibernate.CriteriaSpec;
import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.plover.criteria.hibernate.LeftHand;
import com.bee32.sem.people.entity.OrgUnit;
import com.bee32.sem.people.entity.Party;
import com.bee32.sem.people.entity.PartyTagname;

public class PeopleCriteria
        extends CriteriaSpec {

    @LeftHand(Party.class)
    public static CriteriaElement namedLike(String keyword) {
        return namedLike(keyword, false);
    }

    @LeftHand(Party.class)
    public static CriteriaElement namedLike(String keyword, boolean ignoreCase) {
        if (keyword == null || keyword.isEmpty())
            return null;
        else if (ignoreCase)
            return or(//
                    likeIgnoreCase("label", keyword, MatchMode.ANYWHERE),//
                    likeIgnoreCase("fullName", keyword, MatchMode.ANYWHERE));
        else
            return or(//
                    like("label", keyword, MatchMode.ANYWHERE),//
                    like("fullName", keyword, MatchMode.ANYWHERE));
    }

    @LeftHand(PartyTagname.class)
    public static CriteriaElement externalTagname() {
        return not(in("id", new Object[] { PartyTagname.INTERNAL.getId() }));
    }

    @LeftHand(Party.class)
    public static ICriteriaElement internal() {
        return compose(alias("tags", "tag"), //
                in("tag.id", PartyTagname.INTERNAL.getId()));
    }

    @LeftHand(OrgUnit.class)
    public static ICriteriaElement internalOrgUnitWithName(String namePattern) {
        return compose(//
                alias("org", "org1"), //
                alias("org1.tags", "tag"), //
                like("name", namePattern, MatchMode.ANYWHERE), //
                in("tag.id", PartyTagname.INTERNAL.getId()));
    }

    @LeftHand(Party.class)
    public static ICriteriaElement customers() {
        return compose(alias("tags", "tag"), //
                in("tag.id", PartyTagname.CUSTOMER.getId()));
    }

    @LeftHand(Party.class)
    public static ICriteriaElement suppliers() {
        return compose(alias("tags", "tag"), //
                in("tag.id", PartyTagname.SUPPLIER.getId()));
    }

}
