package clientFx;

import java.util.ArrayList;

public abstract class Restaurant {
    private String name;
    private String address;
    private ArrayList<String> menu;
    public Restaurant(String name, String address){
        this.name = name;
        this.address = address;
    }
}
