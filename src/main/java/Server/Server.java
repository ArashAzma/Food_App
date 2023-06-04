package Server;
import common.Restaurant;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class Server {
    public static final int PORT = 8080;

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        ServerSocket serverSocket = new ServerSocket(PORT);
        System.out.println("Server started. Waiting for client...");

        Socket socket = serverSocket.accept();
        System.out.println("Client connected.");

        ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
        ArrayList<Restaurant> restaurants = new ArrayList<>();
        restaurants.add(new Restaurant("AceBurger", "Farhad 24", "", false, 5, "C:\\Users\\10\\IdeaProjects\\ClientFx\\src\\main\\resources\\images\\aceBurger burger.jpg"));
        restaurants.add(new Restaurant("Sachi", "Resalat 24", "", false, 5, "C:\\Users\\10\\IdeaProjects\\ClientFx\\src\\main\\resources\\images\\bahar burger.jpg"));
        restaurants.add(new Restaurant("Sachi", "Resalat 24", "", false, 5, "C:\\Users\\10\\IdeaProjects\\ClientFx\\src\\main\\resources\\images\\bahar burger.jpg"));
        restaurants.add(new Restaurant("Sachi", "Resalat 24", "", false, 5, "C:\\Users\\10\\IdeaProjects\\ClientFx\\src\\main\\resources\\images\\bahar burger.jpg"));
        restaurants.add(new Restaurant("Sachi", "Resalat 24", "", false, 5, "C:\\Users\\10\\IdeaProjects\\ClientFx\\src\\main\\resources\\images\\bahar burger.jpg"));
        restaurants.add(new Restaurant("Sachi", "Resalat 24", "", false, 5, "C:\\Users\\10\\IdeaProjects\\ClientFx\\src\\main\\resources\\images\\bahar burger.jpg"));
        outputStream.writeObject(restaurants);

        System.out.println("Object sent to client.");

        // Close the resources
        outputStream.close();
        socket.close();
        serverSocket.close();
    }
}