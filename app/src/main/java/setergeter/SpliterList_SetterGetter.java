package setergeter;

/**
 * Created by ritam on 21/07/17.
 */

public class SpliterList_SetterGetter {

    String UserId,Rating,Image,Name,Splits,Splitters;
    int StatusCheck;

    public SpliterList_SetterGetter(String userId, String rating, String image, String name, String splits, String splitters, int statusCheck) {
        UserId = userId;
        Rating = rating;
        Image = image;
        Name = name;
        Splits = splits;
        Splitters = splitters;
        StatusCheck = statusCheck;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getRating() {
        return Rating;
    }

    public void setRating(String rating) {
        Rating = rating;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getSplits() {
        return Splits;
    }

    public void setSplits(String splits) {
        Splits = splits;
    }

    public String getSplitters() {
        return Splitters;
    }

    public void setSplitters(String splitters) {
        Splitters = splitters;
    }

    public int getStatusCheck() {
        return StatusCheck;
    }

    public void setStatusCheck(int statusCheck) {
        StatusCheck = statusCheck;
    }
}
