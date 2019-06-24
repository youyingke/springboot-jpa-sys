/**
 * Author: hjw
 * Data: 2018/4/18 16:01
 * Description: 测试导出数据库信息到Excel表格中
 */
package com.hawk.demo.excelleadingout;


import com.hawk.demo.excelleadingout.until.ExcelImport;
import com.hawk.demo.sys.model.UserDO;
import com.hawk.demo.sys.service.UserService;
import com.hawk.demo.util.BeansUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ModelAndView;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/excel")
public class ExcelController {

    @Autowired
    private UserService userService;


    /**
     * 对应实体记得添加@Excel注解
     * @param response
     */
    @RequestMapping("export")
    public void export (HttpServletResponse response){
        UserDO user = new UserDO();
        user.setName("hjw");
        //从数据库获取需要导出的数据
        List<UserDO> userList = userService.findAll();
        //导出操作
        ExcelImport.exportExcel(userList,"User表","信息",UserDO.class,"User.xls",response);

    }
    @RequestMapping("list")
    @ResponseBody
    public List<UserDO> list(HttpServletResponse response){
        UserDO user = new UserDO();
        //从数据库获取需要导出的数据
        List<UserDO> userList = userService.findAll();
        ExcelImport.exportExcel(userList,"userTest表","user",UserDO.class,"USerTest.xls",response);
        return userList;
    }

//    @RequestMapping("select")
//    @ResponseBody
//    public User seclectTest(){
//        User user = userService.selectByPrimaryKey(2);
//        return user;
//    }
//
//    @RequestMapping("selectZ")
//    @ResponseBody
//    public Zsj seclectZsj(){
//        Zsj zsj = zsjService.selectByPrimaryKey(2);
//        return zsj;
//    }
   @RequestMapping("import")
    public void importExcel()
   {
       String filePath = "D:\\User.xls";
       //解析excel，
       List<UserDO> userList = ExcelImport.importExcel(filePath,1,1,UserDO.class);
       //也可以使用MultipartFile,使用 FileUtil.importExcel(MultipartFile file, Integer titleRows, Integer headerRows, Class<T> pojoClass)导入
       System.out.println("导入数据一共【"+userList.size()+"】行");
      for(UserDO user:userList)
      {
          System.out.println("name is"+user.getName());
          System.out.println("age is"+user.getUsername());
          System.out.println("name is"+user.getSex());
          System.out.println("age is"+user.getUserId());
          //TODO 保存数据库
         try{ userService.save(user);
         }catch (Exception e){

             e.getStackTrace();
         }

      }



   }

    @RequestMapping("importExcel")
    public List<UserDO> importData() throws Exception
    {
        Workbook wb = null;
        List<UserDO> userList = new ArrayList();
//        try
//        {
//            if (ExcelUtil.isExcel2007(file.getPath())) {
//                wb = new XSSFWorkbook(new FileInputStream(file));
//            } else {
//                wb = new HSSFWorkbook(new FileInputStream(file));
//            }
//        }
//        catch (IOException e)
//        {
//            e.printStackTrace();
//
//            return null;
//        }

        Sheet sheet = wb.getSheetAt(0);//获取第一张表
        for (int i = 1; i < sheet.getLastRowNum(); i++)
        {
            Row row = sheet.getRow(i);//获取索引为i的行，以0开始
            String name= row.getCell(0).getStringCellValue();//获取第i行的索引为0的单元格数据
            String username =row.getCell(1).getStringCellValue();
            System.out.println(name);
            System.out.println(username);
            if (username==null && name==null)
            {
                break;
            }
            UserDO user=new UserDO();
          user.setName(name);
            user.setUsername(username);
            userList.add(user);
        }
        try
        {
            wb.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return userList;
    }

    @RequestMapping(value = "user/upload",method = RequestMethod.POST)
    public String uploadUser(MultipartFile file)
    {
        if(file!=null)
        {
            List<UserDO> userDOList=ExcelImport.importExcel(file,1,1,UserDO.class);
            for(UserDO user:userDOList)
            {
//                System.out.println("name is"+user.getName());
//                System.out.println("age is"+user.getUserId());
//                System.out.println("name is"+user.getSex());
//                System.out.println("age is"+user.getPicId());
                System.out.println("userid:"+user.getUserId()+"/username"+user.getUsername()+"/name"+user.getName()+"/email"+user.getEmail());
                //TODO 保存数据库
                try{
                    userService.save(user);
                    return "导入成功";
                }catch (Exception e){
                    System.out.println("insert database error");
                                    e.getStackTrace();
                    return "插入失败";
                }

            }


        }
        return "file is null";
    }
    @RequestMapping(value = "user/uploadResult", method = RequestMethod.POST)
    public ModelAndView uploadResult(HttpServletRequest request, Model model) {
//  for (MultipartFile file:files) {

        //处理文件流
        MultipartHttpServletRequest multipartHttpServletRequest=null;


        //从MultipartHttpServletRequest获取文件流
        CommonsMultipartResolver multipartResolver=new CommonsMultipartResolver
                (request.getSession().getServletContext());
//        String username= (String) request.getSession().getAttribute("username");
//        String path = "D:/temp/upload"+"/"+username;//request.getSession().getServletContext().getRealPath("/")+"/upload/";
//
//        File dir = new File(path);
//        if (!dir.exists()){
//            dir.mkdirs();
//        }




        try {

            //获取请求中存在的文件流，并却出相关文件（包括缩微图和详情图）
            if(multipartResolver.isMultipart(request))
            {
                multipartHttpServletRequest=(MultipartHttpServletRequest) request;
                //取出缩微图并构建ImageHolder对象
                MultipartFile file=(MultipartFile)  multipartHttpServletRequest.getFile("file");

                String fileName = file.getOriginalFilename();
                //  fileName=getFileExtension(fileName);
                System.out.println(fileName);

                List<UserDO> userList = ExcelImport.importExcel(file,1,1,UserDO.class);

                System.out.println("导入数据一共【"+userList.size()+"】行");
                for(UserDO user:userList)
                {
                    System.out.println("userid:"+user.getUserId()+"/username"+user.getUsername()+"/name"+user.getName()+"/email"+user.getEmail());
                    //TODO 保存数据库

                    UserDO userDO=userService.findById(user.getUserId());
                    BeansUtil.copyPropertiesIgnoreNull(user,userDO);
                    try{
                        //会造成数据库没有导入的字段置空
                        userService.save(userDO);
                    }catch (Exception e){
                              System.out.println("insert database error");
                        e.getStackTrace();
                    }

                }
//                String saveFileName = new SimpleDateFormat("yyyy-DD-dd").format(new Date())+"@"+fileName;
//                File upFile = new File(dir+"/"+saveFileName);
//                file.transferTo(upFile);
                model.addAttribute("result","success");
                return new ModelAndView("result");

            }
            else{
                System.out.println("file is error");
                model.addAttribute("result","error");
                return new ModelAndView("result");
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("file error");
            model.addAttribute("result","error");
            return new ModelAndView("result");
        }
//   }
    }

    @RequestMapping("imoprtweb")
    public ModelAndView result(HttpServletRequest request, Model model){
        String path = request.getContextPath();
        String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
        model.addAttribute("path",basePath);
        System.out.println(basePath);
        return new ModelAndView("import");
    }
}
