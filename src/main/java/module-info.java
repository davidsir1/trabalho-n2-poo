module com.davidsir1.trabalhon2 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.davidsir1.trabalhon2 to javafx.fxml;
    exports com.davidsir1.trabalhon2;
}