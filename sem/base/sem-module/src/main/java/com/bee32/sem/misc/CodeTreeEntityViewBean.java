package com.bee32.sem.misc;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.free.NotImplementedException;

import org.primefaces.model.TreeNode;

import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.plover.orm.util.EntityDto;
import com.bee32.plover.ox1.dict.NameDict;
import com.bee32.plover.ox1.dict.NameDictDto;
import com.bee32.sem.sandbox.UIHelper;

public abstract class CodeTreeEntityViewBean
        extends SimpleEntityViewBean {

    private static final long serialVersionUID = 1L;

    transient Map<Serializable, NameDictDto<?>> index;
    transient TreeNodeIndexer nodeIndex;

    TreeNode rootNode;
    TreeNode selectedNode;

    public <E extends Entity<K>, D extends EntityDto<? super E, K>, K extends Serializable> //
    CodeTreeEntityViewBean(Class<E> entityClass, Class<D> dtoClass, int selection, ICriteriaElement... criteriaElements) {
        super(entityClass, dtoClass, selection, criteriaElements);
    }

    protected synchronized TreeNode loadTree() {
        List<? extends NameDictDto<?>> dtos = (List<? extends NameDictDto<?>>) dataModel.listDtos();

        TreeNode root = UIHelper.buildDtoCodeTree(dtos);
        index = DTOs.index(dtos);

        nodeIndex = new TreeNodeIndexer();
        nodeIndex.learn(root);
        return root;
    }

    Map<Serializable, NameDictDto<?>> getIndex() {
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
                    nodeIndex.learn(rootNode);
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
        for (NameDict entity : uMap.<NameDict> entitySet()) {
            NameDictDto<?> dto = uMap.getSourceDto(entity);
            String id = dto.getId();
            if (id == null) { // For new entities, write back the generated-id to dto.
                id = entity.getId();
                if (id == null)
                    throw new NullPointerException("Null id at post-update.");
                dto.setId(id);
            }

            if (selectedNode != null)
                selectedNode.setSelected(false);
            TreeNode oldNode = findNodeById(id);
            if (oldNode != null)
                UIHelper.detach(oldNode);

            // Create any necessary parent nodes like `mkdir -p`.
            // for (TreeNode ancestor: findAncestorNodes(group))
            TreeNode parentNode = null;
            String parentId = dto.getId();
            while (parentNode == null) {
                if (parentId.isEmpty() || parentId == null)
                    break;
                parentId = parentId.substring(0, parentId.length() - 1);
                parentNode = findNodeById(parentId);
            }
            if (parentNode == null)
                parentNode = rootNode;

            // Select the new node.
            selectedNode = null;
            for (TreeNode node : UIHelper.buildDtoCodeTree(listOf(dto), parentNode)) {
                node.setSelected(true);
                selectedNode = node;
            }
            if (parentNode != null)
                parentNode.setExpanded(true);
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
        // detach the dto, to reduce the dependencies as much as possible.
        // There is nothing to detach for code-tree.
        return true;
    }

    /**
     * Remove the tree nodes deleted.
     */
    @Override
    protected void _postDelete(UnmarshalMap uMap)
            throws Exception {
        super._postDelete(uMap);
        for (NameDictDto<?> dto : uMap.<NameDictDto<?>> dtos()) {
            TreeNode node = findNodeById(dto.getId());
            if (node != null)
                UIHelper.detach(node);
        }
    }

    protected TreeNode findNodeById(Serializable id) {
        return getNodeIndex().get(id);
    }

    protected List<TreeNode> findAncestorNodes(NameDictDto<?> dto) {
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
                NameDictDto<?> dto = (NameDictDto<?>) sel;
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
