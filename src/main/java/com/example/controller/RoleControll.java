package com.example.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.common.R;
import com.example.pojo.Role;
import com.example.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequestMapping("/role")
public class RoleControll {

    @Autowired
    private RoleService roleService;

    @GetMapping("/list")
    @Cacheable(value = "RoleListCache")
    public R<List<Role>> list () {
        List<Role> roleList = roleService.list();
        return R.success(roleList);
    }

    @GetMapping("/page")
    @Cacheable(value = "RolePageCache",key = "#currentPage+'_'+#pageSize+'_'+#name")
    public R<Page<Role>> page (int currentPage,int pageSize,String name) {
        Page<Role> page = roleService.rolePage(currentPage,pageSize, name);
        return R.success(page);
    }

    @PostMapping("/add")
    @CacheEvict(value = {"RolePageCache","RoleListCache"},allEntries = true)
    public R<String> add (@RequestBody Role role) {
        roleService.save(role);
        return R.success("添加成功");
    }
    @GetMapping("/delete/{id}")
    @CacheEvict(value = {"RolePageCache","RoleListCache"},allEntries = true)
    public R<String> delete (@PathVariable Long id) {
        roleService.delete(id);
        return R.success("删除成功");
    }

    @GetMapping("/selectById/{roleId}")
    public R<Role> selectById (@PathVariable Long roleId) {
        Role role = roleService.selectById(roleId);
        return R.success(role);
    }

    @PostMapping("/update")
    @CacheEvict(value = {"RolePageCache","RoleListCache"},allEntries = true)
    public R<String> update (@RequestBody Role role){
        roleService.updateById(role);
        return R.success("更新成功");
    }
}
