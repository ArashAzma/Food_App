package clientFx;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.*;
import java.net.*;

public class Main extends Application {
    public static final int PORT = 8888;
    static Socket socket;
    static ObjectOutputStream outputStream;
    static ObjectInputStream inputStream;
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("loginView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("BUH BUH!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) throws IOException{
        InetAddress addr = InetAddress.getByName(null);
        System.out.println("addr = " + addr);
        socket = new Socket(addr, PORT);
        outputStream = new ObjectOutputStream(socket.getOutputStream());
        outputStream.flush();
        inputStream = new ObjectInputStream(socket.getInputStream());
        launch();
    }
    public static void switchToScene(ActionEvent event, String Scene, Parent root)  throws IOException {
        Node sourceNode = (Node) event.getSource();
        Scene currentScene = sourceNode.getScene();

        if (currentScene != null) {
            Stage stage = (Stage) currentScene.getWindow();
            if (stage != null) {
                Scene newScene = new Scene(root);
                stage.setScene(newScene);
                stage.show();
            } else {
                throw new IllegalStateException("Cannot retrieve the stage from the current scene.");
            }
        } else {
            throw new IllegalStateException("The source node does not have a valid scene.");
        }
    }
    @Override
    public void stop() throws IOException {
        System.out.println("STOP");
        outputStream.writeUTF("Stop");
        outputStream.flush();
        socket.close();
        outputStream.close();
        inputStream.close();
    }
}