package com.example.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.common.R;
import com.example.service.TeacherService;
import com.example.vo.TeacherRoleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/teacher")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;


    @GetMapping("/page")
    @Cacheable(value = "TeacherPageCache",key = "#currentPage+'_'+#pageSize+'_'+#name")
    public R<Page<TeacherRoleVo>> page (int currentPage,int pageSize,String name) {
        Page<TeacherRoleVo> page = teacherService.page(currentPage,pageSize, name);
        return R.success(page);
    }

    @PostMapping("/add")
    @CacheEvict(value = "TeacherPageCache",allEntries = true)
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
    @CacheEvict(value = "TeacherPageCache",allEntries = true)
    public R<String> update (@RequestBody TeacherRoleVo teacherRoleVo) {
        teacherService.updateVo(teacherRoleVo,teacherRoleVo.getRoleIds());
        return R.success("更新成功");
    }

    @GetMapping("/delete")
    @CacheEvict(value = "TeacherPageCache",allEntries = true)
    public R<String> delete (@RequestParam List<Long> ids) {
        teacherService.delete(ids);
        return R.success("删除成功");
    }

}
