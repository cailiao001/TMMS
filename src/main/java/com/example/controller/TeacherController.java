package com.example.controller;

import com.example.pojo.Role;
import com.example.pojo.Teacher;
import com.example.service.RoleService;
import com.example.service.TeacherService;
import com.example.vo.TeacherRoleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

@Controller
@RequestMapping("/teacher")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private RoleService roleService;


    @GetMapping("/list")
    public ModelAndView list (String name, String workNo) {
        List<TeacherRoleVo> teacherRoleVoList = teacherService.listVo(name,workNo);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("teacherRoleVoLis",teacherRoleVoList);
        modelAndView.setViewName("/list.jsp");
        return modelAndView;
    }

    @PostMapping("/add")
    public String add (Teacher teacher,Long[] roleIds) throws UnsupportedEncodingException {
        try {
            teacherService.add(teacher,roleIds);
        }catch (RuntimeException e){
            e.printStackTrace();
            return "redirect:/role/list?msg="+ URLEncoder.encode(e.getMessage(),"UTF-8");
        }
        return "redirect:/teacher/list";
    }


    /**
     * 数据回显
     * @param teacherId
     * @return
     */
    @GetMapping("/seleceById/{teacherId}")
        public ModelAndView seleceById (@PathVariable Long teacherId ) {
        TeacherRoleVo teacherRoleVo = teacherService.seleceById(teacherId);
        List<String> roleNames = teacherService.roleNames(teacherRoleVo);
        List<Role> roleList = roleService.list();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("roleNames",roleNames);
        modelAndView.addObject("teacherRoleVo",teacherRoleVo);
        modelAndView.addObject("roleList",roleList);
        modelAndView.setViewName("/update.jsp");
        return modelAndView;
    }

    @PostMapping("/update")
    public String update (Teacher teacher,Long[] roleIds) {
        teacherService.updateVo(teacher,roleIds);
        return "redirect:/teacher/list";
    }

    @GetMapping("/delete/{teacherId}")
    public String delete (@PathVariable Long teacherId) {
        teacherService.delete(teacherId);
        return "redirect:/teacher/list";
    }

}
