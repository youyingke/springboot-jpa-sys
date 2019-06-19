package com.hawk.demo.connectopc;

import org.jinterop.dcom.common.JIException;
import org.jinterop.dcom.core.JIString;
import org.jinterop.dcom.core.JIVariant;
import org.openscada.opc.lib.common.AlreadyConnectedException;
import org.openscada.opc.lib.common.ConnectionInformation;
import org.openscada.opc.lib.common.NotConnectedException;
import org.openscada.opc.lib.da.*;

import java.io.FileWriter;
import java.net.UnknownHostException;
import java.util.concurrent.Executors;

/**
 * Created by Lenovo on 2018-12-03.
 */
public class ReadOpc {

    public static void main(String args[])
    {
        //连接信息
        final ConnectionInformation ci=new ConnectionInformation();
        ci.setHost("localhost");
        ci.setDomain("localhost");
        ci.setUser("lenovo");
        ci.setPassword("19840102");
        ci.setClsid("A879768A-7387-11D4-B0D8-009027242C59");
         final String itemId="Textual.Months";//项的名字
//        ci.setHost("172.16.142.238");
//        ci.setDomain("172.16.142.238");
//        ci.setUser("OPCServer");
//        ci.setPassword("123456");
//        ci.setClsid("7BC0CC8E-482C-47CA-ABDC-0FE7F9C6E729");
//        final String itemId="CH01.op01.OP01002";//项的名字

        //启动服务
        final Server server=new Server(ci, Executors.newSingleThreadScheduledExecutor());
        try {
            //连接服务
            server.connect();
            //启动一个同步的ACCESS用来读取地址上的值，线程池每500MS读值一次
            //这个用来循环读值，读值一次不用这样
            final AccessBase access=new SyncAccess(server,1000);
            //这是一个回调函数，就是读到值后执行这个打印数据的，使用匿名类写的，也可以写到外面
            access.addItem(itemId, new DataCallback() {
                @Override
                public void changed(Item item, ItemState itemState) {
                    try {
                        int type=itemState.getValue().getType();
                        System.out.println("监控项的数据类型是---"+type);//类型是个数字，用常量定义的
                        System.out.println("监控项的详细信息是---"+itemState);
               //如果读到的是short类型的值
                 if(type== JIVariant.VT_I2)
                  {
                      short n=itemState.getValue().getObjectAsShort();
                      System.out.println("---short类型值："+n);


                  }
                        //如果读到的是字符串类型的值
                        //
                        if(type== JIVariant.VT_BSTR)
                        {
                            JIString value=itemState.getValue().getObjectAsString();
                            String str=value.getString();
                            System.out.println("---String类型值："+str);
                            try {
                                //打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
                                FileWriter fileWriter = new FileWriter("c:\\OpcResult.txt",true);
                                System.out.println("开始写文件---"+str);
                                fileWriter.write("---String类型值："+ str+"\t");
                                System.out.println("写到文件的详细信息是---"+str);
                                fileWriter.flush();
                                fileWriter.close();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                   } catch (JIException e) {
                        e.printStackTrace();
                    }

                }
            });
            //开始读值
            access.bind();
            //有一个10秒的延时
            Thread.sleep(10*1000);
            //stop read 停止读值
            access.unbind();

        }catch (final JIException e)
        {
            System.out.println(String.format("%08X: %s", e.getErrorCode(), server.getErrorMessage(e.getErrorCode())));

        } catch (AlreadyConnectedException e) {
            e.printStackTrace();
        } catch (DuplicateGroupException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (AddFailedException e) {
            e.printStackTrace();
        } catch (NotConnectedException e) {
            e.printStackTrace();
        }
    }




}
