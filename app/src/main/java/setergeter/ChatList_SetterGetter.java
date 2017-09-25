package setergeter;

import java.util.List;

/**
 * Created by ritam on 04/07/17.
 */

public class ChatList_SetterGetter {

    String EventId,GroupId,EventName,EventImage,VenueName,Address,LastMessage,LastMessageSentTime;
    List<Integer> OccupantIds;
    int UnreadMessageCount;

    public ChatList_SetterGetter(String eventId, String groupId, String eventName, String eventImage, String venueName, String address, List<Integer> occupantIds, String lastmessage,int unreadmessagecount,String lastmessagesenttime) {
        EventId = eventId;
        GroupId = groupId;
        EventName = eventName;
        EventImage = eventImage;
        VenueName = venueName;
        Address = address;
        OccupantIds = occupantIds;
        LastMessage = lastmessage;
        UnreadMessageCount = unreadmessagecount;
        LastMessageSentTime = lastmessagesenttime;
    }

    public String getLastMessageSentTime() {
        return LastMessageSentTime;
    }

    public void setLastMessageSentTime(String lastMessageSentTime) {
        LastMessageSentTime = lastMessageSentTime;
    }

    public int getUnreadMessageCount() {
        return UnreadMessageCount;
    }

    public void setUnreadMessageCount(int unreadMessageCount) {
        UnreadMessageCount = unreadMessageCount;
    }

    public String getLastMessage() {
        return LastMessage;
    }

    public void setLastMessage(String lastMessage) {
        LastMessage = lastMessage;
    }

    public List<Integer> getOccupantIds() {
        return OccupantIds;
    }

    public void setOccupantIds(List<Integer> occupantIds) {
        OccupantIds = occupantIds;
    }

    public String getEventId() {
        return EventId;
    }

    public void setEventId(String eventId) {
        EventId = eventId;
    }

    public String getGroupId() {
        return GroupId;
    }

    public void setGroupId(String groupId) {
        GroupId = groupId;
    }

    public String getEventName() {
        return EventName;
    }

    public void setEventName(String eventName) {
        EventName = eventName;
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

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }
}
