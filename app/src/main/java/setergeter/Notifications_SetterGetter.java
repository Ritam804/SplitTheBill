package setergeter;

import org.json.JSONObject;

/**
 * Created by ritam on 23/05/17.
 */

public class Notifications_SetterGetter {

    String Date_Added,NotificationMessage,Type, Description, EventId, Image, OfferAmount, OfferMale, OfferFemale, EventName,EventStartTime,EventEndTime, EventDay, EventMonth, EventYear, RequesterId, RequestedAmount, FollowerId, QB_GroupId,QB_SenderId,VenueId,VenueName;
    int ExpireStatus;
    JSONObject TableObject;


    public Notifications_SetterGetter(String date_Added, String notificationMessage, String type, String description, String eventid, int expirestatus, JSONObject tableobjects, String image, String offeramount, String offermale, String offerfemale, String eventname, String eventstarttime, String eventendtime, String eventday, String eventmonth, String eventYear, String requsterid, String requestedamount, String followerId,String qb_groupid,String qb_senderid,String venueid,String venuename) {
        Date_Added = date_Added;
        NotificationMessage = notificationMessage;
        Type = type;
        Description = description;
        EventId = eventid;
        ExpireStatus = expirestatus;
        TableObject = tableobjects;
        Image = image;
        OfferAmount = offeramount;
        OfferMale = offermale;
        OfferFemale = offerfemale;
        EventName = eventname;
        EventStartTime = eventstarttime;
        EventEndTime = eventendtime;
        EventDay = eventday;
        EventMonth = eventmonth;
        EventYear = eventYear;
        RequesterId = requsterid;
        RequestedAmount = requestedamount;
        FollowerId = followerId;
        QB_GroupId = qb_groupid;
        QB_SenderId = qb_senderid;
        VenueId = venueid;
        VenueName = venuename;
    }

    public String getVenueName() {
        return VenueName;
    }

    public void setVenueName(String venueName) {
        VenueName = venueName;
    }

    public String getVenueId() {
        return VenueId;
    }

    public void setVenueId(String venueId) {
        VenueId = venueId;
    }

    public String getQB_GroupId() {
        return QB_GroupId;
    }

    public void setQB_GroupId(String QB_GroupId) {
        this.QB_GroupId = QB_GroupId;
    }

    public String getQB_SenderId() {
        return QB_SenderId;
    }

    public void setQB_SenderId(String QB_SenderId) {
        this.QB_SenderId = QB_SenderId;
    }

    public String getFollowerId() {
        return FollowerId;
    }

    public void setFollowerId(String followerId) {
        FollowerId = followerId;
    }

    public String getRequestedAmount() {
        return RequestedAmount;
    }

    public void setRequestedAmount(String requestedAmount) {
        RequestedAmount = requestedAmount;
    }

    public String getRequesterId() {
        return RequesterId;
    }

    public void setRequesterId(String requesterId) {
        RequesterId = requesterId;
    }

    public String getEventDay() {
        return EventDay;
    }

    public void setEventDay(String eventDay) {
        EventDay = eventDay;
    }

    public String getEventMonth() {
        return EventMonth;
    }

    public void setEventMonth(String eventMonth) {
        EventMonth = eventMonth;
    }

    public String getEventYear() {
        return EventYear;
    }

    public void setEventYear(String eventYear) {
        EventYear = eventYear;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getOfferAmount() {
        return OfferAmount;
    }

    public void setOfferAmount(String offerAmount) {
        OfferAmount = offerAmount;
    }

    public String getOfferMale() {
        return OfferMale;
    }

    public void setOfferMale(String offerMale) {
        OfferMale = offerMale;
    }

    public String getOfferFemale() {
        return OfferFemale;
    }

    public void setOfferFemale(String offerFemale) {
        OfferFemale = offerFemale;
    }

    public String getEventName() {
        return EventName;
    }

    public void setEventName(String eventName) {
        EventName = eventName;
    }

    public String getEventStartTime() {
        return EventStartTime;
    }

    public void setEventStartTime(String eventStartTime) {
        EventStartTime = eventStartTime;
    }

    public String getEventEndTime() {
        return EventEndTime;
    }

    public void setEventEndTime(String eventEndTime) {
        EventEndTime = eventEndTime;
    }

    public JSONObject getTableObject() {
        return TableObject;
    }

    public void setTableObject(JSONObject tableObject) {
        TableObject = tableObject;
    }

    public String getEventId() {
        return EventId;
    }

    public void setEventId(String eventId) {
        EventId = eventId;
    }

    public int getExpireStatus() {
        return ExpireStatus;
    }

    public void setExpireStatus(int expireStatus) {
        ExpireStatus = expireStatus;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getDate_Added() {
        return Date_Added;
    }

    public void setDate_Added(String date_Added) {
        Date_Added = date_Added;
    }

    public String getNotificationMessage() {
        return NotificationMessage;
    }

    public void setNotificationMessage(String notificationMessage) {
        NotificationMessage = notificationMessage;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }
}
