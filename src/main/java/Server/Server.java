package Server;
import common.Admin;
import common.Food;
import common.Restaurant;
import java.sql.*;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    public static final int PORT = 8888;
    static ArrayList<Restaurant> restaurants = new ArrayList<>();
    private static ArrayList<ClientHandler> clients = new ArrayList<>();
    private static ExecutorService pool = Executors.newFixedThreadPool(4);
    private static Connection connection;

    static {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/test", "root", "arash1382");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException, SQLException {
        ServerSocket serverSocket = new ServerSocket(PORT);
        System.out.println("Server started. Waiting for client...");
        loadDataBase();
        while(true){
            System.out.println("[SERVER] wainting for client");
            Socket socket = serverSocket.accept();
            System.out.println("[SERVER] Client connected.");
            Admin admin = new Admin();
            ClientHandler clientThread = new ClientHandler(socket, admin, connection);
            clients.add(clientThread);
            pool.execute(clientThread);
        }

    }
    private static void loadDataBase(){
        try{
            Statement statement = connection.createStatement();
            Statement statement2 = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM restaurants");
            while(rs.next()){
                int index = rs.getInt(1);
                restaurants.add(new Restaurant(rs.getString(2), rs.getString(3), rs.getString(4), rs.getBoolean(5),
                                               rs.getInt(6), rs.getInt(7), rs.getString(8), rs.getBoolean(9)));
                Restaurant rest = restaurants.get(index-1);
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
    }

}