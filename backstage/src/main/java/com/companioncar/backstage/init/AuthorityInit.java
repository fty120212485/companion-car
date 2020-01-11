package com.companioncar.backstage.init;

import com.companioncar.backstage.model.Authority;
import com.companioncar.backstage.model.Role;
import com.companioncar.backstage.service.AuthorityService;
import com.companioncar.backstage.service.RoleService;
import com.companioncar.dal.util.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public class AuthorityInit implements ApplicationRunner {

    @Autowired
    private AuthorityService authorityService;

    @Autowired
    private RoleService roleService;

    @Autowired
    RequestMappingHandlerMapping requestMappingHandlerMapping;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Map<RequestMappingInfo, HandlerMethod> map = requestMappingHandlerMapping.getHandlerMethods();
        List<Authority> list = new ArrayList<Authority>();
        //1.获取已存放的authorityUrl
        List<Authority> authorityList = authorityService.list(null);

        List<Role> roleList = roleService.list(null);
        map.forEach((k,v)->{
            Set<String> patterns = k.getPatternsCondition().getPatterns();
            StringBuffer urlAuth = new StringBuffer();
            Authority Authority = new Authority();
            for(String url : patterns){
                urlAuth.append(url);
            }
            if(urlAuth.indexOf("back") > 0 && urlAuth.indexOf("login") < 0
                    && (authorityList == null || !authorityList.contains(urlAuth.toString()))){
                Authority.setAuthorityId(UUIDUtil.getUUID());
                Authority.setController(urlAuth.toString());
                Authority.setName("【" + k.getName() +"】");
                list.add(Authority);
            }
        });
        //1.保存权限url信息
        authorityService.insertBatch(list);
        //2.确保用有admin超级管理员
        Role role = roleService.findByRoleName("admin");
        if(role == null){
            role = roleService.roleInit();
        }
        roleService.insertBatch(list, role.getRoleId());
    }
}
