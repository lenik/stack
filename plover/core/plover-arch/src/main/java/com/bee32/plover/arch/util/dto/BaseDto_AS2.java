package com.bee32.plover.arch.util.dto;

import java.beans.IntrospectionException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.free.IllegalUsageException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.dao.DataAccessException;

public abstract class BaseDto_AS2<S, C>
        extends BaseDto_AS1<S, C> {

    private static final long serialVersionUID = 1L;

    // marshal*

    public <_S, _D extends BaseDto<_S, _C>, _C> _D marshal(Class<_D> dtoClass, int selection, _S source) {
        return marshal(dtoClass, selection, source, null);
    }

    public <_S, _D extends BaseDto<_S, _C>, _C> _D marshal(Class<_D> dtoClass, _S source) {
        return marshal(dtoClass, -1, source, null);
    }

    public <_S, _D extends BaseDto<_S, _C>, _C> _D marshal(Class<_D> dtoClass, int selection, _S source,
            boolean refButFilled) {
        return marshal(dtoClass, selection, source, (Boolean) refButFilled);
    }

    public <_S, _D extends BaseDto<_S, _C>, _C> _D marshal(Class<_D> dtoClass, _S source, boolean refButFilled) {
        return marshal(dtoClass, -1, source, (Boolean) refButFilled);
    }

    // mref(...) = marshal(..., true)

    public <_S, _D extends BaseDto<_S, _C>, _C> _D mref(Class<_D> dtoClass, int selection, _S source) {
        return marshal(dtoClass, selection, source, true);
    }

    public <_S, _D extends BaseDto<_S, _C>, _C> _D mref(Class<_D> dtoClass, _S source) {
        return marshal(dtoClass, -1, source, true);
    }

    // marshalList*

    public <_S, _D extends BaseDto<_S, _C>, _C> List<_D> marshalList(//
            Class<_D> dtoClass, int selection, Iterable<? extends _S> sources) {
        return marshalList(dtoClass, selection, sources, null);
    }

    public <_S, _D extends BaseDto<_S, _C>, _C> List<_D> marshalList(//
            Class<_D> dtoClass, Iterable<? extends _S> sources) {
        return marshalList(dtoClass, -1, sources, null);
    }

    public <_S, _D extends BaseDto<_S, _C>, _C> List<_D> marshalList(//
            Class<_D> dtoClass, int selection, Iterable<? extends _S> sources, boolean refButFilled) {
        return marshalList(dtoClass, selection, sources, (Boolean) refButFilled);
    }

    public <_S, _D extends BaseDto<_S, _C>, _C> List<_D> marshalList(//
            Class<_D> dtoClass, Iterable<? extends _S> sources, boolean refButFilled) {
        return marshalList(dtoClass, -1, sources, (Boolean) refButFilled);
    }

    // marshalSet

    public <_S, _D extends BaseDto<_S, _C>, _C> Set<_D> marshalSet(//
            Class<_D> dtoClass, int selection, Iterable<? extends _S> sources) {
        return marshalSet(dtoClass, selection, sources, null);
    }

    public <_S, _D extends BaseDto<_S, _C>, _C> Set<_D> marshalSet(//
            Class<_D> dtoClass, Iterable<? extends _S> sources) {
        return marshalSet(dtoClass, -1, sources, null);
    }

    public <_S, _D extends BaseDto<_S, _C>, _C> Set<_D> marshalSet(//
            Class<_D> dtoClass, int selection, Iterable<? extends _S> sources, boolean refButFilled) {
        return marshalSet(dtoClass, selection, sources, (Boolean) refButFilled);
    }

    public <_S, _D extends BaseDto<_S, _C>, _C> Set<_D> marshalSet(//
            Class<_D> dtoClass, Iterable<? extends _S> sources, boolean refButFilled) {
        return marshalSet(dtoClass, -1, sources, (Boolean) refButFilled);
    }

    // _unmarshalCollection*

    public <_S, _D extends BaseDto<_S, _C>, _C> //
    /*    */List<_S> _unmarshalList(Iterable<? extends _D> dtoList) {
        return _unmarshalCollection(new ArrayList<_S>(), dtoList);
    }

    public <_S, _D extends BaseDto<_S, _C>, _C> //
    /*    */Set<_S> _unmarshalSet(Iterable<? extends _D> dtoList) {
        return _unmarshalCollection(new HashSet<_S>(), dtoList);
    }

    // merge*

    /**
     * Unmarshal a property DTO into the corresponding property of an entity in a smart way.
     * <p>
     * The contents in the given property DTO may be fully unmarshalled to a new created entity
     * bean, or unmarshalled to be reference to the existing entity, or between. The behaviour is
     * determined on the two states of the DTO object: the <i>filled</i> state, and the <i>id</i>
     * state.
     * <p>
     *
     * Transfer table:
     * <table border="1">
     * <tr>
     * <th>DTO</th>
     * <th>DTO.filled</th>
     * <th>DTO.id</th>
     * <th>Meaning</th>
     * <th>Result</th>
     * </tr>
     * <tr>
     * <td align="center"><code>null</code></td>
     * <td align="center">(n/a)</td>
     * <td align="center">(n/a)</td>
     * <td align="center">Skip, no change</td>
     * <td align="left">ENTITY</td>
     * </tr>
     * <tr>
     * <td align="center">*</td>
     * <td align="center">not-filled</td>
     * <td align="center"><code>null</code></td>
     * <td align="center">Set to null</td>
     * <td align="left"><code>null</code></td>
     * </tr>
     * <tr>
     * <td align="center">*</td>
     * <td align="center">not-filled</td>
     * <td align="center">id</td>
     * <td align="center">Reference</td>
     * <td align="left">ENTITY = reference&lt;dto.id&gt;</td>
     * </tr>
     * <tr>
     * <td align="center">*</td>
     * <td align="center">filled</td>
     * <td align="center"><code>null</code></td>
     * <td align="center">Full Reconstruction</td>
     * <td align="left">ENTITY = unmarshal()</td>
     * </tr>
     * <tr>
     * <td align="center">*</td>
     * <td align="center">filled</td>
     * <td align="center">id</td>
     * <td align="center">Partial Modification</td>
     * <td align="left">unmarshalTo(ENTITY = reference&lt;dto.id&gt;)</td>
     * </tr>
     * </table>
     *
     * The DTO is only filled if:
     * <ul>
     * <li>It has been {@link BaseDto#marshal(Object) marhsalled} from an entity bean.
     * <li>It has {@link BaseDto#parse(Map) parsed from a Map}, or
     * {@link BaseDto#parse(HttpServletRequest) from an HttpServletRequest}.</li>
     *
     * You can always clear the filled state by reference the DTO to a specific id, by calling the
     * {@link EntityDto#ref(Entity) ref} method.
     *
     * @param property
     *            The property accessor which is used to get the old property value, and set the
     *            property to a new value.
     * @param dto
     *            The data transfer object which will be unmarshalled into the property of the
     *            context target bean, using the specified property accessor.
     * @return This {@link WithContext} object, for chaining method calls purpose.
     * @throws DataAccessException
     *             If data access exception happened with calls into the
     *             {@link IEntityMarshalContext}.
     */
    public <_S, __s, _C> void merge(_S target, String propertyName, BaseDto<__s, _C> propertyDto) {

        // DTO == null means ignore.
        if (propertyDto == null)
            return;

        Class<_S> targetType = (Class<_S>) target.getClass();

        IPropertyAccessor<_S, __s> property;
        try {
            property = BeanPropertyAccessor.access(targetType, propertyName);
        } catch (IntrospectionException e) {
            // XXX Error message?
            throw new IllegalUsageException(e.getMessage(), e);
        }

        merge(target, property, propertyDto);
    }

}
