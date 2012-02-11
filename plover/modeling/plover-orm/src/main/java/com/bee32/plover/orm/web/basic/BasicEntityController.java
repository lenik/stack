package com.bee32.plover.orm.web.basic;

import java.io.Serializable;

import javax.free.ParseException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;

import com.bee32.plover.arch.util.ClassUtil;
import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.orm.util.EntityDto;
import com.bee32.plover.orm.web.EntityController;
import com.bee32.plover.orm.web.IEntityForming;
import com.bee32.plover.orm.web.IEntityListing;
import com.bee32.plover.orm.web.IPostUpdating;
import com.bee32.plover.orm.web.util.DataTableDxo;
import com.bee32.plover.orm.web.util.SearchModel;
import com.bee32.plover.servlet.mvc.ActionRequest;
import com.bee32.plover.servlet.mvc.ActionResult;

public abstract class BasicEntityController<E extends Entity<K>, K extends Serializable, Dto extends EntityDto<? super E, K>>
        extends EntityController<E, K, Dto> {

    static Logger logger = LoggerFactory.getLogger(BasicEntityController.class);

    protected final Class<E> entityType;

    // protected boolean _createOTF;
    public BasicEntityController() {
        entityType = ClassUtil.infer1(getClass(), BasicEntityController.class, 0);
    }

    class Impl
            implements //
            IEntityListing<E, K>, //
            IEntityForming<E, K>, //
            IPostUpdating<E, K> {

        @SuppressWarnings("unchecked")
        public final void loadEntry(E entity, EntityDto<E, K> _dto) {
            Dto dto = (Dto) _dto;
            BasicEntityController.this.loadEntry(entity, dto);
        }

        @SuppressWarnings("unchecked")
        public final void fillDataRow(DataTableDxo tab, EntityDto<E, K> _dto) {
            Dto dto = (Dto) _dto;
            BasicEntityController.this.fillDataRow(tab, dto);
        }

        @Override
        public void fillSearchModel(SearchModel model, TextMap request)
                throws ParseException {
            BasicEntityController.this.fillSearchModel(model, request);
        }

        @SuppressWarnings("unchecked")
        public final void createForm(E entity, EntityDto<E, K> _dto) {
            Dto dto = (Dto) _dto;
            BasicEntityController.this.createForm(entity, dto);
        };

        @SuppressWarnings("unchecked")
        @Override
        public final void loadForm(E entity, EntityDto<E, K> _dto) {
            Dto dto = (Dto) _dto;
            BasicEntityController.this.loadForm(entity, dto);
        }

        @SuppressWarnings("unchecked")
        @Override
        public final void saveForm(E entity, EntityDto<E, K> _dto) {
            Dto dto = (Dto) _dto;
            BasicEntityController.this.saveForm(entity, dto);
        }

        @Override
        public void postUpdate(E entity) {
            BasicEntityController.this.postUpdate(entity);
        }

    }

    protected final Impl impl = new Impl();

    @Override
    protected void initController()
            throws BeansException {

        class Form1
                extends CreateOrEditFormHandler<E, K> {

            public Form1(Class<E> entityType, IEntityForming<E, K> forming) {
                super(entityType, forming);
            }

            @Override
            public ActionResult _handleRequest(ActionRequest req, ActionResult result)
                    throws Exception {
                result = super._handleRequest(req, result);
                if (result == null)
                    return null; // redirected.
                fillFormExtra(req, result);
                return result;
            }

        }

        addHandler("index", /*      */new IndexHandler<E, K>(entityType));
        addHandler("data", /*       */new DataHandler<E, K>(entityType, impl));

        addHandler("content", /*    */new ContentHandler<E, K>(entityType, impl));

        addHandler("createForm", /* */new Form1(entityType, impl));
        addHandler("editForm", /*   */new Form1(entityType, impl));

        addHandler("create", /*     */new CreateOrEditHandler<E, K>(entityType, impl));
        addHandler("edit", /*       */new CreateOrEditHandler<E, K>(entityType, impl));

        addHandler("delete", /*     */new DeleteHandler<E, K>(entityType));

        // addHandler(new ChooseHandler<E, K>(impl));
    }

    protected void loadEntry(E entity, Dto dto) {
        dto.marshal(entity);
    }

    /**
     * Fill nothing in default implementation.
     */
    protected abstract void fillDataRow(DataTableDxo tab, Dto dto);

    /**
     * Fill nothing in default implementation.
     */
    protected void fillSearchModel(SearchModel model, TextMap request)
            throws ParseException {
    }

    protected void createForm(E entity, Dto dto) {
        // fillTemplate(dto);
    }

    protected void loadForm(E entity, Dto dto) {
        dto.marshal(entity);
    }

    protected void saveForm(E entity, Dto dto) {
        dto.unmarshalTo(entity);
    }

    /**
     * Did nothing in default implementation.
     */
    protected void fillFormExtra(ActionRequest req, ActionResult result) {
    }

    /**
     * Do nothing in default implementation.
     */
    protected void postUpdate(E entity) {
    }

}
