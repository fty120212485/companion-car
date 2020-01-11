package com.companioncar.backstage.controller;

import com.companioncar.backstage.service.UserService;
import com.companioncar.backstage.model.User;
import com.companioncar.dal.msg.ResponseCode;
import com.companioncar.dal.msg.ReturnMsgUtil;
import com.companioncar.dal.util.MD5Util;
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

@Api(value="userController",tags={"用户操作接口"})
@Controller
@RequestMapping("/back/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "detail", method = RequestMethod.GET)
    @ResponseBody
    public ReturnMsgUtil<User> detail(String userId){
        User user = userService.selectBy(userId);
        if(user == null) {
            return ReturnMsgUtil.fail(ResponseCode.NOT_FOUND, "该用户不存在！");
        }
        return ReturnMsgUtil.success(user);
    }

    @RequestMapping(value = "list", method = RequestMethod.GET)
    @ResponseBody
    public ReturnMsgUtil<List> list(User user, BaseQueryParam baseQueryParam){
        PageHelper.startPage(baseQueryParam.getPageNum(),baseQueryParam.getPageSize());
        List<User> list = userService.list(user);
        if(list == null){
            return ReturnMsgUtil.fail(ResponseCode.NOT_FOUND, "找不到数据");
        }
        return ReturnMsgUtil.success(new PageInfo<>(list));
    }

    @RequestMapping(value = "update", method = RequestMethod.POST)
    @ResponseBody
    public ReturnMsgUtil<User> update(User user){
        if(StringUtil.isEmpty(user.getUserId())){
            return ReturnMsgUtil.fail(2, "缺少id参数");
        }
        user.setUpdateTime(new Date());
        int result = userService.update(user);
        if(result == 0){
            return ReturnMsgUtil.fail(2, "更新失败");
        }
        return ReturnMsgUtil.success(null);
    }

    @RequestMapping(value = "delete", method = RequestMethod.GET)
    @ResponseBody
    public ReturnMsgUtil delete(String userId){
        if(StringUtil.isEmpty(userId)){
            return ReturnMsgUtil.fail(2, "缺少id参数");
        }
        int result = userService.delete(userId);
        if(result == 0){
            return ReturnMsgUtil.fail(2, "删除失败");
        }
        return ReturnMsgUtil.success(null);
    }

    @RequestMapping(value = "insert", method = RequestMethod.POST)
    @ResponseBody
    public ReturnMsgUtil insert(User user){
        User check_username = new User();
        String username = user.getUsername();
        if(StringUtil.isEmpty(username)){
            return ReturnMsgUtil.fail(2,"缺少username参数");
        }
        check_username.setUsername(user.getUsername());
        List<User> list = userService.list(check_username);
        if(list != null){
            return ReturnMsgUtil.fail(ResponseCode.FOUND,"用户名已存在");
        }
        String salt = MD5Util.getSalt();
        String pwd = user.getPassword();
        user.setSalt(salt);
        user.setPassword(MD5Util.getMd5(pwd, salt));
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());
        int result = userService.insert(user);
        if(result == 0){
            return ReturnMsgUtil.fail(2, "新增失败");
        }
        return ReturnMsgUtil.success(user);
    }

    @RequestMapping(value="login", method = RequestMethod.POST)
    @ResponseBody
    public ReturnMsgUtil login(String username, String password){
        User user = new User();
        user.setUsername(username);
        List<User> list = userService.list(user);
        if(list == null){
            return ReturnMsgUtil.fail(ResponseCode.NOT_FOUND, "用户名或密码不正确");
        }
        String salt = list.get(0).getSalt();
        user.setPassword(MD5Util.getMd5(password, salt));
        list = userService.list(user);
        if(list == null){
            return ReturnMsgUtil.fail(ResponseCode.NOT_FOUND, "用户名或密码不正确");
        }
        return ReturnMsgUtil.success(list.get(0));
    }
}
