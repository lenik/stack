package com.bee32.plover.orm.ext.util;

import java.io.Serializable;

import javax.free.ParseException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.orm.ext.basic.ContentHandler;
import com.bee32.plover.orm.ext.basic.CreateFormHandler;
import com.bee32.plover.orm.ext.basic.CreateHandler;
import com.bee32.plover.orm.ext.basic.DataHandler;
import com.bee32.plover.orm.ext.basic.EditFormHandler;
import com.bee32.plover.orm.ext.basic.EditHandler;
import com.bee32.plover.orm.ext.basic.IEntityForming;
import com.bee32.plover.orm.ext.basic.IEntityListing;
import com.bee32.plover.orm.ext.basic.IPostUpdating;
import com.bee32.plover.orm.ext.basic.IndexHandler;
import com.bee32.plover.orm.util.EntityDto;

public abstract class BasicEntityController<E extends Entity<K>, K extends Serializable, Dto extends EntityDto<E, K>>
        extends EntityController<E, K, Dto> {

    static Logger logger = LoggerFactory.getLogger(BasicEntityController.class);

    // protected boolean _createOTF;

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

    Impl impl = new Impl();

    public BasicEntityController() {
        addHandler(new IndexHandler<E, K>());
        addHandler(new DataHandler<E, K>(impl));
        addHandler(new ContentHandler<E, K>(impl));
        addHandler(new CreateFormHandler<E, K>(impl));
        addHandler(new CreateHandler<E, K>(impl));
        addHandler(new EditFormHandler<E, K>(impl));
        addHandler(new EditHandler<E, K>(impl));
    }

    protected void loadEntry(E entity, Dto dto) {
        dto.marshal(this, entity);
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
        dto.marshal(this, entity);
    }

    protected void saveForm(E entity, Dto dto) {
        dto.unmarshalTo(this, entity);
    }

    /**
     * Do nothing in default implementation.
     */
    protected void postUpdate(E entity) {
    }

}
