package com.wja.edu.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.wja.base.common.OpResult;
import com.wja.base.system.entity.Org;
import com.wja.base.system.service.OrgService;
import com.wja.base.util.BeanUtil;
import com.wja.base.util.Page;
import com.wja.base.util.Sort;
import com.wja.base.web.AppContext;
import com.wja.base.web.RequestThreadLocal;
import com.wja.edu.entity.Clazz;
import com.wja.edu.entity.ClazzCourse;
import com.wja.edu.entity.Teacher;
import com.wja.edu.service.ClazzService;
import com.wja.edu.service.CourseService;
import com.wja.edu.service.TeacherService;

@Controller
@RequestMapping("/clazz")
public class ClazzController
{
    @Autowired
    private ClazzService clazzService;
    
    @Autowired
    private CourseService courseService;
    
    @Autowired
    private TeacherService teacherService;
    
    @RequestMapping("manage")
    public String manage()
    {
        return "edu/clazz";
    }
    
    @RequestMapping("my")
    public String myClazz(Model model)
    {
        model.addAttribute("times", System.currentTimeMillis());
        model.addAttribute("courseTreeNodes", JSON.toJSONString(this.courseService.findAll()));
        model.addAttribute("treeYears", AppContext.getIntSysParam("clazz.tree.years"));
        
        String userId = RequestThreadLocal.currUser.get().getId();
        Teacher t = this.teacherService.getByUserId(userId);
        if (t != null)
        {
            model.addAttribute("my", t.getId());
        }
        return "edu/teaching/clazz_view";
    }
    
    @RequestMapping("view")
    public String view(Model model)
    {
//        model.addAttribute("times", System.currentTimeMillis());
//        
//        Map<String, Object> params = new HashMap<>();
//        params.put("type", Org.TYPE_SCHOOL);
//        Sort sort = new Sort("pid,ordno", "asc,asc");
//        model.addAttribute("treeNodes", JSON.toJSONString(this.orgService.findAll(params, sort)));
//        model.addAttribute("courseTreeNodes", JSON.toJSONString(this.courseService.findAll()));
//        model.addAttribute("treeYears", AppContext.getIntSysParam("clazz.tree.years"));
        return "edu/teaching/clazz_manage";
    }
    @RequestMapping("clazzCourse")
    public String coursePlan(Model model){
        
        return "edu/teaching/clazz_course";
    }
    
    @RequestMapping("courses")
    @ResponseBody
    public List<ClazzCourse> getClazzCourse(String clazzId)
    {
        List<ClazzCourse> list = this.clazzService.getClazzCourse(clazzId);
        BeanUtil.setCollFieldValues(list);
        return list;
    }
    
    @RequestMapping("saveCourses")
    @ResponseBody
    public Object saveClazzCourse(String clazzId, String course)
    {
        List<ClazzCourse> courses = null;
        if (StringUtils.isNotBlank(course))
        {
            courses = JSON.parseArray(course, ClazzCourse.class);
        }
        this.clazzService.saveClazzCourse(clazzId, courses);
        return OpResult.updateOk();
    }
    
    @RequestMapping("registGet")
    @ResponseBody
    public List<Clazz> registsGet()
    {
        Map<String, Object> params = new HashMap<>();
        params.put("status_in", new String[] {Clazz.STATUS_NOT_START, Clazz.STATUS_STARTED});
        return this.clazzService.query(params, Sort.desc("startTime"));
    }
    
    @RequestMapping("nameExits")
    @ResponseBody
    public boolean nameExits(String name)
    {
        return this.clazzService.getClazzByName(name) == null;
    }
    
    @RequestMapping({"add", "update"})
    @ResponseBody
    public OpResult save(Clazz c)
    {
        boolean add = StringUtils.isBlank(c.getId());
        c = this.clazzService.save(c);
        if (add)
        {
            return OpResult.addOk(c);
        }
        else
        {
            return OpResult.updateOk(c);
        }
    }
    
    @RequestMapping("query")
    @ResponseBody
    public Page<Clazz> pageQuery(@RequestParam Map<String, Object> params, Page<Clazz> page)
    {
        return this.clazzService.pageQuery(params, page);
    }
    
    @RequestMapping("list")
    @ResponseBody
    public List<Clazz> pageQuery(@RequestParam Map<String, Object> params, Sort sort)
    {
        return this.clazzService.query(params, sort);
    }
    
    @RequestMapping("delete")
    @ResponseBody
    public OpResult delete(String[] id)
    {
        this.clazzService.delete(id);
        return OpResult.deleteOk();
    }
}
