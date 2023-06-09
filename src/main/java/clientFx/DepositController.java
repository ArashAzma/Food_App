package clientFx;

import common.Admin;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import static clientFx.Main.PORT;

public class DepositController {
    private Parent root;
    private Bank bank;
    private Admin admin;
    @FXML
    private TextField amount;
    @FXML
    private TextField accountNumber;
    @FXML
    private PasswordField password;
    @FXML
    private TextField cvv2;
    @FXML
    private TextField month;
    @FXML
    private TextField year;
    @FXML
    private Label amountError;
    @FXML
    private Label cost;
    @FXML
    private Label accError;
    @FXML
    private Label passError;
    @FXML
    private Label cvv2Error;
    @FXML
    private Label dateError;
    @FXML
    public void initialize(Bank bank) {
        this.bank = bank;
        try{
            InetAddress addr = InetAddress.getByName(null);
            System.out.println("addr = " + addr);
            Socket socket = new Socket(addr, PORT);
            System.out.println("Connected to server.");
            System.out.println("socket = " + socket);
            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            outputStream.flush();
            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
            outputStream.writeUTF("getAdmin");
            outputStream.flush();
            admin = (Admin) inputStream.readObject();
            System.out.println("received  Admin ");
            inputStream.close();
        }catch (IOException e){
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    private void complete(ActionEvent e) throws IOException {
        String Amount = amount.getText();
        String account = accountNumber.getText();
        String pass = password.getText();
        String CVV2 = cvv2.getText();
        String Month = month.getText();
        String Year = year.getText();

        bank.deposit(Double.parseDouble(Amount));
        cost.setText("$ "+bank.getCost());
        double sum = admin.getMojodi();
        admin.setMojodi(sum+Double.parseDouble(Amount));
        try{
            InetAddress addr = InetAddress.getByName(null);
            System.out.println("addr = " + addr);
            Socket socket = new Socket(addr, PORT);
            System.out.println("Connected to server.");
            System.out.println("socket = " + socket);
            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            outputStream.flush();
            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
            outputStream.writeUTF("setAdmin");
            outputStream.flush();
            outputStream.writeObject(admin);
            outputStream.flush();
            outputStream.close();
            System.out.println("Sent Admin");
            inputStream.close();
        }catch (IOException error){
            error.printStackTrace();
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("restaurantPageView.fxml"));
        root = loader.load();
        RestaurantPageController rpc = loader.getController();
        rpc.cartButton(e);
    }
}