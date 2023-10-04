package com.example.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.common.R;
import com.example.pojo.Role;
import com.example.pojo.Teacher;
import com.example.service.RoleService;
import com.example.service.TeacherService;
import com.example.vo.TeacherRoleVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.websocket.server.PathParam;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

@RestController
@RequestMapping("/teacher")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private RoleService roleService;


    @GetMapping("/page")
    public R<Page<TeacherRoleVo>> Page (int currentPage,int pageSize,String name) {
        Page<TeacherRoleVo> page = teacherService.page(currentPage,pageSize, name);
        return R.success(page);
    }

    @PostMapping("/add")
    public R<String> add (@RequestBody TeacherRoleVo teacherRoleVo)  {
        try {
            teacherService.add(teacherRoleVo,teacherRoleVo.getRoleIds());
        }catch (RuntimeException e){
            e.printStackTrace();
            return R.error(e.getMessage());
        }
        return R.success("添加成功");
    }


    /**
     * 数据回显
     * @param teacherId
     * @return
     */
    @GetMapping("/seleceById/{teacherId}")
        public R<TeacherRoleVo> seleceById (@PathVariable Long teacherId ) {
        TeacherRoleVo teacherRoleVo = teacherService.seleceById(teacherId);
        return R.success(teacherRoleVo);
    }

    @PostMapping("/update")
    public R<String> update (@RequestBody TeacherRoleVo teacherRoleVo) {
        teacherService.updateVo(teacherRoleVo,teacherRoleVo.getRoleIds());
        return R.success("更新成功");
    }

    @GetMapping("/delete/{teacherId}")
    public R<String> delete (@PathVariable Long teacherId) {
        teacherService.delete(teacherId);
        return R.success("删除成功");
    }

}
