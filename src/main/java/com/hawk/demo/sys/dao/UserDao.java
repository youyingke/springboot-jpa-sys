package com.hawk.demo.sys.dao;

import com.hawk.demo.sys.model.UserDO;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Lenovo on 2019-06-12.
 */
@Repository
public interface UserDao extends JpaRepository<UserDO,Long>,JpaSpecificationExecutor<UserDO> {
    List<UserDO> findUserDOByDeptId(Long depId);


}
