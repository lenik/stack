package com.bee32.sem.track.web;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.bee32.plover.faces.utils.SelectableList;
import com.bee32.plover.orm.util.DataViewBean;
import com.bee32.sem.track.util.IssueState;
import com.bee32.sem.track.util.IssueType;

public class TrackDicts
        extends DataViewBean {

    private static final long serialVersionUID = 1L;

    private List<IssueState> issueStates;
    private List<IssueType> issueTypes;

    public SelectableList<IssueState> getStates() {
        if (issueStates == null) {
            issueStates = new ArrayList<IssueState>(IssueState.values());
            Iterator<IssueState> it = issueStates.iterator();
            while (it.hasNext()) {
                IssueState state = (IssueState) it.next();
                if (state.getLabel().equals("")) {
                    // 去除资源文件中没有对应项目，即显示为空的subject
                    it.remove();
                }
            }
        }
        return SelectableList.decorate(issueStates);
    }

    public SelectableList<IssueType> getTypes() {
        if (issueTypes == null) {
            issueTypes = new ArrayList<IssueType>(IssueType.values());
            Iterator<IssueType> it = issueTypes.iterator();
            while (it.hasNext()) {
                IssueType Type = (IssueType) it.next();
                if (Type.getLabel().equals("")) {
                    // 去除资源文件中没有对应项目，即显示为空的subject
                    it.remove();
                }
            }
        }
        return SelectableList.decorate(issueTypes);
    }

}
