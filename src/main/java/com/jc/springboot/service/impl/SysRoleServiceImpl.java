package com.jc.springboot.service.impl;

import com.jc.springboot.dao.SysRoleMapper;
import com.jc.springboot.entity.SysRole;
import com.jc.springboot.service.SysRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Auther: 帅气的达
 * @Date: 2019/8/3 11:27
 * @Description:
 */
@Slf4j
@Service
public class SysRoleServiceImpl implements SysRoleService {

    @Resource
    SysRoleMapper sysRoleMapper;

    @Override
    public List<SysRole> listSysRoleByRoleId(Integer role_id) {
        return sysRoleMapper.listSysRoleByRoleId(role_id);
    }

    @Override
    public List<SysRole> list() {
        return sysRoleMapper.list();
    }
}
