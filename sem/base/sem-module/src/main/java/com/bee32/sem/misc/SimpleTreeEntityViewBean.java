package com.bee32.sem.misc;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.free.NotImplementedException;
import javax.free.ReadOnlyException;

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import com.bee32.icsf.principal.GroupDto;
import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.orm.util.EntityDto;
import com.bee32.plover.ox1.tree.TreeEntityDto;
import com.bee32.plover.ox1.tree.TreeEntityUtils;
import com.bee32.sem.sandbox.ITreeNodeDecorator;
import com.bee32.sem.sandbox.UIHelper;

public abstract class SimpleTreeEntityViewBean
        extends SimpleEntityViewBean {

    private static final long serialVersionUID = 1L;

    transient Map<Serializable, TreeEntityDto<?, ?, ?>> index;
    transient Map<Serializable, TreeNode> nodeIndex;

    TreeNode rootNode;
    TreeNode selectedNode;

    public <E extends Entity<?>, D extends EntityDto<? super E, ?>> SimpleTreeEntityViewBean(Class<E> entityClass,
            Class<D> dtoClass, int selection, ICriteriaElement... criteriaElements) {
        super(entityClass, dtoClass, selection | TreeEntityDto.PARENT, criteriaElements);
    }

    class NodeIndexer
            implements ITreeNodeDecorator {

        @Override
        public void decorate(TreeNode node) {
            TreeEntityDto<?, ?, ?> dto = (TreeEntityDto<?, ?, ?>) node.getData();
            if (dto != null) {
                Serializable id = dto.getId();
                assert id != null;
                nodeIndex.put(id, node);
            }
        }

    }

    protected synchronized TreeNode loadTree() {
        List<? extends TreeEntityDto<?, ?, ?>> dtos = (List<? extends TreeEntityDto<?, ?, ?>>) dataModel.listDtos();
        index = TreeEntityUtils.index(dtos);

        @SuppressWarnings({ "unchecked", "rawtypes" })
        Set<? extends TreeEntityDto<?, ?, ?>> roots = TreeEntityUtils.rebuildTree((List) dtos, (Map) index);

        nodeIndex = new HashMap<Serializable, TreeNode>();
        return UIHelper.buildTree(new NodeIndexer(), roots);
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
                    nodeIndex = new HashMap<Serializable, TreeNode>();
                    UIHelper.decorateTree(new NodeIndexer(), rootNode);
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
    protected void _postUpdate(UnmarshalMap uMap)
            throws Exception {
        super._postUpdate(uMap);
        for (TreeEntityDto<?, ?, ?> dto : uMap.<TreeEntityDto<?, ?, ?>> dtos()) {
            TreeNode node = findNodeById(dto.getId());
            TreeNode parentNode = null;
            if (node != null) {
                parentNode = node.getParent();
                UIHelper.detach(node);
            }
            // Create any necessary parent nodes like `mkdir -p`.
            // for (TreeNode ancestor: findAncestorNodes(group))
            if (parentNode == null) {
                TreeEntityDto<?, ?, ?> parent = dto.getParent();
                if (parent != null)
                    parentNode = findNodeById(parent.getId());
                if (parentNode == null)
                    parentNode = rootNode;
            }
            new DefaultTreeNode(dto, parentNode);
        }
    }

    @Override
    protected boolean _preDelete(UnmarshalMap uMap)
            throws Exception {
        if (!super._preDelete(uMap))
            return false;
        // check for non-leaves.
        for (TreeEntityDto<?, ?, ?> dto : uMap.<TreeEntityDto<?, ?, ?>> dtos()) {
            if (!dto.isLeaf()) {
                uiLogger.error("请先删除子结点。");
                return false;
            }
        }
        // detach
        for (TreeEntityDto<?, ?, ?> dto : uMap.<TreeEntityDto<?, ?, ?>> dtos()) {
            if (!dto.isRoot()) {
                TreeEntityDto<?, ?, ?> parent = dto.getParent();
                parent.getChildren().remove(dto);
                dto.setParent(null);
            }
        }
        return true;
    }

    @Override
    protected void _postDelete(UnmarshalMap uMap)
            throws Exception {
        super._postDelete(uMap);
        for (GroupDto group : uMap.<GroupDto> dtos()) {
            TreeNode node = findNodeById(group.getId());
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
        throw new ReadOnlyException("You can only change the selection by click a node in the Tree.");
    }

}
