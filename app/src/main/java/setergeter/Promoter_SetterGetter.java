package setergeter;

/**
 * Created by ritam on 22/09/17.
 */

public class Promoter_SetterGetter {

    String PromoterId,PromoterName;

    public Promoter_SetterGetter(String promoterId, String promoterName) {
        PromoterId = promoterId;
        PromoterName = promoterName;
    }

    public String getPromoterId() {
        return PromoterId;
    }

    public void setPromoterId(String promoterId) {
        PromoterId = promoterId;
    }

    public String getPromoterName() {
        return PromoterName;
    }

    public void setPromoterName(String promoterName) {
        PromoterName = promoterName;
    }
}
