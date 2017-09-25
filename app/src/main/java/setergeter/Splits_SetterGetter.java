package setergeter;

import org.json.JSONArray;

/**
 * Created by ritam on 24/07/17.
 */

public class Splits_SetterGetter {

    String EventId,EventRating,EventMonth,EventDate,EventTime,EventEndTime,EventFullDate,EventName,VenueName,VenueAddress,Cost,TableName,TableId;

    String HostId,HostPayment,HostName,HostMale,HostFemale,HostRating,HostImage;

    JSONArray AllSplitters;

    public Splits_SetterGetter(String eventId, String eventRating, String eventMonth, String eventDate, String eventTime, String eventEndTime, String eventFullDate, String eventName, String venueName, String venueAddress, String cost, String tableName, String tableId, String hostId, String hostPayment, String hostName, String hostMale, String hostFemale, String hostRating, String hostImage, JSONArray allSplitters) {
        EventId = eventId;
        EventRating = eventRating;
        EventMonth = eventMonth;
        EventDate = eventDate;
        EventTime = eventTime;
        EventEndTime = eventEndTime;
        EventFullDate = eventFullDate;
        EventName = eventName;
        VenueName = venueName;
        VenueAddress = venueAddress;
        Cost = cost;
        TableName = tableName;
        TableId = tableId;
        HostId = hostId;
        HostPayment = hostPayment;
        HostName = hostName;
        HostMale = hostMale;
        HostFemale = hostFemale;
        HostRating = hostRating;
        HostImage = hostImage;
        AllSplitters = allSplitters;
    }

    public String getEventId() {
        return EventId;
    }

    public void setEventId(String eventId) {
        EventId = eventId;
    }

    public String getEventRating() {
        return EventRating;
    }

    public void setEventRating(String eventRating) {
        EventRating = eventRating;
    }

    public String getEventMonth() {
        return EventMonth;
    }

    public void setEventMonth(String eventMonth) {
        EventMonth = eventMonth;
    }

    public String getEventDate() {
        return EventDate;
    }

    public void setEventDate(String eventDate) {
        EventDate = eventDate;
    }

    public String getEventTime() {
        return EventTime;
    }

    public void setEventTime(String eventTime) {
        EventTime = eventTime;
    }

    public String getEventEndTime() {
        return EventEndTime;
    }

    public void setEventEndTime(String eventEndTime) {
        EventEndTime = eventEndTime;
    }

    public String getEventFullDate() {
        return EventFullDate;
    }

    public void setEventFullDate(String eventFullDate) {
        EventFullDate = eventFullDate;
    }

    public String getEventName() {
        return EventName;
    }

    public void setEventName(String eventName) {
        EventName = eventName;
    }

    public String getVenueName() {
        return VenueName;
    }

    public void setVenueName(String venueName) {
        VenueName = venueName;
    }

    public String getVenueAddress() {
        return VenueAddress;
    }

    public void setVenueAddress(String venueAddress) {
        VenueAddress = venueAddress;
    }

    public String getCost() {
        return Cost;
    }

    public void setCost(String cost) {
        Cost = cost;
    }

    public String getTableName() {
        return TableName;
    }

    public void setTableName(String tableName) {
        TableName = tableName;
    }

    public String getTableId() {
        return TableId;
    }

    public void setTableId(String tableId) {
        TableId = tableId;
    }

    public String getHostId() {
        return HostId;
    }

    public void setHostId(String hostId) {
        HostId = hostId;
    }

    public String getHostPayment() {
        return HostPayment;
    }

    public void setHostPayment(String hostPayment) {
        HostPayment = hostPayment;
    }

    public String getHostName() {
        return HostName;
    }

    public void setHostName(String hostName) {
        HostName = hostName;
    }

    public String getHostMale() {
        return HostMale;
    }

    public void setHostMale(String hostMale) {
        HostMale = hostMale;
    }

    public String getHostFemale() {
        return HostFemale;
    }

    public void setHostFemale(String hostFemale) {
        HostFemale = hostFemale;
    }

    public String getHostRating() {
        return HostRating;
    }

    public void setHostRating(String hostRating) {
        HostRating = hostRating;
    }

    public String getHostImage() {
        return HostImage;
    }

    public void setHostImage(String hostImage) {
        HostImage = hostImage;
    }

    public JSONArray getAllSplitters() {
        return AllSplitters;
    }

    public void setAllSplitters(JSONArray allSplitters) {
        AllSplitters = allSplitters;
    }
}
