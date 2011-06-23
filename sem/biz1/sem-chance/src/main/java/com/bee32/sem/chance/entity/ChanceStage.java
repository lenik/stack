package com.bee32.sem.chance.entity;

import java.util.Map.Entry;
import java.util.TreeMap;

import javax.persistence.Entity;
import javax.persistence.Transient;

import com.bee32.plover.orm.ext.color.Blue;
import com.bee32.plover.orm.ext.dict.ShortNameDict;

/**
 * 机会阶段推进
 */
@Entity
@Blue
public class ChanceStage
        extends ShortNameDict {

    private static final long serialVersionUID = 1L;

    static TreeMap<Integer, ChanceStage> stages = new TreeMap<Integer, ChanceStage>();

    public ChanceStage() {
        super();
    }

    public ChanceStage(int order, String name, String label) {
        super(order, name, label);
    }

    public ChanceStage(int order, String name, String label, String description) {
        super(order, name, label, description);
    }

    {
        stages.put(getOrder(), this);
    }

    @Transient
    public ChanceStage getPrevious() {
        int order = getOrder();
        int previous = order - 1;
        Entry<Integer, ChanceStage> entry = stages.floorEntry(previous);
        if (entry == null)
            return null;
        else
            return entry.getValue();
    }

    public static ChanceStage INIT = new ChanceStage(10, "INIT", "初始化");
    public static ChanceStage LAUNCHED = new ChanceStage(20, "LAUN", "初步沟通");
    public static ChanceStage MEETING = new ChanceStage(30, "MEET", "交涉中");
    public static ChanceStage QUOTED = new ChanceStage(40, "QUOT", "已经报价");
    public static ChanceStage PAYMENT = new ChanceStage(50, "PAYM", "合同付款洽谈");
    public static ChanceStage DONE = new ChanceStage(100, "DONE", "合同签订");
    public static ChanceStage TERMINATED = new ChanceStage(110, "TERM", "终止");

}
