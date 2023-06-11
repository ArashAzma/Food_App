package clientFx;

import common.Admin;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;

public class InfoController extends Main{
    private static Admin admin;
    private Parent root;
    @FXML
    private Label nameLabel;
    @FXML
    private TextField nameTextField;
    @FXML
    private Label passLabel;
    @FXML
    private TextField passTextField;
    @FXML
    private Label phoneLabel;
    @FXML
    private TextField phoneTextField;
    @FXML
    private Label addressLabel;
    @FXML
    private TextField addressTextField;
    @FXML
    private Label emailLabel;
    @FXML
    private TextField emailTextField;
    @FXML
    private Label mojodiLabel;
    @FXML
    public void initialize() {
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
            System.out.println("received Admin ");
            inputStream.close();
            updateNameLabel(admin.getName());
            updatePassLabel(admin.getPassword());
            updatePhoneLabel(admin.getPhoneNumber());
            updateAddressLabel(admin.getAddress());
            updateEmailLabel(admin.getEmail());
            updateMojodiLabel(admin.getMojodi());
        }catch(IOException e){
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public void updateNameLabel(String newName) {
        nameLabel.setText("Name: "+newName);
    }
    public void updatePassLabel(String newPass) {
        passLabel.setText("Password: "+newPass);
    }
    public void updatePhoneLabel(String newPhone) {
        phoneLabel.setText("Phonenumber: "+newPhone);
    }
    public void updateAddressLabel(String newAddress) {
        addressLabel.setText("Address: "+newAddress);
    }
    public void updateEmailLabel(String newEmail) {
        emailLabel.setText("Email: "+newEmail);
    }
    public void restaurantButton(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("restaurantsView.fxml"));
        root = loader.load();
        switchToScene(e, "restaurantsView.fxml", root);
    }
    public void cartButton(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("cartView.fxml"));
        root = loader.load();
        switchToScene(e, "cartView.fxml", root);
    }
    private void updateMojodiLabel(double amount){
        this.mojodiLabel.setText("Mojodi: "+amount);
    }
    public void changeInfo(ActionEvent e) throws IOException {
        System.out.println(admin);
        Admin tempAdmin = new Admin();
        System.out.println("ZZ");
        System.out.println(nameTextField.getText());
        System.out.println(passTextField.getText());
        System.out.println("ZZ");
        if(!nameTextField.getText().equals("")) {
            tempAdmin.setName(nameTextField.getText());
        }
        else {
            tempAdmin.setName(admin.getName());
        }
        if(!passTextField.getText().equals("")){
            tempAdmin.setPassword(passTextField.getText());
        }
        else {
            tempAdmin.setPassword(admin.getPassword());
        }
        if(!phoneTextField.getText().equals("")) {
            tempAdmin.setPhoneNumber(phoneTextField.getText());
        }
        else {
            tempAdmin.setPhoneNumber(admin.getPhoneNumber());
        }
        if(!addressTextField.getText().equals("")){
            tempAdmin.setAddress(addressTextField.getText());
        }
        else {
            tempAdmin.setAddress(admin.getAddress());
        }
        if(!emailTextField.getText().equals("")){
            tempAdmin.setEmail(emailTextField.getText());
        }
        else {
            tempAdmin.setEmail(admin.getEmail());
        }
        System.out.println(tempAdmin);
        try{
            InetAddress addr = InetAddress.getByName(null);
            System.out.println("addr = " + addr);
            Socket socket = new Socket(addr, PORT);
            System.out.println("Connected to server.");
            System.out.println("socket = " + socket);
            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            outputStream.flush();
            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
            outputStream.writeUTF("changeInfo");
            try{
                outputStream.writeObject(tempAdmin);
                outputStream.flush();
                System.out.println("Sent Admin");

                String errorCode = inputStream.readUTF();
                System.out.println(errorCode);
                if(!errorCode.equals("true")){
                    int checkName = Integer.parseInt(errorCode.charAt(0)+"");
                    int checkPass = Integer.parseInt(errorCode.charAt(1)+"");
                    int checkNumber = Integer.parseInt(errorCode.charAt(2)+"");
                    int checkEmail = Integer.parseInt(errorCode.charAt(3)+"");

                    switch (checkName){
                        case(1):
                            nameLabel.setText("Name is already in use");
                            break;
                        case (2):
                            nameLabel.setText("Name is too short");
                            break;
                        default:
                            nameLabel.setText(admin.getName());
                            break;
                    }
                    switch (checkPass){
                        case(1):
                            passLabel.setText("Password is too weak!");
                            break;
                        case (2):
                            passLabel.setText("Password is too short");
                            break;
                        default:
                            passLabel.setText(admin.getPassword());
                            break;
                    }
                    if(checkNumber==1)phoneLabel.setText("invalid number");
                    else phoneLabel.setText(admin.getPhoneNumber());
                    if(checkEmail==1)emailLabel.setText("invalid Email");
                    else emailLabel.setText(admin.getPhoneNumber());
                }
                else{
                    initialize();
                }
                outputStream.close();
                inputStream.close();
            }catch(IOException error){
                System.out.println("error");
                error.printStackTrace();
            }
        }catch (IOException E){
            E.printStackTrace();
        }
    }
}