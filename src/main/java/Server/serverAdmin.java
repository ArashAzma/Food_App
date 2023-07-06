package Server;

import common.Food;
import common.Restaurant;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;
import java.util.ArrayList;

public class serverAdmin {
    public static final int PORT = 3033;
//    private static ArrayList<Restaurant> restaurants = new ArrayList<>();
    private static Connection connection;
    static {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/test", "root", "arash1382");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void main(String[] args) throws IOException{

        ServerSocket serverSocket = new ServerSocket(PORT);
        Socket socket = serverSocket.accept();
        System.out.println("SERVER STARTED [Waiting for Client]");
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        out.flush();
        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
        while(true){
            try {
                String str = in.readUTF();
                System.out.println(str);

                if (str.equals("restaurants")) {  // sending restaurants arraylist for table view
                    ArrayList<Restaurant> restaurants = loadRestaurants();
                    System.out.println("sent Restaurants");
                    out.writeObject(restaurants);
                    out.flush();
                }
                else if (str.equals("Add restaurant")){
                    Restaurant r = (Restaurant) in.readObject();
//                    restaurants.add(r);
                    try{
                        PreparedStatement ps = (PreparedStatement) connection.prepareStatement("INSERT INTO restaurants (name, address, time, is_takeAway, tabelCount, " +
                                                                                                    "courierCount, imgPath, is_able) VALUES (?,?,?,?,?,?,?,?)");
                        ps.setString(1, r.getName());
                        ps.setString(2, r.getAddress());
                        ps.setString(3, r.getTime());
                        ps.setBoolean(4, r.isTake_away());
                        ps.setInt(5, r.getTableCount());
                        ps.setInt(6, r.getCourierCount());
                        ps.setString(7, r.getImgPath());
                        ps.setBoolean(8, r.isIs_able());
                        ps.execute();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }


                }
                else if (str.equals("Add food")) {
                    // giving new food object to add to arraylist
                    Restaurant restaurant = (Restaurant) in.readObject();
                    Food food = (Food) in.readObject();
                    restaurant.getFoodsArray().add(food);
                    int index = -1;
                    try{
                        Statement statement = connection.createStatement();
                        ResultSet rs =  statement.executeQuery("SELECT idrestaurants FROM restaurants WHERE  name = '"+restaurant.getName()+"'");
                        if (rs.next()) { // Move the cursor to the first row
                            index = rs.getInt("idrestaurants");
                        }
                        PreparedStatement ps = (PreparedStatement) connection.prepareStatement("INSERT INTO menu (idrestaurants, name, type, price, is_available, " +
                                                                                                                            "imgPath, weight) VALUES (?,?,?,?,?,?,?)");
                        ps.setInt(1, index);
                        ps.setString(2, food.getName());
                        ps.setString(3, food.getType());
                        ps.setDouble(4, food.getPrice());
                        ps.setBoolean(5, food.getIsAvailable());
                        ps.setString(6, food.getImgPath());
                        ps.setDouble(7, food.getWeight());
                        ps.execute();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
                else if(str.equals("login")) { // checking password and username for login

                    String username = in.readUTF();
                    String password = in.readUTF();
                    boolean findUser = false;
                    try (BufferedReader userFile = new BufferedReader(new FileReader("src\\main\\java\\Server\\adminInfo"))) {
                        String line = userFile.readLine();
                        String[] parts = line.split(",");
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
                else if(str.equals("Change restaurant")){
                    String resName = in.readUTF();
                    Restaurant temp = (Restaurant) in.readObject();
                    try{
                        PreparedStatement ps = connection.prepareStatement("UPDATE restaurants SET name = ?, address=?, time=?, is_takeAway=?, tabelCount=?, courierCount=?, " +
                                                                                "imgpath=?, is_able=? WHERE name = ?");

                        ps.setString(1, temp.getName());
                        ps.setString(2, temp.getAddress());
                        ps.setString(3, temp.getTime());
                        ps.setBoolean(4, temp.isTake_away());
                        ps.setInt(5, temp.getTableCount());
                        ps.setInt(6, temp.getCourierCount());
                        ps.setString(7, temp.getImgPath());
                        ps.setBoolean(8, temp.getIs_able());
                        ps.setString(9, resName);
                        ps.execute();

                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
                else if (str.equals("Remove restaurant")){
                    String name = in.readUTF();
                    int index=-1;
                    try{
                        PreparedStatement ps = connection.prepareStatement("SELECT idrestaurants FROM restaurants WHERE  name = ?");
                        ps.setString(1, name);
                        ResultSet rs = ps.executeQuery();
                        ps = (PreparedStatement) connection.prepareStatement("DELETE FROM restaurants WHERE name = ?");
                        ps.setString(1, name);
                        ps.execute();

                        if (rs.next()) {
                            index = rs.getInt("idrestaurants");
                            System.out.println(index);
                        }
                        else {
                            System.out.println("ResultSet is null. No rows were returned or an error occurred.");
                        }
                        ps = (PreparedStatement) connection.prepareStatement("DELETE FROM menu WHERE idrestaurants = ?");
                        ps.setInt(1, index);
                        ps.execute();

                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
                else if (str.equals("Remove food")){
                    String foodName = in.readUTF();
                    try{
                        PreparedStatement ps = (PreparedStatement) connection.prepareStatement("DELETE FROM menu WHERE name = ?");
                        ps.setString(1, foodName);
                        ps.execute();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
                else if(str.equals("Change food")){
                    String foodName = in.readUTF();
                    Food food = (Food) in.readObject();
                    try{
                        PreparedStatement ps = (PreparedStatement) connection.prepareStatement("UPDATE menu SET name = ?, type=?, price=?, is_available=?, " +
                                                                                                     "imgpath=?, weight=? WHERE name = ?");
                        ps.setString(1, food.getName());
                        ps.setString(2, food.getType());
                        ps.setDouble(3, food.getPrice());
                        ps.setBoolean(4, food.getIsAvailable());
                        ps.setString(5, food.getImgPath());
                        ps.setDouble(6, food.getWeight());
                        ps.setString(7, foodName);
                        ps.execute();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
                else{
                    System.out.println("Closing the socket...");
                }
            }catch (IOException ignored){
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public static ArrayList<Restaurant> loadRestaurants() throws IOException {
        ArrayList<Restaurant> restaurants = new ArrayList<>();
        try{
            Statement statement = connection.createStatement();
            Statement statement2 = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM restaurants");
            while(rs.next()){
                int index = rs.getInt(1);
                restaurants.add(new Restaurant(rs.getString(2), rs.getString(3), rs.getString(4), rs.getBoolean(5),
                        rs.getInt(6), rs.getInt(7), rs.getString(8), rs.getBoolean(9)));
                Restaurant rest = restaurants.get(index-1);
//                System.out.println(rest);
                ResultSet ms = statement2.executeQuery("SELECT * FROM menu");
                while(ms.next()){
                    if(ms.getInt(2)==index){
                        rest.add_menu(new Food(ms.getString(3), ms.getString(4), ms.getDouble(5),
                                ms.getBoolean(6), ms.getString(7), ms.getDouble(8)));
                    }
                }
            }
            rs.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return restaurants;
    }
}