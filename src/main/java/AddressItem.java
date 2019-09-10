import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Getter
@Setter

@Entity
@Table(name="address")
public class AddressItem  {

    @Column(name="address_nickname")
    private String addressNickname;

    @Column(name="city_name")
    private String cityName;

    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "postal_code")
    private String postalCode;

    @Column(name = "region_name")
    private String regionName;

    @Column(name = "street")
    private String street;

    @Column(name = "street_additional")
    private String streetAdditional;

    @Column(name = "user_id")
    private int userId;

    public AddressItem() {}

    public AddressItem(String data, String streetName) {
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

    // insert equals override
//    public boolean equals(AddressItem address) {
//        return (this.addressNickname.equals(address.getAddressNickname()) &&);
//    }

//    @Override
//    public boolean isNull() {
//        return (this.street.equals("null") && this.id == 0);
//    }

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
}
