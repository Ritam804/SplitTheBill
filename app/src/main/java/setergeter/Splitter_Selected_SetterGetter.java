package setergeter;

/**
 * Created by ritam on 12/08/17.
 */

public class Splitter_Selected_SetterGetter {

    String Name,Number,photo_uri,stb_status;
    boolean checkStatus;

    public Splitter_Selected_SetterGetter(String name, String number, String photo_uri, String stb_status, boolean checkStatus) {
        Name = name;
        Number = number;
        this.photo_uri = photo_uri;
        this.stb_status = stb_status;
        this.checkStatus = checkStatus;
    }


    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getNumber() {
        return Number;
    }

    public void setNumber(String number) {
        Number = number;
    }

    public String getPhoto_uri() {
        return photo_uri;
    }

    public void setPhoto_uri(String photo_uri) {
        this.photo_uri = photo_uri;
    }

    public String getStb_status() {
        return stb_status;
    }

    public void setStb_status(String stb_status) {
        this.stb_status = stb_status;
    }

    public boolean isCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(boolean checkStatus) {
        this.checkStatus = checkStatus;
    }
}
