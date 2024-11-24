package ro.ase.grupa1094;

import java.io.Serializable;

public class Course implements Serializable {
    private String title;
    private String description;
    private float price;

    public Course(String title, String description, float price)
    {
        this.title = title;
        this.description = description;
        this.price=price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Course{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", price: '" + price +'\''+
                '}';
    }
}
