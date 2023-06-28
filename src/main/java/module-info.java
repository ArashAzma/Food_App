module com.example.clientfx {
    requires javafx.controls;
    requires javafx.fxml;


    opens clientFx to javafx.fxml;
    opens Admin to javafx.fxml;
    opens common to javafx.base;
    exports clientFx;
    exports Admin;
}