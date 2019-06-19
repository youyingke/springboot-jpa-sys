package com.hawk.demo.ztree.service.impl;


import com.hawk.demo.ztree.Dao.DepartMentDao;
import com.hawk.demo.ztree.model.DepartMent;
import com.hawk.demo.ztree.service.DepartMentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Lenovo on 2019-04-10.
 */
@Service
public class DepartMentImpl implements DepartMentService {

    @Autowired
    private DepartMentDao departMentDao;
    @Override
    public List<DepartMent> findByParentIsNull() {
        return departMentDao.findByParentIsNull();
    }

    @Override
    public List<DepartMent> findByParentById(Integer id) {
        return departMentDao.findByParentId(id);
    }

    @Override
    public DepartMent findById(Integer id) {
        return departMentDao.getOne(id);
    }

    @Override
    public void rename(Integer id, String name, Integer parentId) {

        DepartMent departMent;
        if(id>10000)
        {
            departMent=new DepartMent();
            departMent.setParent(departMentDao.getOne(parentId));
        }else{
          departMent=departMentDao.getOne(id);
        }
        departMent.setName(name);
        departMentDao.save(departMent);

    }

    @Override
    public DepartMent add(Integer parentId) {

      DepartMent departMent=new DepartMent();
        departMent.setName("新节点");
        departMent.setParent(departMentDao.getOne(parentId));
        departMentDao.save(departMent);
        return departMent;
    }

    @Override
    public void save(DepartMent departMent) {

        departMentDao.save(departMent);
    }

    @Override
    public void delete(Integer id) {

        departMentDao.deleteById(id);

    }
}
