package com.hawk.demo.sys.service;

import com.hawk.demo.common.CommonService;
import com.hawk.demo.sys.model.UserDO;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Created by Lenovo on 2019-06-12.
 */
public interface UserService extends CommonService<UserDO>{

    List<UserDO> listUserByDepId(Long depId);
    List<UserDO> queryUser(String userName, String address, String mobile);


}
