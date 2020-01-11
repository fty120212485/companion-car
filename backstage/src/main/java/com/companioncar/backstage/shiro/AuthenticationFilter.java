package com.companioncar.backstage.shiro;

import com.companioncar.backstage.util.JSONUtil;
import com.companioncar.backstage.util.ResponseUtil;
import com.companioncar.dal.config.RedisService;
import com.companioncar.dal.msg.ReturnMsgUtil;
import com.companioncar.dal.util.JWTUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;


@Configuration
public class AuthenticationFilter extends AccessControlFilter {

    private static final Logger log = LoggerFactory.getLogger(AuthenticationFilter.class);

    private RedisService redisService;

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        //是否已认证
        if (null != getSubject(request, response)
                && getSubject(request, response).isAuthenticated()) {
            return true;
        }
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        //1.获取token
        String jwt = ((HttpServletRequest)request).getHeader("authorization");
        Claims claims = null;
        try {
            //2.解析token并验证
            claims = JWTUtil.parseJWT(jwt);
            String userId = claims.getSubject();
            BeanFactory factory = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext());
            if(factory != null && factory.getBean("redisService") != null){
                redisService = (RedisService) factory.getBean("redisService");
            }
            if(!redisService.hasKey(userId)){
                ResponseUtil.responseWrite(JSONUtil.stringify(ReturnMsgUtil.fail(2,"未登录")), (HttpServletResponse) response);
                return false;
            }
            String redis = (String) redisService.get(userId);
            if(!redis.equals(jwt)){
                ResponseUtil.responseWrite(JSONUtil.stringify(ReturnMsgUtil.fail(2,"账号已在其他设备登录")), (HttpServletResponse) response);
                return false;
            }
            long time = 1000*60*60;//每次请求刷新缓存1小时
            redisService.set(userId,jwt,time);
            request.setAttribute("claims", claims);
            //3.从token中获取username
            String username = claims.getIssuer();
            Subject subject = getSubject(request, response);
            UsernamePasswordToken token = new UsernamePasswordToken(username,jwt);
            //4.调用登录认证login方法
            subject.login(token);
        } catch (Exception e) {
            ResponseUtil.responseWrite(JSONUtil.stringify(ReturnMsgUtil.fail(2,"请重新登录")), (HttpServletResponse) response);
            log.error(e.getMessage(), e);
            return false;
        }
        return true;
    }
}
