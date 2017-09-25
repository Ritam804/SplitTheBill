package setergeter;

/**
 * Created by ritam on 16/03/17.
 */

public class SetterGetter_CountryDetails {

    String Id,Name,NameCode;

    public SetterGetter_CountryDetails(String id, String name, String nameCode) {
        Id = id;
        Name = name;
        NameCode = nameCode;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getNameCode() {
        return NameCode;
    }

    public void setNameCode(String nameCode) {
        NameCode = nameCode;
    }
}
