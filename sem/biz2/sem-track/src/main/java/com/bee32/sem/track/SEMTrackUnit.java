package com.bee32.sem.track;

import com.bee32.plover.orm.unit.ImportUnit;
import com.bee32.plover.orm.unit.PersistenceUnit;
import com.bee32.sem.asset.SEMAssetUnit;
import com.bee32.sem.chance.SEMChanceUnit;
import com.bee32.sem.file.SEMFileUnit;
import com.bee32.sem.mail.SEMMailUnit;
import com.bee32.sem.makebiz.SEMMakebizUnit;
import com.bee32.sem.people.SEMPeopleUnit;
import com.bee32.sem.track.entity.Issue;
import com.bee32.sem.track.entity.IssueCcGroup;
import com.bee32.sem.track.entity.IssueHref;
import com.bee32.sem.track.entity.IssueObserver;
import com.bee32.sem.track.entity.IssueReply;

@ImportUnit({ SEMPeopleUnit.class, SEMMailUnit.class, SEMFileUnit.class, SEMChanceUnit.class, SEMMakebizUnit.class,
        SEMAssetUnit.class })
public class SEMTrackUnit
        extends PersistenceUnit {

    @Override
    protected void preamble() {
        add(Issue.class);
        add(IssueHref.class);
        add(IssueReply.class);
        add(IssueObserver.class);
        add(IssueCcGroup.class);
    }

}
