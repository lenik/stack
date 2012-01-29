package com.bee32.sem.misc;

import java.io.Serializable;
import java.util.HashMap;

import org.primefaces.model.TreeNode;

import com.bee32.plover.ox1.tree.TreeEntityDto;
import com.bee32.sem.sandbox.ITreeNodeDecorator;
import com.bee32.sem.sandbox.UIHelper;

public class TreeNodeIndexer
        extends HashMap<Serializable, TreeNode>
        implements ITreeNodeDecorator {

    private static final long serialVersionUID = 1L;

    @Override
    public void decorate(TreeNode node) {
        TreeEntityDto<?, ?, ?> dto = (TreeEntityDto<?, ?, ?>) node.getData();
        if (dto != null) {
            Serializable id = dto.getId();
            assert id != null;
            put(id, node);
        }
    }

    public void learn(TreeNode node) {
        UIHelper.decorateTree(this, node);
    }

}
