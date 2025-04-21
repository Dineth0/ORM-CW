package lk.ijse.gdse.ormcw.dao.custom.impl;

import lk.ijse.gdse.ormcw.config.FactoryConfiguration;
import lk.ijse.gdse.ormcw.dao.custom.PaymentDAO;
import lk.ijse.gdse.ormcw.entity.Patient_Registration;
import lk.ijse.gdse.ormcw.entity.Payment;
import lk.ijse.gdse.ormcw.entity.Therapy_Session;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class PaymentDAOImpl implements PaymentDAO {
    @Override
    public String getNextId() throws SQLException, IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        String hql = "SELECT l.paymentId FROM Payment l ORDER BY l.paymentId DESC";
        Query<String> query = session.createQuery(hql);
        query.setMaxResults(1);
        String lastId = query.uniqueResult();

        transaction.commit();
        session.close();

        if (lastId != null) {
            String substring = lastId.substring(3);
            int i = Integer.parseInt(substring);
            int newIdIndex = i + 1;
            return String.format("P%03d", newIdIndex);
        }

        return "P001";
    }

    @Override
    public List<Payment> getAll() throws SQLException, IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        Query<Payment> query = session.createQuery("SELECT c FROM Payment c", Payment.class);
        List<Payment> payments = query.list();

        transaction.commit();
        session.close();
        return payments;
    }

    @Override
    public boolean save(Payment payment) throws SQLException, IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        session.save(payment);
        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public boolean update(Payment payment) throws SQLException, IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        session.update(payment);
        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public boolean delete(String ID) throws SQLException, IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        Payment payment = new Payment();
        payment.setPaymentId(ID);
        session.remove(payment);


        transaction.commit();
        session.close();

        return true;
    }
}
