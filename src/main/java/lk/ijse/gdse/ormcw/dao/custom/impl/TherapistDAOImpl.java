package lk.ijse.gdse.ormcw.dao.custom.impl;

import lk.ijse.gdse.ormcw.config.FactoryConfiguration;
import lk.ijse.gdse.ormcw.dao.custom.TherapistDAO;
import lk.ijse.gdse.ormcw.entity.Patient;
import lk.ijse.gdse.ormcw.entity.Therapist;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TherapistDAOImpl implements TherapistDAO {
    @Override
    public String getNextId() throws SQLException, IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        String hql = "SELECT l.therapistId FROM Therapist l ORDER BY l.therapistId DESC";
        Query<String> query = session.createQuery(hql);
        query.setMaxResults(1);
        String lastId = query.uniqueResult();

        transaction.commit();
        session.close();

        if (lastId != null) {
            String substring = lastId.substring(1);
            int i = Integer.parseInt(substring);
            int newIdIndex = i + 1;
            return String.format("T%03d", newIdIndex);
        }

        return "T001";
    }

    @Override
    public List<Therapist> getAll() throws SQLException, IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        Query<Therapist> query = session.createQuery("SELECT p FROM Therapist p", Therapist.class);
        List<Therapist> therapists = query.list();

        transaction.commit();
        session.close();
        return therapists;
    }

    @Override
    public boolean save(Therapist therapist) throws SQLException, IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        session.save(therapist);
        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public boolean update(Therapist therapist) throws SQLException, IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        session.update(therapist);

        transaction.commit();
        session.close();

        return true;
    }

    @Override
    public boolean delete(String ID) throws SQLException, IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        Therapist therapist = new Therapist();
        therapist.setTherapistId(ID);
        session.remove(therapist);


        transaction.commit();
        session.close();

        return true;
    }

    @Override
    public Therapist findById(String therapistId) throws SQLException, ClassNotFoundException {
        Therapist therapist = null;

        try (Session session = FactoryConfiguration.getInstance().getSession()) {
            // Fetch the Student entity using the primary key
            therapist = session.get(Therapist.class, therapistId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to fetch the student by ID: " + therapistId);
        }

        return therapist;
    }


    @Override
    public ArrayList<String> getAllTherapistIDs() throws SQLException, ClassNotFoundException, IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        String hql = ("SELECT therapistId FROM Therapist");

        Query query = session.createQuery(hql);
        ArrayList<String> list = (ArrayList<String>) query.list();

        transaction.commit();
        session.close();
        return list;
    }
    @Override
    public int getTotalTherapists() throws SQLException, ClassNotFoundException, IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        Query<Long> query = session.createQuery("SELECT COUNT(t) FROM Therapist t", Long.class);
        Long total = query.uniqueResult();

        transaction.commit();
        session.close();
        return total != null ? total.intValue() : 0;
    }
    @Override
    public Therapist getTherapistById(String therapistId) throws SQLException, ClassNotFoundException, IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        Therapist therapist = session.createQuery("FROM Therapist WHERE therapistId = :therapistId",Therapist.class)
                .setParameter("therapistId", therapistId).uniqueResult();
        transaction.commit();
        session.close();
        return therapist;
    }
}
