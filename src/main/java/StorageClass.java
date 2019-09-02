import java.util.HashMap;
import java.util.Map;

public class StorageClass {
    /**
     * Class used to store body responses
     */
    private Map<String, Object> storeJson;

    public StorageClass() {
        storeJson = new HashMap<>();
    }

    public void addToStorage(String key, Object o) {
        storeJson.put(key, o);
    }

    public Object getFromStorage(String key) {
        return storeJson.get(key);
    }

}
