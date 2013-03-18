package com.bee32.plover.site;

import java.io.File;
import java.io.IOException;
import java.util.*;

import javax.free.FilePath;
import javax.free.IFile;
import javax.free.JavaioFile;
import javax.free.ParseException;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bee32.plover.arch.AppProfileManager;
import com.bee32.plover.arch.AppProfileMerger;
import com.bee32.plover.arch.IAppProfile;
import com.bee32.plover.rtx.location.ILocationConstants;
import com.bee32.plover.rtx.location.Location;
import com.bee32.plover.rtx.location.Locations;
import com.bee32.plover.servlet.util.ThreadHttpContext;
import com.bee32.plover.servlet.util.ThreadServletContext;
import com.bee32.plover.site.cfg.*;
import com.bee32.plover.site.scope.StartAndStatsSrl;

public class SiteInstance
        implements ILocationConstants {

    static Logger logger = LoggerFactory.getLogger(SiteInstance.class);

    public static final Location DEFAULT_LOGO_LOCATION = SYMBOLS.join("logo-full.png");

    public static final String CONFIG_EXTENSION = ".sif";

    public static final String ALIASES_KEY = "aliases";
    public static final String LABEL_KEY = "label";
    public static final String DESCRIPTION_KEY = "description";
    public static final String LOGO_KEY = "logo";
    public static final String THEME_KEY = "theme";
    public static final String PROFILE_NAMES_KEY = "profiles";

    public static final String VERBOSE_KEY = "verbose";
    public static final String OPTIMIZATION_KEY = "optimization";

    public static final String DB_DIALECT_KEY = "db.dialect";
    public static final String DB_URL_FORMAT_KEY = "db.url";
    public static final String DB_NAME_KEY = "db.name";
    public static final String DB_USER_KEY = "db.user";
    public static final String DB_PASS_KEY = "db.pass";

    public static final String AUTODDL_KEY = "autoddl";
    public static final String SAMPLES_KEY = "samples";

    String name;
    File configFile;
    FormatProperties properties;
    boolean dirty;
    Map<String, Object> attributes = new HashMap<String, Object>();
    SiteState state = SiteState.STOPPED;

    File statsFile;
    private SiteStats stats;

    private SiteInstance() {
        properties = new FormatProperties();

        setTheme(PrimefacesTheme.redmond);
        setVerboseLevel(VerboseLevel.SQL);
        setOptimizationLevel(OptimizationLevel.MEDIUM);
        setDbDialect(DBDialect.PostgreSQL);
        setProperty(DB_USER_KEY, "semsadmin");
        setProperty(DB_PASS_KEY, "MxDkUWl1");
        setAutoDDL(DBAutoDDL.Update);
        setSamples(SamplesSelection.STANDARD);
        setLogoLocation(DEFAULT_LOGO_LOCATION);
    }

    public static SiteInstance createSite() {
        return new SiteInstance();
    }

    public SiteInstance(File file)
            throws IOException, ParseException {
        this();

        String name = file.getName();
        if (name.endsWith(CONFIG_EXTENSION))
            name = name.substring(0, name.length() - CONFIG_EXTENSION.length());
        this.name = name;

        setConfigFile(file);
        loadConfig();
    }

    SiteInstance(FormatProperties properties, File configFile) {
        if (properties == null)
            throw new NullPointerException("properties");
        this.properties = properties;
        setConfigFile(configFile);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null)
            throw new NullPointerException("name");
        this.name = name;

        // Set default db name to site_db.
        String dbName = getDbName();
        if (dbName == null) {
            String siteName = getName();
            if (siteName != null) {
                dbName = siteName + "_db";
                setDbName(dbName);
            }
        }
    }

    public File getConfigFile() {
        return configFile;
    }

    public void setConfigFile(File configFile) {
        if (configFile == null)
            throw new NullPointerException("configFile");
        this.configFile = configFile;

        String base = configFile.getName();
        String statsName = FilePath.stripExtension(base) + ".stats.xml";
        this.statsFile = new File(configFile.getParentFile(), statsName);
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    public void setProperty(String key, String value) {
        if (value == null)
            removeProperty(key);
        else
            properties.setProperty(key, value);
        dirty = true;
    }

    public void removeProperty(String key) {
        properties.removeProperty(key);
    }

    public int getIntProperty(String key, int defaultValue) {
        Integer property = getIntProperty(key);
        if (property == null)
            return defaultValue;
        else
            return property;
    }

    public Integer getIntProperty(String key) {
        String property = properties.getProperty(key);
        if (property == null)
            return null;
        Integer _int = Integer.valueOf(property);
        return _int;
    }

    public void setIntProperty(String key, Integer value) {
        String property;
        if (value == null)
            property = null;
        else
            property = String.valueOf(value);
        properties.setProperty(key, property);
    }

    public Boolean getBooleanProperty(String key) {
        String property = properties.getProperty(key);
        if (properties == null)
            return null;
        if ("true".equals(property) || "1".equals(property))
            return true;
        else
            return false;
    }

    public void setBooleanProperty(String key, Boolean value) {
        String property;
        if (value == null)
            property = null;
        else
            property = String.valueOf(value);
        properties.setProperty(key, property);
    }

    public boolean containsAttribute(String attributeName) {
        return attributes.containsKey(attributeName);
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public <T> T getAttribute(String attributeName) {
        if (attributeName == null)
            throw new NullPointerException("attributeName");
        return (T) attributes.get(attributeName);
    }

    public void setAttribute(String attributeName, Object attributeValue) {
        if (attributeName == null)
            throw new NullPointerException("attributeName");
        attributes.put(attributeName, attributeValue);
    }

    public <T> T removeAttribute(String attributeName) {
        return (T) attributes.remove(attributeName);
    }

    public void reloadConfig()
            throws IOException, ParseException {
        loadConfig();
    }

    void loadConfig()
            throws IOException, ParseException {
        if (configFile.exists()) {
            IFile _file = new JavaioFile(configFile);
            properties.parse(_file);
        }
    }

    /**
     * Save attributes to the config file.
     */
    public synchronized void saveConfig()
            throws IOException {
        if (dirty) {
            JavaioFile _file = new JavaioFile(configFile);
            _file.forWrite().write(properties.toString());
            dirty = false;
        }
    }

    public String getLoggingPrefix() {
        String prefix = "[" + getName() + " - " + getLabel() + "] ";
        return prefix;
    }

    public String getLabel() {
        return getProperty(LABEL_KEY);
    }

    public void setLabel(String label) {
        setProperty(LABEL_KEY, label);
    }

    public String getDescription() {
        return getProperty(DESCRIPTION_KEY);
    }

    public void setDescription(String description) {
        setProperty(DESCRIPTION_KEY, description);
    }

    public String getLogo() {
        return getProperty(LOGO_KEY);
    }

    public void setLogo(String logo) {
        if (logo == null || logo.isEmpty()) {
            setLogoLocation(DEFAULT_LOGO_LOCATION);
            return;
        }
        if (!logo.contains("::"))
            logo = "URL::" + logo;
        setProperty(LOGO_KEY, logo);
    }

    public Location getLogoLocation() {
        String _logo = getProperty(LOGO_KEY);
        Location logo = Locations.parse(_logo);
        return logo;
    }

    public void setLogoLocation(Location logo) {
        String _logo = Locations.qualify(logo);
        setProperty(LOGO_KEY, _logo);
    }

    public String getLogoHref() {
        HttpServletRequest request = ThreadServletContext.getRequest();
        Location location = getLogoLocation();
        String href = location.resolveAbsolute(request);
        return href;
    }

    public PrimefacesTheme getTheme() {
        String _theme = getProperty(THEME_KEY);
        if (_theme == null)
            return null;
        else
            return PrimefacesTheme.forName(_theme);
    }

    public void setTheme(PrimefacesTheme theme) {
        String _theme = theme == null ? null : theme.getName();
        setProperty(THEME_KEY, _theme);
    }

    public void setThemeByName(String themeName) {
        PrimefacesTheme theme;
        if (themeName == null || themeName.isEmpty())
            theme = null;
        else
            theme = PrimefacesTheme.forName(themeName);
        setTheme(theme);
    }

    public Set<IAppProfile> _getProfiles() {
        Set<String> profileNames = getProfileNames();
        Set<IAppProfile> profiles = new LinkedHashSet<IAppProfile>();
        if (profileNames != null) {
            for (String profileName : profileNames) {
                IAppProfile profile = AppProfileManager.getProfile(profileName);
                if (profile == null) {
                    logger.debug("Undefined profile: " + profileName);
                    continue;
                }
                profiles.add(profile);
            }
        }
        return profiles;
    }

    public void _setProfiles(Set<IAppProfile> profiles) {
        Set<String> profileNames;
        if (profiles == null)
            profileNames = null;
        else {
            profileNames = new LinkedHashSet<String>();
            for (IAppProfile profile : profiles) {
                String simpleName = profile.getClass().getSimpleName();
                profileNames.add(simpleName);
            }
        }
        setProfileNames(profileNames);
    }

    public Set<String> getProfileNames() {
        String _profileNames = getProperty(PROFILE_NAMES_KEY);
        if (_profileNames == null)
            return null;
        else
            _profileNames = _profileNames.trim();

        Set<String> profileNames = new LinkedHashSet<String>();
        if (!_profileNames.isEmpty())
            for (String profileName : _profileNames.split(",")) {
                profileName = profileName.trim();
                if (!profileName.isEmpty()) {
                    profileNames.add(profileName);
                }
            }
        return profileNames;
    }

    public void setProfileNames(Set<String> profileNames) {
        StringBuilder sb = new StringBuilder();
        for (String profileName : profileNames) {
            if (sb.length() != 0)
                sb.append(", ");
            sb.append(profileName);
        }
        setProperty(PROFILE_NAMES_KEY, sb.toString());
    }

    public void setProfileNames(String profileNames) {
        setProperty(PROFILE_NAMES_KEY, profileNames);
    }

    public AppProfileMerger getMergedProfile() {
        Set<IAppProfile> profiles = _getProfiles();
        return new AppProfileMerger(profiles);
    }

    public VerboseLevel getVerboseLevel() {
        String _verbose = getProperty(VERBOSE_KEY);
        VerboseLevel verbose = VerboseLevel.forName(_verbose);
        return verbose;
    }

    public void setVerboseLevel(VerboseLevel verbose) {
        String _verbose = verbose.getName();
        setProperty(VERBOSE_KEY, _verbose);
    }

    public OptimizationLevel getOptimizationLevel() {
        String _optimization = getProperty(OPTIMIZATION_KEY);
        OptimizationLevel optimization = OptimizationLevel.forName(_optimization);
        return optimization;
    }

    public void setOptimizationLevel(OptimizationLevel optimization) {
        String _optimization = optimization.getName();
        setProperty(OPTIMIZATION_KEY, _optimization);
    }

    public Set<String> getAliases() {
        Set<String> aliases = new LinkedHashSet<String>();
        String _aliases = getProperty(ALIASES_KEY);
        if (_aliases != null) {
            StringTokenizer tokens = new StringTokenizer(_aliases, ",");
            while (tokens.hasMoreTokens()) {
                String _alias = tokens.nextToken().trim();
                aliases.add(_alias);
            }
        }
        return aliases;
    }

    public void setAliases(Set<String> aliases) {
        StringBuilder sb = new StringBuilder(aliases.size() * 20);
        int index = 0;
        for (String alias : aliases) {
            if (index++ > 0)
                sb.append(", ");
            sb.append(alias);
        }
        String _aliases = sb.toString();
        setProperty(ALIASES_KEY, _aliases);
    }

    public DBDialect getDbDialect() {
        String _dialect = getProperty(DB_DIALECT_KEY);
        DBDialect dialect = DBDialect.forName(_dialect);
        return dialect;
    }

    public void setDbDialect(DBDialect dialect) {
        String _dialect = dialect.getName();
        setProperty(DB_DIALECT_KEY, _dialect);
        setProperty(DB_URL_FORMAT_KEY, dialect.getUrlFormat());
    }

    public String getDbUrlFormat() {
        String format = getProperty(DB_URL_FORMAT_KEY);
        return format;
    }

    public void setDbUrlFormat(String urlFormat) {
        setProperty(DB_URL_FORMAT_KEY, urlFormat);
    }

    public String getDbName() {
        String dbName = getProperty(DB_NAME_KEY);
        return dbName;
    }

    public void setDbName(String dbName) {
        setProperty(DB_NAME_KEY, dbName);
    }

    public String getDbUrl() {
        String urlFormat = getDbUrlFormat();
        String dbName = getDbName();
        String url = String.format(urlFormat, dbName);
        return url;
    }

    public String getDbUser() {
        String dbUser = getProperty(DB_USER_KEY);
        return dbUser;
    }

    public void setDbUser(String dbUser) {
        setProperty(DB_USER_KEY, dbUser);
    }

    public String getDbPass() {
        String dbPass = getProperty(DB_PASS_KEY);
        return dbPass;
    }

    public void setDbPass(String dbPass) {
        setProperty(DB_PASS_KEY, dbPass);
    }

    public DBAutoDDL getAutoDDL() {
        String _autoddl = getProperty(AUTODDL_KEY);
        DBAutoDDL autoddl = DBAutoDDL.forName(_autoddl);
        return autoddl;
    }

    public void setAutoDDL(DBAutoDDL autoddl) {
        String _autoddl = autoddl.getName();
        setProperty(AUTODDL_KEY, _autoddl);
    }

    public SamplesSelection getSamples() {
        String property = getProperty(SAMPLES_KEY);
        SamplesSelection samples = SamplesSelection.forName(property);
        return samples;
    }

    public void setSamples(SamplesSelection samples) {
        if (samples == null)
            throw new NullPointerException("samples");
        String property = samples.getName();
        setProperty(SAMPLES_KEY, property);
    }

    public SiteState getState() {
        return state;
    }

    public synchronized void start() {
        if (state == SiteState.STOPPED) {
            state = SiteState.STARTING;
            SiteStats stats = getLocalStats();
            long startupTime = System.currentTimeMillis();
            stats.setStartupTime(startupTime);

            // See StartAndStopSrl
            HttpServletRequest request = ThreadHttpContext.getRequestOpt();
            if (request != null)
                request.setAttribute(StartAndStatsSrl.NO_STATS_ATTRIBUTE, true);

            SiteLifecycleDispatcher.startSite(this);

            long cost = (int) (new Date().getTime() - startupTime);
            stats.addStartup(cost);
        }
    }

    public synchronized void stop() {
        if (state == SiteState.STARTED) {
            state = SiteState.STOPPING;
            SiteLifecycleDispatcher.stopSite(this);

            attributes.clear();

            if (statsFile != null && stats != null) {
                try {
                    SiteStats child = stats.getLastChild();
                    stats.addGroup(child);
                    stats.saveToFile(statsFile);
                } catch (IOException e) {
                    logger.error("Failed to save stats to file: " + statsFile, e);
                }
            }

            state = SiteState.STOPPED;
        }
    }

    public SiteStats getAllStats() {
        if (stats == null)
            synchronized (this) {
                if (stats == null)
                    try {
                        stats = SiteStats.readFromFile(statsFile);
                    } catch (IOException e) {
                        logger.error("Failed to read stats from file " + statsFile, e);
                        stats = new SiteStats();
                    }
            }
        return stats;
    }

    public SiteStats getLocalStats() {
        return getAllStats().getLastChild();
    }

    public static final String SITE_REQUESTS_ATTRIBUTE = "requests";
    static int maxRecentRequests = 200;

    public LinkedList<RequestEntry> getRecentRequests() {
        LinkedList<RequestEntry> requests = getAttribute(SITE_REQUESTS_ATTRIBUTE);
        if (requests == null) {
            requests = new LinkedList<>();
            setAttribute(SITE_REQUESTS_ATTRIBUTE, requests);
        }
        return requests;
    }

    public void addRecentRequest(HttpServletRequest request) {
        if (request == null)
            throw new NullPointerException("request");
        LinkedList<RequestEntry> requests = getRecentRequests();
        requests.addFirst(new RequestEntry(request));
        while (requests.size() > maxRecentRequests)
            requests.removeLast();
    }

    @Override
    public String toString() {
        return getName();
    }

}
