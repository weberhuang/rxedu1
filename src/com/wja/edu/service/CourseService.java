package com.wja.edu.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wja.base.common.CommSpecification;
import com.wja.base.util.BeanUtil;
import com.wja.base.util.CollectionUtil;
import com.wja.base.util.Page;
import com.wja.base.util.Sort;
import com.wja.edu.dao.CourseDao;
import com.wja.edu.entity.Course;

@Service
public class CourseService
{
    @Autowired
    private CourseDao dao;
    
    public Course get(String id)
    {
        if (StringUtils.isBlank(id))
        {
            return null;
        }
        
        return this.dao.getOne(id);
    }
    
    public List<Course> findAll()
    {
        Sort sort = new Sort("pid,ordno", "asc,asc");
        return this.dao.findAll(sort.getSpringSort());
    }
    
    public Course getByNameAndPid(String name, String pid)
    {
        if (StringUtils.isBlank(name) || StringUtils.isBlank(pid))
        {
            return null;
        }
        return this.dao.findByNameAndPid(name, pid);
    }
    
    public List<Course> find(String[] ids)
    {
        return this.dao.findAll(ids);
    }
    
    public Course save(Course e)
    {
        if (StringUtils.isNotBlank(e.getId()))
        {
            Course dbc = this.dao.getOne(e.getId());
            BeanUtil.copyPropertiesIgnoreNull(e, dbc);
            e = dbc;
        }
        
        return this.dao.save(e);
    }
    
    public void delete(String id)
    {
        if (StringUtils.isNotBlank(id))
        {
            this.dao.logicDelete(id);
        }
    }
    
    public void delete(String[] ids)
    {
        if (!CollectionUtil.isEmpty(ids))
        {
            this.dao.logicDeleteInBatch(ids);
        }
    }
    
    public List<Course> query(Map<String, Object> params, Sort sort)
    {
        List<Course> result = this.dao.findAll(new CommSpecification<Course>(params), sort == null ? null : sort.getSpringSort());
        
        return result;
    }
    
    public Page<Course> pageQuery(Map<String, Object> params, Page<Course> page)
    {
        return page.setPageData(this.dao.findAll(new CommSpecification<Course>(params), page.getPageRequest()));
    }
}
