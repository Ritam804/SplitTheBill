package setergeter;

/**
 * Created by ritam on 29/04/17.
 */

public class Booking_SetterGetter {


    String EventId,EventName,VenueName,VenueAddress,ReferenceId,TableName,EventMonth,EventDate,EventTime,TableId,HostId;
    String HostName,TotalNoOfSeat,NumberOfAvailableSeat,MaximumMount,GroupId;

    public Booking_SetterGetter(String eventid, String eventName, String venueName, String venueAddress, String referenceId, String tableName, String eventMonth, String eventDate , String eventtime, String tableid, String hostid,String hostname,String totalseat,String availableseat,String maximumamount,String groupid) {
        EventId = eventid;
        EventName = eventName;
        VenueName = venueName;
        VenueAddress = venueAddress;
        ReferenceId = referenceId;
        TableName = tableName;
        EventMonth = eventMonth;
        EventDate = eventDate;
        EventTime = eventtime;
        TableId = tableid;
        HostId = hostid;
        HostName = hostname;
        TotalNoOfSeat = totalseat;
        NumberOfAvailableSeat = availableseat;
        MaximumMount = maximumamount;
        GroupId = groupid;
    }

    public String getHostName() {
        return HostName;
    }

    public void setHostName(String hostName) {
        HostName = hostName;
    }

    public String getTotalNoOfSeat() {
        return TotalNoOfSeat;
    }

    public void setTotalNoOfSeat(String totalNoOfSeat) {
        TotalNoOfSeat = totalNoOfSeat;
    }

    public String getNumberOfAvailableSeat() {
        return NumberOfAvailableSeat;
    }

    public void setNumberOfAvailableSeat(String numberOfAvailableSeat) {
        NumberOfAvailableSeat = numberOfAvailableSeat;
    }

    public String getMaximumMount() {
        return MaximumMount;
    }

    public void setMaximumMount(String maximumMount) {
        MaximumMount = maximumMount;
    }

    public String getGroupId() {
        return GroupId;
    }

    public void setGroupId(String groupId) {
        GroupId = groupId;
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

    public String getEventId() {
        return EventId;
    }

    public void setEventId(String eventId) {
        EventId = eventId;
    }

    public String getEventTime() {
        return EventTime;
    }

    public void setEventTime(String eventTime) {
        EventTime = eventTime;
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

    public String getReferenceId() {
        return ReferenceId;
    }

    public void setReferenceId(String referenceId) {
        ReferenceId = referenceId;
    }

    public String getTableName() {
        return TableName;
    }

    public void setTableName(String tableName) {
        TableName = tableName;
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
}
