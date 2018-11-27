package com.slin.forest.shiro;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.apache.shiro.mgt.SecurityManager;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author yangsonglin
 * @create 2018-11-16 9:48
 **/
@Configuration
public class ShiroConfig {


    @Bean
    public ShiroFilterFactoryBean shirFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        //如果要重写过滤器，需要先获取过滤器链，然后通过新的过滤器覆盖
        Map<String,Filter> filterMap = shiroFilterFactoryBean.getFilters();
        //这里就是通过key:perms 覆盖掉权限的过滤器
//        filterMap.put("perms",new UrlPermissionsFilter());
        //这里就是通过key:perms 覆盖掉角色的过滤器,一般角色和权限，只需要覆盖掉一个，就可以实现权限拦截功能了，不需要覆盖两个
        filterMap.put("roles",new UrlRolesFilter());
        // 必须设置 SecurityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        // setLoginUrl 如果不设置值，默认会自动寻找Web工程根目录下的"/login.jsp"页面 或 "/login" 映射
        //登录
        shiroFilterFactoryBean.setLoginUrl("/login");
        // 设置无权限时跳转的 url;
//        shiroFilterFactoryBean.setUnauthorizedUrl("/notRole");


        //错误页面，认证不通过跳转
//        shiroFilterFactoryBean.setUnauthorizedUrl("/error");

        // 设置拦截器
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        //游客，开发权限
//        filterChainDefinitionMap.put("/guest/**", "anon");
        filterChainDefinitionMap.put("/user/list", "roles[ADMIN,USER]");
        //用户，需要权限
//        filterChainDefinitionMap.put("/user/list", "perms[user:list]");
//        filterChainDefinitionMap.put("/user/add", "perms[user:add]");
        //管理员，需要角色权限 “admin”
//        filterChainDefinitionMap.put("/admin/**", "roles[admin]");
        //开放登陆接口
        filterChainDefinitionMap.put("/loginUser", "anon");

        //其余接口一律拦截
        //主要这行代码必须放在所有权限设置的最后，不然会导致所有 url 都被拦截
        filterChainDefinitionMap.put("/**", "authc");

        //配置不起作用
//        shiroFilterFactoryBean.setSuccessUrl("/main");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        System.out.println("Shiro拦截器工厂类注入成功");
        return shiroFilterFactoryBean;
    }

    /**
     * 注入 securityManager
     */
    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 设置realm.
        securityManager.setRealm(customRealm());
        return securityManager;
    }


    /**
     * 自定义身份认证 realm;
     * <p>
     * 必须写这个类，并加上 @Bean 注解，目的是注入 CustomRealm，
     * 否则会影响 CustomRealm类 中其他类的依赖注入
     */
    @Bean
    public CustomRealm customRealm() {
//        CustomRealm customRealm = new CustomRealm(); //这里如果要自定义解析器可以这样写
//        customRealm.setRolePermissionResolver(new CustomerPermissionResolver());
//        customRealm.setPermissionResolver();
        return new CustomRealm();
    }

    @Bean
    public ShiroDialect shiroDialect() {
        return new ShiroDialect();
    }

}
