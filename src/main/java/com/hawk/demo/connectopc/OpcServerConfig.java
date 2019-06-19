package com.hawk.demo.connectopc;

/**
 * Created by Lenovo on 2018-12-03.
 */
public class OpcServerConfig {
    private String opcServerIp;
    private String username;
    private String password;
    private String OpcServerDomain;
    private String ProgId;
    private String ClassId;

    public String getProgId() {
        return ProgId;
    }

    public void setProgId(String progId) {
        ProgId = progId;
    }

    public String getClassId() {
        return ClassId;
    }

    public void setClassId(String classId) {
        ClassId = classId;
    }

    public String getOpcServerIp() {
        return opcServerIp;
    }

    public void setOpcServerIp(String opcServerIp) {
        this.opcServerIp = opcServerIp;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getOpcServerDomain() {
        return OpcServerDomain;
    }

    public void setOpcServerDomain(String opcServerDomain) {
        OpcServerDomain = opcServerDomain;
    }

    public OpcServerConfig(String opcServerIp, String username, String password, String opcServerDomain) {
        this.opcServerIp = opcServerIp;
        this.username = username;
        this.password = password;
        OpcServerDomain = opcServerDomain;
    }

}
