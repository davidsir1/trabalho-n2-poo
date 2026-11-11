module com.davidsir1.trabalhon2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires atlantafx.base;
    requires java.sql;

    opens com.davidsir1.trabalhon2 to javafx.fxml;
    exports com.davidsir1.trabalhon2;
    exports com.davidsir1.trabalhon2.config;
    opens com.davidsir1.trabalhon2.config to javafx.fxml;
    exports com.davidsir1.trabalhon2.controller.view;
    opens com.davidsir1.trabalhon2.controller.view to javafx.fxml;
    exports com.davidsir1.trabalhon2.controller.form;
    opens com.davidsir1.trabalhon2.controller.form to javafx.fxml;
}