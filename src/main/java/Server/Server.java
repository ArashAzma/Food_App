package Server;
import common.Admin;
import common.Food;
import common.Restaurant;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Server {
    public static final int PORT = 8888;
    static ArrayList<Restaurant> restaurants = new ArrayList<>();
    private static ArrayList<ClientHandler> clients = new ArrayList<>();
    private static ExecutorService pool = Executors.newFixedThreadPool(4);
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        ServerSocket serverSocket = new ServerSocket(PORT);
        System.out.println("Server started. Waiting for client...");
        loadRestaurants();
//        for(Restaurant i:restaurants){
//            System.out.println(i);
//        }
        while(true){
            System.out.println("[SERVER] wainting for client");
            Socket socket = serverSocket.accept();
            System.out.println("[SERVER] Client connected.");
            Admin admin = new Admin();
            ClientHandler clientThread = new ClientHandler(socket, admin);
            clients.add(clientThread);

            pool.execute(clientThread);
        }

    }
    private static void loadRestaurants(){
        try {
            BufferedReader restaurantFile = new BufferedReader(new FileReader("src/main/java/Server/Restaurants"));
            BufferedReader menuFile = new BufferedReader(new FileReader("src/main/java/Server/Menus"));
            String resline;
            String menline;
            while ((resline = restaurantFile.readLine()) != null) {
                String[] parts = resline.split(",");
                boolean isTakeAway = (parts[3].equals("true"));
                int count = Integer.parseInt(parts[4]);
                restaurants.add(new Restaurant(parts[0], parts[1], parts[2], isTakeAway, count, parts[5]));
                int menu_items = Integer.parseInt(parts[6]);
                Restaurant rest = restaurants.get(restaurants.size() - 1);

                for (int i = 0; i < menu_items && (menline = menuFile.readLine()) != null; i++) {
                    String[] foodParts = menline.split(",");
                    double price = Double.parseDouble(foodParts[1]);
                    boolean isAvailable = foodParts[2].equals("true");
                    rest.add_menu(new Food(foodParts[0], price, isAvailable, foodParts[3]));
                }
            }
            menuFile.close();
            restaurantFile.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }


}
