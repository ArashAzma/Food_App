module com.example.clientfx {
    requires javafx.controls;
    requires javafx.fxml;


    opens clientFx to javafx.fxml;
    exports clientFx;
}