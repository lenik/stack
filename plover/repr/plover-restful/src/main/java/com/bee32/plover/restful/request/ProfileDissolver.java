package com.bee32.plover.restful.request;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import javax.free.UnexpectedException;

import com.bee32.plover.model.profile.Profile;
import com.bee32.plover.model.profile.StandardProfiles;

public class ProfileDissolver
        implements IExtensionDissolver {

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
    public boolean desolveExtension(String extension, RequestModel model) {
        if (extension == null)
            throw new NullPointerException("extension");

        if (profileByKeyword == null)
            return false;

        Profile profile = profileByKeyword.get(extension);
        if (profile == null)
            return false;

        model.setProfile(profile);
        return true;
    }

}
