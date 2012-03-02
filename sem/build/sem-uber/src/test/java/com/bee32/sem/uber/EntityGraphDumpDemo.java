package com.bee32.sem.uber;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;

import com.bee32.icsf.principal.Users;
import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.orm.unit.Using;
import com.bee32.plover.orm.unit.xgraph.EntityGraphTool;
import com.bee32.plover.orm.unit.xgraph.EntityPartialRefs;
import com.bee32.plover.orm.unit.xgraph.EntityXrefMap;
import com.bee32.plover.orm.util.WiredDaoFeat;
import com.bee32.plover.test.ICoordinator;
import com.bee32.plover.util.FormatStyle;

@Using(SEMUberUnit.class)
public class EntityGraphDumpDemo
        extends WiredDaoFeat<EntityGraphDumpDemo> {

    @Inject
    EntityGraphTool tool;
    @Inject
    Users users;

    public void dumpXrefs(Entity<?> entity)
            throws DataAccessException {
        if (entity == null)
            throw new NullPointerException("entity");
        EntityXrefMap xrefMap = tool.getReferences(entity);

        System.out.println("References of " + entity.toString(FormatStyle.SIMPLE));
        for (EntityPartialRefs partialRefs : xrefMap.values()) {
            String label = partialRefs.getLabel();
            List<? extends Entity<?>> list = partialRefs.getList();

            System.out.println("Partial references by " + label + ": ");
            for (Entity<?> ref : list) {
                String line = ref.toString(FormatStyle.SIMPLE);
                System.out.println("    " + line);
            }
            System.out.println();
        }
    }

    @Transactional(readOnly = true)
    public void listAllForAdmin() {
        dumpXrefs(users.admin);
    }

    @Override
    protected String getFeatureSite() {
        return "zjhf";
    }

    public static void main(String[] args)
            throws IOException {
        new EntityGraphDumpDemo().mainLoop(new ICoordinator<EntityGraphDumpDemo>() {
            @Override
            public void main(EntityGraphDumpDemo feat)
                    throws Exception {
                feat.listAllForAdmin();
            }
        });
    }

}
