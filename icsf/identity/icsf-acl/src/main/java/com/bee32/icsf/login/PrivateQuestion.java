package com.bee32.icsf.login;

import javax.persistence.Entity;

import com.bee32.plover.orm.ext.dict.LongNameDict;

@Entity
public class PrivateQuestion
        extends LongNameDict {

    private static final long serialVersionUID = 1L;

    public PrivateQuestion() {
        super();
    }

    public PrivateQuestion(String name, String label) {
        super(name, label);
    }

    public static PrivateQuestion DADS_NAME = new PrivateQuestion("DadNm", "你爸爸叫什么？");
    public static PrivateQuestion MOMS_NAME = new PrivateQuestion("MomNm", "你妈妈叫什么？");

}
