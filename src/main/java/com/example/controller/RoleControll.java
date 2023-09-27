package com.example.controller;

import com.example.pojo.Role;
import com.example.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/role")
public class RoleControll {

    @Autowired
    private RoleService roleService;

    @GetMapping("/list")
    public ModelAndView list () {
        List<Role> roleList = roleService.list();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("roleList",roleList);
        modelAndView.setViewName("/add.jsp");
        return modelAndView;
    }

    @GetMapping("/listRole")
    public ModelAndView listRole () {
        List<Role> roleList = roleService.list();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("roleList",roleList);
        modelAndView.setViewName("/rolelist.jsp");
        return modelAndView;
    }

    @PostMapping("/add")
    public String add (Role role) {
        roleService.save(role);
        return "redirect:/role/listRole";
    }
    @GetMapping("/delete/{id}")
    public String delete (@PathVariable Long id) {
        roleService.delete(id);
        return "redirect:/role/listRole";
    }
}
