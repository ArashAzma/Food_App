package Server;
import common.Food;
import common.Restaurant;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Server {
    public static final int PORT = 8080;
    private static ArrayList<Restaurant> restaurants = new ArrayList<>();

    public static void main(String[] args) throws IOException, ClassNotFoundException {
       loadRestaurants();
        for(Restaurant i:restaurants){
            System.out.println(i);
        }

        ServerSocket serverSocket = new ServerSocket(PORT);
        System.out.println("Server started. Waiting for client...");
        Admin admin = new Admin();

        while (true) {
            try {
                Socket socket = serverSocket.accept();
                try {
                    System.out.println("Client connected.");
                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
                    String situation = in.readLine();

                    if(situation.equals("login")){
                        String username = in.readLine();
                        String password = in.readLine();
                        System.out.println(username + " " + password);
                        boolean findUser = false;
                        try (BufferedReader userFile = new BufferedReader(new FileReader("src/main/java/Server/usernames"))) {
                            String line;
                            while ((line = userFile.readLine()) != null && !findUser) {
                                String[] parts = line.split(",");
                                System.out.println(Arrays.toString(parts));
                                if (parts[0].equals(username) && parts[1].equals(password)) {
                                    admin.setName(parts[0]);
                                    admin.setPassword(parts[1]);
                                    admin.setPhoneNumber(parts[2]);
                                    admin.setAddress(parts[3]);
                                    admin.setEmail(parts[4]);
                                    findUser = true;
                                    break;
                                }
                            }

                            if (findUser) {
                                out.println("Found");
                            } else {
                                out.println("!foundUser");
                            }
                        } catch (IOException e) {
                            System.out.println("Error reading usernames file: " + e.getMessage());
                            e.printStackTrace();
                        }
                    }
                    else if(situation.equals("list")){
                        ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
                        outputStream.writeObject(restaurants);
                        outputStream.close();
                        System.out.println("Sent ArrayList");
                    }

                } finally {
                    System.out.println("Closing connection...");
                    socket.close();
                }
            } catch (IOException e) {
                System.out.println("Error accepting client connection: " + e.getMessage());
                e.printStackTrace();
            }
        }
//            socket.close();
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
