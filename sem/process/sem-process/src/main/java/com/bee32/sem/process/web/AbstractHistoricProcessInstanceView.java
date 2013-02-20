package com.bee32.sem.process.web;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricProcessInstanceQuery;
import org.activiti.engine.impl.persistence.entity.HistoricProcessInstanceEntity;
import org.primefaces.model.SortOrder;

import com.bee32.plover.arch.util.Strs;
import com.bee32.plover.orm.util.ITypeAbbrAware;
import com.bee32.plover.util.FormatStyle;
import com.bee32.plover.util.GeneralFormatter;
import com.bee32.sem.misc.AbstractSimpleEntityView;
import com.bee32.sem.sandbox.LazyDataModelImpl;

/**
 * @see com.bee32.ape.engine.beans.ApeActivitiServices
 */
public abstract class AbstractHistoricProcessInstanceView
        extends AbstractSimpleEntityView {

    private static final long serialVersionUID = 1L;

    DataModel dataModel = new DataModel();

    @Override
    public DataModel getDataModel() {
        return dataModel;
    }

    public String getEntityTypeAbbr() {
        return ITypeAbbrAware.ABBR.abbr(HistoricProcessInstanceEntity.class);
    }

    public void test() {
        HistoryService service = BEAN(HistoryService.class);
        HistoricProcessInstanceQuery query = service.createHistoricProcessInstanceQuery();
        List<HistoricProcessInstance> historicProcessInstances = query.list();
        for (HistoricProcessInstance historicProcessInstance : historicProcessInstances) {
            System.out.println("HistoricProcessInstance: ");
            String str = GeneralFormatter.toString(historicProcessInstance, FormatStyle.NORMAL, 2);
            System.out.println(str);
            System.out.println();
        }
    }

    /*************************************************************************
     * Section: Search
     *************************************************************************/

    boolean finishedOnly;
    boolean unfinishedOnly;

    String processDefinitionId;
    String processDefinitionKey;
    String processInstanceBusinessKey;
    String processInstanceId;
    Set<String> processInstanceIds;

    String startedBy;

    protected HistoricProcessInstanceQuery createQuery() {
        HistoryService service = BEAN(HistoryService.class);
        HistoricProcessInstanceQuery query = service.createHistoricProcessInstanceQuery();
        return query;
    }

    public boolean isFinishedOnly() {
        return finishedOnly;
    }

    public void setFinishedOnly(boolean finishedOnly) {
        this.finishedOnly = finishedOnly;
    }

    public boolean isUnfinishedOnly() {
        return unfinishedOnly;
    }

    public void setUnfinishedOnly(boolean unfinishedOnly) {
        this.unfinishedOnly = unfinishedOnly;
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

    public String getStartedBy() {
        return startedBy;
    }

    public void setStartedBy(String startedBy) {
        this.startedBy = startedBy;
    }

    /*************************************************************************
     * Section: Persistence
     *************************************************************************/

    public void deleteSelection() {
        setSelection(null);

        RuntimeService service = BEAN(RuntimeService.class);

        for (Object sel : getSelection()) {
            HistoricProcessInstance processInstance = (HistoricProcessInstance) sel;
            try {
                // Delete an existing runtime process instance.
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
            extends LazyDataModelImpl<HistoricProcessInstance> {

        private static final long serialVersionUID = 1L;

        @Override
        protected int countImpl() {
            HistoricProcessInstanceQuery query = createQuery();
            int count = (int) query.count();
            return count;
        }

        @Override
        protected List<?> loadImpl(int first, int pageSize, String sortField, SortOrder sortOrder,
                Map<String, String> filters) {
            HistoricProcessInstanceQuery query = createQuery();

            if (sortField != null)
                switch (sortField) {
                case "id":
                    query.orderByProcessInstanceId();
                    break;
                case "processDefinitionId":
                    query.orderByProcessDefinitionId();
                    break;
                case "processInstanceBusinessKey":
                    query.orderByProcessInstanceBusinessKey();
                    break;
                default:
                    sortOrder = SortOrder.UNSORTED;
                } // switch

            if (sortOrder != null && sortOrder != SortOrder.UNSORTED)
                if (sortOrder == SortOrder.ASCENDING)
                    query.asc();
                else
                    query.desc();

            if (finishedOnly)
                query.finished();
            if (unfinishedOnly)
                query.unfinished();

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

            if (startedBy != null)
                query.startedBy(startedBy);

            List<HistoricProcessInstance> list = query.listPage(first, pageSize);
            return list;
        }

        @Override
        public String getRowKey(HistoricProcessInstance object) {
            return object.getId();
        }

    }

}
