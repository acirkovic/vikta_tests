import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter

@Entity
@Table(name="category")
public class CategoryItem {
    @Column(name="description")
    private String description;

    @Id
    @Column(name = "id")
    private int id;

    //private int[] imageItemIds;

    @Column(name = "path_to_cat_image")
    private String pathToCatImage;

    @Column(name = "title")
    private String title;

    public CategoryItem() {}

    public CategoryItem(String data) {
        String[] list = data.split(",");
        int k = 0;
        this.description = list[k++];
        this.id = Integer.parseInt(list[k++]);
        this.pathToCatImage = list[k++];
        this.title = list[k++];
    }

    @Override
    public String toString() {

        return '{' +
                "\"description\":\"" + description + '"' +
                ", \"id\":" + id +
                ", \"pathToCatImage\":\"" + pathToCatImage + '"' +
                ", \"title\":\"" + title + '"' +
                '}';
    }
}
