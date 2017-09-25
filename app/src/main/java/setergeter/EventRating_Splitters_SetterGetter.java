package setergeter;

/**
 * Created by ritam on 19/07/17.
 */

public class EventRating_Splitters_SetterGetter {

    String UserId,UserName,UserImage;
    int UserRating;

    public EventRating_Splitters_SetterGetter(String userId, String userName, String userImage, int userRating) {
        UserId = userId;
        UserName = userName;
        UserImage = userImage;
        UserRating = userRating;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getUserImage() {
        return UserImage;
    }

    public void setUserImage(String userImage) {
        UserImage = userImage;
    }

    public int getUserRating() {
        return UserRating;
    }

    public void setUserRating(int userRating) {
        UserRating = userRating;
    }
}
