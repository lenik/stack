package com.bee32.zebra.oa.hr;


/**
 * 学历字典类
 *
 * 定义学历相关的词汇。
 *
 * <p lang="en">
 * Person Education Type
 */
public enum EducationType {

    PRIMARY("小学"),

    JUNIOR("初中"),

    HIGH("高中"),

    COLLEGE("本科"),

    MASTER("硕士"),

    ;

    String label;

    private EducationType(String label) {
        this.label = label;
    }

}
