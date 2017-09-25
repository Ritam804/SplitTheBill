package setergeter;

/**
 * Created by ritam on 12/05/17.
 */

public class CardDetails_SetterGetter {

    boolean Selectable;

    String CardType,CardHolderName,CardNumber,CardId;


    public CardDetails_SetterGetter(boolean selectable, String cardType, String cardHolderName, String cardNumber, String cardId) {
        Selectable = selectable;
        CardType = cardType;
        CardHolderName = cardHolderName;
        CardNumber = cardNumber;
        CardId = cardId;
    }

    public String getCardId() {
        return CardId;
    }

    public void setCardId(String cardId) {
        CardId = cardId;
    }

    public boolean isSelectable() {
        return Selectable;
    }

    public void setSelectable(boolean selectable) {
        Selectable = selectable;
    }

    public String getCardType() {
        return CardType;
    }

    public void setCardType(String cardType) {
        CardType = cardType;
    }

    public String getCardHolderName() {
        return CardHolderName;
    }

    public void setCardHolderName(String cardHolderName) {
        CardHolderName = cardHolderName;
    }

    public String getCardNumber() {
        return CardNumber;
    }

    public void setCardNumber(String cardNumber) {
        CardNumber = cardNumber;
    }
}
