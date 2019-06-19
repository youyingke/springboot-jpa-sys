package com.hawk.demo.ztree.controller;


import com.hawk.demo.ztree.form.DeptForm;
import com.hawk.demo.ztree.model.DepartMent;
import com.hawk.demo.ztree.service.DepartMentService;
import com.hawk.demo.ztree.util.ZtreeUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by Lenovo on 2019-04-10.
 */
@Controller
@RequestMapping("/ztree")
public class DepartMentController {
    @Autowired
    private DepartMentService departMentService;

    @RequestMapping("/tree")
    public String getTree()
    {
        return "/ztree/TestTree";
    }
    @GetMapping("/index")
    public String index()
    {
        return "/ztree/index";
    }

    @RequestMapping("/list")
    @ResponseBody
    public Object list(Integer id)
    {
        List<DepartMent> list;
        if(id==null)
        {
            list=departMentService.findByParentIsNull();
        }
        else{
            list =departMentService.findByParentById(id);
        }
        return ZtreeUtil.buildZtreeResult(list);
    }
   @RequestMapping(value = "/edit")
    public String edit(Integer id, ModelMap map)
   {
       DepartMent model=departMentService.findById(id);
       map.put("model",model);
       return "edit";
   }
    @RequestMapping(value = "/save")
    public String save(DeptForm form)
    {
        DepartMent model=new DepartMent();
        //属性拷贝
        BeanUtils.copyProperties(form,model,"parent");
        DepartMent parent =departMentService.findById(form.getId());
        model.setParent(parent);
        departMentService.save(model);
        return "redirect:tree";

    }
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(Integer id)
    {
        departMentService.delete(id);
        return "OK";
    }
   @RequestMapping(value = "/rename")
   @ResponseBody
   public Object rename(Integer id,String name,Integer pId)
   {
       departMentService.rename(id,name,pId);
       return "OK";
   }
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(Integer pId)
    {
        DepartMent model=departMentService.add(pId);
        return model;
    }
}
