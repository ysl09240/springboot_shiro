package com.slin.forest.shiro;



import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 权限过滤器,当ajax请求时，返回批定参数
 * @author yangsonglin
 * @create 2018-11-16 12:04
 **/
//@Order(99)
//@WebFilter(urlPatterns = "/loginUser", filterName = "authFilter")

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.util.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.web.filter.authz.PermissionsAuthorizationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class UrlPermissionsFilter extends PermissionsAuthorizationFilter {

    private static final Logger logger = LoggerFactory
            .getLogger(UrlPermissionsFilter.class);

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

