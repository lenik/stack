package com.bee32.sem.track.entity;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;

/**
 * 问题抄送组
 *
 * 参与问题的组，将问题抄送给这些组的成员。
 */
@Entity
@SequenceGenerator(name = "idgen", sequenceName = "issue_cc_group_seq", allocationSize = 1)
public class IssueCcGroup {

}
