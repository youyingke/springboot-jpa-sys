package com.hawk.demo.sys.dao;

import com.hawk.demo.sys.model.UserRoleDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 用户角色对应关系
 * Created by Lenovo on 2019-06-12.
 */
@Repository
public interface UserRoleDao extends JpaRepository<UserRoleDO,Long>,JpaSpecificationExecutor<UserRoleDO> {
    @Query(value = "select role_id from sys_user_role where user_id=1?",nativeQuery = true)
    List<Long> listRoleIdByUserId(Long userId);
    int deleteByUserId(Long userId);
    int deleteByRoleId(Long roleId);
    int deleteByUserIdIn(Long[] ids);
}
