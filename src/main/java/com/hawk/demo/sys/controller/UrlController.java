package com.hawk.demo.sys.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Lenovo on 2019-06-14.
 */
@Controller
public class UrlController {

    @RequestMapping("/user/index")
    public String getUserUrl()
    {
        return "/sys/index";
    }
    @RequestMapping("/file/index")
    public String getFileUploadUrl()
    {
        return "/file/index";
    }
    @RequestMapping("/excel/user/import")
    public String excelImport()
    {
        return "/excelleadingout/import";
    }
}
