package clientFx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;

import java.io.IOException;

public class BankController extends Main{
    private Parent root;
    @FXML
    private Button bank1;
    @FXML
    private Button bank2;
    @FXML
    private Button bank3;

    public void Bank1(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("depositView.fxml"));
        root = loader.load();
        DepositController dc = loader.getController();
        dc.initialize(new Bank1("a","b","c",1,2));
        switchToScene(e, "depositView.fxml", root);
    }
    public void Bank2(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("depositView.fxml"));
        root = loader.load();
        DepositController dc = loader.getController();
        dc.initialize(new Bank2("a","b","c",1,2));
        switchToScene(e, "depositView.fxml", root);
    }
    public void Bank3(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("depositView.fxml"));
        root = loader.load();
        DepositController dc = loader.getController();
        dc.initialize(new Bank3("a","b","c",1,2));
        switchToScene(e, "depositView.fxml", root);
    }
    @FXML
    private void infoButton(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("InfoView.fxml"));
        root = loader.load();
        switchToScene(e, "InfoView.fxml", root);
    }
    public void cartButton(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("cartView.fxml"));
        root = loader.load();
        switchToScene(e, "cartView.fxml", root);
    }
}
