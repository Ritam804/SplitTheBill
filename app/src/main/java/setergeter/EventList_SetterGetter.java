package setergeter;

/**
 * Created by ritam on 29/04/17.
 */

public class EventList_SetterGetter {

    String EventId,EventName,EventDate,EventImage,VenueName,VenueRating,Distance,Address;


    public EventList_SetterGetter(String eventId, String eventName, String eventDate, String eventImage, String venueName, String venueRating, String distance, String address) {
        EventId = eventId;
        EventName = eventName;
        EventDate = eventDate;
        EventImage = eventImage;
        VenueName = venueName;
        VenueRating = venueRating;
        Distance = distance;
        Address = address;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getEventId() {
        return EventId;
    }

    public void setEventId(String eventId) {
        EventId = eventId;
    }

    public String getEventName() {
        return EventName;
    }

    public void setEventName(String eventName) {
        EventName = eventName;
    }

    public String getEventDate() {
        return EventDate;
    }

    public void setEventDate(String eventDate) {
        EventDate = eventDate;
    }

    public String getEventImage() {
        return EventImage;
    }

    public void setEventImage(String eventImage) {
        EventImage = eventImage;
    }

    public String getVenueName() {
        return VenueName;
    }

    public void setVenueName(String venueName) {
        VenueName = venueName;
    }

    public String getVenueRating() {
        return VenueRating;
    }

    public void setVenueRating(String venueRating) {
        VenueRating = venueRating;
    }

    public String getDistance() {
        return Distance;
    }

    public void setDistance(String distance) {
        Distance = distance;
    }
}
