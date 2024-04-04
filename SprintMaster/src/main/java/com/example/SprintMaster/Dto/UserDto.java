package com.example.SprintMaster.Dto;

public class UserDto {
    private String displayName;
    private String avatarHref;
    private String workspaceName;
    private String accountId;


    public UserDto(String displayName, String avatarHref, String workspaceName, String accountId) {
        this.displayName = displayName;
        this.avatarHref = avatarHref;
        this.workspaceName = workspaceName;
        this.accountId = accountId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getAvatarHref() {
        return avatarHref;
    }

    public void setAvatarHref(String avatarHref) {
        this.avatarHref = avatarHref;
    }

    public String getWorkspaceName() {
        return workspaceName;
    }

    public void setWorkspaceName(String workspaceName) {
        this.workspaceName = workspaceName;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }
}
