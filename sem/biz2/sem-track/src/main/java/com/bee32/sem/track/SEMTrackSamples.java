package com.bee32.sem.track;

import java.util.Arrays;

import com.bee32.plover.orm.sample.NormalSamples;
import com.bee32.sem.people.SEMPeopleSamples;
import com.bee32.sem.track.entity.Issue;
import com.bee32.sem.track.entity.IssueReply;
import com.bee32.sem.track.util.IssueState;

public class SEMTrackSamples
        extends NormalSamples {

    public final Issue issue1 = new Issue();
    public final IssueReply reply1 = new IssueReply();
    public final IssueReply reply2 = new IssueReply();

    SEMPeopleSamples peoples = predefined(SEMPeopleSamples.class);

    @Override
    protected void wireUp() {
        reply1.setIssue(issue1);
        reply1.setText("the question is out of time");
        reply1.setOwner(peoples.tang);

        reply2.setIssue(issue1);
        reply2.setPrev(reply1);
        reply1.setFollows(Arrays.asList(reply2));
        reply2.setText("an other comment context");
        reply2.setOwner(peoples.jack);

        issue1.setLabel("test issue");
        issue1.setIssueState(IssueState.DUP);
        issue1.setText("test track sample\n\n问题重现: \n无。。");
        issue1.setTags("what's the token?");
        issue1.setCommitish("commitish?");
        issue1.setOwner(peoples.jack);
        issue1.setReplies(Arrays.asList(reply1, reply2));
    }

}
