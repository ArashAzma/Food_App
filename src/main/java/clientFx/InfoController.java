package clientFx;

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
import java.util.ArrayList;

public class InfoController extends Main{
    private Admin admin = Admin.getInstace(null, null, null, null, null);
    private static Stage stage;
    private static Scene scene;
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
    public void initialize() {
        updateNameLabel(admin.getName());
        updatePassLabel(admin.getPassword());
        updatePhoneLabel(admin.getPhoneNumber());
        updateAddressLabel(admin.getAddress());
        updateEmailLabel(admin.getEmail());
    }
    public void updateNameLabel(String newName) {
        nameLabel.setText("Name: "+newName);
    }
    public void getNewName() throws IOException {
        changeFile(nameTextField.getText(), 0);
        admin.setName(nameTextField.getText());
        updateNameLabel(admin.getName());
    }
    public void updatePassLabel(String newPass) {
        passLabel.setText("Password: "+newPass);
    }
    public void getNewPass() throws IOException {
        changeFile(passTextField.getText(), 1);
        admin.setPassword(passTextField.getText());
        updatePassLabel(admin.getPassword());
    }
    public void updatePhoneLabel(String newPhone) {
        phoneLabel.setText("Phonenumber: "+newPhone);
    }
    public void getNewPhone() throws IOException {
        changeFile(phoneTextField.getText(), 2);
        admin.setPhoneNumber(phoneTextField.getText());
        updatePhoneLabel(admin.getPhoneNumber());
    }
    public void updateAddressLabel(String newAddress) {
        addressLabel.setText("Address: "+newAddress);
    }
    public void getNewAddress() throws IOException {
        changeFile(addressTextField.getText(), 4);
        admin.setAddress(addressTextField.getText());
        updateAddressLabel(admin.getAddress());
    }
    public void updateEmailLabel(String newEmail) {
        emailLabel.setText("Email: "+newEmail);
    }
    public void getNewEmail() throws IOException {
        changeFile(emailTextField.getText(), 3);
        admin.setEmail(emailTextField.getText());
        updateEmailLabel(admin.getEmail());
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
    private void changeFile(String newStr, int p) throws IOException {
        ArrayList<String> lines = new ArrayList<>();
        try (BufferedReader file = new BufferedReader(new FileReader("usernames"))) {
            String line;
            while ((line = file.readLine()) != null) {
                lines.add(line);
            }
        }
        for (int i = 0; i < lines.size(); i++) {
            String[] parts = lines.get(i).split(",");
            if (parts[0].equals(admin.getName()) && parts[1].equals(admin.getPassword())) {
                parts[p] = newStr;
                lines.set(i, String.join(",", parts));
                break;
            }
        }
        try (BufferedWriter file = new BufferedWriter(new FileWriter("usernames"))) {
            for (String line : lines) {
                file.write(line);
                file.newLine();
            }
        }
    }

}