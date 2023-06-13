package clientFx;

import common.Admin;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import static clientFx.Main.PORT;

public class DepositController extends Main{
    private static Parent root;
    private static Bank bank;
    private static Admin admin;
    private static String Amount;
    @FXML
    private Button finalButton;
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
            outputStream.flush();
            outputStream.writeUTF("getAdmin");
            outputStream.flush();
            admin = (Admin) inputStream.readObject();
            System.out.println("received  Admin ");
        }catch (IOException e){
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    private void complete(ActionEvent e) throws IOException {
        Amount = amount.getText();
        String account = accountNumber.getText();
        String pass = password.getText();
        String CVV2 = cvv2.getText();
        String Month = month.getText();
        String Year = year.getText();
        String check = "";
        check+=checkAmount(Amount);
        check+=checkAcc(account);
        check+=checkPass(pass);
        check+=checkCVV2(CVV2);
        check+=checkMonth(Month);
        check+=checkYear(Year);


        bank.deposit(Double.parseDouble(Amount));
        if(bank.getCost() == -1){
            cost.setTextFill(Color.RED);
            cost.setText("The amount is too low");
        }
        else if(Integer.parseInt(check)==0){

//            bank.deposit(Double.parseDouble(Amount));
            cost.setTextFill(Color.GREEN);
            cost.setText("$ "+bank.getCost());
            finalButton.setDisable(false);
            amountError.setText("");
            accError.setText("");
            passError.setText("");
            cvv2Error.setText("");
            dateError.setText("");
        }
        else{
            int amError = Integer.parseInt(check.charAt(0)+"");
            int acError = Integer.parseInt(check.charAt(1)+"");
            int pasError = Integer.parseInt(check.charAt(2)+"");
            int cvError = Integer.parseInt(check.charAt(3)+"");
            int moError = Integer.parseInt(check.charAt(4)+"");
            int yeError = Integer.parseInt(check.charAt(5)+"");
            if(amError==1) amountError.setText("Invalid Amount");
            else amountError.setText("");
            if(acError==1) accError.setText("Invalid Account");
            else accError.setText("");
            if(pasError==1) passError.setText("Invalid Password");
            else passError.setText("");
            if(cvError==1) cvv2Error.setText("Invalid Cvv2");
            else cvv2Error.setText("");
            if(moError==1 || yeError==1) dateError.setText("Invalid Date");
            else dateError.setText("");
        }
    }
    @FXML
    private void Final(ActionEvent e) throws IOException {
        double sum = admin.getMojodi();
        admin.setMojodi(Double.parseDouble(Amount)+sum);
        try{
//            InetAddress addr = InetAddress.getByName(null);
//            System.out.println("addr = " + addr);
//            Socket socket = new Socket(addr, PORT);
//            System.out.println("Connected to server.");
//            System.out.println("socket = " + socket);
//            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
//            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
            outputStream.flush();
            outputStream.writeUTF("changeMojodi");
            outputStream.flush();
            outputStream.writeObject(admin);
            outputStream.flush();
            System.out.println("Sent Admin");
        }catch (IOException error){
            error.printStackTrace();
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("restaurantPageView.fxml"));
        root = loader.load();
        RestaurantPageController rpc = loader.getController();
        rpc.cartButton(e);
    }
    @FXML
    private void cancelButton(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("restaurantPageView.fxml"));
        root = loader.load();
        RestaurantPageController rpc = loader.getController();
        rpc.cartButton(e);
    }

    private static String checkAcc(String str){
        if(str.length() != 16) return "1";
        if(str.matches("\\d+")) return "0";
        return "1";
    }
    private static String checkPass(String str){
        if(str.length() != 4) return "1";
        if(str.matches("\\d+")) return "0";
        return "1";
    }
    private static String checkCVV2(String str){
        if(str.length() != 4) return "1";
        if(str.matches("\\d+")) return "0";
        return "1";
    }
    private static String checkMonth(String str){
        if(str.length() != 2) return "1";
        if(str.matches("\\d+")) return "0";
        return "1";
    }
    private static String checkYear(String str){
        if(str.length() != 2) return "1";
        if(str.matches("\\d+")) return "0";
        return "1";
    }
    private static String checkAmount(String str){
        if(str.matches("\\d+")) return "0";
        return "1";
    }
}