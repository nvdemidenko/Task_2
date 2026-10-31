package stellarburgers.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Ingredient {

    @JsonProperty("_id")
    private String id;

    private String name;
    private String type;
    private double proteins;
    private double fat;
    private double carbohydrates;
    private double calories;
    private String image;
    private long price;

    public Ingredient() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    // Геттеры и сеттеры (name, type и т.д.)
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public double getProteins() { return proteins; }
    public void setProteins(double proteins) { this.proteins = proteins; }

    public double getFat() { return fat; }
    public void setFat(double fat) { this.fat = fat; }

    public double getCarbohydrates() { return carbohydrates; }
    public void setCarbohydrates(double carbohydrates) { this.carbohydrates = carbohydrates; }

    public double getCalories() { return calories; }
    public void setCalories(double calories) { this.calories = calories; }

    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }

    public long getPrice() { return price; }
    public void setPrice(long price) { this.price = price; }
}