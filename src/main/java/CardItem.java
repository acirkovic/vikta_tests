public class CardItem {

    private String cardCode;
    private String cardNickName;
    private String cardNumber;
    private String expirationDate;
    private int id;
    private String ownerName;
    private int userId;

    CardItem(String data, String ownerName) {
        String[] list = data.split(",");
        int k = 0;
        this.cardCode = list[k++];
        this.cardNickName = list[k++];
        this.cardNumber = list[k++];
        this.expirationDate = list[k++];
        this.id = Integer.parseInt(list[k++]);
        this.ownerName = ownerName;
        this.userId = Integer.parseInt(list[k]);
    }

    @Override
    public String toString() {

        return '{' +
                "\"cardCode\":\"" + cardCode + '"' +
                ", \"cardNickName\":\"" + cardNickName + '"' +
                ", \"cardNumber\":\"" + cardNumber + '"' +
                ", \"expirationDate\":\"" + expirationDate + '"' +
                ", \"id\":" + id +
                ", \"ownerName\":\"" + ownerName + '"' +
                ", \"userId\":" + userId +
                '}';
    }

    public int getID() {
        return this.id;
    }

    public String getCardNickName() {
        return cardNickName;
    }

    public int userID() {
        return this.userId;
    }
}
