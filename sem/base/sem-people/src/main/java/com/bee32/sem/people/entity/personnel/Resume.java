package com.bee32.sem.people.entity.personnel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;

import com.bee32.sem.base.tx.TxEntity;

@Entity
@SequenceGenerator(name = "idgen", sequenceName = "resume_seq", allocationSize = 1)
public class Resume extends TxEntity {

    private static final long serialVersionUID = 1L;

    private static final int CONTENT_LENGTH = 5000;

    String content;

    public String getContent() {
        return content;
    }

    @Column(length = CONTENT_LENGTH)
    public void setContent(String content) {
        this.content = content;
    }
}
