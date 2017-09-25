package setergeter;

/**
 * Created by ritam on 12/08/17.
 */

public class Phone_Selected_SetterGetter {

    String Name,Number,photo_uri,stb_status,ListStatus;
    boolean checkStatus;


    public Phone_Selected_SetterGetter(String name, String number, String photo_uri, String stb_status, boolean checkStatus,String listStatus) {
        Name = name;
        Number = number;
        this.photo_uri = photo_uri;
        this.stb_status = stb_status;
        this.checkStatus = checkStatus;
        ListStatus = listStatus;
    }

    public String getListStatus() {
        return ListStatus;
    }

    public void setListStatus(String listStatus) {
        ListStatus = listStatus;
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
