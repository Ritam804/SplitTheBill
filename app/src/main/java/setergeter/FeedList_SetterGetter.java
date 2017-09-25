package setergeter;

/**
 * Created by ritam on 19/07/17.
 */

public class FeedList_SetterGetter {

    String UserId,HostId,BookedDate,Payment,FolloName,FollowImage,TableName,EventName,EventCoverImage,SplitsCount,VenueNAme,EventId,TableId,ExpireStatus;
    int Rating;

    public FeedList_SetterGetter(String userId, String hostId, String bookedDate, String payment, String folloName, String followImage, String tableName, String eventName, String eventCoverImage, int rating, String splitscount, String venueNAme,String eventId,String tableid,String expireStatus) {
        UserId = userId;
        HostId = hostId;
        BookedDate = bookedDate;
        Payment = payment;
        FolloName = folloName;
        FollowImage = followImage;
        TableName = tableName;
        EventName = eventName;
        EventCoverImage = eventCoverImage;
        Rating = rating;
        SplitsCount = splitscount;
        VenueNAme = venueNAme;
        EventId = eventId;
        TableId = tableid;
        ExpireStatus = expireStatus;
    }

    public String getExpireStatus() {
        return ExpireStatus;
    }

    public void setExpireStatus(String expireStatus) {
        ExpireStatus = expireStatus;
    }

    public String getTableId() {
        return TableId;
    }

    public void setTableId(String tableId) {
        TableId = tableId;
    }

    public String getEventId() {
        return EventId;
    }

    public void setEventId(String eventId) {
        EventId = eventId;
    }

    public String getSplitsCount() {
        return SplitsCount;
    }

    public void setSplitsCount(String splitsCount) {
        SplitsCount = splitsCount;
    }

    public String getVenueNAme() {
        return VenueNAme;
    }

    public void setVenueNAme(String venueNAme) {
        VenueNAme = venueNAme;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getHostId() {
        return HostId;
    }

    public void setHostId(String hostId) {
        HostId = hostId;
    }

    public String getBookedDate() {
        return BookedDate;
    }

    public void setBookedDate(String bookedDate) {
        BookedDate = bookedDate;
    }

    public String getPayment() {
        return Payment;
    }

    public void setPayment(String payment) {
        Payment = payment;
    }

    public String getFolloName() {
        return FolloName;
    }

    public void setFolloName(String folloName) {
        FolloName = folloName;
    }

    public String getFollowImage() {
        return FollowImage;
    }

    public void setFollowImage(String followImage) {
        FollowImage = followImage;
    }

    public String getTableName() {
        return TableName;
    }

    public void setTableName(String tableName) {
        TableName = tableName;
    }

    public String getEventName() {
        return EventName;
    }

    public void setEventName(String eventName) {
        EventName = eventName;
    }

    public String getEventCoverImage() {
        return EventCoverImage;
    }

    public void setEventCoverImage(String eventCoverImage) {
        EventCoverImage = eventCoverImage;
    }

    public int getRating() {
        return Rating;
    }

    public void setRating(int rating) {
        Rating = rating;
    }
}
