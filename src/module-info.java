module schedulingApp {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.base;
    requires java.desktop;
    requires java.sql;

    opens main.controllers to javafx.fxml, javafx.graphics;
    opens main.controllers.adding to javafx.fxml, javafx.graphics;
    opens main.controllers.modifying to javafx.fxml, javafx.graphics;
    opens main to javafx.fxml, javafx.graphics;
}