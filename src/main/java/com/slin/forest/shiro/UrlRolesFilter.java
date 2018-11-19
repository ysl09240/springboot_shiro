package com.slin.forest.shiro;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.util.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.RolesAuthorizationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 角色过滤器
 *
 * @author yangsonglin
 * @create 2018-11-19 10:15
 **/
public class UrlRolesFilter extends RolesAuthorizationFilter {
    private static final Logger logger = LoggerFactory
            .getLogger(UrlRolesFilter.class);

    @Override
    public boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws IOException {
        Subject subject = getSubject(request, response);
        String[] rolesArray = (String[]) mappedValue;

        if (rolesArray == null || rolesArray.length == 0) { //没有角色限制，有权限访问
            return true;
        }
        for (int i = 0; i < rolesArray.length; i++) {
            if (subject.hasRole(rolesArray[i])) { //若当前用户是rolesArray中的任何一个，则有权限访问
                return true;
            }
        }
        return false;
//        return super.isAccessAllowed(request,response,mappedValue);

    }

    /**
     * shiro认证perms资源失败后回调方法
     * @param servletRequest
     * @param servletResponse
     * @return
     * @throws IOException
     */
    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws IOException {
        logger.info("----------权限控制-------------");
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        String requestedWith = httpServletRequest.getHeader("X-Requested-With");
        if (!StringUtil.isEmpty(requestedWith) &&
                StringUtils.contains(requestedWith, "XMLHttpRequest")) {//如果是ajax返回指定格式数据
            Map<String, Object> result = new HashMap<String, Object>();
            result.put("flag", false);
            result.put("msg", "权限不足！");
            httpServletResponse.setCharacterEncoding("UTF-8");
            httpServletResponse.setContentType("application/json");
            httpServletResponse.getWriter().write(JSON.toJSONString(result));
        } else {//如果是普通请求进行重定向
            httpServletResponse.sendRedirect("/login");
        }
        return false;
    }


}
