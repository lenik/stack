package com.bee32.sem.hr.web;

import java.util.ArrayList;
import java.util.List;

import com.bee32.plover.faces.utils.SelectableList;
import com.bee32.plover.orm.util.DataViewBean;
import com.bee32.sem.hr.dto.JobPerformanceDto;
import com.bee32.sem.hr.dto.JobPostDto;
import com.bee32.sem.hr.dto.JobTitleDto;
import com.bee32.sem.hr.dto.PersonEducationTypeDto;
import com.bee32.sem.hr.dto.PersonSkillCategoryDto;
import com.bee32.sem.hr.dto.PersonSkillCategoryLevelDto;
import com.bee32.sem.hr.entity.JobPerformance;
import com.bee32.sem.hr.entity.JobPost;
import com.bee32.sem.hr.entity.JobTitle;
import com.bee32.sem.hr.entity.PersonEducationType;
import com.bee32.sem.hr.entity.PersonSkillCategory;

public class EmployeeDictsBean
        extends DataViewBean {

    private static final long serialVersionUID = 1L;

    List<JobTitleDto> jobTitles;
    List<JobPostDto> jobPosts;
    List<JobPerformanceDto> jobPerformances;
    List<PersonEducationTypeDto> educationTypes;
    static List<PersonSkillCategoryLevelDto> skillLevels;

    public List<JobTitleDto> getJobTitles() {
        if (jobTitles == null) {
            synchronized (this) {
                if (jobTitles == null) {
                    jobTitles = mrefList(JobTitle.class, JobTitleDto.class, 0);
                }
            }
        }
        return SelectableList.decorate(jobTitles);
    }

    public List<JobPostDto> getJobPosts() {
        if (jobPosts == null) {
            synchronized (this) {
                if (jobPosts == null) {
                    jobPosts = mrefList(JobPost.class, JobPostDto.class, 0);
                }
            }
        }
        return SelectableList.decorate(jobPosts);
    }

    public List<JobPerformanceDto> getJobPerformances() {
        if (jobPerformances == null) {
            synchronized (this) {
                if (jobPerformances == null) {
                    jobPerformances = mrefList(JobPerformance.class, JobPerformanceDto.class, 0);
                }
            }
        }
        return SelectableList.decorate(jobPerformances);
    }

    public List<PersonEducationTypeDto> getEducationTypes() {
        if (educationTypes == null) {
            synchronized (this) {
                if (educationTypes == null) {
                    educationTypes = mrefList(PersonEducationType.class, PersonEducationTypeDto.class, 0);
                }
            }
        }
        return SelectableList.decorate(educationTypes);
    }

    public List<PersonSkillCategoryLevelDto> getSkillLevels () {
        if (skillLevels == null ){
            synchronized (this) {
                if(skillLevels == null){
                    skillLevels = new ArrayList<PersonSkillCategoryLevelDto>();
                    int id = 1;
                    List<PersonSkillCategoryDto> categories = mrefList(PersonSkillCategory.class, PersonSkillCategoryDto.class, 0);
                    for (PersonSkillCategoryDto category : categories) {
                        List<PersonSkillCategoryLevelDto> levelList = category.getLevelList();
                        for (PersonSkillCategoryLevelDto level : levelList) {
                            level.setId(id++);
                            skillLevels.add(level);
                        }
                    }
                }
            }
        }
        return SelectableList.decorate(skillLevels);
    }
}
