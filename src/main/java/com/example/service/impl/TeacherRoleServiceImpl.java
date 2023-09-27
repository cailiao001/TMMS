package com.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mapper.TeacherRoleMapper;
import com.example.pojo.TeacherRole;
import com.example.service.TeacherRoleService;
import org.springframework.stereotype.Service;

@Service
public class TeacherRoleServiceImpl extends ServiceImpl<TeacherRoleMapper, TeacherRole> implements TeacherRoleService {
}
