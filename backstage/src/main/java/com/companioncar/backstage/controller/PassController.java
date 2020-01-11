package com.companioncar.backstage.controller;

import com.companioncar.backstage.model.User;
import com.companioncar.backstage.service.UserService;
import com.companioncar.backstage.service.impl.UserServiceImpl;
import com.companioncar.dal.config.RedisService;
import com.companioncar.dal.msg.ResponseCode;
import com.companioncar.dal.msg.ReturnMsgUtil;
import com.companioncar.dal.util.JWTUtil;
import com.companioncar.dal.util.MD5Util;
import com.companioncar.dal.util.UUIDUtil;
import com.companioncar.backstage.model.JsonWebToken;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.Api;
import org.apache.logging.log4j.util.Strings;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Api(value = "passController", tags = "通行操作接口")
@Controller
@RequestMapping("/back/pass")
@ComponentScan("com.companioncar.dal.config")
public class PassController {

    @Autowired
    private UserService userService;

    @Autowired
    private RedisService redisService;

    @RequestMapping(value = "login", method = RequestMethod.POST, name = "登录")
    @ResponseBody
    public ReturnMsgUtil login(String username, String password, HttpServletRequest request){
        if(Strings.isBlank(username)){
            return ReturnMsgUtil.fail(2, "用户名不能为空");
        }
        User user = new User();
        user.setUsername(username);
        List<User> check_username = userService.list(user);
        if(check_username == null){
            return ReturnMsgUtil.fail(ResponseCode.NOT_FOUND, "用户名或密码错误");
        }
        User result = check_username.get(0);
        String salt = result.getSalt();
        user.setPassword(MD5Util.getMd5(password, salt));
        List<User> check_password = userService.list(user);
        if(check_password == null){
            return ReturnMsgUtil.fail(ResponseCode.NOT_FOUND, "用户名或密码错误");
        }
        JsonWebToken token = new JsonWebToken();
        token.setUser(check_password.get(0));
        String rolesName = userService.findByRoleName(token.user.getUserId());
        Long millis = 1000 * 60 * 60L;
        String jwt = JWTUtil.createJWT(UUIDUtil.getUUID(), token.user.getUserId(), user.getUsername(), rolesName, millis);
        token.setToken(jwt);
        if( redisService == null) {
            return ReturnMsgUtil.fail(2, "系统错误");
        }
        redisService.set(token.user.getUserId(), jwt, millis);
        return ReturnMsgUtil.success(token);
    }

    @RequestMapping(value="logout", method = RequestMethod.GET, name = "注销")
    @ResponseBody
    public ReturnMsgUtil logout(HttpServletRequest request) throws ServletException {
        Claims claims = (Claims) request.getAttribute("claims");
        if( claims == null) {
            return ReturnMsgUtil.fail(ResponseCode.NOT_FOUND, "账号未登录，注销失败");
        }
        String userId = claims.getSubject();
        if( redisService == null) {
            return ReturnMsgUtil.fail(2, "系统错误");
        }
        if (!redisService.hasKey(userId)) {
            return ReturnMsgUtil.fail(ResponseCode.NOT_FOUND, "账号未登录，注销失败");
        }
        redisService.del(userId);
        return ReturnMsgUtil.success(null);
    }
}
