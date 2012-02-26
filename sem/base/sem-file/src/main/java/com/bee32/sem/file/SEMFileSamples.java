package com.bee32.sem.file;

import com.bee32.plover.orm.util.SampleContribution;
import com.bee32.sem.file.entity.UserFileTagname;

public class SEMFileSamples
        extends SampleContribution {

    @Override
    protected void listSamples() {
        add(new UserFileTagname("图片"));
        add(new UserFileTagname("音乐"));
        add(new UserFileTagname("设计资料"));
        add(new UserFileTagname("机密文件"));
        add(new UserFileTagname("宣传"));
    }

}
