package com.jc.springboot.dao;

import com.jc.springboot.entity.SysUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author: 帅气的达
 * @description: 登录相关dao
 * @date: 2017/10/24 11:02
 */
@Repository
public interface LoginDao {
	/**
	 * 根据用户名和密码查询对应的用户
	 */
	SysUser loadUser(@Param("username") String username, @Param("password") String password);
}
