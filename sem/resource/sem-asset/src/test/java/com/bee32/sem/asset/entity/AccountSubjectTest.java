package com.bee32.sem.asset.entity;

import java.util.Collection;

import org.junit.Assert;
import org.junit.Test;

import com.bee32.plover.ox1.dict.DictEntity;
import com.bee32.plover.ox1.dict.DictUtil;

public class AccountSubjectTest
        extends Assert {

    @Test
    public void testEnumPredefined() {
        Collection<? extends DictEntity<?>> subjects = DictUtil.getPredefinedInstances(AccountSubject.class);
        subjects.contains(AccountSubject.s1011);
    }

}
