package com.bee32.sem.make;

import com.bee32.plover.orm.unit.ImportUnit;
import com.bee32.plover.orm.unit.PersistenceUnit;
import com.bee32.plover.ox1.PloverOx1Unit;
import com.bee32.sem.make.entity.QCResult;
import com.bee32.sem.make.entity.QCResultParameter;
import com.bee32.sem.make.entity.QCSpec;
import com.bee32.sem.make.entity.QCSpecParameter;

/**
 * 质检数据单元
 *
 * <p lang="en">
 * SEM QC Unit
 */
@ImportUnit({ PloverOx1Unit.class })
public class SEMQCUnit
        extends PersistenceUnit {

    @Override
    protected void preamble() {
        add(QCSpec.class);
        add(QCSpecParameter.class);
        add(QCResult.class);
        add(QCResultParameter.class);
    }

}
