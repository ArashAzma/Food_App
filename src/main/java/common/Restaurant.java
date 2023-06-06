package common;

import java.io.Serializable;
import java.util.*;

public class Restaurant implements Serializable {
    private String imgPath;
    private String name;
    private String address;
    private ArrayList<Food> menu = new ArrayList<Food>();
    private int foodCount;
    private String time;
    private boolean is_takeAway;
    private int tableCount = 0;
    private int courierCount = 0;
    public Restaurant(String name){this.name = name;}
    public Restaurant(String name, String address, String time, boolean is_takeAway, int count, String imgPath){
        this.name = name;
        this.address = address;
        this.time = time;
        this.is_takeAway = is_takeAway;
        if (is_takeAway) {
            this.courierCount = count;
        } else {
            this.tableCount = count;
        }
        this.imgPath = imgPath;
    }
    public void add_menu(Food food){
        menu.add(food);
    }
    public String getName(){return name;}
    public String getAddress(){return address;}
    public ArrayList getMenu(){return menu;}
    public String getImgPath() {
        return imgPath;
    }
    public int getFoodCount() {
        return foodCount;
    }
    public String toString(){
        String str = name+" "+address+" "+time;
        for(Food i:menu){
            str+="\n\t"+i;
        }
        return str;
    }
}