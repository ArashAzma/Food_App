package clientFx;

import common.Restaurant;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;

public class LoginController extends Main{
    protected static ArrayList<Restaurant> restaurants;
    private static Stage stage;
    private static Scene scene;
    private Parent root;
    @FXML
    private TextField nameBox;
    @FXML
    private PasswordField passBox;
    @FXML
    private Label passError;
    @FXML
    private Label nameLabel;
    @FXML
    private Label passwordLabel;
    @FXML
    private void login(ActionEvent e)throws IOException{
        InetAddress addr = InetAddress.getByName(null);
        System.out.println("addr = " + addr);
        Socket socket = new Socket(addr, PORT);
        try {
            System.out.println("Connected to server.");
            System.out.println("socket = " + socket);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true);
            String name = nameBox.getText();
            String password = passBox.getText();
            out.println(name);
            out.println(password);
            String foundUser = in.readLine();
            if(foundUser.equals("Found")) {
                System.out.println("found user");
                String line = in.readLine();
                String[] parts = line.split(",");
                Admin admin = Admin.getInstace(parts[0], parts[1], parts[2], parts[3], parts[4]);
                System.out.println("received Admin");
                try{
                    ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
                    restaurants = (ArrayList<Restaurant>) inputStream.readObject();
                    System.out.println("received Restaurants...");
                    System.out.println(Arrays.toString(restaurants.toArray()));
                    inputStream.close();
                }catch (ClassNotFoundException error){
                    error.printStackTrace();
                }finally {
                    socket.close();
                }

                FXMLLoader loader = new FXMLLoader(getClass().getResource("restaurantsView.fxml"));
                root = loader.load();
                switchToScene(e, "restaurantsView.fxml", root);
            }
            else System.out.println("Did not find user");
            out.println("END");

        } catch(IOException error){
            error.printStackTrace();
        }finally{
            System.out.println("closing...");
            socket.close();
        }
    }

//    @FXML
//    protected void login(ActionEvent e) throws IOException {
//        String name = nameBox.getText();
//        String password = passBox.getText();
//        try(BufferedReader file = new BufferedReader(new FileReader("usernames"))){
//            String line;
//            boolean findUser = false;
//            while ((line = file.readLine()) != null) {
//                String[] parts = line.split(",");
//                if(parts[0].equals(name)  && parts[1].equals(password)){
//                    Admin admin = Admin.getInstace(parts[0], parts[1], parts[2], parts[3], parts[4]);
//                    System.out.println("Found it!!!");
//                    findUser = true;
//                    nameLabel.setText("Name");
//                    passwordLabel.setText("Password");
//
//                    FXMLLoader loader = new FXMLLoader(getClass().getResource("restaurantsView.fxml"));
//                    root = loader.load();
//                    Main.switchToScene(e, "restaurantsView.fxml", root);
//                }
//            }
//            if(!findUser){
//                passError.setTextFill(Color.RED);
//                passError.setText("Couldnt find youe account! ");
//            }
//        }catch(IOException error){
//            System.out.println("error");
//            error.printStackTrace();
//        }
//    }

    @FXML
    protected void signUp(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("mainView.fxml"));
        root = loader.load();
        switchToScene(e, "mainView.fxml", root);
    }
}
