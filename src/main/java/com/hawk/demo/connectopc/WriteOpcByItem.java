package com.hawk.demo.connectopc;

import org.jinterop.dcom.common.JIException;
import org.jinterop.dcom.core.JIArray;
import org.jinterop.dcom.core.JIVariant;
import org.openscada.opc.lib.common.AlreadyConnectedException;
import org.openscada.opc.lib.common.ConnectionInformation;
import org.openscada.opc.lib.common.NotConnectedException;
import org.openscada.opc.lib.da.*;

import java.net.UnknownHostException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by Lenovo on 2018-12-03.
 */
public class WriteOpcByItem {

    public static void main(String args[]) throws AddFailedException {
        //连接信息
        final ConnectionInformation ci=new ConnectionInformation();
        ci.setHost("localhost");
        ci.setDomain("localhost");
        ci.setUser("lenovo");
        ci.setPassword("19840102");
        ci.setClsid("A879768A-7387-11D4-B0D8-009027242C59");
        final String itemId="Numeric.temperature";//项的名字
         final Server server=new Server(ci, Executors.newSingleThreadScheduledExecutor());


        try {
            server.connect();
            final AccessBase access=new SyncAccess(server,500);
            access.addItem(itemId, new DataCallback() {
                @Override
                public void changed(Item item, ItemState itemState) {
                    try {
                        if(itemState.getValue().getType()== JIVariant.VT_UI4)//如果读到的值类型是UNsignedinteger,即无符号整形数值
                        {
                            try {
                                System.out.println("<<<"+itemState+"/value="+itemState.getValue().getObjectAsUnsigned().getValue());
                            } catch (JIException e) {
                                e.printStackTrace();
                            }
                        }
                        else{
                            System.out.println("<<<"+itemState+"/value="+itemState.getValue().getObject());
                        }
                    } catch (JIException e) {
                        e.printStackTrace();
                    }
                    try {
                        // 8196是打印state.getValue().getType()得到的
                        if(itemState.getValue().getType()==8196)//如果读到的值是数组类型，下面为读取FLOAT类型数组
                        {
                            JIArray jiArray=itemState.getValue().getObjectAsArray();//按数组读取
                            Float[] arr=(Float[]) jiArray.getArrayInstance();//得到数组
                            String value="";
                            for (Float f:arr)
                            {
                                value=value+f+",";
                            }
                            System.out.println(value.substring(0,value.length()-1));//遍历打印数组的值，中间用逗号分隔，
                        }
                    } catch (JIException e) {
                        e.printStackTrace();
                    }
                }
            });
            // Add a new group，添加一个组，这个用来就读值或者写值一次，而不是循环
            // 组的名字随意，给组起名字是因为，server可以addGroup也可以removeGroup，读一次值，就先添加组，然后移除组，再读一次就再添加然后删除
             final Group group=server.addGroup("test");
            //将一个item加入到组，item名字就是opcserver上面建的项名字
            final  Item item=group.addItem(itemId);
            //开始读值
            access.bind();
            // 写一次就是item.write(value)，一直写就起个线程一直执行item.write(value)
            ScheduledExecutorService writeThread=Executors.newSingleThreadScheduledExecutor();

            writeThread.scheduleWithFixedDelay(new Runnable() {
                @Override
                public void run() {
                    // 单个值，如1，"R37"，就当字符串直接写入
                    final JIVariant value=new JIVariant("24");//写入24
                    System.out.println(">>>"+"write into value"+"24");
                    try {
                        item.write(value);
                    } catch (JIException e) {
                        e.printStackTrace();
                    }
                    // 如果写的值是数组类型呢，比如6位Long类型的数组
                    // // 构造数组，前缀加(long)是因为编辑器对于数值一般按int类型处理，其他类型要指定类型
//                     Long[] integerData={(long) 1,(long)2,(long)3,(long)4,(long)5,(long)6};
//                    final JIArray array=new JIArray(integerData,false);
//                    final JIVariant value_arr=new JIVariant(array);
//                    try {
//                        item.write(value_arr);
//                    } catch (JIException e) {
//                        e.printStackTrace();
//                    }
                }
            },5,3, TimeUnit.SECONDS);// 启动后5秒第一次执行代码，以后每3秒执行一次代码
            Thread.sleep(20*1000);
            writeThread.shutdown();//杀死线程
            access.unbind();//停止写值
        } catch (final JIException e) {
            System.out.println(String.format("%08X: %s", e.getErrorCode(), server.getErrorMessage(e.getErrorCode())));
            e.printStackTrace();
        } catch (AlreadyConnectedException e) {
            e.printStackTrace();
        } catch (NotConnectedException e) {
            e.printStackTrace();
        } catch (DuplicateGroupException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

}
