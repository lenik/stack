package com.bee32.plover.arch.util.res;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.ResourceBundle.Control;

public class NlsBundles {

    public static final String BUNDLE_EXTENSION = "properties";

    /**
     * Gets a resource bundle using the specified base name, the default locale, and the caller's
     * class loader. Calling this method is equivalent to calling <blockquote>
     * <code>getBundle(baseName, Locale.getDefault(), this.getClass().getClassLoader())</code>,
     * </blockquote> except that <code>getClassLoader()</code> is run with the security privileges
     * of <code>ResourceBundle</code>. See {@link #getBundle(String, Locale, ClassLoader) getBundle}
     * for a complete description of the search and instantiation strategy.
     *
     * @param baseName
     *            the base name of the resource bundle, a fully qualified class name
     * @exception java.lang.NullPointerException
     *                if <code>baseName</code> is <code>null</code>
     * @exception MissingResourceException
     *                if no resource bundle for the specified base name can be found
     * @return a resource bundle for the given base name and the default locale
     */
    public static final INlsBundle getBundle(String baseName) {
        ResourceBundle resourceBundle = ResourceBundle.getBundle(baseName, UTF8Control.INSTANCE);
        return new NlsFromResourceBundle(resourceBundle);
    }

    /**
     * Gets a resource bundle using the specified base name and locale, and the caller's class
     * loader. Calling this method is equivalent to calling <blockquote>
     * <code>getBundle(baseName, locale, this.getClass().getClassLoader())</code>, </blockquote>
     * except that <code>getClassLoader()</code> is run with the security privileges of
     * <code>ResourceBundle</code>. See {@link #getBundle(String, Locale, ClassLoader) getBundle}
     * for a complete description of the search and instantiation strategy.
     *
     * @param baseName
     *            the base name of the resource bundle, a fully qualified class name
     * @param locale
     *            the locale for which a resource bundle is desired
     * @exception NullPointerException
     *                if <code>baseName</code> or <code>locale</code> is <code>null</code>
     * @exception MissingResourceException
     *                if no resource bundle for the specified base name can be found
     * @return a resource bundle for the given base name and locale
     */
    public static final INlsBundle getBundle(String baseName, Locale locale) {
        ResourceBundle resourceBundle = ResourceBundle.getBundle(baseName, locale, UTF8Control.INSTANCE);
        return new NlsFromResourceBundle(resourceBundle);
    }

    /**
     * Gets a resource bundle using the specified base name, locale, and class loader.
     *
     * <p>
     * <a name="default_behavior"/>This method behaves the same as calling
     * {@link #getBundle(String, Locale, ClassLoader, Control)} passing a default instance of
     * {@link Control}. The following describes this behavior.
     *
     * <p>
     * <code>getBundle</code> uses the base name, the specified locale, and the default locale
     * (obtained from {@link java.util.Locale#getDefault() Locale.getDefault}) to generate a
     * sequence of <a name="candidates"><em>candidate bundle names</em></a>. If the specified
     * locale's language, script, country, and variant are all empty strings, then the base name is
     * the only candidate bundle name. Otherwise, a list of candidate locales is generated from the
     * attribute values of the specified locale (language, script, country and variant) and appended
     * to the base name. Typically, this will look like the following:
     *
     * <pre>
     *     baseName + "_" + language + "_" + script + "_" + country + "_" + variant
     *     baseName + "_" + language + "_" + script + "_" + country
     *     baseName + "_" + language + "_" + script
     *     baseName + "_" + language + "_" + country + "_" + variant
     *     baseName + "_" + language + "_" + country
     *     baseName + "_" + language
     * </pre>
     *
     * <p>
     * Candidate bundle names where the final component is an empty string are omitted, along with
     * the underscore. For example, if country is an empty string, the second and the fifth
     * candidate bundle names above would be omitted. Also, if script is an empty string, the
     * candidate names including script are omitted. For example, a locale with language "de" and
     * variant "JAVA" will produce candidate names with base name "MyResource" below.
     *
     * <pre>
     *     MyResource_de__JAVA
     *     MyResource_de
     * </pre>
     *
     * In the case that the variant contains one or more underscores ('_'), a sequence of bundle
     * names generated by truncating the last underscore and the part following it is inserted after
     * a candidate bundle name with the original variant. For example, for a locale with language
     * "en", script "Latn, country "US" and variant "WINDOWS_VISTA", and bundle base name
     * "MyResource", the list of candidate bundle names below is generated:
     *
     * <pre>
     * MyResource_en_Latn_US_WINDOWS_VISTA
     * MyResource_en_Latn_US_WINDOWS
     * MyResource_en_Latn_US
     * MyResource_en_Latn
     * MyResource_en_US_WINDOWS_VISTA
     * MyResource_en_US_WINDOWS
     * MyResource_en_US
     * MyResource_en
     * </pre>
     *
     * <blockquote><b>Note:</b> For some <code>Locale</code>s, the list of candidate bundle names
     * contains extra names, or the order of bundle names is slightly modified. See the description
     * of the default implementation of {@link Control#getCandidateLocales(String, Locale)
     * getCandidateLocales} for details.</blockquote>
     *
     * <p>
     * <code>getBundle</code> then iterates over the candidate bundle names to find the first one
     * for which it can <em>instantiate</em> an actual resource bundle. It uses the default
     * controls' {@link Control#getFormats getFormats} method, which generates two bundle names for
     * each generated name, the first a class name and the second a properties file name. For each
     * candidate bundle name, it attempts to create a resource bundle:
     *
     * <ul>
     * <li>First, it attempts to load a class using the generated class name. If such a class can be
     * found and loaded using the specified class loader, is assignment compatible with
     * ResourceBundle, is accessible from ResourceBundle, and can be instantiated,
     * <code>getBundle</code> creates a new instance of this class and uses it as the
     * <em>result resource
     * bundle</em>.
     *
     * <li>Otherwise, <code>getBundle</code> attempts to locate a property resource file using the
     * generated properties file name. It generates a path name from the candidate bundle name by
     * replacing all "." characters with "/" and appending the string ".properties". It attempts to
     * find a "resource" with this name using
     * {@link java.lang.ClassLoader#getResource(java.lang.String) ClassLoader.getResource}. (Note
     * that a "resource" in the sense of <code>getResource</code> has nothing to do with the
     * contents of a resource bundle, it is just a container of data, such as a file.) If it finds a
     * "resource", it attempts to create a new {@link PropertyResourceBundle} instance from its
     * contents. If successful, this instance becomes the <em>result resource bundle</em>.
     * </ul>
     *
     * <p>
     * This continues until a result resource bundle is instantiated or the list of candidate bundle
     * names is exhausted. If no matching resource bundle is found, the default control's
     * {@link Control#getFallbackLocale getFallbackLocale} method is called, which returns the
     * current default locale. A new sequence of candidate locale names is generated using this
     * locale and and searched again, as above.
     *
     * <p>
     * If still no result bundle is found, the base name alone is looked up. If this still fails, a
     * <code>MissingResourceException</code> is thrown.
     *
     * <p>
     * <a name="parent_chain"/> Once a result resource bundle has been found, its
     * <em>parent chain</em> is instantiated. If the result bundle already has a parent (perhaps
     * because it was returned from a cache) the chain is complete.
     *
     * <p>
     * Otherwise, <code>getBundle</code> examines the remainder of the candidate locale list that
     * was used during the pass that generated the result resource bundle. (As before, candidate
     * bundle names where the final component is an empty string are omitted.) When it comes to the
     * end of the candidate list, it tries the plain bundle name. With each of the candidate bundle
     * names it attempts to instantiate a resource bundle (first looking for a class and then a
     * properties file, as described above).
     *
     * <p>
     * Whenever it succeeds, it calls the previously instantiated resource bundle's
     * {@link #setParent(java.util.ResourceBundle) setParent} method with the new resource bundle.
     * This continues until the list of names is exhausted or the current bundle already has a
     * non-null parent.
     *
     * <p>
     * Once the parent chain is complete, the bundle is returned.
     *
     * <p>
     * <b>Note:</b> <code>getBundle</code> caches instantiated resource bundles and might return the
     * same resource bundle instance multiple times.
     *
     * <p>
     * <b>Note:</b>The <code>baseName</code> argument should be a fully qualified class name.
     * However, for compatibility with earlier versions, Sun's Java SE Runtime Environments do not
     * verify this, and so it is possible to access <code>PropertyResourceBundle</code>s by
     * specifying a path name (using "/") instead of a fully qualified class name (using ".").
     *
     * <p>
     * <a name="default_behavior_example"/> <strong>Example:</strong>
     * <p>
     * The following class and property files are provided:
     *
     * <pre>
     *     MyResources.class
     *     MyResources.properties
     *     MyResources_fr.properties
     *     MyResources_fr_CH.class
     *     MyResources_fr_CH.properties
     *     MyResources_en.properties
     *     MyResources_es_ES.class
     * </pre>
     *
     * The contents of all files are valid (that is, public non-abstract subclasses of
     * <code>ResourceBundle</code> for the ".class" files, syntactically correct ".properties"
     * files). The default locale is <code>Locale("en", "GB")</code>.
     *
     * <p>
     * Calling <code>getBundle</code> with the locale arguments below will instantiate resource
     * bundles as follows:
     *
     * <table>
     * <tr>
     * <td>Locale("fr", "CH")</td>
     * <td>MyResources_fr_CH.class, parent MyResources_fr.properties, parent MyResources.class</td>
     * </tr>
     * <tr>
     * <td>Locale("fr", "FR")</td>
     * <td>MyResources_fr.properties, parent MyResources.class</td>
     * </tr>
     * <tr>
     * <td>Locale("de", "DE")</td>
     * <td>MyResources_en.properties, parent MyResources.class</td>
     * </tr>
     * <tr>
     * <td>Locale("en", "US")</td>
     * <td>MyResources_en.properties, parent MyResources.class</td>
     * </tr>
     * <tr>
     * <td>Locale("es", "ES")</td>
     * <td>MyResources_es_ES.class, parent MyResources.class</td>
     * </tr>
     * </table>
     *
     * <p>
     * The file MyResources_fr_CH.properties is never used because it is hidden by the
     * MyResources_fr_CH.class. Likewise, MyResources.properties is also hidden by
     * MyResources.class.
     *
     * @param baseName
     *            the base name of the resource bundle, a fully qualified class name
     * @param locale
     *            the locale for which a resource bundle is desired
     * @param loader
     *            the class loader from which to load the resource bundle
     * @return a resource bundle for the given base name and locale
     * @exception java.lang.NullPointerException
     *                if <code>baseName</code>, <code>locale</code>, or <code>loader</code> is
     *                <code>null</code>
     * @exception MissingResourceException
     *                if no resource bundle for the specified base name can be found
     * @since 1.2
     */
    public static INlsBundle getBundle(String baseName, Locale locale, ClassLoader loader) {
        ResourceBundle resourceBundle = ResourceBundle.getBundle(baseName, locale, UTF8Control.INSTANCE);
        return new NlsFromResourceBundle(resourceBundle);
    }

}