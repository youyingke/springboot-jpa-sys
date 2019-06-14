package com.hawk.demo.sys.dao;

import com.hawk.demo.sys.model.RoleMenuDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by Lenovo on 2019-06-12.
 */
public interface RoleMenuDao extends JpaRepository<RoleMenuDO,Long>,JpaSpecificationExecutor<RoleMenuDO>{
}
