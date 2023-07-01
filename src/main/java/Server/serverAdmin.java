package Server;

import common.Food;
import common.Restaurant;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class serverAdmin {
    public static final int PORT = 3033;
    private static ArrayList<Restaurant> restaurants;
    public static void main(String[] args) throws IOException{
        ServerSocket s = new ServerSocket(PORT);
        Socket socket = s.accept();
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
        out.flush();
        System.out.println("SERVER STARTED [Waiting for Client]");
        while(true){
            try {
                String str = in.readUTF();
                System.out.println(str);
                if (str.equals("restaurants")) {  // sending restaurants arraylist for table view
                    restaurants = loadRestaurants();
                    System.out.println(restaurants.get(0).getTime());
                    System.out.print("sent Restaurants");
                    out.writeObject(restaurants);
                    out.flush();

                }
                else if (str.equals("add restaurant")) { // giving new restaurant object to add to arraylist
                    System.out.println("adding..");
                    Restaurant r = (Restaurant) in.readObject();
                    restaurants.add(r);
                    FileWriter writer = new FileWriter("src\\main\\java\\Server\\Restaurants",true);
                    writer.write(r.getName()+","+r.getAddress()+","+r.getTime()+","+r.isTake_away()+","+r.getTableCount()+","+r.getCourierCount()+","+r.getImgPath()+","+r.getIs_able()+"\n");
                    writer.close();

                }
                else if (str.equals("add food")) { // giving new food object to add to arraylist
                    System.out.println("adding food");
                    Restaurant restaurant = (Restaurant) in.readObject();
                    Food food = (Food) in.readObject();
                    restaurant.getFoodsArray().add(food);
                    FileWriter writer = new FileWriter("src\\main\\java\\Server\\Menus",true);
                    writer.write(restaurant.getName()+","+food.getName()+","+food.getType()+","+food.getPrice()+","+food.isAvailable()+","+food.getImgPath()+"\n");
                    writer.close();
                }
                else if(str.equals("login")) {
                    String username = in.readUTF();
                    String password = in.readUTF();

                    System.out.println(username + " " + password);
                    boolean findUser = false;
                    try (BufferedReader userFile = new BufferedReader(new FileReader("src\\main\\java\\Server\\adminInfo"))) {
                        String line = userFile.readLine();
                        String[] parts = line.split(",");
                        System.out.println(parts[0]+"   "+parts[1]);
                        if(username.equals(parts[0]) && password.equals(parts[1])){
                            findUser = true;
                        }
                        if (findUser) {
                            out.writeUTF("Found");
                            out.flush();
                        } else {
                            out.writeUTF("!foundUser");
                            out.flush();
                        }
                    } catch (IOException e) {
                        System.out.println("Error reading usernames file: " + e.getMessage());
                        e.printStackTrace();
                    }
                }
                else if(str.equals("change restaurant")){
                    String resName = in.readUTF();
//                    System.out.println(resName);
                    String newline = in.readUTF();
//                    System.out.println(newline);
                    try {
                        BufferedReader file = new BufferedReader(new FileReader("src\\main\\java\\Server\\Restaurants"));
                        StringBuffer inputBuffer = new StringBuffer();
                        String line;

                        while ((line = file.readLine()) != null) {
                            String[] parts = line.split(",");
                            if(parts[0].equals(resName)){
                                line = newline;
                            }
                            inputBuffer.append(line);
                            inputBuffer.append('\n');
                        }
                        file.close();

                        FileOutputStream fileOut = new FileOutputStream("src\\main\\java\\Server\\Restaurants");
                        fileOut.write(inputBuffer.toString().getBytes());
                        fileOut.close();

                    } catch (Exception e) {
                        System.out.println("Problem reading file.");
                    }
                } else if (str.equals("remove restaurant")) {
                    String name = in.readUTF();
                    ArrayList<Restaurant> replace = new ArrayList<>();
                    for(int i = 0; i < restaurants.size(); ++i){
                        if(restaurants.get(i).getName().equals(name)){
                            for(int j = 0; j < restaurants.size(); ++j){
                                if(i != j){
                                    replace.add(restaurants.get(j));
                                }
                            }
                        }
                    }
                    restaurants.clear();
                    restaurants = new ArrayList<>(replace);
                    System.out.println(restaurants.get(0).getName());
                    File rest = new File("src\\main\\java\\Server\\Restaurants");
                    PrintWriter writerEmpty = new PrintWriter(rest);
                    writerEmpty.print("");
                    writerEmpty.close();
                    FileWriter writer = new FileWriter("src\\main\\java\\Server\\Restaurants",true);
                    for (Restaurant i : restaurants){
                        writer.write(i.getName()+","+i.getAddress()+","+i.getTime()+","+i.isTake_away()+","+i.getTableCount()+","+i.getCourierCount()+","+i.getImgPath()+","+i.getIs_able()+"\n");
                    }
                    writer.close();
                } else if (str.equals("remove food")) {
                    String restName = in.readUTF();
                    String foodName = in.readUTF();
                    ArrayList<Food> replace = new ArrayList<>();
                    for(int i = 0; i < restaurants.size(); ++i){
                        if(restaurants.get(i).getName().equals(restName)){
                            for(int j = 0; j < restaurants.get(i).getFoodsArray().size(); ++j){
                                if(!restaurants.get(i).getFoodsArray().get(j).getName().equals(foodName)){
                                    replace.add(restaurants.get(i).getFoodsArray().get(j));
                                }
                            }
                            restaurants.get(i).getFoodsArray().clear();
                            restaurants.get(i).setFoodsArray(replace);
                        }
                    }
                    File rest = new File("src\\main\\java\\Server\\Menus");
                    PrintWriter writerEmpty = new PrintWriter(rest);
                    writerEmpty.print("");
                    writerEmpty.close();
                    FileWriter writer = new FileWriter("src\\main\\java\\Server\\Menus",true);
                    for (Restaurant i : restaurants) {
                        for (Food j : i.getFoodsArray()) {
                            writer.write(i.getName()+","+j.getName()+","+j.getType()+","+j.getPrice()+","+j.isAvailable()+","+j.getImgPath()+"\n");
                        }
                    }
                    writer.close();
                } else if(str.equals("change food")){
                    String resName = in.readUTF();
                    String foodName = in.readUTF();
                    String newline = in.readUTF();

                    try {
                        BufferedReader file = new BufferedReader(new FileReader("src\\main\\java\\Server\\Menus"));
                        StringBuffer inputBuffer = new StringBuffer();
                        String line;

                        while ((line = file.readLine()) != null) {
                            String[] parts = line.split(",");
                            if(parts[0].equals(resName) && parts[1].equals(foodName)){
                                line = newline;
                            }
                            inputBuffer.append(line);
                            inputBuffer.append('\n');
                        }
                        file.close();

                        FileOutputStream fileOut = new FileOutputStream("src\\main\\java\\Server\\Menus");
                        fileOut.write(inputBuffer.toString().getBytes());
                        fileOut.close();

                    } catch (Exception e) {
                        System.out.println("Problem reading file.");
                    }

                }
            }catch (IOException ignored){
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public static ArrayList<Restaurant> loadRestaurants() throws IOException {
        BufferedReader restaurantFile = new BufferedReader(new FileReader("src/main/java/Server/Restaurants"));
        ArrayList<Restaurant> restaurants = new ArrayList<>();
        //reading data from Restaurants.txt and add to restaurants
        String resline;
        String menline;
        while ((resline=restaurantFile.readLine()) != null) {
            String[] fields = resline.split(",");
//            System.out.println(resline);
            Restaurant rest = new Restaurant(fields[0],fields[1],fields[2],Boolean.parseBoolean(fields[3]), Short.parseShort(fields[4]), Short.parseShort(fields[5]),fields[6],Boolean.parseBoolean(fields[7]));
            restaurants.add(rest);
            BufferedReader menuFile = new BufferedReader(new FileReader("src/main/java/Server/Menus"));
            while ((menline=menuFile.readLine()) != null){
                String[] fields2 = menline.split(",");
                if(fields2[0].equals(fields[0])){
                    rest.getFoodsArray().add(new Food(fields2[1],fields2[2],Double.parseDouble(fields2[3]),Boolean.parseBoolean(fields2[4]),fields2[5]));
                }
            }
        }
        return restaurants;
    }
}