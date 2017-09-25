package setergeter;

/**
 * Created by ritam on 04/07/17.
 */

public class FetchingChat_SetterGetter {

    String SenderId,SenderName,ToDo,Message,DateTime;

    public FetchingChat_SetterGetter(String senderId, String senderName, String toDo, String message, String dateTime) {
        SenderId = senderId;
        SenderName = senderName;
        ToDo = toDo;
        Message = message;
        DateTime = dateTime;
    }

    public String getSenderId() {
        return SenderId;
    }

    public void setSenderId(String senderId) {
        SenderId = senderId;
    }

    public String getSenderName() {
        return SenderName;
    }

    public void setSenderName(String senderName) {
        SenderName = senderName;
    }

    public String getToDo() {
        return ToDo;
    }

    public void setToDo(String toDo) {
        ToDo = toDo;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getDateTime() {
        return DateTime;
    }

    public void setDateTime(String dateTime) {
        DateTime = dateTime;
    }
}
