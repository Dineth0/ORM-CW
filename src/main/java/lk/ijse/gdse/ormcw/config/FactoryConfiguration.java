package lk.ijse.gdse.ormcw.config;

import lk.ijse.gdse.ormcw.entity.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.IOException;
import java.util.Properties;

public class FactoryConfiguration {
    private static FactoryConfiguration factoryConfiguration;
    private SessionFactory sessionFactory;

    private FactoryConfiguration() throws IOException {
        try {
            Configuration configuration = new Configuration();
            Properties properties = new Properties();


            properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("hibernate.properties"));
            configuration.setProperties(properties);

            configuration.addAnnotatedClass(User.class);
            configuration.addAnnotatedClass(Therapist.class);
            configuration.addAnnotatedClass(Patient.class);
            configuration.addAnnotatedClass(Patient_Registration.class);
            configuration.addAnnotatedClass(TherapyProgram.class);
            configuration.addAnnotatedClass(Therapy_Session.class);
            configuration.addAnnotatedClass(Payment.class);



//            configuration.addAnnotatedClass(User.class);
//            configuration.addAnnotatedClass(Course.class);
//            configuration.addAnnotatedClass(Registration.class);


            sessionFactory = configuration.buildSessionFactory();
        } catch (IOException e) {
            throw new IOException("Failed to load hibernate.properties", e);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create SessionFactory", e);
        }
    }

    public static FactoryConfiguration getInstance() throws IOException {
        return (factoryConfiguration == null) ?
                factoryConfiguration = new FactoryConfiguration() :
                factoryConfiguration;
    }

    public Session getSession() {

        return sessionFactory.openSession();
    }
}
