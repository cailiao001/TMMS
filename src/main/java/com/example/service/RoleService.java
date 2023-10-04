package com.example.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.pojo.Role;

public interface RoleService extends IService<Role> {
    void delete(Long id);

    Page<Role> rolePage(int currentPage, int pageSize, String name);

    Role selectById(Long roleId);

}
