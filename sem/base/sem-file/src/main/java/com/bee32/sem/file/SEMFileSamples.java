package com.bee32.sem.file;

import com.bee32.plover.orm.util.SampleContribution;
import com.bee32.sem.file.entity.UserFileTagname;

public class SEMFileSamples
        extends SampleContribution {

    public static UserFileTagname defaultTag;
    public static UserFileTagname tag1;
    public static UserFileTagname tag2;
    public static UserFileTagname tag3;

    static {

        tag1 = new UserFileTagname();
        tag1.setTag("自然系");

        tag2 = new UserFileTagname();
        tag2.setTag("动物系");

        tag3 = new UserFileTagname();
        tag3.setTag("超人系");
    }

    @Override
    protected void preamble() {
        add(tag1);
        add(tag2);
        add(tag3);
    }

}
