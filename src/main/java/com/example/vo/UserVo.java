package com.example.vo;

import com.example.pojo.User;
import lombok.Data;

import java.io.Serializable;
@Data
public class UserVo extends User implements Serializable {
    private static final long serialVersionUID = 1L;
    private String code;
}
