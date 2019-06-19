package com.hawk.demo.util;

import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;

import java.io.*;

/**
 * Created by Lenovo on 2019-03-01.
 */
public class ReadPdf {

    /**
     * 查找某个字符在字符串中出现的次数
     * @param srcText
     * @param findText
     * @return
     */
    public static int appearNumber(String srcText, String findText) {
        int count = 0;
        int index = 0;
        while ((index = srcText.indexOf(findText, index)) != -1) {
            index = index + findText.length();
            System.out.println("index"+index);
            String str=srcText.substring(index,index+5);
            System.out.println(str);
            count++;
        }
        return count;
    }

    /**
     *
     * @param filePath pdf文件的路径
     * @return 字符串
     */

    public static String getTextFromPdf(String filePath) {
        String result = null;
        FileInputStream is = null;
        PDDocument document = null;
        try {
            is = new FileInputStream(filePath);
            PDFParser parser = new PDFParser(is);
            parser.parse();
            document = parser.getPDDocument();
            PDFTextStripper stripper = new PDFTextStripper();
            result = stripper.getText(document);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (document != null) {
                try {
                    document.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    public static void  main(String args[])  {
        ReadPdf readPdf=new ReadPdf();
//        String result=readPdf.getTextFromPdf("D:/123.txt");
//        System.out.println(result);

     //  String str= result.substring(result.indexOf("经营单位")-1,18);

     // String str="经营单位       60117219001488定单号";
        try{
           // FileReader fileReader=new FileReader("D://123.txt","utf-8");
         //   BufferedReader buffereReader = new BufferedReader(new InputStreamReader(new FileInputStream("d://123.txt"),"UTF-8"));
            BufferedReader buffereReader = new BufferedReader(new InputStreamReader(new FileInputStream("D://123.txt"),"UTF-8"));
           // BufferedReader buffereReader=new BufferedReader(fileReader);
          String str=null;
           while((str=buffereReader.readLine())!=null)
           {   //System.out.println(str);
               String str1= str.substring(str.indexOf("dingdan")+7,str.indexOf("dingdan")+12);
               System.out.println(str1);
               readPdf.appearNumber(str,"dingdan");

           }
            buffereReader.close();


//            List<String> lines=new ArrayList<>();
//            // final String[] lastString = {null};
//            for(String line=buffereReader.readLine();line!=null;line=buffereReader.readLine())
//            {
//                lines.add(line);
//            }
//            //  String[] str = new String[lines.size()];
//            //   int i=0;
//            lines.forEach(e->{
//                System.out.println(e);
//            });






        } catch (FileNotFoundException e)
        {e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


//        int index=result.indexOf("经营单位");
//
//        System.out.println("weizhi"+index);
//        String str1= result.substring(result.indexOf("定单号")+3,result.indexOf("经营单位")+6);
//        System.out.println("截取字符为"+str1);

//        try{
//        FileWriter fileWriter=new FileWriter("D:\\pdf_result.txt");
//        fileWriter.write(result);
//            fileWriter.flush();
//            fileWriter.close();
//        }
//      catch (IOException e) {
//            e.printStackTrace();
//        }
    }
}
