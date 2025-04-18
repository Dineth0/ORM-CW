package lk.ijse.gdse.ormcw;

import javafx.application.Application;
import lk.ijse.gdse.ormcw.config.FactoryConfiguration;
import lk.ijse.gdse.ormcw.entity.User;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        session.clear();
    }
}