package com.bee32.sem.misc;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;

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
import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.plover.criteria.hibernate.InCollection;
import com.bee32.plover.criteria.hibernate.Or;
import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.orm.entity.EntityAccessor;
import com.bee32.plover.orm.entity.EntityFlags;
import com.bee32.plover.orm.sample.SamplesLoader;
import com.bee32.plover.orm.util.EntityDto;
import com.bee32.plover.orm.util.EntityViewBean;
import com.bee32.plover.orm.web.EntityHelper;
import com.bee32.plover.ox1.c.CEntity;
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
        extends EntityViewBean
        implements ISearchFragmentsHolder {

    private static final long serialVersionUID = 1L;
    static Logger logger = LoggerFactory.getLogger(SimpleEntityViewBean.class);

    public static Permission visiblePermission = new Permission(Permission.READ);

    /** Delete entities with user lock */
    protected static final int DELETE_FORCE = 1;
    /** Detach the entity before deletion */
    protected static final int DELETE_DETACH = 2;
    /** Don't refresh row count */
    protected static final int DELETE_NO_REFRESH = 128;

    /** Force to update locked entities */
    protected static final int SAVE_FORCE = 1;
    /** Save entities with user lock on */
    protected static final int SAVE_LOCKED = 2;
    /** Save entities with user lock off. */
    protected static final int SAVE_UNLOCKED = 4;
    /** Save entities with user lock off, unlock at first if necessary. */
    protected static final int SAVE_FORCE_UNLOCKED = SAVE_FORCE | SAVE_UNLOCKED;
    /** Change the entities' owner to current user before save */
    protected static final int SAVE_CHOWN = 8;
    /** Only create, never update */
    protected static final int SAVE_NOEXIST = 16;
    /** Only update, never create */
    protected static final int SAVE_MUSTEXIST = 32;
    /** Don't refresh row count */
    protected static final int SAVE_NO_REFRESH = 128;
    /** Used by saveDup */
    protected static final int SAVE_CONT = 256;

    protected int saveFlags = 0;
    protected int deleteFlags = 0;

    String currentView = StandardViews.LIST;
    protected Set<Serializable> requestWindow = new HashSet<Serializable>();
    List<ICriteriaElement> baseRestriction;
    ICriteriaElement userRestriction;
    boolean verifiedOnly;
    List<SearchFragment> searchFragments = new ArrayList<SearchFragment>();
    EntityDataModelOptions<?, ?> dataModelOptions;
    LazyDataModel<?> dataModel;

    public <E extends Entity<K>, D extends EntityDto<? super E, K>, K extends Serializable> //
    /*    */SimpleEntityViewBean(Class<E> entityClass, Class<D> dtoClass, int fmask, ICriteriaElement... criteriaElements) {
        this.entityClass = entityClass;
        this.dtoClass = dtoClass;
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
            return (Class<E>) SimpleEntityViewBean.this.entityClass;
        }

        @Override
        public Class<D> getDtoClass() {
            return (Class<D>) SimpleEntityViewBean.this.dtoClass;
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

    public boolean refreshRowCount() {
        if (dataModel instanceof ZLazyDataModel<?, ?>)
            try {
                ((ZLazyDataModel<?, ?>) dataModel).refreshRowCount();
            } catch (Exception e) {
                uiLogger.warn("刷新记录时出现异常。", e);
                return false;
            }
        return true;
    }

    protected final List<? extends ICriteriaElement> composeBaseRestrictions() {
        List<ICriteriaElement> join = new ArrayList<ICriteriaElement>();
        join.addAll(baseRestriction);

        /** Only restrict owner for CEntity+ */
        if (CEntity.class.isAssignableFrom(entityClass)) {
            Permission defaultPermission = AclEasTxWrapper.defaults.get(entityClass);
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
                        ACLCriteria.aclWithin(me.getACLs(visiblePermission))));
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

        for (SearchFragment fragment : searchFragments) {
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
            reportException(e, "读取", entityClass);
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

    /**
     * Show (or switch to) the real view.
     *
     * You need to override this method to set extra view states.
     *
     * @param viewName
     *            Standard view name.
     * @see StandardViews#LIST
     * @see StandardViews#CONTENT
     * @see StandardViews#CREATE_FORM
     * @see StandardViews#EDIT_FORM
     */
    protected void showView(String viewName) {
        currentView = viewName;
    }

    @Operation
    public final void showIndex() {
        showIndex(null);
    }

    protected void showIndex(Integer offset) {
        setOpenedObjects(Collections.emptyList());
        showView(StandardViews.LIST);
    }

    @Operation
    public void showCreateForm() {
        Object newInstance = create();
        if (newInstance == null)
            return;
        setOpenedObject(newInstance);
        showView(StandardViews.CREATE_FORM);
    }

    @Operation
    public void showEditForm(Object selection) {
        setSingleSelection(selection);
        showEditForm();
    }

    @Operation
    public void showEditForm() {
        if (getSelection().isEmpty()) {
            uiLogger.error("没有选定对象!");
            return;
        }

        boolean readOnly = false;
        for (Object sel : getSelection()) {
            EntityDto<?, ?> dto = (EntityDto<?, ?>) sel;
            EntityFlags ef = dto.getEntityFlags();
            if (ef.isLocked()) {
                uiLogger.warn("不能编辑锁定的对象【系统锁定】");
                readOnly = true;
                break;
            }
            if (ef.isUserLock()) {
                uiLogger.warn("不能编辑锁定的对象【用户级锁定】");
                readOnly = true;
                break;
            }
            if (!ef.isOverrided()) {
                if (ef.isBuitlinData()) {
                    uiLogger.warn("不能编辑系统预置数据: 更改系统数据可能导致系统无法正常运行。");
                    readOnly = true;
                    break;
                }
                if (ef.isTestData()) {
                    uiLogger.warn("您正在编辑测试数据: 测试数据是自动生成的，未来可能会被系统复原。");
                }
            }
        }

        openSelection();
        if (readOnly)
            showView(StandardViews.CONTENT);
        else
            showView(StandardViews.EDIT_FORM);
    }

    @Operation
    public void showContent(Object selection) {
        setSingleSelection(selection);
        showContent();
    }

    @Operation
    public void showContent() {
        if (getSelection().isEmpty()) {
            uiLogger.error("没有选定对象!");
            return;
        }
        openSelection();
        showView(StandardViews.CONTENT);
    }

    @Operation
    public void showPartialForm(Object selection) {
        setSingleSelection(selection);
        showPartialForm();
    }

    @Operation
    public void showPartialForm() {
        // default fmask override..
        showEditForm();
    }

    @Operation
    public void showPartialContent(Object selection, int fmask) {
        setSingleSelection(selection);
        showPartialContent(fmask);
    }

    @Operation
    public void showPartialContent(int fmask) {
        showContent();
    }

    public String getCurrentView() {
        return currentView;
    }

    public boolean isCreating() {
        if (!currentView.equals(StandardViews.CREATE_FORM))
            return false;
        if (getOpenedObjects().isEmpty())
            throw new IllegalStateException("No opened objects for creating");
        return true;
    }

    public boolean isEditing() {
        if (currentView.equals(StandardViews.CREATE_FORM) //
                || currentView.equals(StandardViews.EDIT_FORM)) {
            if (getOpenedObjects().isEmpty())
                throw new IllegalStateException("No opened objects for editing");
            return true;
        }
        return false;
    }

    public void refresh() {
        switch (currentView) {
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
            entityDto = dtoClass.newInstance(); // Create fmask is always F_MORE..
            entityDto.setFmask(Fmask.F_MORE);
            entityDto = entityDto.create();
        } catch (Exception e) {
            uiLogger.error("无法创建对象", e);
            return null;
        }
        return entityDto;
    }

    @Operation
    public final boolean saveDup() {
        // Re-mark as new-created object.
        for (Object openedObject : getOpenedObjects()) {
            EntityDto<?, ?> dto = (EntityDto<?, ?>) openedObject;
            dto.clearId();
        }
        if (!save(SAVE_CONT, null))
            return false;
        // showCreateForm();
        return true;
    }

    @Operation
    public final boolean save() {
        return save(saveFlags, null);
    }

    protected final boolean save(boolean createOrUpdate) {
        return save(createOrUpdate ? SAVE_NOEXIST : SAVE_MUSTEXIST, null);
    }

    protected Integer getFmaskOverride(int saveFlags) {
        return null;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    protected boolean save(int saveFlags, String hint) {
        checkSaveFlags(saveFlags);
        boolean creating = isCreating();

        if (hint == null)
            // if ((saveFlags & SAVE_MUSTEXIST) != 0)
            hint = creating ? "保存" : "更新";

        if (getOpenedObjects().isEmpty()) {
            uiLogger.error("没有需要" + hint + "的对象!");
            return false;
        }

        List<EntityDto<?, ?>> dtos = getOpenedObjects();
        try {
            for (SevbFriend friend : sortedFriends)
                if (!friend.postValidate(dtos))
                    return false;
            if (!postValidate(dtos))
                return false;
        } catch (Exception e) {
            uiLogger.error("参数整理失败", e);
        }

        UnmarshalMap uMap;
        try {
            uMap = unmarshalDtos(dtos, getFmaskOverride(saveFlags), false/* result not-null */);
        } catch (Exception e) {
            uiLogger.error("反编列失败", e);
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
            uiLogger.error("预处理失败", e);
            return false;
        }

        Set<Entity<?>> entities = uMap.keySet();
        EntityAccessor.updateTimestamp(entities);
        try {
            if ((saveFlags & SAVE_MUSTEXIST) != 0)
                for (Entity<?> entity : entities)
                    ctx.data.access(entityClass).update(entity);
            else if ((saveFlags & SAVE_NOEXIST) != 0)
                ctx.data.access(entityClass).saveAll(entities);
            else
                ctx.data.access(entityClass).saveOrUpdateAll(entities);
        } catch (Exception e) {
            uiLogger.error(hint + "失败", e);
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
                ctx.data.access((Class) subEntityClass).saveOrUpdateAll(subEntities);
            } catch (Exception e) {
                uiLogger.error(hint + subMap.getLabel() + "失败。", e);
                return false;
            }
        }

        for (SevbFriend friend : sortedFriends) {
            try {
                friend.saveOpenedObject(saveFlags, uMap);
            } catch (Exception e) {
                uiLogger.error(hint + "友元失败" + friend, e);
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
            uiLogger.warn(hint + "不完全，次要的数据可能不一致，建议您检查相关的数据。", e);
            warned = true;
        }

        if (!warned)
            uiLogger.info(hint + "成功");

        if ((saveFlags & SAVE_CONT) == 0)
            showIndex(creating ? 1 : null);
        return true;
    }

    @Operation
    public final void deleteSelection() {
        deleteSelection(deleteFlags);
    }

    protected void deleteSelection(int deleteFlags) {
        checkDeleteFlags(deleteFlags);

        if (getSelection().isEmpty()) {
            uiLogger.error("没有选定对象!");
            return;
        }

        UnmarshalMap uMap;
        try {
            uMap = unmarshalDtos(getSelection(), null, true/* result nullable */);
        } catch (Exception e) {
            uiLogger.error("反编列失败", e);
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
                ctx.data.access(entityClass).update(entity);
            }

            boolean locked = EntityAccessor.isAnyLocked(entity);
            if (locked) {
                lockedList.add(entity);
                continue;
            }

            EntityFlags ef = EntityAccessor.getFlags(entity);
            // if (!ef.isWeakData()) ...: WEAK_DATA is skipped in deleteion.
            if (ef.isBuitlinData()) {
                uiLogger.error("不能删除系统预置数据: 删除系统数据可能导致系统无法正常运行。");
                return;
            }
        }

        if (!checkLockList(lockedList, "删除"))
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
            uiLogger.error("删除的先决条件处理失败", e);
            return;
        }

        for (SevbFriend friend : sortedFriends) {
            try {
                friend.deleteSelection(deleteFlags);
            } catch (Exception e) {
                uiLogger.error("友元删除失败:" + friend, e);
                return;
            }
        }

        Set<Entity<?>> entities = uMap.keySet();
        int count;
        try {
            count = ctx.data.access(entityClass).deleteAll(entities);
        } catch (Exception e) {
            uiLogger.error("删除失败", e);
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
            uiLogger.warn("保存不完全，次要的数据可能不一致，建议您检查相关的数据。", e);
            return;
        }

        String countHint = count == -1 ? "" : (" [" + count + "]");
        uiLogger.info("删除成功" + countHint);
    }

    protected boolean checkLockList(Iterable<? extends Entity<?>> lockedList, String hint) {
        StringBuilder buf = null;
        for (Entity<?> lockedEntity : lockedList) {
            if (buf == null)
                buf = new StringBuilder("以下对象被锁定：\n");
            else
                buf.append(", \n");
            String entryText = lockedEntity.getEntryLabel();
            buf.append("    " + entryText);
        }
        if (buf != null) {
            uiLogger.error("不能" + hint + "锁定的对象: " + buf.toString());
            return false;
        } else
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
            if (dtoClass.isInstance(object)) {
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

    static void checkSaveFlags(int saveFlags) {
        int forceLockedMask = SAVE_FORCE | SAVE_LOCKED;
        if ((saveFlags & forceLockedMask) == forceLockedMask)
            throw new IllegalUsageException("SAVE_FORCE and SAVE_LOCKED are exclusive.");
    }

    static void checkDeleteFlags(int deleteFlags) {
    }

    /*************************************************************************
     * Section: Search
     *************************************************************************/
    protected String searchPattern;
    protected DateRangeTemplate dateRange = DateRangeTemplate.recentWeek;
    protected Date beginDate;
    protected Date endDate;
    protected PrincipalDto searchPrincipal; // TODO implication opt.

    @Override
    public List<SearchFragment> getSearchFragments() {
        return searchFragments;
    }

    public void setSearchFragments(List<SearchFragment> searchFragments) {
        if (searchFragments == null)
            throw new NullPointerException("searchFragments");
        this.searchFragments = searchFragments;
    }

    public void addSearchFragment(String entryLabel, ICriteriaElement... criteriaElements) {
        CriteriaComposite composite = new CriteriaComposite(criteriaElements);
        addSearchFragment(entryLabel, composite);
    }

    public void addSearchFragment(String entryLabel, ICriteriaElement criteriaElement) {
        if (criteriaElement == null)
            return;
        SearchFragmentWrapper fragment = new SearchFragmentWrapper(criteriaElement, entryLabel);
        addSearchFragment(fragment);
    }

    @Override
    public void addSearchFragment(SearchFragment fragment) {
        if (fragment == null)
            throw new NullPointerException("fragment");
        fragment.setHolder(this);
        searchFragments.add(fragment);
        searchFragmentsChanged();
    }

    @Override
    public void removeSearchFragment(SearchFragment fragment) {
        if (searchFragments.remove(fragment))
            searchFragmentsChanged();
    }

    protected void searchFragmentsChanged() {
        refreshRowCount();
    }

    public String getSearchPattern() {
        return searchPattern;
    }

    public void setSearchPattern(String searchPattern) {
        this.searchPattern = searchPattern;
    }

    public void addNameRestriction() {
        addSearchFragment("名称含有 " + searchPattern, CommonCriteria.namedLike(searchPattern, true));
        searchPattern = null;
    }

    public void addLabelRestriction() {
        addSearchFragment("标题含有 " + searchPattern, CommonCriteria.labelledWith(searchPattern, true));
        searchPattern = null;
    }

    public void addNameOrLabelRestriction() {
        addSearchFragment("名称含有 " + searchPattern, //
                // UIEntity doesn't have name: CommonCriteria.namedLike(pattern), //
                CommonCriteria.labelledWith(searchPattern, true));
        searchPattern = null;
    }

    public void addDescriptionRestriction() {
        addSearchFragment("描述含有 " + searchPattern, CommonCriteria.describedWith(searchPattern, true));
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

    protected void addDateSearchFragment(String hint, IDateCriteriaComposer composer) {
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
                    sb.append(Dates.YYYY_MM_DD.format(begin) + "之后");
                if (end != null)
                    sb.append(Dates.YYYY_MM_DD.format(end) + "之前");
                label = sb.toString();
            }
            addSearchFragment(label, criteriaElement);
        }
    }

    public void addCreatedDateRestriction() {
        addDateSearchFragment("创建于", IDateCriteriaComposer.createdDate);
    }

    public void addLastModifiedRestriction() {
        addDateSearchFragment("修改于", IDateCriteriaComposer.lastModified);
    }

    public void addBeginEndDateRestriction() {
        addDateSearchFragment("发生于", IDateCriteriaComposer.beginEndDate);
    }

    public PrincipalDto getSearchPrincipal() {
        return searchPrincipal;
    }

    public void setSearchPrincipal(PrincipalDto searchPrincipal) {
        this.searchPrincipal = searchPrincipal;
    }

    public void addOwnerRestriction() {
        if (searchPrincipal == null)
            return;
        Principal principal = searchPrincipal.unmarshal();
        addSearchFragment("为 " + searchPrincipal.getDisplayName() + " 所有", //
                CommonCriteria.ownedBy(principal));
        searchPrincipal = null;
    }

    /* ********************************************************************** */

}
