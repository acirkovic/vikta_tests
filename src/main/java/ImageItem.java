import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter

@Entity
@Table(name="imageitem")
public class ImageItem {

    @Column(name="author")
    private String author;

    private int[] categoryIds = new int[1];

    @Column(name="description")
    private String description;

    @Id
    @Column(name="id")
    private int id;

    @Column(name="path_to_image")
    private String pathToImage;

    @Column(name="price")
    private double price;

    @Column(name="rating")
    private int rating;

    private String[] tags = new String[1];

    @Column(name="title")
    private String title;


    public ImageItem() {}

    public ImageItem(String data) {
        String[] list = data.split(",");
        int k = 0;
        this.author = list[k++];
        this.description = list[k++];
        this.id = Integer.parseInt(list[k++]);
        this.pathToImage = list[k++];
        this.price = Double.parseDouble(list[k++]);
        this.rating = Integer.parseInt(list[k++]);
        this.title = list[k];
    }

    @Override
    public String toString() {

        return '{' +
                "\"author\":\"" + author + '"' +
                ", \"description\":" + description +
                ", \"id\":" + id +
                ", \"pathToImage\":\"" + pathToImage + '"' +
                ", \"price\":\"" + price + '"' +
                ", \"rating\":\"" + rating + '"' +
                ", \"title\":\"" + title + '"' +
                '}';
    }
}
