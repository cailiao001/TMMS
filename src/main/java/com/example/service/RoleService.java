package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.pojo.Role;

public interface RoleService extends IService<Role> {
    void delete(Long id);

}
