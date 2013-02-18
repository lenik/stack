package com.bee32.sem.process.web;

import java.util.List;
import java.util.Map;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
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
public class ProcessDefinitionView
        extends DataViewBean {

    private static final long serialVersionUID = 1L;

    DataModel dataModel = new DataModel();

    @Override
    public DataModel getDataModel() {
        return dataModel;
    }

    public String getEntityTypeAbbr() {
        return ITypeAbbrAware.ABBR.abbr(ProcessDefinitionEntity.class);
    }

    public void test() {
        RepositoryService service = BEAN(RepositoryService.class);
        ProcessDefinitionQuery query = service.createProcessDefinitionQuery();
        List<ProcessDefinition> defs = query.list();
        for (ProcessDefinition def : defs) {
            System.out.println("ProcessDefinition: ");
            String str = GeneralFormatter.toString(def, FormatStyle.NORMAL, 2);
            System.out.println(str);
            System.out.println();
        }
    }

    /*************************************************************************
     * Section: Search
     *************************************************************************/

    boolean activeOnly;
    boolean suspendedOnly;
    boolean latestVersionOnly;

    String deploymentId;
    String processDefinitionId;
    String processDefinitionKeyLike;
    String processDefinitionNameLike;
    String startableByUser;

    protected ProcessDefinitionQuery createQuery() {
        RepositoryService service = BEAN(RepositoryService.class);
        ProcessDefinitionQuery query = service.createProcessDefinitionQuery();
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

    public boolean isLatestVersionOnly() {
        return latestVersionOnly;
    }

    public void setLatestVersionOnly(boolean latestVersionOnly) {
        this.latestVersionOnly = latestVersionOnly;
    }

    public String getDeploymentId() {
        return deploymentId;
    }

    public void setDeploymentId(String deploymentId) {
        this.deploymentId = deploymentId;
    }

    public String getProcessDefinitionId() {
        return processDefinitionId;
    }

    public void setProcessDefinitionId(String processDefinitionId) {
        this.processDefinitionId = processDefinitionId;
    }

    public String getProcessDefinitionKeyLike() {
        return processDefinitionKeyLike;
    }

    public void setProcessDefinitionKeyLike(String processDefinitionKeyLike) {
        this.processDefinitionKeyLike = processDefinitionKeyLike;
    }

    public String getProcessDefinitionNameLike() {
        return processDefinitionNameLike;
    }

    public void setProcessDefinitionNameLike(String processDefinitionNameLike) {
        this.processDefinitionNameLike = processDefinitionNameLike;
    }

    public String getStartableByUser() {
        return startableByUser;
    }

    public void setStartableByUser(String startableByUser) {
        this.startableByUser = startableByUser;
    }

    /*************************************************************************
     * Section: Persistence
     *************************************************************************/

    public void deleteSelection() {
        if (!isSelected()) {
            uiLogger.error("没有选择需要删除的对象。");
            return;
        }
        uiLogger.error("过程定义不可删除。");
    }

    class DataModel
            extends LazyDataModelImpl<ProcessDefinition> {

        private static final long serialVersionUID = 1L;

        @Override
        protected int countImpl() {
            ProcessDefinitionQuery query = createQuery();
            int count = (int) query.count();
            return count;
        }

        @Override
        protected List<?> loadImpl(int first, int pageSize, String sortField, SortOrder sortOrder,
                Map<String, String> filters) {
            ProcessDefinitionQuery query = createQuery();

            if (sortField != null)
                switch (sortField) {
                case "id":
                    query.orderByProcessDefinitionId();
                    break;
                case "name":
                    query.orderByProcessDefinitionName();
                    break;
                case "key":
                    query.orderByProcessDefinitionKey();
                    break;
                case "deploymentId":
                    query.orderByDeploymentId();
                    break;
                case "category":
                    query.orderByProcessDefinitionCategory();
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
            if (latestVersionOnly)
                query.latestVersion();

            if (Strs.isNotEmpty(deploymentId))
                query.deploymentId(deploymentId);

            if (Strs.isNotEmpty(processDefinitionId))
                query.processDefinitionId(processDefinitionId);
            if (Strs.isNotEmpty(processDefinitionKeyLike))
                query.processDefinitionKeyLike(processDefinitionKeyLike);
            if (Strs.isNotEmpty(processDefinitionNameLike))
                query.processDefinitionNameLike(processDefinitionNameLike);

            if (Strs.isNotEmpty(startableByUser))
                query.startableByUser(startableByUser);

            List<ProcessDefinition> list = query.listPage(first, pageSize);
            return list;
        }

        @Override
        public String getRowKey(ProcessDefinition object) {
            return object.getId();
        }

    }

}
