package com.bee32.sem.makebiz.web;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.StringUtils;

import com.bee32.plover.criteria.hibernate.Alias;
import com.bee32.plover.criteria.hibernate.Equals;
import com.bee32.plover.orm.annotation.ForEntity;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.plover.ox1.util.CommonCriteria;
import com.bee32.sem.chance.entity.Chance;
import com.bee32.sem.makebiz.dto.MakeProcessDto;
import com.bee32.sem.makebiz.dto.MakeTaskDto;
import com.bee32.sem.makebiz.entity.MakeProcess;
import com.bee32.sem.makebiz.entity.MakeTask;
import com.bee32.sem.misc.SimpleEntityViewBean;

@ForEntity(Chance.class)
public class MakeProcessListAdminBean
        extends SimpleEntityViewBean {

    private static final long serialVersionUID = 1L;


    public MakeProcessListAdminBean() {
        super(MakeProcess.class, MakeProcessDto.class, 0);
    }

    @PostConstruct
    public void init() {
        String taskId = ctx.view.getRequest().getParameter("taskId");
        if(StringUtils.isNotBlank(taskId)) {
            MakeTask task = ctx.data.getOrFail(MakeTask.class, Long.parseLong(taskId));
            searchTask = DTOs.marshal(MakeTaskDto.class, task);
            addTaskRestriction();
        }
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
    @Override
    public void addNameOrLabelRestriction() {
        setSearchFragment("name", "名称含有 " + searchPattern,
                CommonCriteria.labelledWith(searchPattern, true));
        searchPattern = null;
    }

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
