module lk.ijse.gdse.ormcw {
    requires javafx.controls;
    requires javafx.fxml;
    requires lombok;
    requires java.sql;
    requires net.sf.jasperreports.core;


    requires org.hibernate.orm.core;
    requires jakarta.persistence;
    requires java.naming;
    requires jbcrypt;

    opens lk.ijse.gdse.ormcw.entity to org.hibernate.orm.core;
    opens lk.ijse.gdse.ormcw.config to jakarta.persistence;


    opens lk.ijse.gdse.ormcw.controller to javafx.fxml;
    exports lk.ijse.gdse.ormcw;
    opens lk.ijse.gdse.ormcw.tm to javafx.base;
}