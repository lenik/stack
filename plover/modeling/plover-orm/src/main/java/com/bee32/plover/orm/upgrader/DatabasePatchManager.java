package com.bee32.plover.orm.upgrader;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

public class DatabasePatchManager {

    /**
     * 获取需要更新的补丁集.
     *
     * @param currentVersion
     *            当前的数据库版本
     * @return Non-<code>null</code> patch list.
     */
    public static List<IDatabasePatch> getPatches(int currentVersion) {
        List<IDatabasePatch> patches = new ArrayList<IDatabasePatch>();
        for (IDatabasePatch patch : ServiceLoader.load(IDatabasePatch.class)) {
            if (patch.getVersion() > currentVersion)
                patches.add(patch);
        }
        return patches;
    }

}
