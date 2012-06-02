package com.bee32.plover.arch;

import java.util.Set;

import com.bee32.plover.inject.NotAComponent;

@NotAComponent
public class AppProfileAssembly
        extends Component
        implements IAppProfile {

    Set<IAppProfile> profiles;

    public AppProfileAssembly(Set<IAppProfile> profiles) {
        if (profiles == null)
            throw new NullPointerException("profiles");
        this.profiles = profiles;
    }

    public Set<IAppProfile> getProfiles() {
        return profiles;
    }

    /**
     * Find the first occurence of the feature parameter.
     *
     * @return Returns the value of the first parameter occurred in the profile list. If not found,
     *         <code>null</code> is returned.
     */
    @Override
    public Object getParameter(Class<?> feature, String key) {
        for (IAppProfile profile : profiles) {
            Object parameter = profile.getParameter(feature, key);
            if (parameter != null)
                return parameter;
        }
        return null;
    }

}
