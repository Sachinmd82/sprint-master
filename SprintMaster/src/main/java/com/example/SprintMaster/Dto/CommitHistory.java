package com.example.SprintMaster.Dto;

public class CommitHistory {

    private String author;
    private String createdDate;
    private String commitLink;

    public CommitHistory(String author, String createdDate, String commitLink) {
        this.author = author;
        this.createdDate = createdDate;
        this.commitLink = commitLink;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getCommitLink() {
        return commitLink;
    }

    public void setCommitLink(String commitLink) {
        this.commitLink = commitLink;
    }
}
