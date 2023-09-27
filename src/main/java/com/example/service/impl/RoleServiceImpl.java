package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mapper.RoleMapper;
import com.example.mapper.TeacherRoleMapper;
import com.example.pojo.Role;
import com.example.pojo.TeacherRole;
import com.example.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {


    @Autowired
    private TeacherRoleMapper teacherRoleMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public void delete(Long id) {
        LambdaQueryWrapper<TeacherRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TeacherRole::getRoleId,id);
        teacherRoleMapper.delete(queryWrapper);
        roleMapper.deleteById(id);
    }
}
