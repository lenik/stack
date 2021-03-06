package com.bee32.plover.arch;

import java.util.Map;
import java.util.TreeMap;

import javax.free.ClassLocal;

import com.bee32.plover.inject.ServiceTemplate;

@ServiceTemplate
public abstract class AppProfile
        extends Component
        implements IAppProfile {

    ClassLocal<Map<String, Object>> featureParameters;

    public AppProfile() {
        featureParameters = new ClassLocal<Map<String, Object>>();
        preamble();
    }

    // @Override
    public Map<String, Object> getParameters(Class<?> featureClass) {
        Map<String, Object> parameters = featureParameters.get(featureClass);
        if (parameters == null) {
            // parameters = new TextMap(Collections.<String, Object> emptyMap());
            parameters = new TreeMap<String, Object>();
            featureParameters.put(featureClass, parameters);
        }
        return parameters;
    }

    @Override
    public Object getParameter(Class<?> featureClass, String key) {
        Map<String, Object> map = getParameters(featureClass);
        if (map == null)
            return null;
        else
            return map.get(key);
    }

    /**
     * The parameter set here will scanned in the priority order by
     * {@link AppProfileMerger#getParameter(Class, String)}.
     *
     * @see AppProfileMerger
     */
    protected void setParameter(Class<?> featureClass, String key, Object value) {
        Map<String, Object> parameters = getParameters(featureClass);
        parameters.put(key, value);
    }

    protected abstract void preamble();

}
