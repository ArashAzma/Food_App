package Server;

import common.Food;
import common.Restaurant;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class serverAdmin {
    public static final int PORT = 3030;

    public static void main(String[] args) throws IOException{
        ArrayList<Restaurant> restaurants = new ArrayList<>();
        // my restaurants information

        BufferedReader restaurantFile = new BufferedReader(new FileReader("src/main/java/Server/Restaurants"));
        //BufferedReader menuFile = new BufferedReader(new FileReader("src/main/java/Server/Menus"));
        //reading data from Restaurants.txt and add to restaurants
        String resline;
        String menline;
        while ((resline=restaurantFile.readLine()) != null) {
            String[] fields = resline.split(",");
            Restaurant rest = new Restaurant(fields[0],fields[1],fields[2],Boolean.parseBoolean(fields[3]), Short.parseShort(fields[4]), Short.parseShort(fields[5]),fields[6]);
            restaurants.add(rest);
            BufferedReader menuFile = new BufferedReader(new FileReader("src/main/java/Server/Menus"));
            while ((menline=menuFile.readLine()) != null){
                String[] fields2 = menline.split(",");
                if(fields2[0].equals(fields[0])){
                    rest.getFoodsArray().add(new Food(fields2[1],fields2[2],Double.parseDouble(fields2[3]),Boolean.parseBoolean(fields2[4]),fields2[5]));
                }
            }
        }
//        restaurants.add(new Restaurant("sachi", "khonm", "4", true, 5,0,"jbhk"));
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
                    System.out.print("wsd");
                    out.writeObject(restaurants);
                    out.flush();

                } else if (str.equals("add restaurant")) { // giving new restaurant object to add to arraylist
                    System.out.println("adding..");
                    Restaurant r = (Restaurant) in.readObject();
                    restaurants.add(r);
                    FileWriter writer = new FileWriter("src\\main\\java\\Server\\Restaurants",true);
                    writer.write(r.getName()+","+r.getAddress()+","+r.getTime()+","+r.isTake_away()+","+r.getTableCount()+","+r.getCourierCount()+","+r.getImgPath()+"\n");
                    writer.close();

                }else if (str.equals("add food")) { // giving new food object to add to arraylist
                    System.out.println("adding food");
                    Restaurant restaurant = (Restaurant) in.readObject();
                    Food food = (Food) in.readObject();
                    restaurant.getFoodsArray().add(food);
                    FileWriter writer = new FileWriter("src\\main\\java\\Server\\Menus",true);
                    writer.write(restaurant.getName()+","+food.getName()+","+food.getType()+","+food.getPrice()+","+food.isAvailable()+","+food.getImgPath()+"\n");
                    writer.close();
                }else if(str.equals("login")) {
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
            }catch (IOException ignored){
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }finally {
//                reader2.close();
//                reader.close();
            }
        }
    }
}
