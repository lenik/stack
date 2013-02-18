package com.bee32.sem.process.web;

import java.util.List;
import java.util.Map;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.impl.persistence.entity.ModelEntity;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ModelQuery;
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
public class ModelView
        extends DataViewBean {

    private static final long serialVersionUID = 1L;

    DataModel dataModel = new DataModel();

    @Override
    public DataModel getDataModel() {
        return dataModel;
    }

    public String getEntityTypeAbbr() {
        return ITypeAbbrAware.ABBR.abbr(ModelEntity.class);
    }

    public void test() {
        RepositoryService service = BEAN(RepositoryService.class);
        ModelQuery query = service.createModelQuery();
        List<Model> defs = query.list();
        for (Model def : defs) {
            System.out.println("Model: ");
            String str = GeneralFormatter.toString(def, FormatStyle.NORMAL, 2);
            System.out.println(str);
            System.out.println();
        }
    }

    /*************************************************************************
     * Section: Search
     *************************************************************************/

    boolean latestVersionOnly;

    String deploymentId;

    protected ModelQuery createQuery() {
        RepositoryService service = BEAN(RepositoryService.class);
        ModelQuery query = service.createModelQuery();
        return query;
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
            extends LazyDataModelImpl<Model> {

        private static final long serialVersionUID = 1L;

        @Override
        protected int countImpl() {
            ModelQuery query = createQuery();
            int count = (int) query.count();
            return count;
        }

        @Override
        protected List<?> loadImpl(int first, int pageSize, String sortField, SortOrder sortOrder,
                Map<String, String> filters) {
            ModelQuery query = createQuery();

            if (sortField != null)
                switch (sortField) {
                case "id":
                    query.orderByModelId();
                    break;
                case "name":
                    query.orderByModelName();
                    break;
                case "key":
                    query.orderByModelKey();
                    break;
                case "category":
                    query.orderByModelCategory();
                    break;
                default:
                    sortOrder = SortOrder.UNSORTED;
                } // switch

            if (sortOrder != null && sortOrder != SortOrder.UNSORTED)
                if (sortOrder == SortOrder.ASCENDING)
                    query.asc();
                else
                    query.desc();

            if (latestVersionOnly)
                query.latestVersion();

            if (Strs.isNotEmpty(deploymentId))
                query.deploymentId(deploymentId);

            List<Model> list = query.listPage(first, pageSize);
            return list;
        }

        @Override
        public String getRowKey(Model object) {
            return object.getId();
        }

    }

}
