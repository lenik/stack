package com.bee32.sem.asset.entity;

import java.util.Collection;

import org.junit.Assert;

import com.bee32.plover.ox1.dict.CodeTreeBuilder;
import com.bee32.plover.ox1.dict.DictUtil;

public class AccountSubjectTest
        extends Assert {

    public static void main(String[] args) {
        AccountSubjects samples = new AccountSubjects();

        Collection<? extends AccountSubject> subjects = DictUtil.getPredefinedInstances(AccountSubject.class);
        subjects.contains(samples.s1002);

        CodeTreeBuilder ctb = new CodeTreeBuilder();
        ctb.learn(subjects);
        ctb.reduce();

        System.out.println(ctb.dump());
    }

}
