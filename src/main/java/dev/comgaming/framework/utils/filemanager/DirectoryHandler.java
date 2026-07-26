package dev.comgaming.framework.utils.filemanager;

import dev.comgaming.framework.utils.InternalMethods;
import lombok.Setter;

import java.io.File;

public class DirectoryHandler {

    String USERPATH = "\\home\\" + InternalMethods.getCurrentUser() + "\\";
    private final String ROOTPATH = "\\";

    /*
        TODO:
        chg: backend to programmname
     */
    private final String PROGRAMMPATH = "\\home\\backend\\";
    @Setter
    private String path = "";

    public String generateDirectory(String directoryPath){

        if(InternalMethods.getOS().equals("linux")){
            switch (directoryPath){
                case ROOTPATH:
                    return "use OBJECT.getRootPath();";
                case PROGRAMMPATH:
                    return "use OBJECT.getBackendPath();";
                //case USERPATH:
                //    return "use OBJECT.getUserPath();";
                default:
                    return "use OBJECT.getProgramPath();";
            }
        }else{

        }
        if(!directoryPath.endsWith("\\"))
            return "Only allowed with \\";


        File file = new File(directoryPath);
        if(file.exists())
            return "Directory already exists.";
        else{
            file.mkdir();
            setPath(directoryPath);
            return "Directory " + directoryPath + " created";
        }

    }

    public String getAbsolutePath(){
        return path;
    }

    public String getProgrammPath() {
        return PROGRAMMPATH;
    }

    public String getUserPath() {
        return USERPATH;
    }

    public String getRootPath(){
        return ROOTPATH;
    }

    public String getRelativePath(){
        int length = 0;

        if(path.startsWith(ROOTPATH))
            length = ROOTPATH.length();
        else if (path.startsWith(PROGRAMMPATH))
            length = PROGRAMMPATH.length();
        else
            length = USERPATH.length();


        String newRelativePath = "";
        for(int i = length;  i< path.length(); i++){
            newRelativePath += path.charAt(i);
        }
        return path;
    }

    public String existDirectory(String directoryPath){
        if(ROOTPATH.contains(directoryPath)||PROGRAMMPATH.contains(directoryPath)||USERPATH.contains(directoryPath))
            return "Directory " + directoryPath + " already exists.";
        else
            return "Directory " + directoryPath + " does not exist.";
    }

    public int getLength(){
        return path.length();
    }

    public String getLowerCase(){
        return path.toLowerCase();
    }

    public String getUpperCase(){
        return path.toUpperCase();
    }

    public boolean existsDirectory(String directoryPath){
        return new File(directoryPath).exists();
    }

}
