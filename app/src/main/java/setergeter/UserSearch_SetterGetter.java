package setergeter;

/**
 * Created by ritam on 29/07/17.
 */

public class UserSearch_SetterGetter {

    String UserId,Name,Image,Rating;

    int FollowStatus;

    public UserSearch_SetterGetter(String userId, String name, String image, String rating, int followStatus) {
        UserId = userId;
        Name = name;
        Image = image;
        Rating = rating;
        FollowStatus = followStatus;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getRating() {
        return Rating;
    }

    public void setRating(String rating) {
        Rating = rating;
    }

    public int getFollowStatus() {
        return FollowStatus;
    }

    public void setFollowStatus(int followStatus) {
        FollowStatus = followStatus;
    }
}
