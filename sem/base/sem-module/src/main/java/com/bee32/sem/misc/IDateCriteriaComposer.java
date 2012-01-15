package com.bee32.sem.misc;

import java.util.Date;

import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.plover.ox1.util.CommonCriteria;

public interface IDateCriteriaComposer {

    ICriteriaElement composeDateCriteria(Date begin, Date end);

    IDateCriteriaComposer lastModified = new IDateCriteriaComposer() {
        @Override
        public ICriteriaElement composeDateCriteria(Date begin, Date end) {
            return CommonCriteria.lastModifiedBetween(begin, end);
        }
    };

    IDateCriteriaComposer createdDate = new IDateCriteriaComposer() {
        @Override
        public ICriteriaElement composeDateCriteria(Date begin, Date end) {
            return CommonCriteria.createdBetween(begin, end);
        }
    };

    IDateCriteriaComposer beginEndDate = new IDateCriteriaComposer() {
        @Override
        public ICriteriaElement composeDateCriteria(Date begin, Date end) {
            return CommonCriteria.beginEndDateBetween(begin, end);
        }
    };

}