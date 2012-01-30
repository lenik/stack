package com.bee32.sem.misc;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.free.NotImplementedException;

import org.primefaces.model.TreeNode;

import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.plover.orm.util.EntityDto;
import com.bee32.plover.ox1.tree.TreeEntity;
import com.bee32.plover.ox1.tree.TreeEntityDto;
import com.bee32.plover.ox1.tree.TreeEntityUtils;
import com.bee32.sem.sandbox.UIHelper;

public abstract class SimpleTreeEntityViewBean
        extends SimpleEntityViewBean {

    private static final long serialVersionUID = 1L;

    transient Map<Serializable, TreeEntityDto<?, ?, ?>> index;
    transient TreeNodeIndexer nodeIndex;

    TreeNode rootNode;
    TreeNode selectedNode;

    public <E extends Entity<K>, D extends EntityDto<? super E, K>, K extends Serializable> //
    SimpleTreeEntityViewBean(Class<E> entityClass, Class<D> dtoClass, int selection,
            ICriteriaElement... criteriaElements) {
        super(entityClass, dtoClass, selection | TreeEntityDto.PARENT, criteriaElements);
    }

    protected synchronized TreeNode loadTree() {
        List<? extends TreeEntityDto<?, ?, ?>> dtos = (List<? extends TreeEntityDto<?, ?, ?>>) dataModel.listDtos();
        index = DTOs.index(dtos);

        @SuppressWarnings({ "unchecked", "rawtypes" })
        Set<? extends TreeEntityDto<?, ?, ?>> roots = TreeEntityUtils.rebuildTree((List) dtos, (Map) index);

        nodeIndex = new TreeNodeIndexer();
        return UIHelper.buildTree(nodeIndex, roots);
    }

    Map<Serializable, TreeEntityDto<?, ?, ?>> getIndex() {
        if (index == null) {
            synchronized (this) {
                if (index == null) {
                    loadTree();
                }
            }
        }
        return index;
    }

    Map<Serializable, TreeNode> getNodeIndex() {
        if (nodeIndex == null) {
            synchronized (this) {
                if (nodeIndex == null) {
                    nodeIndex = new TreeNodeIndexer();
                    nodeIndex.learn(getRootNode());
                }
            }
        }
        return nodeIndex;
    }

    public void refreshTree() {
        rootNode = null;
    }

    public void reindexNodes() {
        nodeIndex = null;
    }

    @Override
    protected void searchFragmentsChanged() {
        refreshTree();
        reindexNodes();
    }

    /**
     * Moving the tree node around.
     */
    @Override
    protected void _postUpdate(UnmarshalMap uMap)
            throws Exception {
        super._postUpdate(uMap);
        for (TreeEntity<?, ?> entity : uMap.<TreeEntity<?, ?>> entitySet()) {
            TreeEntityDto<?, ?, ?> dto = uMap.getSourceDto(entity);
            Serializable id = dto.getId();
            if (id == null) {
                id = entity.getId();
                if (id == null)
                    throw new NullPointerException("Null id at post-update.");
                @SuppressWarnings("unchecked")
                EntityDto<?, Serializable> _dto = (EntityDto<?, Serializable>) dto;
                _dto.setId(id);
            }

            if (selectedNode != null)
                selectedNode.setSelected(false);
            TreeNode oldNode = findNodeById(id);
            if (oldNode != null)
                UIHelper.detach(oldNode);

            // Create any necessary parent nodes like `mkdir -p`.
            // for (TreeNode ancestor: findAncestorNodes(group))
            TreeNode newParentNode = null;
            if (newParentNode == null) {
                TreeEntityDto<?, ?, ?> parent = dto.getParent();
                if (parent != null)
                    newParentNode = findNodeById(parent.getId());
                if (newParentNode == null)
                    newParentNode = rootNode;
            }

            // Select the new node.
            selectedNode = UIHelper.buildTree(null, listOf(dto), newParentNode);
            selectedNode.setSelected(true);
            if (newParentNode != null)
                newParentNode.setExpanded(true);
        }
    }

    /**
     * Check dependencies before delete.
     */
    @Override
    protected boolean _preDelete(UnmarshalMap uMap)
            throws Exception {
        if (!super._preDelete(uMap))
            return false;
        for (TreeEntityDto<?, ?, ?> dto : uMap.<TreeEntityDto<?, ?, ?>> dtos()) {
            if (!dto.isLeaf()) {
                uiLogger.error("请先删除子结点。");
                return false;
            }
        }
        // detach the dto, to reduce the dependencies as much as possible.
        for (TreeEntityDto<?, ?, ?> dto : uMap.<TreeEntityDto<?, ?, ?>> dtos()) {
            if (!dto.isRoot()) {
                TreeEntityDto<?, ?, ?> parent = dto.getParent();
                parent.getChildren().remove(dto);
                dto.setParent(null);
            }
        }
        return true;
    }

    /**
     * Remove the tree nodes deleted.
     */
    @Override
    protected void _postDelete(UnmarshalMap uMap)
            throws Exception {
        super._postDelete(uMap);
        for (TreeEntityDto<?, ?, ?> dto : uMap.<TreeEntityDto<?, ?, ?>> dtos()) {
            TreeNode node = findNodeById(dto.getId());
            if (node != null)
                UIHelper.detach(node);
        }
    }

    protected TreeNode findNodeById(Serializable id) {
        return getNodeIndex().get(id);
    }

    protected List<TreeNode> findAncestorNodes(TreeEntityDto<?, ?, ?> dto) {
        throw new NotImplementedException();
    }

    public TreeNode getRootNode() {
        if (rootNode == null) {
            synchronized (this) {
                if (rootNode == null) {
                    rootNode = loadTree();
                }
            }
        }
        return rootNode;
    }

    public void setRootNode(TreeNode rootNode) {
        this.rootNode = rootNode;
    }

    public TreeNode getSelectedNode() {
        return selectedNode;
    }

    public void setSelectedNode(TreeNode selectedNode) {
        this.selectedNode = selectedNode;
    }

    @Override
    public List<?> getSelection() {
        if (selectedNode == null)
            return Collections.emptyList();
        Object data = selectedNode.getData();
        return listOfNonNulls(data);
    }

    @Override
    public void setSelection(List<?> selection) {
        if (selection == null || selection.isEmpty())
            selectedNode = null;
        else
            for (Object sel : selection) {
                TreeEntityDto<?, ?, ?> dto = (TreeEntityDto<?, ?, ?>) sel;
                Object id = dto.getId();
                if (id != null) {
                    TreeNode node = getNodeIndex().get(id);
                    selectedNode = node;
                } else {
                    selectedNode = null;
                }
            }
    }

}
