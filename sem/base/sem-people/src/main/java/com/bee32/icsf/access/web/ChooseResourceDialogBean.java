package com.bee32.icsf.access.web;

import java.util.IdentityHashMap;
import java.util.Map;

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bee32.icsf.access.resource.IResourceNamespace;
import com.bee32.icsf.access.resource.Resource;
import com.bee32.icsf.access.resource.ScannedResourceRegistry;
import com.bee32.plover.arch.util.ClassCatalog;
import com.bee32.plover.arch.util.ClassUtil;
import com.bee32.plover.orm.entity.EntityResource;
import com.bee32.plover.orm.entity.EntityResourceNS;
import com.bee32.plover.ox1.tree.TreeEntity;
import com.bee32.plover.ox1.tree.TreeEntityDto;
import com.bee32.sem.misc.ChooseTreeEntityDialogBean;

public class ChooseResourceDialogBean
        extends ChooseTreeEntityDialogBean {

    private static final long serialVersionUID = 1L;

    static Logger logger = LoggerFactory.getLogger(ChooseResourceDialogBean.class);

    /**
     * NOT USED !
     */
    @SuppressWarnings("unchecked")
    public ChooseResourceDialogBean() {
        super(TreeEntity.class, TreeEntityDto.class, 0);
    }

    @Override
    public String getObjectTypeName() {
        return "Security Resource";
    }

    @Override
    protected synchronized TreeNode loadTree() {
        DefaultTreeNode rootNode = new DefaultTreeNode();

        ScannedResourceRegistry srr = BEAN(ScannedResourceRegistry.class);
        for (IResourceNamespace namespace : srr.getNamespaces()) {
            ResourceNodeData nsData = new ResourceNodeData();
            nsData.setTypeName(ClassUtil.getTypeName(namespace.getResourceType()));
            DefaultTreeNode nsNode = new DefaultTreeNode(nsData, rootNode);
            nsNode.setExpanded(true);

            // This is used to group entities under catalogs.
            Map<ClassCatalog, TreeNode> catalogMap = new IdentityHashMap<>();
            if (namespace instanceof EntityResourceNS) {
                for (Resource catalogResource : namespace.getResources()) {
                    EntityResource entityRes = (EntityResource) catalogResource;
                    if (entityRes.getEntityClass() == null) {
                        // catalog grouping node.
                        ClassCatalog catalog = entityRes.getCatalog();
                        ResourceNodeData catalogData = new ResourceNodeData();
                        catalogData.setResource(catalogResource);
                        catalogData.setTypeName("数据单元");
                        TreeNode catalogNode = new DefaultTreeNode(catalogData, nsNode);
                        // catalogNode.setExpanded(true);
                        catalogMap.put(catalog, catalogNode);
                    }
                }
            }

            for (Resource resource : namespace.getResources()) {
                TreeNode parentNode = nsNode;
                if (resource instanceof EntityResource) {
                    EntityResource entityRes = (EntityResource) resource;
                    if (entityRes.getEntityClass() == null)
                        continue;
                    parentNode = catalogMap.get(entityRes.getCatalog());
                    if (parentNode == null) // No catalog?? seems impossible.
                        parentNode = nsNode;
                }
                ResourceNodeData data = new ResourceNodeData();
                data.setResource(resource);
                /* DefaultTreeNode resNode = */new DefaultTreeNode(data, parentNode);
            }
        }
        return rootNode;
    }

}
