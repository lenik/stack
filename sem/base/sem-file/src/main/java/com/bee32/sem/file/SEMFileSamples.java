package com.bee32.sem.file;

import com.bee32.plover.orm.entity.EntityAccessor;
import com.bee32.plover.orm.entity.EntityFlags;
import com.bee32.plover.orm.sample.NormalSamples;
import com.bee32.sem.file.entity.UserFileTagname;

public class SEMFileSamples
        extends NormalSamples {

    public final UserFileTagname pictureTag = new UserFileTagname("图片");
    public final UserFileTagname musicTag = new UserFileTagname("音乐");
    public final UserFileTagname designTag = new UserFileTagname("设计资料");
    public final UserFileTagname secretTag = new UserFileTagname("机密文件");
    public final UserFileTagname adTag = new UserFileTagname("宣传资料");

    public SEMFileSamples() {
        EntityAccessor.putFlags(EntityFlags.WEAK_DATA, //
                pictureTag, musicTag, designTag, secretTag, adTag);
    }

}
