package dev.syster42.framework.api;

public class LizenzAPI {

    String licencekey;

    public LizenzAPI(){}

    public void createKey(int lengthKey, int sign, String smallLetters, String bigLetters, String specialssigns){
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
    }

    public void setLicencekey(String licencekey) {
        this.licencekey = licencekey;
    }

    public String getLicenceKey(){
        return this.licencekey;
    }

    public void changeLicenceKey(){}

    public void deleteLicenceKey(){}

}
