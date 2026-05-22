module org.desktop.system.sgli.sgli {
    requires javafx.controls;
    requires javafx.fxml;
    requires jakarta.persistence;
    requires itextpdf;
    requires org.hibernate.orm.core;
    requires org.hibernate.orm.community.dialects;
    requires org.xerial.sqlitejdbc;
    requires java.sql;

    opens org.desktop.system.sgli.sgli.Controller to javafx.fxml;
    opens org.desktop.system.sgli.sgli.Entity to org.hibernate.orm.core;
    exports org.desktop.system.sgli.sgli;
    exports org.desktop.system.sgli.sgli.Controller;
    exports org.desktop.system.sgli.sgli.Entity;


}
