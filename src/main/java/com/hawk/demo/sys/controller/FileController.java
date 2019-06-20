package com.hawk.demo.sys.controller;

import com.hawk.demo.util.FileUtil;
import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Lenovo on 2019-06-20.
 */
@Controller
@RequestMapping("/file")
public class FileController {

    @PostMapping("/uploadFile")
  //  @ResponseBody
    public String uploadFile(@RequestParam("file")MultipartFile multipartFile)
    {

        String fileName=multipartFile.getOriginalFilename();
        fileName=fileName.substring(fileName.lastIndexOf("\\")+1);
        String filePath= FileUtil.getFileBath();
        File dir=new File(filePath);
        if(!dir.exists())
        {
            /**
             * mkdir() 如果你想在已经存在的文件夹(D盘下的yyy文件夹)下建立新的文件夹（2010-02-28文件夹），就可以用此方法。
             此方法不能在不存在的文件夹下建立新的文件夹。假如想建立名字是”2010-02-28”文件夹，那么它的父文件夹必须存在。
             mkdirs() 如果你想根据File里的路径名建立文件夹（当你不知道此文件夹是否存在，也不知道父文件夹存在），就可用此方法，
             它建立文件夹的原则是：如果父文件夹不存在并且最后一级子文件夹不存在，它就自动新建所有路经里写的文件夹；如果父文件夹存在，
             它就直接在已经存在的父文件夹下新建子文件夹。
             */
            dir.mkdirs();
        }

        String saveFileName = new SimpleDateFormat("yyyy-mm-dd").format(new Date())+"@"+fileName;

        File uploadFile=new File(filePath+"/"+saveFileName);

        try {
            //
            multipartFile.transferTo(uploadFile);

            //从新定向url写法
            return "redirect:/file/filedownlist";
        } catch (IOException e) {
            e.printStackTrace();
            return "error";
        }



    }
    @GetMapping("/filedownlist")
    public String filedownList(HttpServletRequest request)
    {
        Map<String,Object> fileMap=new HashMap<>();
        String path="D:/upload";
        File dir=new File(path);
        getFiles(dir,fileMap);
       request.setAttribute("files",fileMap);
        return "/file/download";

    }
    @RequestMapping(value = "download",method = RequestMethod.GET)
    public ResponseEntity<byte[]> downFile(String filename, HttpServletRequest request) throws IOException

    {
        String path="D:/upload/file/";
        File file=new File(path+File.separator+filename);
        HttpHeaders headers = new HttpHeaders();
        String downloadFileName = null;
        try {
            downloadFileName = new String(filename.getBytes("UTF-8"),"iso-8859-1");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        headers.setContentDispositionFormData("attachment", downloadFileName);
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

        System.out.println(FileUtils.readFileToByteArray(file));
        return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),headers, HttpStatus.CREATED);
    }


    private void getFiles(File dir, Map<String, Object> fileMap) {
        if(!dir.isFile())
        {
            File[] files=dir.listFiles();
            for(File f:files)
            {
                getFiles(f,fileMap);
            }
        }
        else
        {
            String filename=dir.getName().substring(dir.getName().lastIndexOf("@")+1);
            fileMap.put(dir.getName(),filename);
        }
    }

}
