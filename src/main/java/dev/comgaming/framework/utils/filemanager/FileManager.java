package dev.comgaming.framework.utils.filemanager;

import dev.comgaming.framework.Framework;
import lombok.Getter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class FileManager {

    private final File file;
    @Getter
    public static int numberOfFileManagers= 0;

    public FileManager(String filePath) {
        file = new File(filePath);
        numberOfFileManagers++;
    }

    public boolean exists() {
        return this.file.exists();
    }

    public void createFile() {
        try {
            Framework.getLogger().info("file", String.valueOf(this.file.createNewFile()));
        } catch (IOException e) {
            Framework.getLogger().warn("ioexception",String.valueOf(new RuntimeException(e)));
        }
    }

    public void renameFile(String newName) {
        Framework.getLogger().info("file",String.valueOf(this.file.renameTo(new File(newName))));
    }

    public void writeInNextFreeLine(String text) {
        try {
            FileWriter writer = new FileWriter(this.file, true);
            writer.write(text + "\n");
            writer.close();
        } catch (IOException e) {
            Framework.getLogger().warn("ioexception",String.valueOf(new RuntimeException(e)));
        }
    }

    public ArrayList<String> readAll() {
        ArrayList<String> lines = new ArrayList<>();
        try {
            Scanner scn = new Scanner(this.file);
            while (scn.hasNext()) {
                lines.add(scn.nextLine());
            }
            scn.close();
        } catch (IOException e) {
            Framework.getLogger().warn("ioexception",String.valueOf(new RuntimeException(e)));
        }
        return lines;
    }
}
