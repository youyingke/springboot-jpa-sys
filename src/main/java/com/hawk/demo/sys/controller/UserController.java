package com.hawk.demo.sys.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hawk.demo.base.Data;
import com.hawk.demo.sys.model.UserDO;
import com.hawk.demo.sys.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * Created by Lenovo on 2019-06-12.
 */
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/getUserList")
    public Map<String,Object> getUserList()
    {

        Map<String,Object> map=new HashMap<>();

      //  Page<UserDO> userDOList=userService.find(1,5);
           List<UserDO> userDOList=userService.findAll();
        Data data=new Data();

      //  data.setUserDOList(userDOList.getContent());
       data.setUserDOList(userDOList);
       map.put("status","1000");
        map.put("data",data);

        return map;
    }

    @PostMapping("/addUser")
    public Map<String,Object> addUser(@RequestBody JSONObject jsonObject)
    {
        UserDO userDO= JSON.toJavaObject(jsonObject,UserDO.class);

        Map<String,Object> map=new HashMap<>();

        if (userDO.getUsername()!=null)
        {
            userService.save(userDO);

        map.put("status","1000");
      //  map.put("data",data);
        }

        return map;
    }

    @GetMapping("/deleteUser")
    public Map<String,Object> deleteUser(@RequestParam("ids") List<Long> ids)
    {
        Map<String,Object> map=new HashMap<>();

        List<UserDO> userDOList=new ArrayList<>();

        for(Long id:ids)
        {
            UserDO userDO=new UserDO();
            userDO.setUserId(id);
            userDOList.add(userDO);
        }

        if(ids!=null&&ids.size()!=0)
        {

           try {

               userService.deleteAll(userDOList);

               map.put("status","1000");

               return map;
           }catch (Exception e)
           {
               map.put("status","1001");
               map.put("error",e.getMessage());
           }
        }
        map.put("status","1002");
        map.put("error","删除id 为空");
        return map;
    }
    @GetMapping("/getUserById/{id}")
    public Map<String,Object> getUserById(@PathVariable("id") Long id)
    {
        Map<String,Object> map=new HashMap<>();
        UserDO userDO=userService.findById(id);
        Data data=new Data();
        data.setUserId(userDO.getUserId());
        data.setEmail(userDO.getEmail());
        data.setLiveAddress(userDO.getLiveAddress());
        data.setName(userDO.getName());
            map.put("status","1000");
        map.put("data",data);
        return map;
    }

    @PostMapping("/updateUser")
    public Map<String,Object> updateUser(@RequestBody JSONObject jsonObject)
    {
        UserDO userDO= JSON.toJavaObject(jsonObject,UserDO.class);

        Map<String,Object> map=new HashMap<>();

        if (userDO.getUserId()!=null)
        {
          UserDO userDO_N =userService.save(userDO);

            map.put("status","1000");
            map.put("data",userDO_N);
        }

        return map;
    }

    @GetMapping("/getUserByCondition")
    public Map<String,Object> getUserByCondition(@RequestParam("name")String name,@RequestParam("liveAddress") String liveAddress,@RequestParam("mobile") String mobile)
    {
  if(name.equals(""))
  {
      name=null;
  }
        if(liveAddress.equals(""))
        {
            liveAddress=null;
        }
        if(mobile.equals(""))
            mobile=null;
        List<UserDO> userDOList=userService.queryUser(name,liveAddress,mobile);
        Map<String,Object> map=new HashMap<>();
        Data data=new Data();
        data.setUserDOList(userDOList);
        map.put("status","1000");
        map.put("data",data);
        return map;

    }

}
