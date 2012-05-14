package com.bee32.sem.people.util;

import org.hibernate.criterion.MatchMode;

import com.bee32.plover.criteria.hibernate.CriteriaElement;
import com.bee32.plover.criteria.hibernate.CriteriaSpec;
import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.plover.criteria.hibernate.LeftHand;
import com.bee32.sem.people.entity.Party;

public class PeopleCriteria extends CriteriaSpec {

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

    @LeftHand(Party.class)
    public static CriteriaElement interestLike(String interest) {
        if (interest == null || interest.isEmpty())
            return null;
        else
            return likeIgnoreCase("interests", interest, MatchMode.ANYWHERE);
    }

    @LeftHand(Party.class)
    public static CriteriaElement isEmployee() {
        return equals("employee", true);
    }

    @LeftHand(Party.class)
    public static CriteriaElement isCustomer() {
        return equals("customer", true);
    }

    @LeftHand(Party.class)
    public static CriteriaElement isSupplier() {
        return equals("supplier", true);
    }

    @LeftHand(Party.class)
    public static ICriteriaElement withAnyNumberLike(String number){

        return compose(alias("contacts", "contact"),
                or(likeIgnoreCase("contact.mobile", number,MatchMode.ANYWHERE),
                        likeIgnoreCase("contact.tel", number,MatchMode.ANYWHERE)));
    }

    @LeftHand(Party.class)
    public static ICriteriaElement internal() {
        return equals("employee", true);
    }

    @LeftHand(Party.class)
    public static ICriteriaElement customers() {
        return equals("customer", true);
    }

    @LeftHand(Party.class)
    public static ICriteriaElement suppliers() {
        return equals("supplier", true);
    }

}
