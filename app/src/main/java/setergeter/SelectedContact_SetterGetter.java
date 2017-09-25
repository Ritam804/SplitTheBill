package setergeter;

/**
 * Created by ritam on 13/05/17.
 */

public class SelectedContact_SetterGetter {

    String Name,Number,photo_uri;
    boolean checkStatus;
    int SelectedPosition;
    String Pupose;

    public SelectedContact_SetterGetter(String name, String number, String photo_uri, boolean checkStatus, int selectedPosition, String purpose) {
        Name = name;
        Number = number;
        this.photo_uri = photo_uri;
        this.checkStatus = checkStatus;
        SelectedPosition = selectedPosition;
        Pupose = purpose;
    }

    public String getPupose() {
        return Pupose;
    }

    public void setPupose(String pupose) {
        Pupose = pupose;
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

    public boolean isCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(boolean checkStatus) {
        this.checkStatus = checkStatus;
    }

    public int getSelectedPosition() {
        return SelectedPosition;
    }

    public void setSelectedPosition(int selectedPosition) {
        SelectedPosition = selectedPosition;
    }
}
