module com.davidsir1.trabalhon2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires atlantafx.base;
    requires java.sql;

    opens com.davidsir1.trabalhon2 to javafx.fxml;
    exports com.davidsir1.trabalhon2;
    opens com.davidsir1.trabalhon2.controller to javafx.fxml;
    exports com.davidsir1.trabalhon2.controller;
    exports com.davidsir1.trabalhon2.config;
    opens com.davidsir1.trabalhon2.config to javafx.fxml;
}