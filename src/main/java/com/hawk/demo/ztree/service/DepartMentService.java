package com.hawk.demo.ztree.service;



import com.hawk.demo.ztree.model.DepartMent;

import java.util.List;

/**
 * Created by Lenovo on 2019-04-10.
 */
public interface DepartMentService {

    public List<DepartMent> findByParentIsNull();

    public List<DepartMent> findByParentById(Integer id);

    public DepartMent findById(Integer id);

    public void rename(Integer id, String name, Integer parentId);

    public DepartMent add(Integer parentId);

    public void save(DepartMent departMent);

    public void delete(Integer id);
}
