package com.slin.forest.shiro;

import com.slin.forest.model.RoleBean;
import com.slin.forest.model.UserBean;
import com.slin.forest.service.IUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.authz.permission.PermissionResolver;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author yangsonglin
 * @create 2018-11-16 9:49
 **/

public class CustomRealm extends AuthorizingRealm {

    @Autowired
    private IUserService userService;

    @Autowired
    CustomerPermissionResolver customerPermissionResolver;
    /**
     * 获取身份验证信息
     * Shiro中，最终是通过 Realm 来获取应用程序中的用户、角色及权限信息的。
     *
     * @param authenticationToken 用户身份信息 token
     * @return 返回封装了用户信息的 AuthenticationInfo 实例
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("————身份认证方法————");
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        // 从数据库获取对应用户名密码的用户
        UserBean  userBean= userService.getByUsername(token.getUsername());
        String password = userBean.getPassword();
        if (null == password) {
            throw new AccountException("用户名不正确");
        } else if (!password.equals(new String((char[]) token.getCredentials()))) {
            throw new AccountException("密码不正确");
        }
        return new SimpleAuthenticationInfo(userBean, password, getName());
    }

    /**
     * 获取授权信息
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("————权限认证————");
        UserBean userBean = (UserBean) SecurityUtils.getSubject().getPrincipal();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //获得该用户角色
        List<RoleBean> roles = userService.getRoleByUserId(userBean.getId());

        Set<String> setRole = new HashSet<>();
        for (RoleBean role:roles) {
            setRole.add(role.getRoleEnName());
        }
        info.setRoles(setRole);
        Set<String> setPermiss = new HashSet<>();
        setPermiss.add("user:add");
//        setPermiss.add("user:list");
        info.setStringPermissions(setPermiss);
        return info;
    }

    @Override
    public void setPermissionResolver(PermissionResolver permissionResolver) {
        super.setPermissionResolver(customerPermissionResolver);
    }
}
