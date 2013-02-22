package com.bee32.sem.process.web;

import java.util.List;
import java.util.Map;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.primefaces.model.SortOrder;

import com.bee32.plover.arch.util.Strs;
import com.bee32.plover.orm.util.ITypeAbbrAware;
import com.bee32.sem.sandbox.LazyDataModelImpl;

/**
 * @see com.bee32.ape.engine.beans.ApeActivitiServices
 */
public abstract class AbstractProcessInstanceView
        extends GenericProcessInstanceView {

    private static final long serialVersionUID = 1L;

    DataModel dataModel = new DataModel();

    @Override
    public DataModel getDataModel() {
        return dataModel;
    }

    public String getEntityTypeAbbr() {
        return ITypeAbbrAware.ABBR.abbr(ProcessInstance.class);
    }

    /*************************************************************************
     * Section: Persistence
     *************************************************************************/

    @Override
    protected boolean saveImpl(int saveFlags, String hint, boolean creating) {
        throw new UnsupportedOperationException("Process instance can only be started.");
    }

    public void deleteSelection() {
        RuntimeService runtimeService = BEAN(RuntimeService.class);
        HistoryService historyService = BEAN(HistoryService.class);

        for (Object sel : getSelection()) {
            ProcessInstance processInstance = (ProcessInstance) sel;
            try {
                runtimeService.deleteProcessInstance(processInstance.getId(), "forced");
                historyService.deleteHistoricProcessInstance(processInstance.getId());
            } catch (Exception e) {
                uiLogger.error("无法删除过程 " + processInstance.getId(), e);
                return;
            }
        }
        uiLogger.info("删除成功。");

        setSingleSelection(null);
        // if ((deleteFlags & DELETE_NO_REFRESH) == 0)
        refreshRowCount();
    }

    /*************************************************************************
     * Section: Search
     *************************************************************************/

    protected ProcessInstanceQuery createQuery() {
        RuntimeService service = BEAN(RuntimeService.class);
        ProcessInstanceQuery query = service.createProcessInstanceQuery();
        return query;
    }

    /*************************************************************************
     * Section: Data Model
     *************************************************************************/

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
