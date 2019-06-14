package com.hawk.demo.sys.dao;

import com.hawk.demo.sys.model.DeptDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by Lenovo on 2019-06-12.
 */
public interface DeptDao extends JpaRepository<DeptDO,Long>,JpaSpecificationExecutor<DeptDO> {
}
