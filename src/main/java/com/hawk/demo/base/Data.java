package com.hawk.demo.base;

import com.hawk.demo.sys.model.UserDO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lenovo on 2019-06-14.
 */
public class Data {

    List<UserDO> userDOList=new ArrayList<>();
    private Long userId;
    private String name;
    private String liveAddress;
    private String mobile;
    private String email;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLiveAddress() {
        return liveAddress;
    }

    public void setLiveAddress(String liveAddress) {
        this.liveAddress = liveAddress;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<UserDO> getUserDOList() {
        return userDOList;
    }

    public void setUserDOList(List<UserDO> userDOList) {
        this.userDOList = userDOList;
    }
}
