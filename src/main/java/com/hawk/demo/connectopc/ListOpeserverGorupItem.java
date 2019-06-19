package com.hawk.demo.connectopc;

import org.jinterop.dcom.common.JIException;
import org.openscada.opc.lib.common.AlreadyConnectedException;
import org.openscada.opc.lib.common.ConnectionInformation;
import org.openscada.opc.lib.da.Server;
import org.openscada.opc.lib.da.browser.Branch;
import org.openscada.opc.lib.da.browser.FlatBrowser;
import org.openscada.opc.lib.da.browser.Leaf;

import java.net.UnknownHostException;
import java.util.concurrent.Executors;

/**
 * Created by Lenovo on 2018-12-03.
 */
public class ListOpeserverGorupItem {


    public void ListOpcserverGroupItme(OpcServerConfig opcServerConfig) throws AlreadyConnectedException, JIException, UnknownHostException {

        ConnectionInformation ci = new ConnectionInformation();

         /*
        * ICONICS.SimulatorOPCDA.2配置
        * */
        ci.setHost("localhost");
        ci.setDomain("localhost");
        ci.setUser("lenovo");
        ci.setPassword("19840102");
        ci.setClsid("A879768A-7387-11D4-B0D8-009027242C59");
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
