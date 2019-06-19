package com.hawk.demo.ztree.Dao;


import com.hawk.demo.ztree.model.DepartMent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Lenovo on 2019-04-10.
 */
@Repository
public interface DepartMentDao extends JpaRepository<DepartMent,Integer>,JpaSpecificationExecutor<DepartMent> {

    public List<DepartMent> findByParentIsNull();

    public List<DepartMent> findByParentId(Integer id);

}
