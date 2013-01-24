package com.bee32.sem.makebiz.web;

import com.bee32.plover.criteria.hibernate.Alias;
import com.bee32.plover.criteria.hibernate.Equals;
import com.bee32.plover.orm.annotation.ForEntity;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.sem.makebiz.dto.MakeProcessDto;
import com.bee32.sem.makebiz.dto.MakeTaskDto;
import com.bee32.sem.makebiz.entity.MakeProcess;
import com.bee32.sem.makebiz.entity.MakeTask;
import com.bee32.sem.misc.SimpleEntityViewBean;

@ForEntity(MakeProcess.class)
public class MakeProcessListAdminBean
        extends SimpleEntityViewBean {

    private static final long serialVersionUID = 1L;

    Long taskId;
    boolean invalidateTaskId = false;

    public MakeProcessListAdminBean() {
        super(MakeProcess.class, MakeProcessDto.class, 0);
    }


    public void init() {
        if (!invalidateTaskId) {
            clearSearchFragments();
            taskId = null;
        } else {
            MakeTask task = ctx.data.getOrFail(MakeTask.class, taskId);
            searchTask = DTOs.marshal(MakeTaskDto.class, task);
            addTaskRestriction();
        }
        invalidateTaskId = false;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
        invalidateTaskId = true;
    }

    /*************************************************************************
     * Section: MBeans
     *************************************************************************/



    /*************************************************************************
     * Section: Persistence
     *************************************************************************/



    /*************************************************************************
     * Section: Search
     *************************************************************************/
    MakeTaskDto searchTask;

    public MakeTaskDto getSearchTask() {
        return searchTask;
    }

    public void setSearchTask(MakeTaskDto searchTask) {
        this.searchTask = searchTask;
    }

    public void addTaskRestriction() {
        if (searchTask != null) {
            setSearchFragment("task", "生产任务为 " + searchTask.getLabel(), //
                    new Alias("taskItem", "taskItem"),
                    new Equals("taskItem.task.id", searchTask.getId()));
            searchTask = null;
        }
    }
}
