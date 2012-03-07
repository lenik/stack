package com.bee32.sem.people.entity;

import static com.bee32.sem.people.entity.PartySidType.ORG;
import static com.bee32.sem.people.entity.PartySidType.PERSON;

import com.bee32.plover.orm.sample.StandardSamples;

public class PartySidTypes
        extends StandardSamples {

    public final PartySidType IDENTITYCARD = new PartySidType(PERSON, "I.D.", "身份证", "公民身份证件");
    public final PartySidType PASSPORT = new PartySidType(PERSON, "PASS", "护照", "出国护照");
    public final PartySidType DRIVINGLICENES = new PartySidType(PERSON, "DRIV", "驾驶证", "机动车驾驶证件");
    public final PartySidType STUDENT = new PartySidType(PERSON, "STUD", "学生证", "在校学生证明");

    public final PartySidType TAX_ID = new PartySidType(ORG, "TAX", "税务识别号", "企业税务识别号");

}
