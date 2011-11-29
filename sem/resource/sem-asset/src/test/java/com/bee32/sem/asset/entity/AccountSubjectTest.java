package com.bee32.sem.asset.entity;

import java.util.Collection;

import org.junit.Assert;

import com.bee32.plover.ox1.dict.CodeTreeBuilder;
import com.bee32.plover.ox1.dict.DictUtil;

public class AccountSubjectTest
        extends Assert {

    public static void main(String[] args) {
        Collection<? extends AccountSubject> subjects = DictUtil.getPredefinedInstances(AccountSubject.class);
        subjects.contains(AccountSubject.s1011);

        CodeTreeBuilder ctb = new CodeTreeBuilder();
        ctb.collect(subjects);
        ctb.reduce();

        System.out.println(ctb.dump());
    }

}
