package com.bee32.sem.hr.util;

import javax.persistence.Column;

public final class ScoreLevel {

    public static final int LABEL_LENGTH = 30;

    String label;
    int score;

    @Column(length = LABEL_LENGTH, nullable = false)
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Column(nullable = false)
    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

}
