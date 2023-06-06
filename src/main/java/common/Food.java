package common;

import java.io.Serializable;

public class Food implements Serializable {
    private String name;
    private String imgPath;
    private double price;
    private boolean isAvailable;

    public Food(String name, double price, boolean isAvailable, String imgpath){
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
    public double getPrice() {
        return price;
    }
    public String toString(){
        return name+" "+price+" "+imgPath;
    }

}
