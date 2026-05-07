module org.desktop.system.sgli.sgli {
    requires javafx.controls;
    requires javafx.fxml;
    requires jakarta.persistence;
    requires itextpdf;

    opens org.desktop.system.sgli.sgli.Controller to javafx.fxml;
    exports org.desktop.system.sgli.sgli;
    exports org.desktop.system.sgli.sgli.Controller;
    exports org.desktop.system.sgli.sgli.Entity;
}