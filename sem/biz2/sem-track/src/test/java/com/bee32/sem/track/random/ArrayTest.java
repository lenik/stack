package com.bee32.sem.track.random;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.ManyToOne;
import javax.xml.crypto.Data;

public class ArrayTest {

    public static final NumberFormat fmt = new DecimalFormat("000000");

    public static void main(String[] args) {

        List<Member> accounts = new ArrayList<Member>();
        // TODO fetch your data from database

        for (int i = 0; i < 10; i++) {
            int size = accounts.size() + 1;
            Member member = new Member();
            member.setMemberId("" + 2012 + fmt.format(size));
            accounts.add(member);
        }
    }

    public class UserVo {
        String id;
        @ManyToOne
        UserVo cre;
        @ManyToOne
        UserVo mod;
        Date createdDate;
        Data modifiedDate;
    }
}
