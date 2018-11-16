package com.slin.forest.service;



import com.slin.forest.model.RoleBean;
import com.slin.forest.model.UserBean;

import java.util.List;

public interface IUserService {


    List<UserBean> findUserList();


    UserBean getByUsername(String username);

    List<RoleBean> getRoleByUserId(long userId);
}
