package com.example.pojo;

import lombok.Data;

import java.io.Serializable;

@Data
public class TeacherRole implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long teacherId;
    private Long roleId;

}
