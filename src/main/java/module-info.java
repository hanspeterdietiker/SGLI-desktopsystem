module org.desktop.system.sgli.sgli {
    requires javafx.controls;
    requires javafx.fxml;
    requires jakarta.persistence;


    opens org.desktop.system.sgli.sgli.Controller to javafx.fxml;
    exports org.desktop.system.sgli.sgli;
    exports org.desktop.system.sgli.sgli.Controller;

}