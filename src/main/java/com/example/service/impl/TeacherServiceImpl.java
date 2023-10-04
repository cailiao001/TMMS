package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mapper.RoleMapper;
import com.example.mapper.TeacherMapper;
import com.example.mapper.TeacherRoleMapper;
import com.example.pojo.Role;
import com.example.pojo.Teacher;
import com.example.pojo.TeacherRole;
import com.example.service.TeacherService;
import com.example.vo.TeacherRoleVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper,Teacher> implements TeacherService {

    @Autowired
    private TeacherMapper teacherMapper;

    @Autowired
    private TeacherRoleMapper teacherRoleMapper;

    @Autowired
    private RoleMapper roleMapper;


    @Transactional
    public Page<TeacherRoleVo> page(int currentPage, int pageSize, String name) {
        Page<Teacher> page = new Page<>(currentPage,pageSize);
        LambdaQueryWrapper<Teacher> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(name != null,Teacher::getName,name);
        Page<Teacher> teacherPage = teacherMapper.selectPage(page, queryWrapper);
        List<Teacher> teacherList = teacherPage.getRecords();

        List<TeacherRoleVo> teacherRoleVoList = new ArrayList<>();
        for (Teacher teacher : teacherList) {
            List<Role> roleList = new ArrayList<>();
            LambdaQueryWrapper<TeacherRole> queryWrapper1 = new LambdaQueryWrapper<>();
            queryWrapper1.eq(TeacherRole::getTeacherId,teacher.getId());
            List<TeacherRole> teacherRoleList = teacherRoleMapper.selectList(queryWrapper1);
            for (TeacherRole teacherRole : teacherRoleList) {
                Role role = roleMapper.selectById(teacherRole.getRoleId());
                roleList.add(role);
            }
            TeacherRoleVo teacherRoleVo = new TeacherRoleVo();
            BeanUtils.copyProperties(teacher,teacherRoleVo);
            teacherRoleVo.setRoleList(roleList);
            teacherRoleVoList.add(teacherRoleVo);
        }

        Page<TeacherRoleVo> teacherRoleVoPage = new Page<>();
        BeanUtils.copyProperties(teacherPage,teacherRoleVoPage,"records");
        teacherRoleVoPage.setRecords(teacherRoleVoList);

        return teacherRoleVoPage;
    }



    @Transactional
    public void add(Teacher teacher, Long[] roleIds) {
        LambdaQueryWrapper<Teacher> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Teacher::getWorkNo,teacher.getWorkNo());
        Teacher selectOne = teacherMapper.selectOne(queryWrapper);
        if (!Objects.isNull(selectOne)){
            throw new RuntimeException("该职工已存在!");
        }
        teacherMapper.insert(teacher);
        Long teacherId = teacher.getId();
        TeacherRole teacherRole = new TeacherRole();
        for (Long roleId : roleIds) {
            teacherRole.setTeacherId(teacherId);
            teacherRole.setRoleId(roleId);
            teacherRoleMapper.insert(teacherRole);
        }

    }

    /**
     * 数据回显
     * @param teacherId
     * @return
     */
    @Transactional
    public TeacherRoleVo seleceById(Long teacherId) {
        List<Long> roleIds = new ArrayList<>();
        Teacher teacher = teacherMapper.selectById(teacherId);
        LambdaQueryWrapper<TeacherRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TeacherRole::getTeacherId,teacher.getId());
        List<TeacherRole> teacherRoleList = teacherRoleMapper.selectList(queryWrapper);
        for (TeacherRole teacherRole : teacherRoleList) {
            Role role = roleMapper.selectById(teacherRole.getRoleId());
            roleIds.add(role.getId());
        }
        TeacherRoleVo teacherRoleVo = new TeacherRoleVo();
        BeanUtils.copyProperties(teacher,teacherRoleVo);
        teacherRoleVo.setRoleIds(roleIds.toArray(new Long[0]));
        return teacherRoleVo;
    }



    @Transactional
    public void updateVo(Teacher teacher, Long[] roleIds) {
        teacherMapper.updateById(teacher);
        LambdaQueryWrapper<TeacherRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TeacherRole::getTeacherId,teacher.getId());
        teacherRoleMapper.delete(queryWrapper);
        TeacherRole teacherRole = new TeacherRole();
        for (Long roleId : roleIds) {
            teacherRole.setTeacherId(teacher.getId());
            teacherRole.setRoleId(roleId);
            teacherRoleMapper.insert(teacherRole);
        }
    }

    @Transactional
    public void delete(Long teacherId) {
        LambdaQueryWrapper<TeacherRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TeacherRole::getTeacherId,teacherId);
        teacherRoleMapper.delete(queryWrapper);
        teacherMapper.deleteById(teacherId);
    }


}
