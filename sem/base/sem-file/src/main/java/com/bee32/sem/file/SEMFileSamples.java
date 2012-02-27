package com.bee32.sem.file;

import com.bee32.plover.orm.util.SampleList;
import com.bee32.plover.orm.util.NormalSamples;
import com.bee32.sem.file.entity.UserFileTagname;

public class SEMFileSamples
        extends NormalSamples {

    @Override
    protected void getSamples(SampleList samples) {
        samples.add(new UserFileTagname("图片"));
        samples.add(new UserFileTagname("音乐"));
        samples.add(new UserFileTagname("设计资料"));
        samples.add(new UserFileTagname("机密文件"));
        samples.add(new UserFileTagname("宣传"));
    }

}
