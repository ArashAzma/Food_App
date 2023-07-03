package Admin;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class Main extends Application {
    protected static  Socket socket;
    protected static ObjectOutputStream out;
    protected static ObjectInputStream in;
    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/AdminFx/login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) throws IOException {
        InetAddress addr =
                InetAddress.getByName(null);
        socket = new Socket(addr, 3033);
        out = new ObjectOutputStream(socket.getOutputStream());
        out.flush();
        in = new ObjectInputStream(socket.getInputStream());
        launch();
    }
    @Override
    public void stop() throws IOException {
        System.out.println("STOP");
        out.writeUTF("Stop");
        out.flush();
        socket.close();
        in.close();
        out.close();
    }
}
