package com.bee32.sem.sandbox;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.faces.model.SelectItem;
import javax.free.IllegalUsageException;

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import com.bee32.plover.arch.util.EnumAlt;
import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.orm.util.EntityDto;
import com.bee32.plover.ox1.color.UIEntityAuto;
import com.bee32.plover.ox1.color.UIEntityDto;
import com.bee32.plover.ox1.dict.CodeTreeBuilder;
import com.bee32.plover.ox1.dict.DtoCodeTreeBuilder;
import com.bee32.plover.ox1.dict.NameDict;
import com.bee32.plover.ox1.dict.NameDictDto;
import com.bee32.plover.ox1.dict.PoNode;
import com.bee32.plover.ox1.dict.PoTreeBuilder;
import com.bee32.plover.ox1.tree.TreeEntityDto;

public class UIHelper {

    public static List<SelectItem> selectItemsFromDict(Iterable<? extends NameDictDto<?>> dictEntries) {
        List<SelectItem> items = new ArrayList<SelectItem>();

        for (NameDictDto<?> entry : dictEntries) {
            SelectItem item = new SelectItem(entry.getDisplayId(), entry.getLabel());
            items.add(item);
        }

        return items;
    }

    @Deprecated
    public static List<SelectItem> selectItemsFromDict2(Iterable<? extends NameDictDto<?>> dictEntries) {
        List<SelectItem> items = new ArrayList<SelectItem>();

        for (NameDictDto<?> entry : dictEntries) {
            SelectItem item = new SelectItem(entry.getDisplayId(), entry.getLabel() + " (" + entry.getName() + ")");
            items.add(item);
        }

        return items;
    }

    @Deprecated
    public static List<SelectItem> selectItemsFromEnum(Iterable<? extends EnumAlt<?, ?>> enums) {
        List<SelectItem> items = new ArrayList<SelectItem>();

        for (EnumAlt<?, ?> entry : enums) {
            Object value = entry.getValue();
            String label = entry.getLabel();
            SelectItem item = new SelectItem(value, label);
            items.add(item);
        }

        return items;
    }

    public static <E extends UIEntityAuto<K>, K extends Serializable> //
    List<SelectItem> selectItems(Iterable<? extends UIEntityDto<E, K>> entries) {
        List<SelectItem> items = new ArrayList<SelectItem>();

        for (UIEntityDto<E, K> entry : entries) {
            SelectItem item = new SelectItem(entry.getId(), entry.getLabel());
            items.add(item);
        }

        return items;
    }

    public static <E extends Entity<?>, D extends EntityDto<? super E, ?>> //
    ZLazyDataModel<E, D> buildLazyDataModel(EntityDataModelOptions<E, D> options) {
        return new ZLazyDataModel<E, D>(options);
    }

    public static TreeNode buildCodeTree(Collection<? extends NameDict> objs) {
        DefaultTreeNode virtualRoot = new DefaultTreeNode();
        buildCodeTree(objs, virtualRoot);
        return virtualRoot;
    }

    public static List<TreeNode> buildCodeTree(Collection<? extends NameDict> objs, TreeNode parentNode) {
        CodeTreeBuilder ctb = new CodeTreeBuilder();
        ctb.learn(objs);
        return buildPoTree(ctb, parentNode);
    }

    public static TreeNode buildDtoCodeTree(Collection<? extends NameDictDto<?>> dtos) {
        DefaultTreeNode virtualRoot = new DefaultTreeNode();
        buildDtoCodeTree(dtos, virtualRoot);
        return virtualRoot;
    }

    public static List<TreeNode> buildDtoCodeTree(Collection<? extends NameDictDto<?>> dtos, TreeNode parentNode) {
        DtoCodeTreeBuilder ctb = new DtoCodeTreeBuilder();
        ctb.learn(dtos);
        return buildPoTree(ctb, parentNode);
    }

    static List<TreeNode> buildPoTree(PoTreeBuilder<?, ?> ptb, TreeNode parentNode) {
        ptb.reduce();
        PoNode<?> root = ptb.getRoot();
        List<TreeNode> toplevels = new ArrayList<TreeNode>();
        _convertTree(root, parentNode, toplevels);
        return toplevels;
    }

    static TreeNode _convertTree(PoNode<?> src, TreeNode parentNode, Collection<TreeNode> toplevels) {
        Object data = src.getData();
        TreeNode treeNode;
        if (src.isVirtual())
            treeNode = parentNode;
        else {
            treeNode = new DefaultTreeNode(data, parentNode);
            if (toplevels != null) {
                toplevels.add(treeNode);
                toplevels = null;
            }
        }
        for (PoNode<?> child : src.getChildren()) {
            _convertTree(child, treeNode, toplevels);
        }
        return treeNode;
    }

    /**
     * @return The root node created.
     */
    public static TreeNode buildTree(ITreeNodeDecorator decorator, Collection<? extends TreeEntityDto<?, ?, ?>> dtoNodes) {
        TreeNode rootNode = new DefaultTreeNode();
        buildTree(decorator, dtoNodes, rootNode);
        return rootNode;
    }

    /**
     * @return The last node created by this method.
     */
    public static TreeNode buildTree(ITreeNodeDecorator decorator,
            Collection<? extends TreeEntityDto<?, ?, ?>> dtoNodes, TreeNode parentNode) {
        TreeNode last = null;
        for (TreeEntityDto<?, ?, ?> dtoNode : dtoNodes)
            last = _buildTree(decorator, dtoNode, parentNode);
        return last;
    }

    static TreeNode _buildTree(ITreeNodeDecorator decorator, TreeEntityDto<?, ?, ?> dtoNode, TreeNode parentNode) {
        TreeNode treeNode = new DefaultTreeNode(dtoNode, parentNode);
        if (decorator != null)
            decorator.decorate(treeNode);
        for (Object _child : dtoNode.getChildren()) {
            TreeEntityDto<?, ?, ?> child = (TreeEntityDto<?, ?, ?>) _child;
            _buildTree(decorator, child, treeNode);
        }
        return treeNode;
    }

    public static void decorateTree(ITreeNodeDecorator decorator, TreeNode node) {
        if (decorator == null)
            throw new NullPointerException("decorator");
        if (node == null)
            throw new NullPointerException("node");
        _decorateTree(decorator, node);
    }

    static void _decorateTree(ITreeNodeDecorator decorator, TreeNode node) {
        decorator.decorate(node);
        for (TreeNode child : node.getChildren())
            _decorateTree(decorator, child);
    }

    public static void detach(TreeNode node) {
        if (node == null)
            throw new NullPointerException("node");
        TreeNode parent = node.getParent();
        if (parent == null)
            throw new IllegalUsageException("You can't detach a root node.");
        /**
         * Should node.children merged into parent?
         */
        // ...
        parent.getChildren().remove(node);
        node.setParent(null);
    }

}
