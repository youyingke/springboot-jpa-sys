package com.hawk.demo.connectopc;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lenovo on 2018-12-03.
 */
public class WriteInfoIntoTxt {
    public static void main(String[] args) {
        try {
            FileWriter fileWriter = new FileWriter("c:\\Result.txt");
            String ss = "cName\tCode\t\tcreateDate\n";
            ss += "dbc券\t\t111188\t\t2017-05-14\n";
            ss += "zc券\t\t111199\t\t2017-05-14\n";
            fileWriter.write(ss);
            fileWriter.flush();
            fileWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(">>>>>>>>>>>>>>>>>>write end");

        try {
            FileReader fileReader = new FileReader("c:\\test.txt");
            BufferedReader br = new BufferedReader(fileReader);
            String s;

            String lines[] = new String[0];
            while ((s = br.readLine()) != null) {
                System.out.println(s);

                lines = s.split("\r?\n");
            }
            for (String line : lines) {

                System.out.println(line);
            }
            fileReader.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
       try{ FileReader fileReader=new FileReader("D://OpcResult.txt");
           BufferedReader buffereReader=new BufferedReader(fileReader);
           List<String> lines=new ArrayList<>();
          // final String[] lastString = {null};
           for(String line=buffereReader.readLine();line!=null;line=buffereReader.readLine())
           {
               lines.add(line);
           }
        //  String[] str = new String[lines.size()];
        //   int i=0;
           lines.forEach(e->{
               System.out.println(e);
           });

    } catch (FileNotFoundException e)
       {e.printStackTrace();
       } catch (IOException e) {
           e.printStackTrace();
       }


        System.out.println(">>>>>>>>>>>>>>>>>>read end");
    }

}
