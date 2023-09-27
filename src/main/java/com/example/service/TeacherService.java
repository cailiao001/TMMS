package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.pojo.Teacher;
import com.example.vo.TeacherRoleVo;

import java.util.List;

public interface TeacherService extends IService<Teacher> {
    List<TeacherRoleVo> listVo(String name, String workNo);

    void add(Teacher teacher, Long[] roleIds);

    TeacherRoleVo seleceById(Long teacherId);

    List<String> roleNames (TeacherRoleVo teacherRoleVo);

    void updateVo(Teacher teacher, Long[] roleIds);

    void delete(Long teacherId);

}
