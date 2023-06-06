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
            aceBurger.add_menu(new Food ("burger", "C:\\Users\\10\\IdeaProjects\\ClientFx\\src\\main\\resources\\images\\aceBurger burger.jpg", true, 90));
            aceBurger.add_menu(new Food ("pizza", "C:\\Users\\10\\IdeaProjects\\ClientFx\\src\\main\\resources\\images\\bahar burger.jpg",  true, 110));

            Restaurant sachi = new Restaurant("Sachi", "Resalat 24", "", false, 5, "C:\\Users\\10\\IdeaProjects\\ClientFx\\src\\main\\resources\\images\\bahar burger.jpg");
            sachi.add_menu(new Food("sausage", "C:\\Users\\10\\IdeaProjects\\ClientFx\\src\\main\\resources\\images\\aceBurger burger.jpg",  false, 127));
            sachi.add_menu(new Food("kalbus", "C:\\Users\\10\\IdeaProjects\\ClientFx\\src\\main\\resources\\images\\aceBurger burger.jpg",  false, 255));

            Restaurant subway = new Restaurant("SubWay", "turkey 36", "", false, 5, "C:\\Users\\10\\IdeaProjects\\ClientFx\\src\\main\\resources\\images\\subwayLogo.jpg");
            subway.add_menu(new Food("sandwich", "C:\\Users\\10\\IdeaProjects\\ClientFx\\src\\main\\resources\\images\\aceBurger burger.jpg", true, 99));
            subway.add_menu(new Food("peperoni", "C:\\Users\\10\\IdeaProjects\\ClientFx\\src\\main\\resources\\images\\pizza.jpg", true, 99));
            subway.add_menu(new Food("makhsoos", "C:\\Users\\10\\IdeaProjects\\ClientFx\\src\\main\\resources\\images\\pizza2.jpg", false, 99));
            restaurants.add(aceBurger);
            restaurants.add(sachi);
            restaurants.add(subway);

            outputStream.writeObject(restaurants);
            System.out.println("Object sent to client.");
            // Close the resources
            outputStream.close();
            socket.close();
        }
//        serverSocket.close();
    }
}