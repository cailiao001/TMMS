package com.example.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.pojo.Teacher;
import com.example.vo.TeacherRoleVo;

import java.util.List;

public interface TeacherService extends IService<Teacher> {

    //分页
    Page<TeacherRoleVo> page (int currentPage,int pageSize,String name);

    void add(Teacher teacher, Long[] roleIds);

    TeacherRoleVo seleceById(Long teacherId);

    void updateVo(Teacher teacher, Long[] roleIds);

    void delete(Long teacherId);

}
