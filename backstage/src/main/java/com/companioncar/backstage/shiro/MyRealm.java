package com.companioncar.backstage.shiro;

import com.companioncar.dal.util.JWTUtil;
import io.jsonwebtoken.Claims;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class MyRealm extends AuthorizingRealm {

    protected static final Logger log = LoggerFactory.getLogger(MyRealm.class);

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String roles = (String) principals.getPrimaryPrincipal();
        String[] roleArr = roles.split(",");
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        if(roleArr.length != 0){
            for (String role:roleArr) {
                simpleAuthorizationInfo.addRole(role);
            }
            return simpleAuthorizationInfo;
        }
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        if(token.getPrincipal() == null){
            return null;
        }
        SimpleAuthenticationInfo simpleAuthenticationInfo = null;
        try {
            //1.获取token
            char[] credentials = (char[]) token.getCredentials();
            StringBuffer jwt = new StringBuffer(0);
            for (char c : credentials){
                jwt.append(c);
            }
            Claims claims = JWTUtil.parseJWT(jwt.toString());
            String roles = claims.get("role", String.class);
            //2.从token中提取角色
            simpleAuthenticationInfo = new SimpleAuthenticationInfo(roles, jwt.toString(), this.getName());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
        return simpleAuthenticationInfo;
    }
}
