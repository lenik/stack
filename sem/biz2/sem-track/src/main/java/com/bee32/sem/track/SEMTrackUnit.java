package com.bee32.sem.track;

import com.bee32.plover.orm.unit.ImportUnit;
import com.bee32.plover.orm.unit.PersistenceUnit;
import com.bee32.sem.asset.SEMAssetUnit;
import com.bee32.sem.chance.SEMChanceUnit;
import com.bee32.sem.event.SEMEventUnit;
import com.bee32.sem.file.SEMFileUnit;
import com.bee32.sem.makebiz.SEMMakebizUnit;
import com.bee32.sem.people.SEMPeopleUnit;
import com.bee32.sem.track.entity.Issue;
import com.bee32.sem.track.entity.IssueFav;
import com.bee32.sem.track.entity.IssueObserver;
import com.bee32.sem.track.entity.IssueReply;
import com.bee32.sem.track.entity.ProductLine;

@ImportUnit({ SEMPeopleUnit.class, SEMEventUnit.class, SEMFileUnit.class, SEMChanceUnit.class, SEMMakebizUnit.class,
        SEMAssetUnit.class })
public class SEMTrackUnit
        extends PersistenceUnit {

    @Override
    protected void preamble() {
        add(Issue.class);
        add(IssueReply.class);
        add(IssueFav.class);
        add(IssueObserver.class);
        add(ProductLine.class);
    }

}
