package com.wja.edu.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wja.base.common.OpResult;
import com.wja.base.util.CollectionUtil;
import com.wja.base.util.Page;
import com.wja.base.util.Sort;
import com.wja.edu.entity.Course;
import com.wja.edu.entity.Teacher;
import com.wja.edu.service.CourseService;
import com.wja.edu.service.TeacherService;

@Controller
@RequestMapping("/teacher")
public class TeacherController
{
    @Autowired
    private TeacherService service;
    
    @Autowired
    private CourseService courseService;
    
    @RequestMapping("manage")
    public String manage()
    {
        return "edu/teacher";
    }
    
    @RequestMapping({"add", "update"})
    @ResponseBody
    public OpResult save(Teacher c, String[] courseIds)
    {
        if (CollectionUtil.isNotEmpty(courseIds))
        {
            List<Course> curses = this.courseService.find(courseIds);
            c.setCourses(new HashSet<Course>(curses));
        }
        
        boolean add = StringUtils.isBlank(c.getId());
        c = this.service.save(c);
        if (add)
        {
            return OpResult.addOk(c.getId());
        }
        else
        {
            return OpResult.updateOk();
        }
    }
    
    @RequestMapping("query")
    @ResponseBody
    public Page<Teacher> pageQuery(@RequestParam Map<String, Object> params, Page<Teacher> page)
    {
        return this.service.pageQuery(params, page);
    }
    
    @RequestMapping("list")
    @ResponseBody
    public List<Teacher> query(@RequestParam Map<String, Object> params, Sort sort)
    {
        return this.service.query(params, sort);
    }
    
    @RequestMapping("delete")
    @ResponseBody
    public OpResult delete(String[] id)
    {
        this.service.delete2(id);
        return OpResult.deleteOk();
    }
    
    @RequestMapping("get")
    @ResponseBody
    public Teacher get(String id)
    {
       Teacher techer= this.service.get(id);
        return techer;
    }
    
}
