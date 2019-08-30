import org.json.JSONObject;

import java.util.Map;


public class JSONClass extends JSONObject {

    private final String[] addressKeys = {
            "addressNickname",
            "cityName",
            "postalCode",
            "regionName",
            "street",
            "streetAdditional",
            "userId"
    };

    public void fillJsonBodyAddress(String data) {
        String[] list = data.split(",");
        int k = list.length;
        for (int i=0; i<k; i++) {
            if (i == k-1)
                put(addressKeys[i], Integer.parseInt(list[i]));
            else
                put(addressKeys[i], list[i]);
        }
    }
}
