package com.bee32.plover.arch;

import java.util.Map;
import java.util.ServiceLoader;
import java.util.TreeMap;

/**
 * 应用剪裁管理器
 *
 * <p lang="en">
 * Application Profile Manager
 */
public class AppProfileManager {

    static final Map<String, IAppProfile> profileMap;

    static {
        profileMap = new TreeMap<String, IAppProfile>();
        for (IAppProfile profile : ServiceLoader.load(IAppProfile.class)) {
            // profile.getName();
            String profileName = profile.getClass().getSimpleName();
            profileMap.put(profileName, profile);
        }
    }

    /**
     * Get profile by name.
     *
     * @return <code>null</code> if the specified profile name doesn't exist.
     */
    public static IAppProfile getProfile(String profileName) {
        return profileMap.get(profileName);
    }

    public static Map<String, IAppProfile> getProfileMap() {
        return profileMap;
    }

}
