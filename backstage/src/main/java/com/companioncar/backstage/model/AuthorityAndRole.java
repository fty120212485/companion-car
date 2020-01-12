package com.companioncar.backstage.model;

import io.swagger.annotations.ApiModelProperty;

public class AuthorityAndRole {
    @ApiModelProperty(hidden = true)
    private String authorityRoleId;

    private String authorityId;

    private String roleId;

    @ApiModelProperty(hidden = true)
    private String controller;

    @ApiModelProperty(hidden = true)
    private String roleCode;

    public String getAuthorityRoleId() {
        return authorityRoleId;
    }

    public void setAuthorityRoleId(String authorityRoleId) {
        this.authorityRoleId = authorityRoleId;
    }

    public String getAuthorityId() {
        return authorityId;
    }

    public void setAuthorityId(String authorityId) {
        this.authorityId = authorityId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getController() {
        return controller;
    }

    public void setController(String controller) {
        this.controller = controller;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }
}
