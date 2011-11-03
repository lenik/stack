package com.bee32.plover.arch.naming;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.free.ParseException;
import javax.free.ParserUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bee32.plover.arch.Repository;

public abstract class RepositoryNode<K, Tn extends INamed>
        extends Repository<K, Tn>
        implements INamedNode {

    static Logger logger = LoggerFactory.getLogger(RepositoryNode.class);

    private INamedNode parentLocator;

    {
        // ReverseLookupRegistry.getInstance().register(this);
    }

    public RepositoryNode() {
        super();
    }

    public RepositoryNode(String name) {
        super(name);
    }

    public RepositoryNode(Class<? extends K> keyType, Class<? extends Tn> objectType) {
        super(keyType, objectType);
    }

    public RepositoryNode(String name, Class<? extends K> keyType, Class<? extends Tn> objectType) {
        super(name, keyType, objectType);
    }

    // --o INamedNode

    public K convertRefNameToKey(String refName)
            throws ParseException {
        if (keyType == String.class)
            return keyType.cast(refName);

        if (keyType == Integer.class) {
            Integer integerId = Integer.valueOf(refName);
            return keyType.cast(integerId);
        }

        if (keyType == Long.class) {
            Long longId = Long.valueOf(refName);
            return keyType.cast(longId);
        }

        return ParserUtil.parse(keyType, refName);
    }

    public String convertKeyToRefName(K key) {
        return String.valueOf(key);
    }

    @Override
    public String refName() {
        return getName();
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
        return objectType;
    }

    @Override
    public Object getChild(String location) {
        K parsedKey;
        try {
            parsedKey = convertRefNameToKey(location);
        } catch (ParseException e) {
            if (logger.isDebugEnabled())
                logger.warn("Bad location token: " + location + ", for " + this, e);
            return null;
        }
        return get(parsedKey);
    }

    @Override
    public boolean hasChild(Object obj) {
        if (obj == null)
            return false;

        if (!objectType.isInstance(obj))
            return false;

        return true;
    }

    @Override
    public String getChildName(Object obj) {
        Tn instance = objectType.cast(obj);
        return instance.refName();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Collection<String> getChildNames() {
        Collection<? extends K> keys = keys();

        if (keyType == String.class)
            return (Collection<String>) keys;

        List<String> keyStrings = new ArrayList<String>(keys.size());
        for (K key : keys) {
            String keyString = convertKeyToRefName(key);
            keyStrings.add(keyString);
        }

        return keyStrings;
    }

    @Override
    public Iterable<?> getChildren() {
        return list();
    }

}
