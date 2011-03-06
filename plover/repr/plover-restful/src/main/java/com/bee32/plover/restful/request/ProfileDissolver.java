package com.bee32.plover.restful.request;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import javax.free.UnexpectedException;

import com.bee32.plover.model.profile.Profile;
import com.bee32.plover.model.profile.StandardProfiles;

/**
 * Only standard profile names are supported.
 * <p>
 * Non-standard profile should not be used, alternatively, one should construct specific profiles
 * thru a specific model builder functions.
 */
public class ProfileDissolver
        implements ISuffixDissolver {

    private static Map<String, Profile> profileByKeyword;

    static {
        profileByKeyword = new HashMap<String, Profile>();

        for (Field field : StandardProfiles.class.getFields()) {
            if (Profile.class.isAssignableFrom(field.getType())) {
                Profile profile;
                try {
                    profile = (Profile) field.get(null);
                } catch (IllegalAccessException e) {
                    throw new UnexpectedException(e.getMessage(), e);
                }
                String profileName = profile.getName();
                profileByKeyword.put(profileName, profile);
            }
        }

        if (profileByKeyword.isEmpty())
            profileByKeyword = null;
    }

    @Override
    public boolean dissolveSuffix(String name, RestfulRequest model) {
        if (name == null)
            throw new NullPointerException("extension");

        if (profileByKeyword == null)
            return false;

        Profile profile = profileByKeyword.get(name);
        if (profile == null)
            profile = new Profile(name);

        model.setProfile(profile);
        return true;
    }

}
