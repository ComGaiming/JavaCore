package dev.syster42.framework.api;

import dev.syster42.framework.utils.FileAPI;

import java.io.File;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ServerAPI {

    private boolean allowedConnection;
    private boolean secret;

    public boolean isAllowedConnection() {
        return this.allowedConnection;
    }

    public void setAllowedConnection(boolean allowedConnection) {
        this.allowedConnection = allowedConnection;
    }

    public String getOS() {
        return System.getProperty("os.name").toLowerCase();
    }

    public String getOSArch() {
        return System.getProperty("os.arch").toLowerCase();
    }

    public String getOSVersion() {
        return System.getProperty("os.version").toLowerCase();
    }

    public void printAllNetworkaddresses() {
        InetAddress[] ias;
        try {
            ias = InetAddress.getAllByName(getOwnerHostName());
            if (ias != null)
                for (InetAddress ia : ias) {
                    System.out.println(ia.getHostAddress());
                }
        } catch (UnknownHostException e) {
            System.err.println("unknown hostname");
        }
    }

    public String getOwnerHostName() {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getOwnerNetworkDeviceName() {
        try {
            NetworkInterface ni = NetworkInterface.getByInetAddress(InetAddress.getLocalHost());
            if (ni != null)
                return ni.getDisplayName();
        } catch (SocketException | UnknownHostException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void getOwnerMac() {
        try {
            NetworkInterface ni = NetworkInterface.getByInetAddress(InetAddress.getLocalHost());
            byte[] hwa = null;
            if (ni != null) {
                hwa = ni.getHardwareAddress();
            } else {
                return;
            }
            if (hwa != null)  {
                StringBuilder mac = new StringBuilder();
                for (byte b : hwa) {
                    mac.append(String.format("%x:", b));
                }
                if (mac.length() > 0 && !ni.isLoopback()) {
                    System.out.println(mac.toString().toLowerCase().substring(0, mac.length() - 1));
                }

            }
        } catch (SocketException | UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public String getOwnerIp() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getInstallationPathJava() {
        return System.getProperty("java.home").toLowerCase();
    }

    public String getJavaVendorName() {
        return System.getProperty("java.vendor").toLowerCase();
    }

    public String getJavaVendorURL() {
        return System.getProperty("java.vendor.url").toLowerCase();
    }

    public String getJavaVersion() {
        return System.getProperty("java.version").toLowerCase();
    }

    public long getTotalMemory() {
        Runtime rt = Runtime.getRuntime();
        return rt.totalMemory();
    }

    public long getFreeMemory() {
        Runtime rt = Runtime.getRuntime();
        return rt.freeMemory();
    }

    public long getUsedMemory() {
        Runtime rt = Runtime.getRuntime();
        return rt.totalMemory() - rt.freeMemory();
    }

    public double getCPULoad() {
        double cpuload = ManagementFactory.getOperatingSystemMXBean().getSystemLoadAverage();
        return (Math.round(cpuload * 100));
    }

    public long getUptime() {
        RuntimeMXBean rb = ManagementFactory.getRuntimeMXBean();
        return rb.getUptime()/1000;
    }

    public double getTotalSaveStorage() {
        File file = new File("/");
        long totalSpace = file.getTotalSpace();
        return (double) (totalSpace/ 8/1024/1024/1024);
    }

    public double getFreeSaveStorage() {
        File file = new File("/");
        long freeSpace = file.getFreeSpace();
        return (double) (freeSpace/ 8/1024/1024/1024);
    }

    public double getUsedSaveStorage() {
        File file = new File("/");
        long usedSpace = file.getUsableSpace();
        return (double) (usedSpace/8/1024/1024/1024);
    }

    public boolean isSecret() {
        return secret;
    }

    public void setSecret(boolean secret) {
        this.secret = secret;
    }

    public String showSecretInformation(){
        if(this.isSecret())
            return "online";
        else
            return "offline";
    }

    public String getTimeForConsole() {
        SimpleDateFormat sdf = new SimpleDateFormat("[HH:mm:ss] ");
        return sdf.format(new Date());
    }

    public String getTimeForFiles() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH_mm_ss");
        return sdf.format(new Date());
    }

    public String getTimeForStats(){
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        return sdf.format(new Date());
    }

    public void generateWindowsRestartScript(String nameOfFinalJar){
        FileAPI startbat = new FileAPI("start.bat");
        startbat.writeInNextFreeLine("@echo off");
        startbat.writeInNextFreeLine("java -Xmx1G -Xms1G -jar " + nameOfFinalJar + " nogui");
        startbat.writeInNextFreeLine("PAUSE");
    }

    public void generateWindowsRestartScript(String nameOfFinalJar, short maxRam){
        FileAPI startbat = new FileAPI("start.bat");
        startbat.writeInNextFreeLine("@echo off");
        startbat.writeInNextFreeLine("java -Xmx" + maxRam + "G -Xms" + maxRam + "G -jar " + nameOfFinalJar + " nogui");
        startbat.writeInNextFreeLine("PAUSE");
    }

    public void generateLinuxRestartScript(String nameOfScreen, String nameOfFinalJar) {
        FileAPI startsh = new FileAPI("start.sh");
        startsh.writeInNextFreeLine("#!/bin/bash");
        startsh.writeInNextFreeLine("");
        startsh.writeInNextFreeLine("BINDIR=$(dirname \"$(readlink -fn \"$0\")\")");
        startsh.writeInNextFreeLine("cd \"$BINDIR\"");
        startsh.writeInNextFreeLine("");
        startsh.writeInNextFreeLine("screen -S \"" + nameOfScreen + "\" bash -c \"sh ./loop.sh\"");

        FileAPI loopsh = new FileAPI("loop.sh");
        loopsh.writeInNextFreeLine("while true");
        loopsh.writeInNextFreeLine("do");
        loopsh.writeInNextFreeLine("\tjava -Xms1G -Xmx1G -jar " + nameOfFinalJar);
        loopsh.writeInNextFreeLine("\techo 'If you don't like to restart this server, you can make STRG + C");
        loopsh.writeInNextFreeLine("\techo \"Rebooting in:\"");
        loopsh.writeInNextFreeLine("\tfor i in 5 4 3 2 1");
        loopsh.writeInNextFreeLine("\tdo");
        loopsh.writeInNextFreeLine("\t\techo \"$i...\"");
        loopsh.writeInNextFreeLine("\t\tsleep 1");
        loopsh.writeInNextFreeLine("\tdone");
        loopsh.writeInNextFreeLine("\techo \"Serverrestart\"");
        loopsh.writeInNextFreeLine("done");
    }

    public void generateLinuxRestartScript(String nameOfScreen, String nameOfFinalJar, short maxRam) {
        FileAPI startsh = new FileAPI("start.sh");
        startsh.writeInNextFreeLine("#!/bin/bash");
        startsh.writeInNextFreeLine("");
        startsh.writeInNextFreeLine("BINDIR=$(dirname \"$(readlink -fn \"$0\")\")");
        startsh.writeInNextFreeLine("cd \"$BINDIR\"");
        startsh.writeInNextFreeLine("");
        startsh.writeInNextFreeLine("screen -S \"" + nameOfScreen + "\" bash -c \"sh ./loop.sh\"");

        FileAPI loopsh = new FileAPI("loop.sh");
        loopsh.writeInNextFreeLine("while true");
        loopsh.writeInNextFreeLine("do");
        loopsh.writeInNextFreeLine("\tjava -Xms" + maxRam + "G -Xmx" + maxRam + "G -jar " + nameOfFinalJar);
        loopsh.writeInNextFreeLine("\techo 'If you don't like to restart this server, you can make STRG + C");
        loopsh.writeInNextFreeLine("\techo \"Rebooting in:\"");
        loopsh.writeInNextFreeLine("\tfor i in 5 4 3 2 1");
        loopsh.writeInNextFreeLine("\tdo");
        loopsh.writeInNextFreeLine("\t\techo \"$i...\"");
        loopsh.writeInNextFreeLine("\t\tsleep 1");
        loopsh.writeInNextFreeLine("\tdone");
        loopsh.writeInNextFreeLine("\techo \"Serverrestart\"");
        loopsh.writeInNextFreeLine("done");
    }

    public static double generateRandom(double min, double max){
        if(max>min)
            return Math.floor((Math.random()*max)+min);
        else if(min> max)
            return Math.floor((Math.random()*min)+max);
        else
            return 0;
    }

}