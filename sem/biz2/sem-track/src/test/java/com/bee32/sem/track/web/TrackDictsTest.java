package com.bee32.sem.track.web;

import java.util.Locale;

import org.junit.Assert;

import com.bee32.plover.faces.utils.SelectableList;
import com.bee32.sem.track.util.IssueState;

public class TrackDictsTest
        extends Assert {

    public static void main(String[] args) {
        Locale.setDefault(Locale.CHINA);

        SelectableList<IssueState> states = new TrackDicts().getStates();
        for (IssueState state : states) {
            System.out.println(state.getLabel());
        }
    }

}
