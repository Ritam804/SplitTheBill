package setergeter;

/**
 * Created by ritam on 24/07/17.
 */

public class Splits_SplittersList_SetterGetter {

    String SplitterId,SplitterPayment,SplitterName,SpltterMale,SplitterFemale,SplitterRating,SpltterImage,HostId;

    public Splits_SplittersList_SetterGetter(String splitterId, String splitterPayment, String splitterName, String spltterMale, String splitterFemale, String splitterRating, String spltterImage, String hostId) {
        SplitterId = splitterId;
        SplitterPayment = splitterPayment;
        SplitterName = splitterName;
        SpltterMale = spltterMale;
        SplitterFemale = splitterFemale;
        SplitterRating = splitterRating;
        SpltterImage = spltterImage;
        HostId = hostId;
    }

    public String getSplitterId() {
        return SplitterId;
    }

    public void setSplitterId(String splitterId) {
        SplitterId = splitterId;
    }

    public String getSplitterPayment() {
        return SplitterPayment;
    }

    public void setSplitterPayment(String splitterPayment) {
        SplitterPayment = splitterPayment;
    }

    public String getSplitterName() {
        return SplitterName;
    }

    public void setSplitterName(String splitterName) {
        SplitterName = splitterName;
    }

    public String getSpltterMale() {
        return SpltterMale;
    }

    public void setSpltterMale(String spltterMale) {
        SpltterMale = spltterMale;
    }

    public String getSplitterFemale() {
        return SplitterFemale;
    }

    public void setSplitterFemale(String splitterFemale) {
        SplitterFemale = splitterFemale;
    }

    public String getSplitterRating() {
        return SplitterRating;
    }

    public void setSplitterRating(String splitterRating) {
        SplitterRating = splitterRating;
    }

    public String getSpltterImage() {
        return SpltterImage;
    }

    public void setSpltterImage(String spltterImage) {
        SpltterImage = spltterImage;
    }

    public String getHostId() {
        return HostId;
    }

    public void setHostId(String hostId) {
        HostId = hostId;
    }
}
