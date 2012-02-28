package com.bee32.sem.file;

import com.bee32.plover.orm.util.NormalSamples;
import com.bee32.plover.orm.util.SampleList;
import com.bee32.sem.file.entity.UserFileTagname;

public class SEMFileSamples
        extends NormalSamples {

    UserFileTagname pictureTag = new UserFileTagname("图片");
    UserFileTagname musicTag = new UserFileTagname("音乐");
    UserFileTagname designTag = new UserFileTagname("设计资料");
    UserFileTagname secretTag = new UserFileTagname("机密文件");
    UserFileTagname adTag = new UserFileTagname("宣传");

    @Override
    protected void getSamples(SampleList samples) {
        samples.addBatch(//
                pictureTag, //
                musicTag, //
                designTag, //
                secretTag, //
                adTag);
    }

}
