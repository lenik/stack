package com.bee32.sem.misc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.free.Dates;
import javax.free.IllegalUsageException;

import org.primefaces.model.LazyDataModel;

import com.bee32.icsf.login.SessionUser;
import com.bee32.icsf.principal.User;
import com.bee32.plover.arch.operation.Operation;
import com.bee32.plover.collections.Varargs;
import com.bee32.plover.criteria.hibernate.CriteriaComposite;
import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.orm.entity.EntityAccessor;
import com.bee32.plover.orm.entity.EntityFlags;
import com.bee32.plover.orm.util.EntityDto;
import com.bee32.plover.orm.util.EntityViewBean;
import com.bee32.plover.ox1.c.CEntity;
import com.bee32.plover.ox1.util.CommonCriteria;
import com.bee32.plover.restful.resource.StandardViews;
import com.bee32.sem.frame.search.ISearchFragmentsHolder;
import com.bee32.sem.frame.search.SearchFragment;
import com.bee32.sem.frame.search.SearchFragmentWrapper;
import com.bee32.sem.sandbox.AbstractCriteriaHolder;
import com.bee32.sem.sandbox.EntityDataModelOptions;
import com.bee32.sem.sandbox.UIHelper;

public abstract class SimpleEntityViewBean
        extends EntityViewBean
        implements ISearchFragmentsHolder {

    private static final long serialVersionUID = 1L;

    /** Delete entities with user lock */
    protected static final int DELETE_FORCE = 1;
    /** Detach the entity before deletion */
    protected static final int DELETE_DETACH = 2;

    /** Force to update entities with user lock */
    protected static final int SAVE_FORCE = 1;
    /** Save entities with user lock on */
    protected static final int SAVE_LOCKED = 2;
    /** Save entities with user lock off. */
    protected static final int SAVE_UNLOCKED = 4;
    /** Save entities with user lock off. */
    protected static final int SAVE_FORCE_UNLOCKED = SAVE_FORCE | SAVE_UNLOCKED;
    /** Change the entities' owner to current user before save */
    protected static final int SAVE_CHOWN = 8;

    protected Class<? extends Entity<?>> entityClass;
    protected Class<? extends EntityDto<?, ?>> dtoClass;
    protected int saveFlags = 0;
    protected int deleteFlags = 0;

    List<ICriteriaElement> baseCriteriaElements;
    List<SearchFragment> searchFragments = new ArrayList<SearchFragment>();
    EntityDataModelOptions<?, ?> options;
    LazyDataModel<?> dataModel;

    protected String searchPattern;
    protected DateRangeTemplate dateRange = DateRangeTemplate.recentWeek;
    protected Date beginDate;
    protected Date endDate;

    public <E extends Entity<?>, D extends EntityDto<? super E, ?>> //
    /*    */SimpleEntityViewBean(Class<E> entityClass, Class<D> dtoClass, int selection,
            ICriteriaElement... criteriaElements) {
        this.entityClass = entityClass;
        this.dtoClass = dtoClass;
        this.baseCriteriaElements = Varargs.toList(criteriaElements);
        this.options = new EntityDataModelOptions<E, D>(//
                entityClass, dtoClass, selection, new _CriteriaHolder());
        this.dataModel = UIHelper.buildLazyDataModel(options);
    }

    private class _CriteriaHolder
            extends AbstractCriteriaHolder {

        private static final long serialVersionUID = 1L;

        @Override
        protected List<? extends ICriteriaElement> getCriteriaElements() {
            return SimpleEntityViewBean.this.composeCriteriaElements();
        }

    }

    public LazyDataModel<?> getDataModel() {
        return dataModel;
    }

    protected List<? extends ICriteriaElement> composeCriteriaElements() {
        List<ICriteriaElement> join = new ArrayList<ICriteriaElement>(baseCriteriaElements);
        for (SearchFragment fragment : searchFragments) {
            ICriteriaElement element = fragment.compose();
            if (element != null)
                join.add(element);
        }
        return join;
    }

    protected final List<ICriteriaElement> getBaseCriteriaElements() {
        return baseCriteriaElements;
    }

    protected final void setBaseCriteriaElements(List<ICriteriaElement> criteriaElements) {
        options.setCriteriaElements(criteriaElements);
    }

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
    }

    @Override
    public void removeSearchFragment(SearchFragment fragment) {
        searchFragments.remove(fragment);
    }

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
    }

    @Operation
    public void showIndex() {
        setActiveObject(null);
        showView(StandardViews.LIST);
    }

    @Operation
    public void showContent() {
        if (getSelection().isEmpty()) {
            uiLogger.error("没有选定对象!");
            return;
        }
        openSelectedDtos(-1);
        showView(StandardViews.CONTENT);
    }

    @Operation
    public void showCreateForm() {
        EntityDto<?, ?> entityDto;
        try {
            entityDto = dtoClass.newInstance();
            entityDto = entityDto.create();
        } catch (Exception e) {
            uiLogger.error("无法创建对象", e);
            return;
        }
        setActiveObject(entityDto);
        showView(StandardViews.CREATE_FORM);
    }

    @Operation
    public void showEditForm() {
        if (getSelection().isEmpty()) {
            uiLogger.error("没有选定对象!");
            return;
        }
        openSelectedDtos(-1);
        showView(StandardViews.EDIT_FORM);
    }

    @Operation
    public void save() {
        save(saveFlags);
    }

    protected void save(int saveFlags) {
        checkSaveFlags(saveFlags);

        if (getActiveObjects().isEmpty()) {
            uiLogger.error("没有需要保存的对象!");
            return;
        }

        Map<Entity<?>, EntityDto<?, ?>> entityMap;
        try {
            entityMap = unmarshalDtos(getActiveObjects(), false);
        } catch (NullPointerException e) {
            uiLogger.error("有些需要保存的对象已失效，请刷新页面重试。", e);
            return;
        } catch (Exception e) {
            uiLogger.error("反编列失败", e);
            return;
        }

        User me = SessionUser.getInstance().getInternalUserOpt();
        List<Entity<?>> lockedList = new ArrayList<Entity<?>>();
        for (Entity<?> entity : entityMap.keySet()) {
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

        try {
            preUpdate(entityMap);
        } catch (Exception e) {
            uiLogger.error("预处理失败", e);
        }

        Set<Entity<?>> entities = entityMap.keySet();
        try {
            serviceFor(entityClass).saveOrUpdateAll(entities);
            // refreshCount();
        } catch (Exception e) {
            uiLogger.error("保存失败", e);
            return;
        }

        try {
            postUpdate(entityMap);
        } catch (Exception e) {
            uiLogger.warn("保存不完全，次要的数据可能不一致，建议您检查相关的数据。", e);
        }

        uiLogger.info("保存成功");
        showIndex();
    }

    protected void preUpdate(Map<Entity<?>, EntityDto<?, ?>> entityMap)
            throws Exception {
    }

    protected void postUpdate(Map<Entity<?>, EntityDto<?, ?>> entityMap)
            throws Exception {
    }

    @Operation
    public void deleteSelection() {
        deleteSelection(deleteFlags);
    }

    protected void deleteSelection(int deleteFlags) {
        checkDeleteFlags(deleteFlags);

        if (getSelection().isEmpty()) {
            uiLogger.error("没有选定对象!");
            return;
        }

        Map<Entity<?>, EntityDto<?, ?>> entityMap;
        try {
            entityMap = unmarshalDtos(getSelection(), true);
        } catch (Exception e) {
            uiLogger.error("反编列失败", e);
            return;
        }

        List<Entity<?>> lockedList = new ArrayList<Entity<?>>();
        for (Entity<?> entity : entityMap.keySet()) {
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
                serviceFor(entityClass).update(entity);
            }

            boolean locked = EntityAccessor.isAnyLocked(entity);
            if (locked) {
                lockedList.add(entity);
                continue;
            }
        }

        if (!lockedList.isEmpty()) {
            StringBuilder buf = null;
            for (Entity<?> lockedEntity : lockedList) {
                if (buf == null)
                    buf = new StringBuilder("以下对象被锁定：\n");
                else
                    buf.append(", \n");
                String entryText = lockedEntity.getEntryLabel();
                buf.append("    " + entryText);
            }
            uiLogger.error("不能删除锁定的对象: " + buf.toString());
            setSelection(null);
            return;
        }

        setSelection(null);

        Set<Entity<?>> entities = entityMap.keySet();
        try {
            serviceFor(entityClass).deleteAll(entities);
        } catch (Exception e) {
            uiLogger.error("删除失败", e);
            return;
        }
    }

    void openSelectedDtos(int reloadDtoSel) {
        List<Object> reloadedList = new ArrayList<Object>();
        for (Object selection : getSelection()) {
            Object reloaded;
            if (selection instanceof EntityDto<?, ?>) {
                EntityDto<?, ?> dto = (EntityDto<?, ?>) selection;
                reloaded = reload(dto, reloadDtoSel);
            } else
                reloaded = selection;
            reloadedList.add(reloaded);
        }
        setActiveObjects(reloadedList);
    }

    Map<Entity<?>, EntityDto<?, ?>> unmarshalDtos(Collection<?> objects, boolean nullable) {
        Map<Entity<?>, EntityDto<?, ?>> resultMap = new LinkedHashMap<Entity<?>, EntityDto<?, ?>>();
        for (Object object : objects) {
            if (dtoClass.isInstance(object)) {
                EntityDto<?, ?> entityDto = (EntityDto<?, ?>) object;
                Entity<?> entity = entityDto.unmarshal(this);
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

    public String getSearchPattern() {
        return searchPattern;
    }

    public void setSearchPattern(String searchPattern) {
        this.searchPattern = searchPattern;
    }

    public void addNameRestriction() {
        addSearchFragment("名称含有 " + searchPattern, CommonCriteria.namedLike(searchPattern));
        searchPattern = null;
    }

    public void addLabelRestriction() {
        addSearchFragment("标题含有 " + searchPattern, CommonCriteria.labelledWith(searchPattern));
        searchPattern = null;
    }

    public void addNameOrLabelRestriction() {
        addSearchFragment("名称含有 " + searchPattern, //
                // UIEntity doesn't have name: CommonCriteria.namedLike(pattern), //
                CommonCriteria.labelledWith(searchPattern));
        searchPattern = null;
    }

    public void addDescriptionRestriction() {
        addSearchFragment("描述含有 " + searchPattern, CommonCriteria.describedWith(searchPattern));
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

}
