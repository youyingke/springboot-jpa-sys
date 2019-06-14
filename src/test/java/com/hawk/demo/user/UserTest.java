package com.hawk.demo.user;

import com.hawk.demo.sys.model.UserDO;
import com.hawk.demo.sys.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lenovo on 2019-06-12.
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class UserTest {
    @Autowired
    private UserService userService;
    @Test
    public void useradd()
    {
        List<Long> roleIds=new ArrayList<>();
        roleIds.add(1l);
        roleIds.add(2l);
        UserDO userDO=new UserDO();
      //  userDO.setUserId(1l);
        userDO.setUsername("hawk");
        userDO.setPassword("123");
        userDO.setRoleIds(roleIds);
        UserDO user=userService.save(userDO);
        System.out.println(user.getName());
    }
    @Test
    public void userDelete()
    {
        userService.deleteById(1l);
    }
    @Test
    public void getuserBypage()
    {
        Page<UserDO> page=userService.find(1,2);
        for(UserDO userDO:page)
        {
            System.out.println(userDO.getUserId());
        }
    }
    @Test
    public void updateuser()
    {
        UserDO userDO=new UserDO();
        userDO.setUserId(7l);
        userDO.setPassword("123214");
        userService.save(userDO);
    }

    @Test
    public void getUserByDeptId()
    {
       List<UserDO> userDOPage= userService.listUserByDepId(1l);
        for(UserDO userDO:userDOPage)
        {
            System.out.println(userDO.getName()+userDO.getDeptName());
        }

    }
    @Test
    public void getAllUser()
    {
        List<UserDO> userDOList=userService.findAll();
        for(UserDO userDO:userDOList)
        {
            System.out.println(userDO.getUserId());
        }
    }
    @Test
    public void queryUserByCondition()
    {
        String name="1";
        String address="河北";
        String mobile="12";
        List<UserDO> userDOList=userService.queryUser(name,address,mobile);
        for(UserDO userDO:userDOList)
        {
            System.out.println(userDO.getUserId());
        }
    }
}
