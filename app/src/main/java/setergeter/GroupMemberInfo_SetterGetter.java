package setergeter;

/**
 * Created by ritam on 09/05/17.
 */

public class GroupMemberInfo_SetterGetter {

    String UserId,UserName,UserImage,Phone,HostStatus;

    public GroupMemberInfo_SetterGetter(String userid, String userName, String userImage, String phone, String hostStatus) {
        UserId = userid;
        UserName = userName;
        UserImage = userImage;
        Phone = phone;
        HostStatus = hostStatus;
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

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getHostStatus() {
        return HostStatus;
    }

    public void setHostStatus(String hostStatus) {
        HostStatus = hostStatus;
    }
}
