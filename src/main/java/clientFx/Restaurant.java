package clientFx;

import java.util.*;

public class Restaurant {
    private String name;
    private String address;
    private ArrayList<Food> menu = new ArrayList<Food>();
    private String time;
    private boolean is_takeAway;
    private int tableCount = 0;
    private int courierCount = 0;
    public Restaurant(String name, String address, String time, boolean is_takeAway, int count){
        this.name = name;
        this.address = address;
        this.time = time;
        this.is_takeAway = is_takeAway;
        if (is_takeAway) {
            this.courierCount = count;
        } else {
            this.tableCount = count;
        }
    }
    public void add_menu(Food food){
        menu.add(food);
    }
    public String getName(){return name;}
}