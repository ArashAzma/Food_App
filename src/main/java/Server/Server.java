package Server;
import common.Food;
import common.Restaurant;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class Server {
    public static final int PORT = 8080;

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        ServerSocket serverSocket = new ServerSocket(PORT);
        System.out.println("Server started. Waiting for client...");
        while(true){

            Socket socket = serverSocket.accept();
            System.out.println("Client connected.");

            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            ArrayList<Restaurant> restaurants = new ArrayList<>();
            Restaurant aceBurger = new Restaurant("AceBurger", "Farhad 24", "", false, 5, "C:\\Users\\10\\IdeaProjects\\ClientFx\\src\\main\\resources\\images\\aceBurger burger.jpg");
            aceBurger.add_menu(new Food ("burger", "C:\\Users\\10\\IdeaProjects\\ClientFx\\src\\main\\resources\\images\\aceBurger burger.jpg", true));
            aceBurger.add_menu(new Food ("pizza", "C:\\Users\\10\\IdeaProjects\\ClientFx\\src\\main\\resources\\images\\bahar burger.jpg",  true));

            Restaurant sachi = new Restaurant("Sachi", "Resalat 24", "", false, 5, "C:\\Users\\10\\IdeaProjects\\ClientFx\\src\\main\\resources\\images\\bahar burger.jpg");
            sachi.add_menu(new Food("sausage", "C:\\Users\\10\\IdeaProjects\\ClientFx\\src\\main\\resources\\images\\aceBurger burger.jpg",  true));
            sachi.add_menu(new Food("kalbus", "C:\\Users\\10\\IdeaProjects\\ClientFx\\src\\main\\resources\\images\\aceBurger burger.jpg",  true));

            restaurants.add(aceBurger);
            restaurants.add(sachi);

            outputStream.writeObject(restaurants);
            System.out.println("Object sent to client.");
            // Close the resources
            outputStream.close();
            socket.close();
        }
//        serverSocket.close();
    }
}