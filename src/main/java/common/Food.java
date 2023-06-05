package common;

import java.io.Serializable;

public class Food implements Serializable {
    private String name;
    private String imgPath;
    private double price;
    private boolean isAvailable;

    public Food(String name, String imgpath, boolean isAvailable, double price){
        this.name = name;
        this.imgPath = imgpath;
        this.isAvailable = isAvailable;
        this.price = price;
    }
    public String isAvailable() {
        if(isAvailable) return "yes";
        return "no";
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getImgPath() {
        return imgPath;
    }
    public String toString(){
        return name;
    }
    public double getPrice() {
        return price;
    }
}
