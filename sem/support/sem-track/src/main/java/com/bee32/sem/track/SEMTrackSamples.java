package com.bee32.sem.track;

import java.util.Arrays;

import com.bee32.plover.orm.sample.NormalSamples;
import com.bee32.sem.people.SEMPeopleSamples;
import com.bee32.sem.track.entity.Issue;
import com.bee32.sem.track.entity.IssueReply;
import com.bee32.sem.track.entity.IssueState;

public class SEMTrackSamples
        extends NormalSamples {

    public final Issue issue1 = new Issue();
    public final IssueReply reply1 = new IssueReply();

    SEMPeopleSamples peoples = predefined(SEMPeopleSamples.class);

    @Override
    protected void wireUp() {

        reply1.setIssue(issue1);
        reply1.setOwner(peoples.tang);
        reply1.setText("这是什么问题啊？");

        issue1.setState(IssueState.DUP);
        issue1.setText("test track sample");
        issue1.setTags("what's the token?");
        issue1.setCommitish("commitish?");
        issue1.setOwner(peoples.jack);
        issue1.setReplay("问题重现");
        issue1.setReplies(Arrays.asList(reply1));

    }

}
