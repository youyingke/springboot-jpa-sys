package com.hawk.demo.connectopc;

import org.jinterop.dcom.common.JIException;
import org.jinterop.dcom.core.JIString;
import org.jinterop.dcom.core.JIVariant;
import org.openscada.opc.dcom.list.ClassDetails;
import org.openscada.opc.lib.common.AlreadyConnectedException;
import org.openscada.opc.lib.common.ConnectionInformation;
import org.openscada.opc.lib.common.NotConnectedException;
import org.openscada.opc.lib.da.*;
import org.openscada.opc.lib.da.browser.Branch;
import org.openscada.opc.lib.da.browser.FlatBrowser;
import org.openscada.opc.lib.da.browser.Leaf;
import org.openscada.opc.lib.list.Categories;
import org.openscada.opc.lib.list.Category;
import org.openscada.opc.lib.list.ServerList;

import java.io.FileWriter;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Collection;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by youyingke on 2018-12-03.
 */
public class JavaConnectOpcServer {


    /*
    * 获取OPC服务器信息
    * */
    public Collection<ClassDetails> findOpcServerList(OpcServerConfig opcServerConfig)
            throws JIException, UnknownHostException {
        ServerList serverList=new ServerList(
                opcServerConfig.getOpcServerIp(),opcServerConfig.getUsername(),opcServerConfig.getPassword(),opcServerConfig.getOpcServerDomain());

        Collection<ClassDetails> classDetailsCollection=serverList.listServersWithDetails(
                new Category[]{
                        Categories.OPCDAServer10,
                        Categories.OPCDAServer20,
                        Categories.OPCDAServer30
                },new Category[]{}
        );
        return classDetailsCollection;
    }
    /**
     * 列出opcserver所有组合项
     */
    public void listOpcServerGroupItem(OpcServerConfig opcServerConfig) throws AlreadyConnectedException, JIException, UnknownHostException {
        ConnectionInformation ci = initOpc(opcServerConfig);
         /*
        * ICONICS.SimulatorOPCDA.2配置
        * */
//         ci.setHost("localhost");
//         ci.setDomain("localhost");
//         ci.setUser("lenovo");
//         ci.setPassword("19840102");
//         ci.setClsid("A879768A-7387-11D4-B0D8-009027242C59");
        /*
        * kepserver配置
        * */
//         ci.setHost("172.16.142.238");
//          ci.setDomain("172.16.142.238");
//        ci.setUser("OPCServer");
//        ci.setPassword("123456");


        //ci.setProgId("13486D44-4821-11D2-A494-3CB306C10000");
        // ci.setClsid("F8582D2E-88FB-11D0-B850-00C0F0104305");

        Server server = new Server(ci, Executors.newSingleThreadScheduledExecutor());

        server.connect();

        dumpTree(server.getTreeBrowser().browse(), 0);
        dumpFlat(server.getFlatBrowser());

        server.disconnect();
    }



        /*
    * 通过项item获取OPC服务器对应的值,异步读取
    * */
    public void readOpcByItem(OpcServerConfig opcServerConfig,String item)
    {
        //连接信息
        ConnectionInformation ci=initOpc(opcServerConfig);
        final String itemId=item;//项的名字
        //启动服务
        final Server server=new Server(ci, Executors.newSingleThreadScheduledExecutor());
       // final Short[] value = new Short[1];
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
                        if(type== JIVariant.VT_I4)
                        {
                         Integer value = itemState.getValue().getObjectAsInt();
                            System.out.println("---short类型值："+ value);

                            try{
                                FileWriter fileWriter=new FileWriter("D://OpcResult.txt",true);
                                System.out.println("----开始写入文件");
                                fileWriter.write(value+"\r\n");
                                System.out.println("写入文件的信息是"+value);
                                fileWriter.flush();
                                fileWriter.close();

                            }catch (IOException e)
                            {
                                e.printStackTrace();
                            }


                        }
                        //如果读到的是字符串类型的值
                        //
                        if(type== JIVariant.VT_BSTR)
                        {
                            JIString value=itemState.getValue().getObjectAsString();
                            String str=value.getString();
                            System.out.println("---String类型值："+str);
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
    } catch (AlreadyConnectedException e) {
            e.printStackTrace();
        } catch (DuplicateGroupException e) {
            e.printStackTrace();
        } catch (JIException e) {
            e.printStackTrace();
        } catch (AddFailedException e) {
            e.printStackTrace();
        } catch (NotConnectedException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

        /*
    * 向制定OPC服务器的项中写入值
    * */

    public void writeIntoOpcByItem(OpcServerConfig opcServerConfig,String item,String strvalue)
    {
        //连接信息
      ConnectionInformation ci=initOpc(opcServerConfig);
       // final String itemId=item;//项的名字
        //启动服务
        final Server server=new Server(ci, Executors.newSingleThreadScheduledExecutor());


        try {
            server.connect();
            final AccessBase access=new SyncAccess(server,500);

            // Add a new group，添加一个组，这个用来就读值或者写值一次，而不是循环
            // 组的名字随意，给组起名字是因为，server可以addGroup也可以removeGroup，读一次值，就先添加组，然后移除组，再读一次就再添加然后删除
            final Group group=server.addGroup("test");
            //将一个item加入到组，item名字就是opcserver上面建的项名字
            final  Item itemId=group.addItem(item);
            //开始读值
            access.bind();
            // 写一次就是item.write(value)，一直写就起个线程一直执行item.write(value)
            ScheduledExecutorService writeThread=Executors.newSingleThreadScheduledExecutor();

            writeThread.scheduleWithFixedDelay(new Runnable() {
                @Override
                public void run() {
                    // 单个值，如1，"R37"，就当字符串直接写入
                    final JIVariant value=new JIVariant(strvalue);//写入24
                    System.out.println(">>>"+"write into value"+strvalue);
                    try {
                        itemId.write(value);
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
        } catch (AddFailedException e) {
            e.printStackTrace();
        }
    }
    /*
    * 同步读取
    * */
    public void readOpcByItemS(OpcServerConfig opcServerConfig,String item)
    {

    }

    /*
    * 订阅查询
    * */
    public void querySubScribe(OpcServerConfig opcServerConfig,String item) throws AlreadyConnectedException, JIException, UnknownHostException, NotConnectedException, DuplicateGroupException, AddFailedException, InterruptedException {


        ConnectionInformation ci=initOpc(opcServerConfig);
        Server server = new Server(ci,
                Executors.newSingleThreadScheduledExecutor());

        server.connect();
        //*订阅查询

        AccessBase access20 = new Async20Access(server, 200, false);

        access20.addItem(item, new DataCallback() {

            private int count;

            public void changed(Item item, ItemState itemstate) {
                System.out.println("订阅查询"+"[" + (++count) + "],ItemName:["
                        + item.getId() + "],value:" + itemstate.getValue());
                try {
                    FileWriter fileWriter=new FileWriter("D:\\Opcsubscribe.txt");
                    fileWriter.write(String.valueOf(itemstate.getValue())+"\r\n");
                    fileWriter.flush();
                    fileWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        access20.bind();
        Thread.sleep(10*1000);
        access20.unbind();
        //*订阅查询


        server.dispose();
    }

    public ConnectionInformation initOpc(OpcServerConfig opcServerConfig)
    {
        final ConnectionInformation ci=new ConnectionInformation();
        ci.setHost(opcServerConfig.getOpcServerIp());
        ci.setDomain(opcServerConfig.getOpcServerDomain());
        ci.setUser(opcServerConfig.getUsername());
        ci.setPassword(opcServerConfig.getPassword());
        ci.setClsid(opcServerConfig.getClassId());
        return ci;
    }

    private static void dumpFlat(final FlatBrowser browser)
            throws IllegalArgumentException, UnknownHostException, JIException {
        for (String name : browser.browse()) {
            System.out.println("Flat is :>>"+name);
        }
    }

    private static void dumpTree(final Branch branch, final int level) {

        for (final Leaf leaf : branch.getLeaves()) {
            dumpLeaf(leaf, level);
        }
        for (final Branch subBranch : branch.getBranches()) {
            dumpBranch(subBranch, level);
            dumpTree(subBranch, level + 1);
        }
    }

    private static String printTab(int level) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < level; i++) {
            sb.append("\t");
        }
        return sb.toString();
    }

    private static void dumpLeaf(final Leaf leaf, final int level) {
        System.out.println(printTab(level) + "Leaf: " + leaf.getName() + ":"
                + leaf.getItemId());
    }

    private static void dumpBranch(final Branch branch, final int level) {
        System.out.println(printTab(level) + "Branch: " + branch.getName());
    }
}
