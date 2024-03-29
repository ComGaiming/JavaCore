package dev.syster42.framework.product;

import dev.syster42.framework.utils.FileAPI;

public class LizenzAPI {

    String licencekey;
    FileAPI fileapi = new FileAPI("licenekeys.txt");

    public LizenzAPI(String savetype){
        if(savetype.equals("file")){
            if(!fileapi.exists()){
                fileapi.createFile();
            }
        }
    }

    public String createKey(int lengthKey, int sign, String smallLetters, String bigLetters, String specialssigns, String product){
        String[] key = new String[lengthKey];
        String whole = smallLetters.toLowerCase() + bigLetters.toUpperCase() + specialssigns;
        this.setLicencekey("");
        System.out.print("Erstellter Key: ");
        for (int i = 0; i < lengthKey; i++) {
            int rest = i%sign;
            if (rest == 0) {
                if (i != 0) {
                    key[i] = "-";
                } else {
                    int random = (int) Math.floor((Math.random() * (whole.length() - 1) + 1));
                    key[i] = String.valueOf(whole.charAt(random));
                }
            } else {
                int random = (int) Math.floor((Math.random() * (whole.length() - 1) + 1));
                key[i] = String.valueOf(whole.charAt(random));
            }
            this.licencekey = this.licencekey + key[i];
            System.out.print(key[i]);
        }
        return licencekey;
    }

    public void setLicencekey(String licencekey) {
        this.licencekey = licencekey;
    }

    public String getLicenceKey(){
        return this.licencekey;
    }

    public void changeLicenceKey(){}

    public void deleteLicenceKey(){}

    public void saveLicenceKey(String product, String licencekey){
        fileapi.writeInNextFreeLine(product + ": " + licencekey);
    }

}
