

public class AddressItem  {

    private String addressNickname;
    private String cityName;
    private int id;
    private String postalCode;
    private String regionName;
    private String street;
    private String streetAdditional;
    private int userId;

    AddressItem(String data, String streetName) {
        String[] list = data.split(",");
        int k = 0;
        this.addressNickname = list[k++];
        this.cityName = list[k++];
        this.id = Integer.parseInt(list[k++]);
        this.postalCode = list[k++];
        this.regionName = list[k++];
        this.street = streetName;
        this.streetAdditional = list[k++];
        this.userId = Integer.parseInt(list[k]);
    }

    @Override
    public String toString() {

        return '{' +
                "\"addressNickname\":\"" + addressNickname + '"' +
                ", \"cityName\":\"" + cityName + '"' +
                ", \"id\":" + id +
                ", \"postalCode\":\"" + postalCode + '"' +
                ", \"regionName\":\"" + regionName + '"' +
                ", \"street\":\"" + street + '"' +
                ", \"streetAdditional\":\"" + streetAdditional + '"' +
                ", \"userId\":" + userId +
                '}';
    }

    public int getID() {
        return this.id;
    }

    public String getStreetName() {
        return street;
    }

    public int userID() {
        return this.userId;
    }
}
