package dev.comgaming.framework.utils;

import de.comgaming.backend.Main;
import lombok.Getter;

import java.io.File;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Getter
public class InternalMethods {

    @Getter
    public static boolean maintenance = false;

    public static String getOS() {
        return System.getProperty("os.name").toLowerCase();
    }

    public static String getOSArch() {
        return System.getProperty("os.arch").toLowerCase();
    }

    public static String getOSVersion() {
        return System.getProperty("os.version").toLowerCase();
    }

    public static String getOwnerHostName() {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            Main.getLogger().logError(String.valueOf(new UnknownHostException()));
            return null;
        }
    }

    public static String getOwnerNetworkDeviceName() {
        try {
            NetworkInterface ni = NetworkInterface.getByInetAddress(InetAddress.getLocalHost());
            if (ni != null) {
                return ni.getDisplayName();
            }
        } catch (SocketException | UnknownHostException e) {
            Main.getLogger().logError(String.valueOf(new UnknownHostException()));
        }
        return null;
    }

    public static String getOwnerIp() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            Main.getLogger().logError(String.valueOf(new UnknownHostException()));
            return null;
        }
    }

    public static String getInstallationPathJava() {
        return System.getProperty("java.home");
    }

    public static String getJavaVendorName() {
        return System.getProperty("java.vendor");
    }

    public static String getJavaVersion() {
        return System.getProperty("java.version");
    }

    public static long getTotalMemory() {
        Runtime rt = Runtime.getRuntime();
        return rt.totalMemory();
    }

    public static long getFreeMemory() {
        Runtime rt = Runtime.getRuntime();
        return rt.freeMemory();
    }

    public static long getUsedMemory() {
        Runtime rt = Runtime.getRuntime();
        return rt.totalMemory() - rt.freeMemory();
    }

    public static long getUptime() {
        RuntimeMXBean rb = ManagementFactory.getRuntimeMXBean();
        return rb.getUptime() / 1000;
    }

    public static  double getTotalStorageInGB() {
        File file = new File("/");
        long totalSpace = file.getTotalSpace();
        return (double) totalSpace / (1024 * 1024 * 1024);
    }

    public static double getFreeStorageInGB() {
        File file = new File("/");
        long freeSpace = file.getFreeSpace();
        return (double) freeSpace / (1024 * 1024 * 1024);
    }

    public static  double getUsedStorageInGB() {
        File file = new File("/");
        long usableSpace = file.getUsableSpace();
        return (double) usableSpace / (1024 * 1024 * 1024);
    }

    public static String getTimeForStats() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        return sdf.format(new Date());
    }

    public static String getTimeForConsole() {
        SimpleDateFormat sdf = new SimpleDateFormat("[HH:mm:ss]");
        return sdf.format(new Date());
    }

    public static String createPassword(int lengthKey, String smallLetters, String bigLetters, String specialSigns) {
        String characters = smallLetters.toLowerCase() + bigLetters.toUpperCase() + specialSigns;
        StringBuilder password = new StringBuilder();
        for (int i = 0; i < lengthKey; i++) {
            int random = (int) (Math.random() * characters.length());
            password.append(characters.charAt(random));
        }
        return password.toString();
    }

    public static void toggleMaintenance(){
        if(maintenance){
            maintenance = false;
            Main.getLogger().logInfo("Maintenance is now disabled.");
        }else{
            maintenance = true;
            Main.getLogger().logInfo("Maintenance is now enabled.");
        }
    }

    public static void generateRestartScript(String nameOfFinalJar){
        FileManager startsh = new FileManager("start.sh");
        if(!startsh.exists()) {
            startsh.writeInNextFreeLine("#!/bin/bash");
            startsh.writeInNextFreeLine("");
            startsh.writeInNextFreeLine("BINDIR=$(dirname \"$(readlink -fn \"$0\")\")");
            startsh.writeInNextFreeLine("cd \"$BINDIR\"");
            startsh.writeInNextFreeLine("");
            startsh.writeInNextFreeLine("screen -S " + nameOfFinalJar+".jar bash -c \"sh ./loop.sh\"");
            Main.getLogger().logInfo(startsh.getFile().getName() + " created");
        }

        FileManager loopsh = new FileManager("loop.sh");
        if(!loopsh.exists()) {
            loopsh.writeInNextFreeLine("while true");
            loopsh.writeInNextFreeLine("do");
            loopsh.writeInNextFreeLine("\tjava -Xms1G -Xmx1G -jar " + nameOfFinalJar + ".jar");
            loopsh.writeInNextFreeLine("\techo 'If you don't like to restart this server, you can make STRG + C");
            loopsh.writeInNextFreeLine("\techo \"Rebooting in:\"");
            loopsh.writeInNextFreeLine("\tfor i in 5 4 3 2 1");
            loopsh.writeInNextFreeLine("\tdo");
            loopsh.writeInNextFreeLine("\t\techo \"$i...\"");
            loopsh.writeInNextFreeLine("\t\tsleep 1");
            loopsh.writeInNextFreeLine("\tdone");
            loopsh.writeInNextFreeLine("\techo \"Serverrestart\"");
            loopsh.writeInNextFreeLine("done");
            Main.getLogger().logInfo(loopsh.getFile().getName() + " created");
        }

    }

}