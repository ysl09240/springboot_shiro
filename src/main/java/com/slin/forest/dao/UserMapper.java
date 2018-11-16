package com.slin.forest.dao;


import com.slin.forest.model.RoleBean;
import com.slin.forest.model.UserBean;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component(value ="userMapper")
public interface UserMapper {
    /**
     * 查询用户列表
     * @return
     */
    List<UserBean> findUserList();

    UserBean getByUsername(String username);

    List<RoleBean> getRoleByUserId(long userId);
}
