package clientFx;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;

public class InfoController {
    private Admin admin = Admin.getInstace(null, null, null, null, null);
    private static Stage stage;
    private static Scene scene;
    private Parent root;
    @FXML
    private Label nameLabel;
    @FXML
    private TextField nameTextField;

    public void updateNameLabel(String newName) {
        nameLabel.setText("Name: "+newName);
    }
    public void getNewName() throws IOException {
        changeFile(nameTextField.getText(), 0);
        admin.setName(nameTextField.getText());
        updateNameLabel(admin.getName());
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
