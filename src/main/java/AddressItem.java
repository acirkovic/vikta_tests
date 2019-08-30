import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Iterator;

public class AddressItem  {

    private String addressNickname;
    private String cityName;
    private int id;
    private String postalCode;
    private String regionName;
    private String street;
    private String streetAdditional;
    private int userId;

    AddressItem(JSONObject json) {
        this.addressNickname = json.getString("addressNickname");
        this.cityName = json.getString("cityName");
        this.id = json.getInt("id");
        this.postalCode = json.getString("postalCode");
        this.regionName = json.getString("regionName");
        this.street = json.getString("street");
        this.streetAdditional = json.getString("streetAdditional");
        this.userId = json.getInt("userId");

    }

    public AddressItem deserializeResponse(JSONObject response) {

        for (Iterator iterator = response.keySet().iterator(); iterator.hasNext();) {
            String key = (String) iterator.next();
            if (response.get(key) instanceof JSONArray) {
                // handle the JSONArrayb
                break;
            }
        }
        AddressItem adItem = new AddressItem(response);
        return adItem;
    }

    @Override
    public String toString() {

        return "{" +
                "addressNickname='" + addressNickname + '\'' +
                ", cityName='" + cityName + '\'' +
                ", id=" + id +
                ", postalCode='" + postalCode + '\'' +
                ", regionName='" + regionName + '\'' +
                ", street='" + street + '\'' +
                ", streetAdditional='" + streetAdditional + '\'' +
                ", userId=" + userId +
                '}';
    }

}
