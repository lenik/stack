package com.bee32.plover.orm.util;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.free.Pred1;

import org.postgresql.util.Base64;

import com.bee32.plover.xutil.ResourceScanner;

public class TypeAbbr {

    private final int length;
    private Map<String, Class<?>> abbr2ClassMap = new HashMap<String, Class<?>>();
    private Map<String, String> abbr2FqcnMap = new HashMap<String, String>();

    static boolean dumpAbbrevs = false;

    public TypeAbbr(int length, String... scanPackageNames) {
        if (length < 5)
            throw new IllegalArgumentException("Length should be >= 5.");
        this.length = length;

        for (String scanPackageName : scanPackageNames)
            try {
                scanAndRegister(scanPackageName);
            } catch (IOException e) {
                throw new Error(e.getMessage(), e);
            }

        if (dumpAbbrevs) {
            System.out.println("Type abbrevs: ");
            for (Entry<String, String> entry : abbr2FqcnMap.entrySet())
                System.out.println("    " + entry.getKey() + " := " + entry.getValue());
        }
    }

    public String abbr(Class<?> clazz) {
        if (clazz == null)
            return null;
        String abbr = abbr(clazz.getName());
        return abbr;
    }

    static final int prefixLen = 4;

    public String abbr(String name) {
        // if (name.length() <= length) return name;

        StringBuilder sb = new StringBuilder(length);

        int lastDot = name.lastIndexOf('.');
        String simpleName = lastDot == -1 ? name : name.substring(lastDot + 1);

        if (simpleName.length() > prefixLen)
            simpleName = simpleName.substring(0, prefixLen);
        sb.append(simpleName);

        sb.append('_');

        String hash = hash(name);
        hash = hash.substring(0, length - sb.length());
        sb.append(hash);

        return sb.toString();
    }

    public String register(Class<?> clazz) {
        String abbr = register(clazz.getCanonicalName());
        abbr2ClassMap.put(abbr, clazz);
        return abbr;
    }

    public String register(String fqcn) {
        if (fqcn == null)
            throw new NullPointerException("fqcn");

        String abbr = abbr(fqcn);
        String existing = abbr2FqcnMap.get(abbr);
        if (existing != null && !existing.equals(fqcn))
            throw new IllegalStateException("Abbreviation collision: " + fqcn + " and " + existing);

        abbr2FqcnMap.put(abbr, fqcn);
        return abbr;
    }

    public Map<String, String> getAbbrMap() {
        return Collections.unmodifiableMap(abbr2FqcnMap);
    }

    public Class<?> expand(String abbr)
            throws ClassNotFoundException {
        if (abbr == null)
            return null;

        if (abbr.contains("."))
            return Class.forName(abbr);

        Class<?> clazz = abbr2ClassMap.get(abbr);
        if (clazz == null) {
            String fqcn = abbr2FqcnMap.get(abbr);
            if (fqcn != null) {
                clazz = Class.forName(fqcn);
                abbr2ClassMap.put(abbr, clazz);
            }
        }
        return clazz;
    }

    private static MessageDigest md5;
    static {
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    protected static synchronized String hash(String str) {
        byte[] digest;
        digest = md5.digest(str.getBytes());

        String base64 = Base64.encodeBytes(digest);
        return base64;
    }

    class RegisterAbbr
            extends Pred1<String> {

        @Override
        public boolean test(String typeName) {
            if (typeName.endsWith("package-info"))
                return true; // skip
            register(typeName);
            return true;
        }

    }

    final RegisterAbbr registerAbbrInstance = new RegisterAbbr();

    public void scanAndRegister(String packageName)
            throws IOException {
        ResourceScanner.scanTypenames(packageName, registerAbbrInstance);
    }

    public Map<Class<?>, String[]> loadUsageMap()
            throws ClassNotFoundException, IOException {
        Map<Class<?>, String[]> usageMap = new LinkedHashMap<Class<?>, String[]>();
        EntityUsageCollector collector = new EntityUsageCollector();
        collector.loadFromResources();
        EntityUsage abbrUsage = collector.getUsage(ITypeAbbrAware.USAGE_ID);
        Map<Class<?>, String> entityMap = abbrUsage.getEntityMap();
        for (Entry<Class<?>, String> entry : entityMap.entrySet()) {
            Class<?> entityType = entry.getKey();
            String[] properties = entry.getValue().split(",");
            for (int i = 0; i < properties.length; i++)
                properties[i] = properties[i].trim();
            usageMap.put(entityType, properties);
        }
        return usageMap;
    }

}
