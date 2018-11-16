package com.slin.forest.service.impl;


import com.slin.forest.dao.UserMapper;
import com.slin.forest.model.RoleBean;
import com.slin.forest.model.UserBean;
import com.slin.forest.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * user实现接口类
 *
 * @author yangsonglin
 * @create 2018-11-13 10:19
 **/
@Service("userService")
public class UserServiceImpl implements IUserService {

    @Autowired
    UserMapper userMapper;
    @Override
    public List<UserBean> findUserList() {
        return userMapper.findUserList();
    }

    @Override
    public UserBean getByUsername(String username) {
        return userMapper.getByUsername(username);
    }

    @Override
    public List<RoleBean> getRoleByUserId(long userId) {
        return userMapper.getRoleByUserId(userId);
    }
}
