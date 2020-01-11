package com.companioncar.backstage.controller;

import com.companioncar.backstage.model.Authority;
import com.companioncar.backstage.service.AuthorityService;
import com.companioncar.dal.msg.ResponseCode;
import com.companioncar.dal.msg.ReturnMsgUtil;
import com.companioncar.dal.vo.BaseQueryParam;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.StringUtil;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

@Api(value="roleController",tags={"权限操作接口"})
@Controller
@RequestMapping("/back/authority")
public class AuthorityController {

    @Autowired
    private AuthorityService authorityService;

    @RequestMapping(value = "detail", method = RequestMethod.GET)
    @ResponseBody
    public ReturnMsgUtil<Authority> detail(String memberId){
        Authority authority = authorityService.selectBy(memberId);
        if(authority == null) {
            return ReturnMsgUtil.fail(ResponseCode.NOT_FOUND, "该角色不存在！");
        }
        return ReturnMsgUtil.success(authority);
    }

    @RequestMapping(value = "list", method = RequestMethod.GET)
    @ResponseBody
    public ReturnMsgUtil<List> list(Authority authority, BaseQueryParam baseQueryParam){
        PageHelper.startPage(baseQueryParam.getPageNum(),baseQueryParam.getPageSize());
        List<Authority> list = authorityService.list(authority);
        if(list == null){
            return ReturnMsgUtil.fail(ResponseCode.NOT_FOUND, "找不到数据");
        }
        return ReturnMsgUtil.success(new PageInfo<>(list));
    }

    @RequestMapping(value = "update", method = RequestMethod.POST)
    @ResponseBody
    public ReturnMsgUtil<Authority> update(Authority authority){
        if(StringUtil.isEmpty(authority.getAuthorityId())){
            return ReturnMsgUtil.fail(2, "缺少id参数");
        }
        authority.setUpdateTime(new Date());
        int result = authorityService.update(authority);
        if(result == 0){
            return ReturnMsgUtil.fail(2, "更新失败");
        }
        return ReturnMsgUtil.success(null);
    }

    @RequestMapping(value = "delete", method = RequestMethod.GET)
    @ResponseBody
    public ReturnMsgUtil delete(String authorityId){
        if(StringUtil.isEmpty(authorityId)){
            return ReturnMsgUtil.fail(2, "缺少id参数");
        }
        int result = authorityService.delete(authorityId);
        if(result == 0){
            return ReturnMsgUtil.fail(2, "删除失败");
        }
        return ReturnMsgUtil.success(null);
    }

    @RequestMapping(value = "insert", method = RequestMethod.POST)
    @ResponseBody
    public ReturnMsgUtil insert(Authority authority){
        Authority check_controller = new Authority();
        String controller = authority.getController();
        if(StringUtil.isEmpty(controller)){
            return ReturnMsgUtil.fail(2,"缺少controller参数");
        }
        check_controller.setController(authority.getController());
        List<Authority> list = authorityService.list(check_controller);
        if(list != null){
            return ReturnMsgUtil.fail(ResponseCode.FOUND,"角色名已存在");
        }
        authority.setCreateTime(new Date());
        authority.setUpdateTime(new Date());
        int result = authorityService.insert(authority);
        if(result == 0){
            return ReturnMsgUtil.fail(2, "新增失败");
        }
        return ReturnMsgUtil.success(authority);
    }
}
