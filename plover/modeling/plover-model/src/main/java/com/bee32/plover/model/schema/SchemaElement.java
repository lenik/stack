package com.bee32.plover.model.schema;

import javax.free.DecodeException;
import javax.free.EncodeException;
import javax.free.ICommonTraits;
import javax.free.IFormatter;
import javax.free.IParser;
import javax.free.IValidator;
import javax.free.Nullables;
import javax.free.ParseException;
import javax.free.Traits;
import javax.free.ValidateException;

import com.bee32.plover.arch.Component;
import com.bee32.plover.arch.util.LoadFlags32;
import com.bee32.plover.model.qualifier.Qualifier;
import com.bee32.plover.model.qualifier.QualifierMap;
import com.bee32.plover.model.stereo.StereoType;

/**
 * @param <T>
 *            Type of the value which this element refers to.
 */
public class SchemaElement<T>
        extends Component
        implements ISchemaElement<T> {

    private final StereoType stereo;

    private final Class<T> type;

    private PreferenceLevel preferenceLevel = PreferenceLevel.INTERMEDIATE;

    private QualifierMap qualifierMap; // XXX

    private transient LoadFlags32 flags;
    private static final int HAVE_TRAITS = 1 << 0;
    private static final int HAVE_PARSER = 1 << 1;
    private static final int HAVE_FORMATTER = 1 << 2;
    private static final int HAVE_VALIDATOR = 1 << 3;

    private transient ICommonTraits<T> traits;
    private transient IParser<T> parser;
    private transient IFormatter<T> formatter;
    private transient IValidator<T> validator;

    public SchemaElement(String name, StereoType stereo, Class<T> type) {
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

    /**
     * Get the data type this element refers to.
     * <p>
     * For {@link StereoType#PROPERTY properties}, the type is the property type.
     *
     * For {@link StereoType#COMMAND methods}, the type is the returned type of the corresponding
     * method.
     */
    @Override
    public Class<T> getType() {
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
    public Iterable<? extends Qualifier<?>> getQualifiers() {
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

    public ICommonTraits<T> getTraits() {
        if (flags.checkAndLoad(HAVE_TRAITS)) {

            @SuppressWarnings("unchecked")
            ICommonTraits<T> _traits = Traits.getTraits(type, ICommonTraits.class);

            this.traits = _traits;
        }
        return traits;
    }

    @Override
    public T decodeText(String s)
            throws DecodeException {
        if (flags.checkAndLoad(HAVE_PARSER))
            parser = getTraits().getParser();

        if (parser == null)
            throw new UnsupportedOperationException("Don't know how to decode text for " + type);

        try {
            return parser.parse(s);
        } catch (ParseException e) {
            throw new DecodeException(e.getMessage(), e);
        }
    }

    @Override
    public String encodeText(T value)
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
    public void validate(T value)
            throws ValidateException {
        if (flags.checkAndLoad(HAVE_VALIDATOR))
            validator = getTraits().getValidator();

        if (validator == null)
            return;

        validator.validate(value);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof SchemaElement))
            return false;
        SchemaElement<?> o = (SchemaElement<?>) obj;

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
