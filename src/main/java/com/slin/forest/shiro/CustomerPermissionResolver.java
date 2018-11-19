package com.slin.forest.shiro;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.permission.PermissionResolver;
import org.springframework.stereotype.Component;

/**
 * 自定义权限解析器
 * @author yangsonglin
 *  * @create 2018-11-17 13:04
 */
@Component
public class CustomerPermissionResolver implements PermissionResolver {
    @Override
    public Permission resolvePermission(String permissionString) {
        return new AntPathPermission(permissionString);
    }
    protected class AntPathPermission implements Permission {
        private String permissionString;
        public AntPathPermission(String permissionString) {
            this.permissionString = permissionString;
        }
        public String getPermissionString() {
            return permissionString;
        }
        @Override
        public boolean implies(Permission p) {
            if (!(p instanceof AntPathPermission)) {
                return false;
            }
            AntPathPermission wp = (AntPathPermission) p;
            String wpPerm = wp.getPermissionString();
            if (StringUtils.equals(wpPerm, permissionString)) {
                return true;
            }
            return false;
        }
    }
}
