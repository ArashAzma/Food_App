package clientFx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

public class PurchaseController extends Main{
    private Parent root;
    @FXML
    public void initialize() throws IOException {
//        outputStream.close();
//        socket.close();
    }

    public void restaurantButton(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("restaurantsView.fxml"));
        root = loader.load();
        switchToScene(e, "restaurantsView.fxml", root);
    }
}

