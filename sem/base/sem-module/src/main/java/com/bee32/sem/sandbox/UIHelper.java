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
import com.bee32.plover.orm.util.FacesContextSupport2;
import com.bee32.plover.ox1.color.UIEntityAuto;
import com.bee32.plover.ox1.color.UIEntityDto;
import com.bee32.plover.ox1.dict.CodeTreeBuilder;
import com.bee32.plover.ox1.dict.DtoCodeTreeBuilder;
import com.bee32.plover.ox1.dict.NameDict;
import com.bee32.plover.ox1.dict.NameDictDto;
import com.bee32.plover.ox1.dict.PoNode;
import com.bee32.plover.ox1.dict.PoTreeBuilder;
import com.bee32.plover.ox1.tree.TreeEntityDto;

public class UIHelper
        extends FacesContextSupport2 {

    public static List<SelectItem> selectItemsFromDict(Iterable<? extends NameDictDto<?>> dictEntries) {
        List<SelectItem> items = new ArrayList<SelectItem>();

        for (NameDictDto<?> entry : dictEntries) {
            SelectItem item = new SelectItem(entry.getDisplayId(), entry.getLabel());
            items.add(item);
        }

        return items;
    }

    public static List<SelectItem> selectItemsFromDict2(Iterable<? extends NameDictDto<?>> dictEntries) {
        List<SelectItem> items = new ArrayList<SelectItem>();

        for (NameDictDto<?> entry : dictEntries) {
            SelectItem item = new SelectItem(entry.getDisplayId(), entry.getLabel() + " (" + entry.getName() + ")");
            items.add(item);
        }

        return items;
    }

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

    public static <T> ListHolder<T> selectable(List<T> list) {
        if (list == null)
            throw new NullPointerException("list");
        return new ListHolder<T>(list);
    }

    public static TreeNode buildCodeTree(Collection<? extends NameDict> objs) {
        CodeTreeBuilder ctb = new CodeTreeBuilder();
        ctb.learn(objs);
        return _convertTree(ctb);
    }

    public static TreeNode buildDtoCodeTree(Collection<? extends NameDictDto<?>> dtos) {
        DtoCodeTreeBuilder ctb = new DtoCodeTreeBuilder();
        ctb.learn(dtos);
        return _convertTree(ctb);
    }

    static TreeNode _convertTree(PoTreeBuilder<?, ?> ptb) {
        ptb.reduce();
        PoNode<?> root = ptb.getRoot();
        DefaultTreeNode guiRoot = new DefaultTreeNode();
        _convertTree(root, guiRoot);
        return guiRoot;
    }

    static TreeNode _convertTree(PoNode<?> src, TreeNode guiParent) {
        Object data = src.getData();
        TreeNode result;
        if (src.isVirtual())
            result = guiParent;
        else
            result = new DefaultTreeNode(data, guiParent);
        for (PoNode<?> child : src.getChildren()) {
            _convertTree(child, result);
        }
        return result;
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
