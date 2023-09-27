package com.example.vo;

import com.example.pojo.Role;
import com.example.pojo.Teacher;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;
@Data
@EqualsAndHashCode(callSuper = true)
public class TeacherRoleVo extends Teacher implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<Role> roleList;
}
