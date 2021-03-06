package com.bee32.sem.misc;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;
import java.util.Map.Entry;

import javax.free.ChainUsage;
import javax.free.Dates;
import javax.free.IllegalUsageException;
import javax.free.OverrideOption;
import javax.free.ParseException;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bee32.icsf.access.AccessControlException;
import com.bee32.icsf.access.Permission;
import com.bee32.icsf.access.acl.ACLCriteria;
import com.bee32.icsf.access.shield.AclEasTxWrapper;
import com.bee32.icsf.login.SessionUser;
import com.bee32.icsf.principal.Principal;
import com.bee32.icsf.principal.PrincipalDto;
import com.bee32.icsf.principal.User;
import com.bee32.icsf.principal.UserCriteria;
import com.bee32.plover.arch.operation.Operation;
import com.bee32.plover.arch.util.ClassUtil;
import com.bee32.plover.arch.util.GetOpenedObjectTransformer;
import com.bee32.plover.arch.util.PriorityComparator;
import com.bee32.plover.arch.util.dto.Fmask;
import com.bee32.plover.collections.Varargs;
import com.bee32.plover.criteria.hibernate.CriteriaComposite;
import com.bee32.plover.criteria.hibernate.Equals;
import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.plover.criteria.hibernate.InCollection;
import com.bee32.plover.criteria.hibernate.Or;
import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.orm.entity.EntityAccessor;
import com.bee32.plover.orm.entity.EntityFlags;
import com.bee32.plover.orm.entity.EntityUtil;
import com.bee32.plover.orm.util.EntityDto;
import com.bee32.plover.orm.web.EntityHelper;
import com.bee32.plover.ox1.c.CEntity;
import com.bee32.plover.ox1.color.UIEntityDto;
import com.bee32.plover.ox1.util.CommonCriteria;
import com.bee32.plover.restful.resource.StandardViews;
import com.bee32.sem.frame.search.ISearchFragmentsHolder;
import com.bee32.sem.frame.search.SearchFragment;
import com.bee32.sem.frame.search.SearchFragmentWrapper;
import com.bee32.sem.sandbox.AbstractCriteriaHolder;
import com.bee32.sem.sandbox.CriteriaHolderExpansion;
import com.bee32.sem.sandbox.EntityDataModelOptions;
import com.bee32.sem.sandbox.ZLazyDataModel;

/**
 * Suggested Section Order:
 *
 * <ol>
 * <li>Interface Implementation
 * <li>...
 * <li>User Properties...
 * <li>...
 * <li>Search
 * <li>MBeans
 * <li>Persistence
 * </ol>
 */
public abstract class SimpleEntityViewBean
        extends AbstractSimpleEntityView
        implements ISearchFragmentsHolder {

    private static final long serialVersionUID = 1L;
    static Logger logger = LoggerFactory.getLogger(SimpleEntityViewBean.class);

    public static Permission visiblePermission = new Permission(Permission.READ);

    protected Set<Serializable> requestWindow = new HashSet<Serializable>();
    List<ICriteriaElement> baseRestriction;
    ICriteriaElement userRestriction;
    boolean verifiedOnly;
    Map<String, List<SearchFragment>> searchFragmentMap = new LinkedHashMap<>();
    EntityDataModelOptions<?, ?> dataModelOptions;
    LazyDataModel<?> dataModel;

    public SimpleEntityViewBean() {
    }

    public <E extends Entity<K>, D extends EntityDto<? super E, K>, K extends Serializable> //
    /*    */SimpleEntityViewBean(Class<E> entityClass, Class<D> dtoClass, int fmask, ICriteriaElement... criteriaElements) {
        setEntityType(entityClass);
        setEntityDtoType(dtoClass);
        this.baseRestriction = Varargs.toList(criteriaElements);

        String requestIdList = ctx.view.getRequest().getParameter("id");
        if (requestIdList != null) {
            EntityHelper<E, K> eh = EntityHelper.getInstance(entityClass);
            for (String _id : requestIdList.split(",")) {
                _id = _id.trim();
                try {
                    K id = eh.parseId(_id);
                    requestWindow.add(id);
                } catch (ParseException e) {
                    throw new IllegalArgumentException(e.getMessage(), e);
                }
            }
        }

        this.dataModelOptions = new SevbEdmo<E, D>(//
                entityClass, dtoClass, fmask, new CriteriaHolderExpansion(new SevbCriteriaHolder()));
        this.dataModel = new SevbLazyDataModel<>(dataModelOptions);
    }

    class SevbEdmo<E extends Entity<?>, D extends EntityDto<? super E, ?>>
            extends EntityDataModelOptions<E, D> {

        private static final long serialVersionUID = 1L;

        public SevbEdmo(Class<E> entityClass, Class<D> dtoClass, int fmask, ICriteriaElement... criteriaElements) {
            super(entityClass, dtoClass, fmask, criteriaElements);
        }

        @Override
        public Class<E> getEntityClass() {
            return (Class<E>) SimpleEntityViewBean.this.getEntityType();
        }

        @Override
        public Class<D> getDtoClass() {
            return (Class<D>) SimpleEntityViewBean.this.getEntityDtoType();
        }

    }

    class SevbCriteriaHolder
            extends AbstractCriteriaHolder {

        private static final long serialVersionUID = 1L;

        @Override
        protected List<? extends ICriteriaElement> getCriteriaElements() {
            return SimpleEntityViewBean.this.composeBaseRestrictions();
        }

    }

    protected class SevbLazyDataModel<E extends Entity<?>, D extends EntityDto<? super E, ?>>
            extends ZLazyDataModel<E, D> {

        private static final long serialVersionUID = 1L;

        public SevbLazyDataModel(EntityDataModelOptions<E, D> options) {
            super(options);
        }

        @Override
        protected List<E> loadImpl(int first, int pageSize, String sortField, SortOrder sortOrder,
                Map<String, String> filters) {
            return SimpleEntityViewBean.this.loadImpl(this, first, pageSize, sortField, sortOrder, filters);
        }

        @Override
        protected int countImpl() {
            return SimpleEntityViewBean.this.countImpl(this);
        }

        List<E> _loadImpl(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters) {
            return super.loadImpl(first, pageSize, sortField, sortOrder, filters);
        }

        int _countImpl() {
            return super.countImpl();
        }

        /**
         * Select the first on initial.
         */
        @Override
        protected void postLoad(List<D> data) {
            D selection = (D) SimpleEntityViewBean.this.getSingleSelection();
            if (selection == null && !data.isEmpty()) {
                D first = data.get(0);
                SimpleEntityViewBean.this.setSingleSelection(first);
            }
        }

    }

    public String getExportFileName()
            throws UnsupportedEncodingException {
        String entityName = ClassUtil.getTypeName(getEntityType());
        String encoded = URLEncoder.encode(entityName, "utf-8");
        return encoded;
    }

    public LazyDataModel<?> getDataModel() {
        return dataModel;
    }

    protected final List<? extends ICriteriaElement> composeBaseRestrictions() {
        List<ICriteriaElement> join = new ArrayList<ICriteriaElement>();
        join.addAll(baseRestriction);

        Class<? extends Entity<?>> entityType = getEntityType();

        /** Only restrict owner for CEntity+ */
        if (CEntity.class.isAssignableFrom(entityType)) {
            Permission defaultPermission = AclEasTxWrapper.defaults.get(entityType);
            // Boolean anyOwner = AnyOwnerUtil.isForAnyOwner(getClass());
            // if (anyOwner) ...
            if (defaultPermission == null || !defaultPermission.implies(visiblePermission)) {
                SessionUser me = SessionUser.getInstance();
                join.add(Or.of(//
                        // owner in (currentUser.imset)
                        UserCriteria.ownedByCurrentUser(), //

                        // r-ace(entity-resource, currentUser.imset) > OWN ==> VISIBLE
                        // (disabled)

                        // acl in (currentUser.visibleACLs)
                        ACLCriteria.aclWithin(me.getACLs(visiblePermission), true)));
            }
        }

        if (requestWindow != null && !requestWindow.isEmpty()) {
            join.add(new InCollection("id", requestWindow));
        } else {
            if (verifiedOnly) {
                // TODO
            }
            composeBaseRestrictions(join);
        }

        if (userRestriction != null)
            join.add(userRestriction);

        for (List<SearchFragment> fragments : searchFragmentMap.values())
            for (SearchFragment fragment : fragments) {
                ICriteriaElement element = fragment.compose();
                if (element != null)
                    join.add(element);
            }
        return join;
    }

    @OverrideOption(chain = ChainUsage.PREFERRED)
    protected void composeBaseRestrictions(List<ICriteriaElement> elements) {
    }

    /**
     * User criteria.
     */
    public ICriteriaElement getRestriction() {
        return userRestriction;
    }

    public void setRestriction(ICriteriaElement restriction) {
        this.userRestriction = restriction;
    }

    protected <E extends Entity<?>> List<E> loadImpl(SevbLazyDataModel<E, ?> def, int first, int pageSize,
            String sortField, SortOrder sortOrder, Map<String, String> filters) {
        try {
            return def._loadImpl(first, pageSize, sortField, sortOrder, filters);
        } catch (AccessControlException e) {
            reportException(e, "??????", getEntityType());
            return Collections.emptyList();
        }
    }

    protected <E extends Entity<?>> int countImpl(SevbLazyDataModel<E, ?> def) {
        return def._countImpl();
    }

    /* ********************************************************************** */

    final TreeSet<SevbFriend> sortedFriends = new TreeSet<SevbFriend>(PriorityComparator.INSTANCE);
    final Map<String, SevbFriend> friendMap = new HashMap<String, SevbFriend>();

    public synchronized void addFriend(String name, SevbFriend friend) {
        if (sortedFriends.contains(friend))
            throw new IllegalUsageException("friend is existed: " + friend);
        sortedFriends.add(friend);
        friendMap.put(name, friend);
        addSelectionChangeListener(friend);
        addObjectOpenListener(friend);
    }

    public synchronized void removeFriend(String name) {
        SevbFriend friend = friendMap.remove(name);
        if (friend != null)
            sortedFriends.remove(friend);
        removeSelectionChangeListener(friend);
        removeObjectOpenListener(friend);
    }

    public Map<String, Object> getFriends() {
        return GetOpenedObjectTransformer.decorate(friendMap);
    }

    /* ********************************************************************** */

    @Operation
    public void showEditForm() {
        if (getSelection().isEmpty()) {
            uiLogger.error("??????????????????!");
            return;
        }

        boolean readOnly = false;
        for (Object sel : getSelection()) {
            EntityDto<?, ?> dto = (EntityDto<?, ?>) sel;
            EntityFlags ef = dto.getEntityFlags();
            if (ef.isLocked()) {
                uiLogger.warn("?????????????????????????????????????????????");
                readOnly = true;
                break;
            }
            if (ef.isUserLock()) {
                uiLogger.warn("????????????????????????????????????????????????");
                readOnly = true;
                break;
            }
            if (!ef.isOverrided()) {
                if (ef.isBuitlinData()) {
                    uiLogger.warn("??????????????????????????????: ?????????????????????????????????????????????????????????");
                    readOnly = true;
                    break;
                }
                if (ef.isTestData()) {
                    uiLogger.warn("???????????????????????????: ??????????????????????????????????????????????????????????????????");
                }
            }
        }

        openSelection();
        if (readOnly)
            showView(StandardViews.CONTENT);
        else
            showView(StandardViews.EDIT_FORM);
    }

    public void refresh() {
        switch (getCurrentView()) {
        case StandardViews.LIST:
            showIndex();
            break;
        case StandardViews.CONTENT:
        case StandardViews.CREATE_FORM:
        case StandardViews.EDIT_FORM:
            break;
        default:
            break;
        }
    }

    /*************************************************************************
     * Section: Persistence
     *************************************************************************/
    protected Object create() {
        EntityDto<?, ?> entityDto;
        try {
            entityDto = getEntityDtoType().newInstance(); // Create fmask is always F_MORE..
            entityDto.setFmask(Fmask.F_MORE);
            entityDto = entityDto.create();
        } catch (Exception e) {
            uiLogger.error("??????????????????", e);
            return null;
        }
        return entityDto;
    }

    @Operation
    public final boolean saveDup() {
        // Re-mark as new-created object.
        if (!save(SAVE_DUP | SAVE_CONT, null))
            return false;
        // showCreateForm();
        return true;
    }

    protected Integer getFmaskOverride(int saveFlags) {
        return null;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    protected boolean saveImpl(int saveFlags, String hint, boolean creating) {
        Class<? extends Entity<?>> entityType = getEntityType();

        List<EntityDto<?, ?>> dtos = getOpenedObjects();
        try {
            if (checkDuplicatesBeforeCreate && creating) {
                List<Entity<?>> duplicates = new ArrayList<Entity<?>>();
                for (EntityDto<?, ?> dto : dtos) {
                    checkDuplicates(dto, duplicates);
                }
                if (!duplicates.isEmpty()) {
                    StringBuilder mesg = new StringBuilder("????????????????????????<ul>");
                    for (Entity<?> dup : duplicates) {
                        String dupLabel = dup.getEntryLabel();
                        mesg.append("<li>" + dupLabel + "</li>");
                    }
                    mesg.append("</ul>");
                    uiHtmlLogger.error(mesg);
                    return false;
                }
            }

            for (SevbFriend friend : sortedFriends)
                if (!friend.postValidate(dtos))
                    return false;
            if (!__postValidate(dtos))
                return false;
            if (!postValidate(dtos))
                return false;
        } catch (Exception e) {
            uiLogger.error("??????????????????", e);
        }

        if ((saveFlags & SAVE_DUP) != 0) {
            for (EntityDto<?, ?> dto : dtos) {
                dto.copy();
                dto.clearId();
            }
        }

        UnmarshalMap uMap;
        try {
            uMap = unmarshalDtos(dtos, getFmaskOverride(saveFlags), false/* result not-null */);
        } catch (Exception e) {
            uiLogger.error("???????????????", e);
            return false;
        }
        // For debug purpose.
        for (Entry<Entity<?>, EntityDto<?, ?>> _ent : uMap.entrySet()) {
            Entity<?> entity = _ent.getKey();
            EntityDto<?, ?> dto = _ent.getValue();
            logger.trace("Unmarshalled {} => {}", entity, dto);
        }

        User me = SessionUser.getInstance().getInternalUserOpt();
        List<Entity<?>> lockedList = new ArrayList<Entity<?>>();
        for (Entity<?> entity : uMap.keySet()) {
            EntityFlags flags = EntityAccessor.getFlags(entity);

            if ((saveFlags & SAVE_FORCE) != 0) {
                if (flags.isUserLock())
                    flags.setUserLock(false);
            }

            boolean locked = EntityAccessor.isAnyLocked(entity);
            if (locked) {
                lockedList.add(entity);
                continue;
            }

            if ((saveFlags & SAVE_LOCKED) != 0)
                flags.setUserLock(true);

            if ((saveFlags & SAVE_UNLOCKED) != 0)
                flags.setUserLock(false);

            if ((saveFlags & SAVE_CHOWN) != 0 && me != null) {
                if (entity instanceof CEntity<?>) {
                    CEntity<?> ce = (CEntity<?>) entity;
                    ce.setOwner(me);
                }
            }
        }

        if (!checkLockList(lockedList, hint))
            return false;

        try {
            if (!__preUpdate(uMap))
                return false;
            for (SevbFriend friend : sortedFriends)
                if (!friend.preUpdate(uMap))
                    return false;
            if (!preUpdate(uMap))
                return false;
        } catch (Exception e) {
            uiLogger.error("???????????????", e);
            return false;
        }

        Set<Entity<?>> entities = uMap.keySet();
        EntityAccessor.updateTimestamp(entities);
        try {
            if ((saveFlags & SAVE_MUSTEXIST) != 0)
                for (Entity<?> entity : entities)
                    DATA(entityType).update(entity);
            else if ((saveFlags & SAVE_NOEXIST) != 0)
                DATA(entityType).saveAll(entities);
            else
                DATA(entityType).saveOrUpdateAll(entities);
        } catch (Exception e) {
            uiLogger.error(hint + "??????", e);
            return false;
        }

        if (creating) // write back generated-id(s).
            for (Entity<?> entity : entities) {
                EntityDto<?, Serializable> dto = uMap.getSourceDto(entity);
                Serializable newId = entity.getId();
                dto.setId(newId);
            }

        for (UnmarshalMap subMap : uMap.getDeltaMaps().values()) {
            Class<?> subEntityClass = subMap.getEntityClass();
            Set<Entity<?>> subEntities = subMap.keySet();
            EntityAccessor.updateTimestamp(subEntities);
            try {
                DATA((Class) subEntityClass).saveOrUpdateAll(subEntities);
            } catch (Exception e) {
                uiLogger.error(hint + subMap.getLabel() + "?????????", e);
                return false;
            }
        }

        for (SevbFriend friend : sortedFriends) {
            try {
                friend.saveOpenedObject(saveFlags, uMap);
            } catch (Exception e) {
                uiLogger.error(hint + "????????????" + friend, e);
                return false;
            }
        }

        if ((saveFlags & SAVE_NO_REFRESH) == 0)
            refreshRowCount();

        boolean warned = false;

        try {
            postUpdate(uMap);
            for (SevbFriend friend : sortedFriends)
                friend.postUpdate(uMap);
            __postUpdate(uMap);
        } catch (Exception e) {
            uiLogger.warn(hint + "??????????????????????????????????????????????????????????????????????????????", e);
            warned = true;
        }

        if (!warned)
            uiLogger.info(hint + "??????");

        return true;
    }

    @Operation
    public final void deleteSelection() {
        deleteSelection(deleteFlags);
    }

    protected void deleteSelection(int deleteFlags) {
        checkDeleteFlags(deleteFlags);

        if (getSelection().isEmpty()) {
            uiLogger.error("??????????????????!");
            return;
        }

        UnmarshalMap uMap;
        try {
            uMap = unmarshalDtos(getSelection(), null, true/* result nullable */);
        } catch (Exception e) {
            uiLogger.error("???????????????", e);
            return;
        }

        List<Entity<?>> lockedList = new ArrayList<Entity<?>>();
        for (Entity<?> entity : uMap.keySet()) {
            boolean needUpdateBeforeDelete = false;

            // force to delete locked
            if ((deleteFlags & DELETE_FORCE) != 0) {
                EntityFlags flags = EntityAccessor.getFlags(entity);
                if (flags.isUserLock()) {
                    flags.setUserLock(false);
                    // save & reload it before delete..?
                    needUpdateBeforeDelete = true;
                }
            }

            if ((deleteFlags & DELETE_DETACH) != 0) {
                entity.detach();
                needUpdateBeforeDelete = true; // optim: only detach changes something..?
            }

            if (needUpdateBeforeDelete) {
                DATA(getEntityType()).update(entity);
            }

            boolean locked = EntityAccessor.isAnyLocked(entity);
            if (locked) {
                lockedList.add(entity);
                continue;
            }

            EntityFlags ef = EntityAccessor.getFlags(entity);
            // if (!ef.isWeakData()) ...: WEAK_DATA is skipped in deleteion.
            if (ef.isBuitlinData()) {
                uiLogger.error("??????????????????????????????: ?????????????????????????????????????????????????????????");
                return;
            }
        }

        if (!checkLockList(lockedList, "??????"))
            return;

        try {
            if (!__preDelete(uMap))
                return;
            for (SevbFriend friend : sortedFriends)
                if (!friend.preDelete(uMap))
                    return;
            if (!preDelete(uMap))
                return;
        } catch (Exception e) {
            uiLogger.error("?????????????????????????????????", e);
            return;
        }

        for (SevbFriend friend : sortedFriends) {
            try {
                friend.deleteSelection(deleteFlags);
            } catch (Exception e) {
                uiLogger.error("??????????????????:" + friend, e);
                return;
            }
        }

        Set<Entity<?>> entities = uMap.keySet();
        int count;
        try {
            count = DATA(getEntityType()).deleteAll(entities);
        } catch (Exception e) {
            uiLogger.error("????????????", e);
            return;
        }

        setSelection(null);

        if ((deleteFlags & DELETE_NO_REFRESH) == 0)
            refreshRowCount();

        try {
            postDelete(uMap);
            for (SevbFriend friend : sortedFriends)
                friend.postDelete(uMap);
            __postDelete(uMap);
        } catch (Exception e) {
            uiLogger.warn("????????????????????????????????????????????????????????????????????????????????????", e);
            return;
        }

        String countHint = count == -1 ? "" : (" [" + count + "]");
        uiLogger.info("????????????" + countHint);
    }

    protected boolean checkLockList(Iterable<? extends Entity<?>> lockedList, String hint) {
        StringBuilder buf = null;
        for (Entity<?> lockedEntity : lockedList) {
            if (buf == null)
                buf = new StringBuilder("????????????????????????\n");
            else
                buf.append(", \n");
            String entryText = lockedEntity.getEntryLabel();
            buf.append("    " + entryText);
        }
        if (buf != null) {
            uiLogger.error("??????" + hint + "???????????????: " + buf.toString());
            return false;
        } else
            return true;
    }

    protected void checkDuplicates(EntityDto<?, ?> creating, List<Entity<?>> duplicates) {
        // Check UI-Entity.
        if (creating instanceof UIEntityDto<?, ?>) {
            UIEntityDto<?, ?> o = (UIEntityDto<?, ?>) creating;

            // Check for duplicated labels.
            if (checkDuplicatedLabel) {
                String label = o.getLabel();
                if (label != null && !label.trim().isEmpty()) { // Only check non-empty labels.
                    Entity<?> first = DATA(getEntityType()).getFirst(new Equals("label", label));
                    if (first != null)
                        duplicates.add(first);
                }
            }
        }
    }

    protected boolean __postValidate(List<?> dtos)
            throws Exception {
        return true;
    }

    protected boolean postValidate(List<?> dtos)
            throws Exception {
        return true;
    }

    /**
     * Perform pre-condition checking before update.
     *
     * You must invoke super._preUpdate at first, and return immediately if super returns false.
     *
     * @return <code>false</code> if update should be canceled. The implementation should raise
     *         error messages if necessary.
     */
    protected boolean __preUpdate(UnmarshalMap uMap)
            throws Exception {
        return true;
    }

    protected void __postUpdate(UnmarshalMap uMap)
            throws Exception {
    }

    /**
     * Perform pre-condition checking before delete.
     *
     * You must invoke super._preDelete at first, and return immediately if super returns false.
     *
     * @return <code>false</code> if delete should be canceled. The implementation should raise
     *         error messages if necessary.
     */
    protected boolean __preDelete(UnmarshalMap uMap)
            throws Exception {
        return true;
    }

    protected void __postDelete(UnmarshalMap uMap)
            throws Exception {
    }

    /**
     * Perform pre-condition checking before update.
     *
     * @return <code>false</code> if update should be canceled. The implementation should raise
     *         error messages if necessary.
     */
    protected boolean preUpdate(UnmarshalMap uMap)
            throws Exception {
        return true;
    }

    protected void postUpdate(UnmarshalMap uMap)
            throws Exception {
    }

    /**
     * Perform pre-condition checking before delete.
     *
     * @return <code>false</code> if delete should be canceled. The implementation should raise
     *         error messages if necessary.
     */
    protected boolean preDelete(UnmarshalMap uMap)
            throws Exception {
        return true;
    }

    protected void postDelete(UnmarshalMap uMap)
            throws Exception {
    }

    UnmarshalMap unmarshalDtos(Collection<?> objects, Integer fmaskOverride, boolean nullable) {
        UnmarshalMap resultMap = new UnmarshalMap();
        for (Object object : objects) {
            if (getEntityDtoType().isInstance(object)) {
                EntityDto<?, ?> entityDto = (EntityDto<?, ?>) object;

                int oldFmask = entityDto.getFmask();
                if (fmaskOverride != null)
                    entityDto.setFmask(fmaskOverride);

                Entity<?> entity;
                try {
                    entity = entityDto.unmarshal();
                } finally {
                    if (fmaskOverride != null)
                        entityDto.setFmask(oldFmask);
                }

                if (entity == null) {
                    if (!nullable)
                        throw new NullPointerException("DTO unmarshalled to null: " + entityDto);
                    else
                        continue;
                }
                resultMap.put(entity, entityDto);
            }
        }
        return resultMap;
    }

    /*************************************************************************
     * Section: Search
     *************************************************************************/
    protected String searchId;
    protected String searchPattern;
    protected DateRangeTemplate dateRange = DateRangeTemplate.recentWeek;
    protected Date beginDate;
    protected Date endDate;
    protected PrincipalDto searchPrincipal; // TODO implication opt.

    public List<SearchFragment> getSearchFragments() {
        List<SearchFragment> all = new ArrayList<SearchFragment>();
        for (List<SearchFragment> fragments : searchFragmentMap.values())
            all.addAll(fragments);
        return all;
    }

    @Override
    public List<SearchFragment> getSearchFragments(String groupId) {
        if (groupId == null)
            throw new NullPointerException("groupId");
        List<SearchFragment> fragments = searchFragmentMap.get(groupId);
        if (fragments == null) {
            fragments = new ArrayList<SearchFragment>();
            searchFragmentMap.put(groupId, fragments);
        }
        return fragments;
    }

    @Override
    public void clearSearchFragments() {
        if (!searchFragmentMap.isEmpty()) {
            searchFragmentMap.clear();
            searchFragmentsChanged();
        }
    }

    @Override
    public void setSearchFragment(String groupId, SearchFragment fragment) {
        if (fragment == null)
            throw new NullPointerException("searchFragments");
        fragment.setHolder(this);
        List<SearchFragment> fragments = getSearchFragments(groupId);
        fragments.clear();
        fragments.add(fragment);
        searchFragmentsChanged();
    }

    @Override
    public void addSearchFragment(String groupId, SearchFragment fragment) {
        if (fragment == null)
            throw new NullPointerException("fragment");
        fragment.setHolder(this);
        List<SearchFragment> fragments = getSearchFragments(groupId);
        fragments.add(fragment);
        searchFragmentsChanged();
    }

    public void setSearchFragment(String groupId, String entryLabel, ICriteriaElement... criteriaElements) {
        if (criteriaElements.length == 0)
            return;
        ICriteriaElement composite;
        if (criteriaElements.length == 1)
            composite = criteriaElements[0];
        else
            composite = new CriteriaComposite(criteriaElements);
        SearchFragmentWrapper fragment = new SearchFragmentWrapper(composite, entryLabel);
        setSearchFragment(groupId, fragment);
    }

    public void addSearchFragment(String groupId, String entryLabel, ICriteriaElement... criteriaElements) {
        if (criteriaElements.length == 0)
            return;
        ICriteriaElement composite;
        if (criteriaElements.length == 1)
            composite = criteriaElements[0];
        else
            composite = new CriteriaComposite(criteriaElements);
        SearchFragmentWrapper fragment = new SearchFragmentWrapper(composite, entryLabel);
        addSearchFragment(groupId, fragment);
    }

    @Override
    public void removeSearchFragmentGroup(String groupId) {
        searchFragmentMap.remove(groupId);
    }

    @Override
    public void removeSearchFragment(SearchFragment fragment) {
        boolean dirty = false;
        for (List<SearchFragment> fragments : searchFragmentMap.values())
            if (fragments.remove(fragment))
                dirty = true;
        if (dirty)
            searchFragmentsChanged();
    }

    protected void searchFragmentsChanged() {
        refreshRowCount();
    }

    public String getSearchId() {
        return searchId;
    }

    public void setSearchId(String searchId) {
        this.searchId = searchId;
    }

    public void addIdRestriction() {
        if (searchId != null) {
            searchId = searchId.trim();
            if (searchId.isEmpty())
                searchId = null;
        }
        if (searchId == null)
            removeSearchFragmentGroup("id");
        else {
            Serializable _searchId = null;
            try {
                _searchId = EntityUtil.parseId(getKeyType(), searchId);
            } catch (ParseException e) {
                uiLogger.error(e, "ID ????????????");
                return;
            }
            setSearchFragment("id", "ID ??? " + searchId, new Equals("id", _searchId));
            searchId = null;
        }
    }

    public String getSearchPattern() {
        return searchPattern;
    }

    public void setSearchPattern(String searchPattern) {
        this.searchPattern = searchPattern;
    }

    public void addNameRestriction() {
        setSearchFragment("name", "???????????? " + searchPattern, CommonCriteria.namedLike(searchPattern, true));
        searchPattern = null;
    }

    public void addLabelRestriction() {
        setSearchFragment("label", "???????????? " + searchPattern, CommonCriteria.labelledWith(searchPattern, true));
        searchPattern = null;
    }

    public void addNameOrLabelRestriction() {
        setSearchFragment("name", "???????????? " + searchPattern, //
                // UIEntity doesn't have name: CommonCriteria.namedLike(pattern), //
                CommonCriteria.labelledWith(searchPattern, true));
        searchPattern = null;
    }

    public void addDescriptionRestriction() {
        setSearchFragment("description", "???????????? " + searchPattern, CommonCriteria.describedWith(searchPattern, true));
        searchPattern = null;
    }

    public List<DateRangeTemplate> getDateRangeTemplates() {
        return Arrays.asList(DateRangeTemplate.values());
    }

    public int getDateRangeIndex() {
        if (dateRange == null)
            return -1;
        else
            return dateRange.ordinal();
    }

    public void setDateRangeIndex(int dateRangeIndex) {
        if (dateRangeIndex == -1)
            dateRange = null;
        else
            dateRange = DateRangeTemplate.values()[dateRangeIndex];
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    protected void setDateSearchFragment(String groupId, String hint, IDateCriteriaComposer composer) {
        Date now = new Date();
        Date begin = dateRange == null ? beginDate : dateRange.getBegin(now);
        Date end = dateRange == null ? endDate : dateRange.getEnd(now);
        ICriteriaElement criteriaElement = composer.composeDateCriteria(begin, end);
        if (criteriaElement != null) {
            String label;
            if (dateRange != null)
                label = hint + dateRange.getLabel();
            else {
                StringBuilder sb = new StringBuilder();
                sb.append(hint);
                if (begin != null)
                    sb.append(Dates.YYYY_MM_DD.format(begin) + "??????");
                if (end != null)
                    sb.append(Dates.YYYY_MM_DD.format(end) + "??????");
                label = sb.toString();
            }
            setSearchFragment(groupId, label, criteriaElement);
        }
    }

    public void addCreatedDateRestriction() {
        setDateSearchFragment("create-date", "?????????", IDateCriteriaComposer.createdDate);
    }

    public void addLastModifiedRestriction() {
        setDateSearchFragment("modify-date", "?????????", IDateCriteriaComposer.lastModified);
    }

    public void addBeginEndDateRestriction() {
        setDateSearchFragment("begin-date", "?????????", IDateCriteriaComposer.beginEndDate);
    }

    public PrincipalDto getSearchPrincipal() {
        return searchPrincipal;
    }

    public void setSearchPrincipal(PrincipalDto searchPrincipal) {
        this.searchPrincipal = searchPrincipal;
    }

    public void addOwnerRestriction(){
        if(searchPrincipal == null)
            return;
        Principal principal = searchPrincipal.unmarshal();
        setSearchFragment("owner", "??? " + searchPrincipal.getDisplayName() + " ??????", //
                CommonCriteria.ownedBy(principal));
        searchPrincipal = null;
    }

    public void addImpliedOwnerRestriction() {
        if (searchPrincipal == null)
            return;
        Principal principal = searchPrincipal.unmarshal();
        setSearchFragment("owner", "??? " + searchPrincipal.getDisplayName() + " ??????", //
                CommonCriteria.effectiveOwnedBy(principal));
        searchPrincipal = null;
    }

    /* ********************************************************************** */

}
