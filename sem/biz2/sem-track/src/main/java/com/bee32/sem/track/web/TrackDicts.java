package com.bee32.sem.track.web;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.bee32.plover.faces.utils.SelectableList;
import com.bee32.plover.orm.util.DataViewBean;
import com.bee32.sem.track.entity.IssueState;

public class TrackDicts
        extends DataViewBean {

    private static final long serialVersionUID = 1L;

    private List<IssueState> states;

    public SelectableList<IssueState> getStates() {
        if (states == null) {
            states = new ArrayList<IssueState>(IssueState.values());
            Iterator<IssueState> it = states.iterator();
            while (it.hasNext()) {
                IssueState state = (IssueState) it.next();
                if (state.getLabel().equals("")) {
                    // 去除资源文件中没有对应项目，即显示为空的subject
                    it.remove();
                }
            }
        }
        return SelectableList.decorate(states);
    }

}
