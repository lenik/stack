package com.bee32.plover.orm.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.free.ParseException;
import javax.free.ParserUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bee32.plover.arch.Repository;
import com.bee32.plover.arch.naming.INamedNode;
import com.bee32.plover.arch.naming.ReverseLookupRegistry;

public abstract class TreeRepository<E extends IEntity<K>, K extends Serializable>
        extends Repository<K, E>
        implements INamedNode {

    static Logger logger = LoggerFactory.getLogger(TreeRepository.class);

    private INamedNode parentLocator;

    {
        ReverseLookupRegistry.getInstance().register(this);
    }

    public TreeRepository() {
        super();
    }

    public TreeRepository(String name) {
        super(name);
    }

    // --o INamedNode

    protected K parseKey(String keyString)
            throws ParseException {
        if (keyType == String.class)
            return keyType.cast(keyString);

        if (keyType == Integer.class) {
            Integer integerId = Integer.valueOf(keyString);
            return keyType.cast(integerId);
        }

        if (keyType == Long.class) {
            Long longId = Long.valueOf(keyString);
            return keyType.cast(longId);
        }

        return ParserUtil.parse(keyType, keyString);
    }

    protected String formatKey(K key) {
        return String.valueOf(key);
    }

    @Override
    public int getPriority() {
        return 0;
    }

    @Override
    public INamedNode getParent() {
        return parentLocator;
    }

    public void setParent(INamedNode parentLocator) {
        this.parentLocator = parentLocator;
    }

    @Override
    public Class<?> getChildType() {
        return valueType;
    }

    @Override
    public Object getChild(String location) {
        K parsedKey;
        try {
            parsedKey = parseKey(location);
        } catch (ParseException e) {
            if (logger.isDebugEnabled())
                logger.warn("Bad location token: " + location + ", for " + this, e);
            return null;
        }
        return get(parsedKey);
    }

    @Override
    public boolean hasChild(Object entity) {
        if (entity == null)
            return false;

        if (!valueType.isInstance(entity))
            return false;

        return true;
    }

    @Override
    public String getChildName(Object entity) {
        E instance = valueType.cast(entity);
        K key = instance.getId();
        String keyLocation = formatKey(key);
        return keyLocation;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Collection<String> getChildNames() {
        Collection<? extends K> keys = keys();

        if (keyType == String.class)
            return (Collection<String>) keys;

        List<String> keyStrings = new ArrayList<String>(keys.size());
        for (K key : keys) {
            String keyString = formatKey(key);
            keyStrings.add(keyString);
        }

        return keyStrings;
    }

    @Override
    public Iterable<?> getChildren() {
        return list();
    }

}
