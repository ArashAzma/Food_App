package Server;
import common.Admin;
import common.Food;
import common.Restaurant;

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
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        ServerSocket serverSocket = new ServerSocket(PORT);
        System.out.println("Server started. Waiting for client...");
        loadRestaurants();
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
            BufferedReader menuFile = null;
            BufferedReader restaurantFile = new BufferedReader(new FileReader("src/main/java/Server/Restaurants"));
            String resline;
            String menline;
            while ((resline = restaurantFile.readLine()) != null) {
                String[] parts = resline.split(",");
                if(!parts[7].equals("true")){
                    continue;
                }
                boolean isTakeAway = (parts[3].equals("true"));
                boolean isAble = (parts[7].equals("true"));
                int courierCount = Integer.parseInt(parts[4]);
                int tabelCount = Integer.parseInt(parts[5]);
                restaurants.add(new Restaurant(parts[0], parts[1], parts[2], isTakeAway, courierCount, tabelCount, parts[6],isAble));
                Restaurant rest = restaurants.get(restaurants.size() - 1);
                menuFile = new BufferedReader(new FileReader("src/main/java/Server/Menus"));
                while((menline = menuFile.readLine()) != null){
                    String[] foodParts = menline.split(",");
                    if(foodParts[0].equals(parts[0])){
                        double price = Double.parseDouble(foodParts[3]);
                        boolean isAvailable = foodParts[4].equals("true");
                        double weight = Double.parseDouble(foodParts[6]);
                        rest.add_menu(new Food(foodParts[1], foodParts[2], price, isAvailable, foodParts[5],weight));
                    }
                }
            }
            menuFile.close();
            restaurantFile.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}