package com.hawk.demo.ztree.util;



import com.hawk.demo.ztree.model.DepartMent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Lenovo on 2019-04-10.
 */
public class ZtreeUtil {
    public static List<HashMap<String,Object>> buildZtreeResult(List<DepartMent> list)
    {
        List<HashMap<String,Object>> result=new ArrayList<>();
        for(DepartMent departMent:list)
        {
            HashMap<String,Object> map=new HashMap<>();
            map.put("id",departMent.getId());
            map.put("pId",departMent.getParent()==null?null:departMent.getId());
            map.put("name",departMent.getName());
            map.put("isParent",departMent.getIsParent());
            result.add(map);
        }
        return result;
    }

}
