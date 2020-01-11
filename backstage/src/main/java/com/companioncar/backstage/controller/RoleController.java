package com.companioncar.backstage.controller;

import com.companioncar.backstage.model.AuthorityAndRole;
import com.companioncar.backstage.service.RoleService;
import com.companioncar.backstage.model.Role;
import com.companioncar.dal.msg.ResponseCode;
import com.companioncar.dal.msg.ReturnMsgUtil;
import com.companioncar.dal.vo.BaseQueryParam;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.StringUtil;
import io.swagger.annotations.Api;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

@Api(value="roleController",tags={"角色操作接口"})
@Controller
@RequestMapping("/back/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @RequestMapping(value = "detail", method = RequestMethod.GET)
    @ResponseBody
    public ReturnMsgUtil<Role> detail(String memberId){
        Role user = roleService.selectBy(memberId);
        if(user == null) {
            return ReturnMsgUtil.fail(ResponseCode.NOT_FOUND, "该角色不存在！");
        }
        return ReturnMsgUtil.success(user);
    }

    @RequestMapping(value = "list", method = RequestMethod.GET)
    @ResponseBody
    public ReturnMsgUtil<List> list(Role role, BaseQueryParam baseQueryParam){
        /*Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken("admin","123456");
        subject.login(usernamePasswordToken);
        boolean hasRole = subject.hasRole("admin");
        if(!hasRole){
            return ReturnMsgUtil.fail(ResponseCode.NOT_FOUND, "找不到数据");
        }*/
        PageHelper.startPage(baseQueryParam.getPageNum(),baseQueryParam.getPageSize());
        List<Role> list = roleService.list(role);
        if(list == null){
            return ReturnMsgUtil.fail(ResponseCode.NOT_FOUND, "找不到数据");
        }
        return ReturnMsgUtil.success(new PageInfo<>(list));
    }

    @RequestMapping(value = "update", method = RequestMethod.POST)
    @ResponseBody
    public ReturnMsgUtil<Role> update(Role role){
        if(StringUtil.isEmpty(role.getRoleId())){
            return ReturnMsgUtil.fail(2, "缺少id参数");
        }
        role.setUpdateTime(new Date());
        int result = roleService.update(role);
        if(result == 0){
            return ReturnMsgUtil.fail(2, "更新失败");
        }
        return ReturnMsgUtil.success(null);
    }

    @RequestMapping(value = "delete", method = RequestMethod.GET)
    @ResponseBody
    public ReturnMsgUtil delete(String roleId){
        if(StringUtil.isEmpty(roleId)){
            return ReturnMsgUtil.fail(2, "缺少id参数");
        }
        int result = roleService.delete(roleId);
        if(result == 0){
            return ReturnMsgUtil.fail(2, "删除失败");
        }
        return ReturnMsgUtil.success(null);
    }

    @RequestMapping(value = "insert", method = RequestMethod.POST)
    @ResponseBody
    public ReturnMsgUtil insert(Role role){
        Role check_roleName = new Role();
        String roleName = role.getRoleName();
        if(StringUtil.isEmpty(roleName)){
            return ReturnMsgUtil.fail(2,"缺少roleName参数");
        }
        check_roleName.setRoleName(role.getRoleName());
        List<Role> list = roleService.list(check_roleName);
        if(list != null){
            return ReturnMsgUtil.fail(ResponseCode.FOUND,"角色名已存在");
        }
        role.setCreateTime(new Date());
        role.setUpdateTime(new Date());
        int result = roleService.insert(role);
        if(result == 0){
            return ReturnMsgUtil.fail(2, "新增失败");
        }
        return ReturnMsgUtil.success(role);
    }

    @RequestMapping(value = "setStatus", method = RequestMethod.GET)
    @ResponseBody
    public ReturnMsgUtil setStatus(String roleId, Byte isDelete){
        Role role = roleService.selectBy(roleId);
        if(role == null){
            return ReturnMsgUtil.fail(ResponseCode.NOT_FOUND, "该角色不存在");
        }
        role.setIsDelete(isDelete);
        int result = roleService.setStatus(role);
        if(result == 0){
            ReturnMsgUtil.fail(2, "操作失败");
        }
        return ReturnMsgUtil.success(null);
    }

    @RequestMapping(value = "addAuthority", method = RequestMethod.GET)
    @ResponseBody
    public ReturnMsgUtil addAuthority(AuthorityAndRole authorityAndRole){
        if(Strings.isBlank(authorityAndRole.getAuthorityId())){
            return ReturnMsgUtil.fail(ResponseCode.NOT_FOUND, "缺少authorityId参数");
        }
        if(Strings.isBlank(authorityAndRole.getRoleId())){
            return ReturnMsgUtil.fail(ResponseCode.NOT_FOUND, "缺少roleId参数");
        }
        int result = roleService.addAuthority(authorityAndRole);
        if(result == 0){
            ReturnMsgUtil.fail(2, "操作失败");
        }
        return ReturnMsgUtil.success(null);
    }
}
