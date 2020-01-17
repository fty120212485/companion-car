package com.companioncar.backstage.shiro;

import com.companioncar.backstage.model.AuthorityAndRole;
import com.companioncar.backstage.service.AuthorityService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Configuration
@ComponentScan("com.companioncar.dal.config")
public class ShiroConfig {

    @Autowired
    private AuthorityService authorityService;

    @Bean
    public DefaultWebSecurityManager securityManager(MyRealm myRealm){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(myRealm);
        return securityManager;
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        Map<String, Filter> filtersMap = new LinkedHashMap<String, Filter>();
        filtersMap.put("authenticate", new AuthenticationFilter());
        filtersMap.put("authorization", new AuthorizationFilter());
        shiroFilterFactoryBean.setFilters(filtersMap);
        shiroFilterFactoryBean.setFilterChainDefinitionMap(setFilterChainDefinitionMap());
        return shiroFilterFactoryBean;
    }


    private Map<String, String> setFilterChainDefinitionMap() {
        Map<String, String> filterMap = new LinkedHashMap<>();
        List<AuthorityAndRole> list = authorityService.findAuthorityAndRole();
        if(list != null){
            for (AuthorityAndRole authorityAndRole:list) {
                filterMap.put(authorityAndRole.getController(),
                        "authenticate,authorization[admin,"+authorityAndRole.getRoleCode()+"]");
            }
        }
        return filterMap;
    }

    /*public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator(){
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
        return defaultAdvisorAutoProxyCreator;
    }*/
}
