package common;

import java.io.Serializable;

public class Food implements Serializable {
    private String name;
    private String imgPath;
    private String type;
    private double price;
    private boolean isAvailable;

    public Food(String name, String type, double price, boolean isAvailable, String imgpath){
        this.name = name;
        this.type = type;
        this.imgPath = imgpath;
        this.isAvailable = isAvailable;
        this.price = price;
    }

    public Boolean getIsAvailable() {
        if(isAvailable) return true;
        return false;
    }
    public String isAvailable() {
        if(isAvailable) return "yes";
        return "no";
    }
    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public void setPrice(double price) {
        this.price = price;
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
    public String getType() {
        return type;
    }
    public String toString(){
        return name+"   Type: "+type+"  Price: "+price;
    }
}
