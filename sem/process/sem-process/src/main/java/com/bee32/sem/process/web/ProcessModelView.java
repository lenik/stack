package com.bee32.sem.process.web;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.impl.persistence.entity.ModelEntity;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ModelQuery;
import org.activiti.explorer.ExplorerApp;
import org.activiti.explorer.ui.process.simple.editor.SimpleTableEditorConstants;
import org.activiti.workflow.simple.converter.WorkflowDefinitionConversion;
import org.activiti.workflow.simple.converter.json.JsonConverter;
import org.activiti.workflow.simple.definition.WorkflowDefinition;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ObjectNode;
import org.primefaces.model.SortOrder;

import com.bee32.plover.arch.util.Strs;
import com.bee32.plover.orm.util.ITypeAbbrAware;
import com.bee32.sem.sandbox.LazyDataModelImpl;

/**
 * @see com.bee32.ape.engine.beans.ApeActivitiServices
 */
public class ProcessModelView
        extends AbstractApexView {

    private static final long serialVersionUID = 1L;

    DataModel dataModel = new DataModel();

    @Override
    public DataModel getDataModel() {
        return dataModel;
    }

    public String getEntityTypeAbbr() {
        return ITypeAbbrAware.ABBR.abbr(ModelEntity.class);
    }

    public void deploy() {
        Model model = (Model) getSingleSelection();
        if (model == null) {
            uiLogger.error("没有选择要部署的对象。");
            return;
        }
        if (model.getDeploymentId() != null) {
            uiLogger.error("模型已部署：" + model.getDeploymentId());
            return;
        }

        String modelName = model.getName();

        if (StringUtils.isEmpty(modelName) || "process".equals(modelName)) {
            uiLogger.error("模型名称无效：" + modelName);
            return;
        }

        RepositoryService repositoryService = BEAN(RepositoryService.class);
        DeploymentBuilder builder = repositoryService.createDeployment();

        byte[] modelSource = repositoryService.getModelEditorSource(model.getId());
        ObjectNode modelNode;
        try {
            modelNode = (ObjectNode) new ObjectMapper().readTree(modelSource);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
        String bpmnXml;

        if (SimpleTableEditorConstants.TABLE_EDITOR_CATEGORY.equals(model.getCategory())) {
            JsonConverter jsonConverter = new JsonConverter();
            WorkflowDefinition workflowDefinition = jsonConverter.convertFromJson(modelNode);

            WorkflowDefinitionConversion conversion = ExplorerApp.get().getWorkflowDefinitionConversionFactory()
                    .createWorkflowDefinitionConversion(workflowDefinition);
            bpmnXml = conversion.getbpm20Xml();
        } else {
            BpmnModel bpmnModel = new BpmnJsonConverter().convertToBpmnModel(modelNode);
            byte[] bpmnBytes = new BpmnXMLConverter().convertToXML(bpmnModel);
            try {
                bpmnXml = new String(bpmnBytes, "utf-8");
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e.getMessage(), e);
            }
        }

        String processName = modelName + ".bpmn20.xml";

        builder.name(modelName);
        builder.addString(processName, bpmnXml);

        try {
            Deployment deployment = builder.deploy();
            String deployId = deployment.getId();
            model.setDeploymentId(deployId);
            uiLogger.info("部署成功：编号 " + deployId);
        } catch (Exception e) {
            uiLogger.error("部署失败。", e);
        }

        try {
            repositoryService.saveModel(model);
            uiLogger.info("更新模型状态成功。");
        } catch (Exception e) {
            uiLogger.error("更新模型状态失败。", e);
        }
    }

    /*************************************************************************
     * Section: Persistence
     *************************************************************************/
    @Override
    protected Object create() {
        RepositoryService service = BEAN(RepositoryService.class);
        Model model = service.newModel();
        return model;
    }

    @Override
    protected boolean saveImpl(int saveFlags, String hint, boolean creating) {
        Model model = getOpenedObject();

        RepositoryService repositoryService = BEAN(RepositoryService.class);
        repositoryService.saveModel(model);
        return true;
    }

    public void deleteSelection() {
        if (!isSelected()) {
            uiLogger.error("没有选择需要删除的对象。");
            return;
        }
        uiLogger.error("过程定义不可删除。");
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
     * Section: Data Model
     *************************************************************************/

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
