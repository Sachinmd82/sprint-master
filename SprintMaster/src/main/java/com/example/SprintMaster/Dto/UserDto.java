package com.example.SprintMaster.Dto;

public class UserDto {
    private String displayName;
    private String avatarHref;
    private String workspaceName;

    // Constructor, getters, and setters
    public UserDto(String displayName, String avatarHref, String workspaceName) {
        this.displayName = displayName;
        this.avatarHref = avatarHref;
        this.workspaceName = workspaceName;
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
}
