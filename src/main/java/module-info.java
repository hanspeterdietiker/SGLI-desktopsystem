module org.desktop.system.sgli.sgli {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.desktop.system.sgli.sgli to javafx.fxml;
    exports org.desktop.system.sgli.sgli;
}