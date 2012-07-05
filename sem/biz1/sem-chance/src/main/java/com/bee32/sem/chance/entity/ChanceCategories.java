package com.bee32.sem.chance.entity;

import com.bee32.plover.orm.sample.StandardSamples;

public class ChanceCategories
        extends StandardSamples {

    public final ChanceCategory normal = new ChanceCategory("normal", "普通");
    public final ChanceCategory important = new ChanceCategory("important", "重要");
    public final ChanceCategory minor = new ChanceCategory("minor", "次要");
    public final ChanceCategory other = new ChanceCategory("other", "其他");

}
