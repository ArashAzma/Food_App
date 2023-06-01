package clientFx;

import java.util.ArrayList;

public abstract class Restaurant {
    private String name;
    private String address;
    private ArrayList<String> menu;
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
}
