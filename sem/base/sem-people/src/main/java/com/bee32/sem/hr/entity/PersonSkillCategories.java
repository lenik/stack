package com.bee32.sem.hr.entity;

import javax.free.DecodeException;

import com.bee32.plover.orm.sample.NormalSamples;
import com.bee32.sem.hr.util.ScoreLevelMap;

public class PersonSkillCategories
        extends NormalSamples {

    public final PersonSkillCategory english = new PersonSkillCategory("英语");
    public final PersonSkillCategory japanese = new PersonSkillCategory("日语");
    public final PersonSkillCategory financial = new PersonSkillCategory("财务");

    public PersonSkillCategories()
            throws DecodeException {
        ScoreLevelMap englishLevels = new ScoreLevelMap();
        englishLevels.put(8, "英语八级");
        englishLevels.put(6, "英语六级");
        englishLevels.put(4, "英语四级");
        englishLevels.put(3, "英语三级");

        ScoreLevelMap japaneseLevels = new ScoreLevelMap();
        japaneseLevels.put(1, "日语一级");
        japaneseLevels.put(2, "日语二级");
        japaneseLevels.put(3, "日语三级");

        ScoreLevelMap financialLevels = new ScoreLevelMap();
        financialLevels.put(1, "会计从业资格");
        financialLevels.put(2, "初级会计师");
        financialLevels.put(3, "中级会计师");
        financialLevels.put(4, "高级会计师");
        financialLevels.put(5, "注册会计师");

        english.setLevelsData(englishLevels.encode());
        japanese.setLevelsData(japaneseLevels.encode());
        financial.setLevelsData(financialLevels.encode());
    }
}
