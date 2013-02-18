package com.bee32.sem.process.web;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.primefaces.model.SortOrder;

import com.bee32.plover.arch.util.Strs;
import com.bee32.plover.orm.util.DataViewBean;
import com.bee32.plover.orm.util.ITypeAbbrAware;
import com.bee32.plover.util.FormatStyle;
import com.bee32.plover.util.GeneralFormatter;
import com.bee32.sem.sandbox.LazyDataModelImpl;

/**
 * @see com.bee32.ape.engine.beans.ApeActivitiServices
 */
public abstract class AbstractProcessInstanceView
        extends DataViewBean {

    private static final long serialVersionUID = 1L;

    DataModel dataModel = new DataModel();

    @Override
    public DataModel getDataModel() {
        return dataModel;
    }

    public String getEntityTypeAbbr() {
        return ITypeAbbrAware.ABBR.abbr(ExecutionEntity.class);
    }

    public void test() {
        RuntimeService service = BEAN(RuntimeService.class);
        ProcessInstanceQuery query = service.createProcessInstanceQuery();
        List<ProcessInstance> processInstances = query.list();
        for (ProcessInstance processInstance : processInstances) {
            System.out.println("ProcessInstance: ");
            String str = GeneralFormatter.toString(processInstance, FormatStyle.NORMAL, 2);
            System.out.println(str);
            System.out.println();
        }
    }

    /*************************************************************************
     * Section: Search
     *************************************************************************/

    boolean activeOnly;
    boolean suspendedOnly;
    boolean unassignedOnly;

    String processDefinitionId;
    String processDefinitionKey;
    String processInstanceBusinessKey;
    String processInstanceId;
    Set<String> processInstanceIds;
    String subProcessInstanceId;
    String superProcessInstanceId;

    protected ProcessInstanceQuery createQuery() {
        RuntimeService service = BEAN(RuntimeService.class);
        ProcessInstanceQuery query = service.createProcessInstanceQuery();
        return query;
    }

    public boolean isActiveOnly() {
        return activeOnly;
    }

    public void setActiveOnly(boolean activeOnly) {
        this.activeOnly = activeOnly;
    }

    public boolean isSuspendedOnly() {
        return suspendedOnly;
    }

    public void setSuspendedOnly(boolean suspendedOnly) {
        this.suspendedOnly = suspendedOnly;
    }

    public boolean isUnassignedOnly() {
        return unassignedOnly;
    }

    public void setUnassignedOnly(boolean unassignedOnly) {
        this.unassignedOnly = unassignedOnly;
    }

    public String getProcessDefinitionId() {
        return processDefinitionId;
    }

    public void setProcessDefinitionId(String processDefinitionId) {
        this.processDefinitionId = processDefinitionId;
    }

    public String getProcessDefinitionKey() {
        return processDefinitionKey;
    }

    public void setProcessDefinitionKey(String processDefinitionKey) {
        this.processDefinitionKey = processDefinitionKey;
    }

    public String getProcessInstanceBusinessKey() {
        return processInstanceBusinessKey;
    }

    public void setProcessInstanceBusinessKey(String processInstanceBusinessKey) {
        this.processInstanceBusinessKey = processInstanceBusinessKey;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public Set<String> getProcessInstanceIds() {
        return processInstanceIds;
    }

    public void setProcessInstanceIds(Set<String> processInstanceIds) {
        this.processInstanceIds = processInstanceIds;
    }

    /*************************************************************************
     * Section: Persistence
     *************************************************************************/

    public void deleteSelection() {
        setSelection(null);

        RuntimeService service = BEAN(RuntimeService.class);

        for (Object sel : getSelection()) {
            ProcessInstance processInstance = (ProcessInstance) sel;
            try {
                service.deleteProcessInstance(processInstance.getId(), "Reason");
            } catch (Exception e) {
                uiLogger.error("无法删除过程 " + processInstance.getId(), e);
                return;
            }
        }
        uiLogger.info("删除成功。");

        // if ((deleteFlags & DELETE_NO_REFRESH) == 0)
        refreshRowCount();
    }

    class DataModel
            extends LazyDataModelImpl<ProcessInstance> {

        private static final long serialVersionUID = 1L;

        @Override
        protected int countImpl() {
            ProcessInstanceQuery query = createQuery();
            int count = (int) query.count();
            return count;
        }

        @Override
        protected List<?> loadImpl(int first, int pageSize, String sortField, SortOrder sortOrder,
                Map<String, String> filters) {
            ProcessInstanceQuery query = createQuery();

            if (sortField != null)
                switch (sortField) {
                case "id":
                    query.orderByProcessInstanceId();
                    break;
                case "processDefinitionId":
                    query.orderByProcessDefinitionId();
                    break;
                case "processDefinitionKey":
                    query.orderByProcessDefinitionKey();
                    break;
                default:
                    sortOrder = SortOrder.UNSORTED;
                } // switch

            if (sortOrder != null && sortOrder != SortOrder.UNSORTED)
                if (sortOrder == SortOrder.ASCENDING)
                    query.asc();
                else
                    query.desc();

            if (activeOnly)
                query.active();
            if (suspendedOnly)
                query.suspended();

            if (Strs.isNotEmpty(processDefinitionId))
                query.processDefinitionId(processDefinitionId);
            if (Strs.isNotEmpty(processDefinitionKey))
                query.processDefinitionKey(processDefinitionKey);
            if (Strs.isNotEmpty(processInstanceBusinessKey))
                query.processInstanceBusinessKey(processInstanceBusinessKey);
            if (Strs.isNotEmpty(processInstanceId))
                query.processInstanceId(processInstanceId);
            if (processInstanceIds != null)
                query.processInstanceIds(processInstanceIds);

            if (Strs.isNotEmpty(subProcessInstanceId))
                query.subProcessInstanceId(subProcessInstanceId);
            if (Strs.isNotEmpty(superProcessInstanceId))
                query.superProcessInstanceId(superProcessInstanceId);

            List<ProcessInstance> list = query.listPage(first, pageSize);
            return list;
        }

        @Override
        public String getRowKey(ProcessInstance object) {
            return object.getId();
        }

    }

}
