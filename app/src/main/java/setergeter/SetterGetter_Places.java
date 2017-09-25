package setergeter;

/**
 * Created by ritam on 16/03/17.
 */

public class SetterGetter_Places {

    String CityName,CountryName,FullAddress;
    long Lat,Long;

    public SetterGetter_Places(String cityName, String countryName, String fullAddress, long lat, long aLong) {
        CityName = cityName;
        CountryName = countryName;
        FullAddress = fullAddress;
        Lat = lat;
        Long = aLong;
    }

    public String getCityName() {
        return CityName;
    }

    public void setCityName(String cityName) {
        CityName = cityName;
    }

    public String getCountryName() {
        return CountryName;
    }

    public void setCountryName(String countryName) {
        CountryName = countryName;
    }

    public String getFullAddress() {
        return FullAddress;
    }

    public void setFullAddress(String fullAddress) {
        FullAddress = fullAddress;
    }

    public long getLat() {
        return Lat;
    }

    public void setLat(long lat) {
        Lat = lat;
    }

    public long getLong() {
        return Long;
    }

    public void setLong(long aLong) {
        Long = aLong;
    }
}
