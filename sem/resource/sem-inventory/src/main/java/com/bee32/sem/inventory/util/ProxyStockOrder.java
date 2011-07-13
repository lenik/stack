package com.bee32.sem.inventory.util;

import java.util.Date;
import java.util.List;
import java.util.Set;

import com.bee32.plover.arch.ui.Appearance;
import com.bee32.plover.arch.util.ExceptionSupport;
import com.bee32.plover.model.qualifier.Qualifier;
import com.bee32.plover.model.qualifier.QualifierMap;
import com.bee32.plover.model.schema.ISchema;
import com.bee32.plover.model.stage.IModelStage;
import com.bee32.plover.model.stage.ModelLoadException;
import com.bee32.plover.model.stage.ModelStageException;
import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.util.FormatStyle;
import com.bee32.plover.util.PrettyPrintStream;
import com.bee32.sem.inventory.entity.StockOrder;
import com.bee32.sem.inventory.entity.StockOrderItem;
import com.bee32.sem.inventory.entity.StockOrderSubject;
import com.bee32.sem.inventory.entity.StockSnapshot;

public class ProxyStockOrder
        extends StockOrder {

    private static final long serialVersionUID = 1L;

    StockOrder order;

    public QualifierMap getQualifierMap() {
        return order.getQualifierMap();
    }

    public Iterable<Qualifier<?>> getQualifiers() {
        return order.getQualifiers();
    }

    public Date getBeginTime() {
        return order.getBeginTime();
    }

    public String getLabel() {
        return order.getLabel();
    }

    public <Q extends Qualifier<Q>> Iterable<Q> getQualifiers(Class<Q> qualifierType) {
        return order.getQualifiers(qualifierType);
    }

    public void setBeginTime(Date beginTime) {
        order.setBeginTime(beginTime);
    }

    public void setLabel(String label) {
        order.setLabel(label);
    }

    public ISchema getSchema() {
        return order.getSchema();
    }

    public Date getEndTime() {
        return order.getEndTime();
    }

    public String getDescription() {
        return order.getDescription();
    }

    public String refName() {
        return order.refName();
    }

    public <Q extends Qualifier<Q>> Q getQualifier(Class<Q> qualifierType) {
        return order.getQualifier(qualifierType);
    }

    public Long getId() {
        return order.getId();
    }

    public void setEndTime(Date endTime) {
        order.setEndTime(endTime);
    }

    public void setDescription(String description) {
        order.setDescription(description);
    }

    public StockSnapshot getBase() {
        return order.getBase();
    }

    public void stage(IModelStage stage)
            throws ModelStageException {
        order.stage(stage);
    }

    public String getName() {
        return order.getName();
    }

    public int getVersion() {
        return order.getVersion();
    }

    public void setBase(StockSnapshot base) {
        order.setBase(base);
    }

    public Appearance getAppearance() {
        return order.getAppearance();
    }

    public void reload(IModelStage stage)
            throws ModelLoadException {
        order.reload(stage);
    }

    public Date getCreatedDate() {
        return order.getCreatedDate();
    }

    public StockSnapshot getInitTarget() {
        return order.getInitTarget();
    }

    public void setInitTarget(StockSnapshot initTarget) {
        order.setInitTarget(initTarget);
    }

    public StockOrderSubject getSubject() {
        return order.getSubject();
    }

    public Date getLastModified() {
        return order.getLastModified();
    }

    public void setSubject(StockOrderSubject subject) {
        order.setSubject(subject);
    }

    public void setLastModified(Date lastModified) {
        order.setLastModified(lastModified);
    }

    public String getSerial() {
        return order.getSerial();
    }

    public void setSerial(String serial) {
        order.setSerial(serial);
    }

    public List<StockOrderItem> getItems() {
        return order.getItems();
    }

    public ExceptionSupport getExceptionSupport() {
        return order.getExceptionSupport();
    }

    public void setItems(List<StockOrderItem> items) {
        order.setItems(items);
    }

    public Long getJobRef() {
        return order.getJobRef();
    }

    public void setJobRef(Long jobRef) {
        order.setJobRef(jobRef);
    }

    public int getAclId() {
        return order.getAclId();
    }

    public StockOrder createPeerOrder(StockOrderSubject peerSubject, boolean copyItems) {
        return order.createPeerOrder(peerSubject, copyItems);
    }

    public void setAclId(int aclId) {
        order.setAclId(aclId);
    }

    public void populate(Object source) {
        order.populate(source);
    }

    public Entity<Long> detach() {
        return order.detach();
    }

    public void toString(PrettyPrintStream out, FormatStyle format) {
        order.toString(out, format);
    }

    public void toString(PrettyPrintStream out, FormatStyle format, Set<Object> occurred, int depth) {
        order.toString(out, format, occurred, depth);
    }

}
