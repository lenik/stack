package com.bee32.plover.model.schema;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.free.DecodeException;
import javax.free.EncodeException;
import javax.free.FinalNegotiation;
import javax.free.ICommonTraits;
import javax.free.IFormatter;
import javax.free.IParser;
import javax.free.IValidator;
import javax.free.Nullables;
import javax.free.Optional;
import javax.free.PrefetchedIterator;
import javax.free.Traits;
import javax.free.ValidateException;

import com.bee32.plover.arch.Component;
import com.bee32.plover.arch.util.LoadFlags32;
import com.bee32.plover.model.qualifier.Qualifier;
import com.bee32.plover.model.qualifier.QualifierMap;
import com.bee32.plover.model.stereo.StereoType;

/**
 * @param <?> Type of the value which this element refers to.
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public abstract class AbstractSchema
        extends Component
        implements ISchema {

    private final StereoType stereo;

    private final Class<?> type;

    private PreferenceLevel preferenceLevel = PreferenceLevel.INTERMEDIATE;

    private QualifierMap qualifierMap;
    // private boolean qualifiersLoaded;

    private transient Map<SchemaKey, ISchema> children;
    private transient boolean childrenLoaded;

    private transient LoadFlags32 flags;
    private static final int HAVE_TRAITS = 1 << 0;
    private static final int HAVE_PARSER = 1 << 1;
    private static final int HAVE_FORMATTER = 1 << 2;
    private static final int HAVE_VALIDATOR = 1 << 3;

    private transient ICommonTraits traits;
    private transient IParser parser;
    private transient IFormatter formatter;
    private transient IValidator validator;

    public AbstractSchema(String name, StereoType stereo, Class<?> type) {
        super(name);
        if (stereo == null)
            throw new NullPointerException("stereo");
        if (type == null)
            throw new NullPointerException("type");
        this.stereo = stereo;
        this.type = type;
    }

    @Override
    public StereoType getStereoType() {
        return stereo;
    }

    @Override
    public SchemaKey getKey() {
        return new SchemaKey(stereo, name);
    }

    /**
     * Get the data type this element refers to.
     * <p>
     * For {@link StereoType#PROPERTY properties}, the type is the property type.
     *
     * For {@link StereoType#COMMAND methods}, the type is the returned type of the corresponding
     * method.
     */
    @Override
    public Class<?> getType() {
        return type;
    }

    @Override
    public PreferenceLevel getPreferenceLevel() {
        return preferenceLevel;
    }

    public void setPreferenceLevel(PreferenceLevel preferenceLevel) {
        if (preferenceLevel == null)
            throw new NullPointerException("preferenceLevel");
        this.preferenceLevel = preferenceLevel;
    }

    @Override
    public boolean isTransient() {
        return false;
    }

    // Qualifier Support

    protected QualifierMap getQualifierMap() {
        if (qualifierMap == null)
            qualifierMap = loadQualifierMap();
        return qualifierMap;
    }

    protected abstract QualifierMap loadQualifierMap();

    @Override
    public Iterable<Qualifier<?>> getQualifiers() {
        return qualifierMap.getQualifiers();
    }

    @Override
    public <Q extends Qualifier<Q>> Iterable<Q> getQualifiers(Class<Q> qualifierType) {
        return qualifierMap.getQualifiers(qualifierType);
    }

    @Override
    public <Q extends Qualifier<Q>> Q getQualifier(Class<Q> qualifierType) {
        return qualifierMap.getQualifier(qualifierType);
    }

    // Composition

    protected abstract void loadChildren(Map<SchemaKey, ISchema> map);

    Map<SchemaKey, ISchema> getChildren() {
        if (children == null)
            children = new HashMap<SchemaKey, ISchema>();
        if (!childrenLoaded)
            loadChildren(children);
        return children;
    }

    public void add(ISchema element) {
        StereoType stereoType = element.getStereoType();
        String name = element.getName();
        SchemaKey key = new SchemaKey(stereoType, name);
        getChildren().put(key, element);
    }

    public void remove(ISchema element) {
        StereoType stereoType = element.getStereoType();
        String name = element.getName();
        SchemaKey key = new SchemaKey(stereoType, name);
        getChildren().remove(key);
    }

    @Override
    public Iterator<ISchema> iterator() {
        return getChildren().values().iterator();
    }

    static class StereoFilter
            implements Iterable<ISchema> {

        private final Iterable<ISchema> schemas;
        private final StereoType stereoType;

        public StereoFilter(Iterable<ISchema> schemas, StereoType stereoType) {
            this.schemas = schemas;
            this.stereoType = stereoType;
        }

        @Override
        public Iterator<ISchema> iterator() {
            final Iterator<ISchema> orig = schemas.iterator();

            return new PrefetchedIterator<ISchema>() {

                @Override
                protected ISchema fetch() {
                    if (orig.hasNext()) {
                        ISchema schema = orig.next();
                        if (Nullables.equals(schema.getStereoType(), stereoType))
                            return schema;
                    }
                    return next();
                }

            };
        }
    }

    @Override
    public Iterable<ISchema> restrict(StereoType stereoType) {
        return new StereoFilter(this, stereoType);
    }

    @Override
    public ISchema get(SchemaKey schemaKey) {
        return getChildren().get(schemaKey);
    }

    @Override
    public ISchema getProperty(String name) {
        return get(new SchemaKey(StereoType.PROPERTY, name));
    }

    // Traits -> encode/decode/validator implementation.

    public ICommonTraits<Object> getTraits() {
        if (flags.checkAndLoad(HAVE_TRAITS)) {
            this.traits = loadTraits();
        }
        return traits;
    }

    protected ICommonTraits<?> loadTraits() {
        return Traits.getTraits(type, ICommonTraits.class);
    }

    @Override
    public Object decodeText(String s, Object enclosingObject)
            throws DecodeException {
        if (flags.checkAndLoad(HAVE_PARSER))
            parser = getTraits().getParser();

        if (parser == null)
            throw new UnsupportedOperationException("Don't know how to decode text for " + type);

        try {
            return parser.parse(s, new FinalNegotiation(//
                    new Optional(IParser.PARSE_CONTEXT, enclosingObject)));
        } catch (Exception e) {
            throw new DecodeException(e.getMessage(), e);
        }
    }

    @Override
    public String encodeText(Object value, Object enclosingObject)
            throws EncodeException {
        if (flags.checkAndLoad(HAVE_FORMATTER))
            formatter = getTraits().getFormatter();

        if (formatter == null)
            throw new UnsupportedOperationException("Don't know how to encode " + type);

        try {
            return formatter.format(value);
        } catch (Exception e) {
            throw new EncodeException(e);
        }
    }

    @Override
    public void validate(Object value, Object enclosingObject)
            throws ValidateException {
        if (flags.checkAndLoad(HAVE_VALIDATOR))
            validator = getTraits().getValidator();

        if (validator == null)
            return;

        validator.validate(value);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (!obj.getClass().equals(getClass()))
            return false;

        AbstractSchema o = (AbstractSchema) obj;

        if (!type.equals(o.type))
            return false;

        if (!Nullables.equals(getName(), o.getName()))
            return false;

        if (preferenceLevel != o.preferenceLevel)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int hash = 8432837;

        String name = getName();
        if (name != null)
            hash += name.hashCode();

        hash += type.hashCode();
        hash += preferenceLevel.hashCode();
        return hash;
    }

    @Override
    public String toString() {
        String name = getName();
        if (name == null)
            name = "(noname)";
        return name + " schema element";
    }

}
