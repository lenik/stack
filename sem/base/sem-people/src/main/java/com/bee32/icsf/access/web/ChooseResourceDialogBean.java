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
import com.bee32.plover.arch.util.ClassUtil;
import com.bee32.plover.orm.entity.EntityResource;
import com.bee32.plover.orm.entity.EntityResourceNS;
import com.bee32.plover.ox1.tree.TreeEntity;
import com.bee32.plover.ox1.tree.TreeEntityDto;
import com.bee32.sem.misc.SimpleTreeEntityViewBean;

public class ChooseResourceDialogBean
        extends SimpleTreeEntityViewBean {

    private static final long serialVersionUID = 1L;

    static Logger logger = LoggerFactory.getLogger(ChooseResourceDialogBean.class);

    String header = "Please choose a resource..."; // NLS: 选择资源…

    /**
     * NOT USED !
     */
    @SuppressWarnings("unchecked")
    public ChooseResourceDialogBean() {
        super(TreeEntity.class, TreeEntityDto.class, 0);
    }

    // Properties

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    @Override
    protected synchronized TreeNode loadTree() {
        DefaultTreeNode rootNode = new DefaultTreeNode();

        ScannedResourceRegistry srr = ctx.bean.getBean(ScannedResourceRegistry.class);
        for (IResourceNamespace namespace : srr.getNamespaces()) {
            ResourceNodeData nsData = new ResourceNodeData();
            nsData.setTypeName(ClassUtil.getTypeName(namespace.getResourceType()));
            DefaultTreeNode nsNode = new DefaultTreeNode(nsData, rootNode);

            // This is used to group entities under catalogs.
            Map<Resource, TreeNode> parentMap = new IdentityHashMap<>();
            if (namespace instanceof EntityResourceNS) {
                for (Resource catalogResource : namespace.getResources()) {
                    EntityResource entityRes = (EntityResource) catalogResource;
                    if (entityRes.getEntityClass() != null) {
                        // catalog grouping node.
                        ResourceNodeData data = new ResourceNodeData();
                        data.setResource(catalogResource);
                        TreeNode catalogNode = new DefaultTreeNode(data, nsNode);
                        parentMap.put(catalogResource, catalogNode);
                    }
                }
            }

            for (Resource resource : namespace.getResources()) {
                TreeNode parentNode = nsNode;
                if (parentMap.containsKey(resource)) {
                    parentNode = parentMap.get(resource);
                    if (parentNode == null)
                        continue;
                }

                ResourceNodeData data = new ResourceNodeData();
                data.setResource(resource);

                /* DefaultTreeNode resNode = */new DefaultTreeNode(data, parentNode);
            }
        }
        return rootNode;
    }

}
