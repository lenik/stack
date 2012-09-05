package com.bee32.sem.frame.web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class VersionDescription
        implements Serializable {

    private static final long serialVersionUID = 1L;

    String version;
    String date;
    String[] authors;
    List<String> commits = new ArrayList<String>();

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String[] getAuthors() {
        return authors;
    }

    public void setAuthors(String[] authors) {
        this.authors = authors;
    }

    public List<String> getCommits() {
        return commits;
    }

    public void setCommits(List<String> commits) {
        this.commits = commits;
    }

    public void addCommit(String commit) {
        commits.add(commit);
    }

}
