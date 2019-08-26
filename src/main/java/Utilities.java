
public class Utilities {
    Utilities() {}

    public static String extractIdFromResponse(String response) {
        return response.substring(response.indexOf(":") +1, response.indexOf(","));
    }
}
