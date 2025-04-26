package lk.ijse.gdse.ormcw.dao.custom.impl;

import javafx.scene.control.Alert;
import lk.ijse.gdse.ormcw.config.FactoryConfiguration;
import lk.ijse.gdse.ormcw.dao.custom.TherapySessionDAO;
import lk.ijse.gdse.ormcw.entity.Patient;
import lk.ijse.gdse.ormcw.entity.Patient_Registration;
import lk.ijse.gdse.ormcw.entity.Therapist;
import lk.ijse.gdse.ormcw.entity.Therapy_Session;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class TherapySessionDAOImpl implements TherapySessionDAO {
    @Override
    public String getNextId() throws SQLException, IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        String hql = "SELECT l.sessionId FROM Therapy_Session l ORDER BY l.sessionId DESC";
        Query<String> query = session.createQuery(hql);
        query.setMaxResults(1);
        String lastId = query.uniqueResult();

        transaction.commit();
        session.close();

        if (lastId != null) {
            String substring = lastId.substring(1);
            int i = Integer.parseInt(substring);
            int newIdIndex = i + 1;
            return String.format("S%03d", newIdIndex);
        }

        return "S001";
    }

    @Override
    public List<Therapy_Session> getAll() throws SQLException, IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        Query<Therapy_Session> query = session.createQuery("SELECT c FROM Therapy_Session c", Therapy_Session.class);
        List<Therapy_Session> therapySessions = query.list();

        transaction.commit();
        session.close();
        return therapySessions;
    }


    @Override
    public boolean save(Therapy_Session therapySession) throws IOException, SQLException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        session.save(therapySession);
        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public List<Therapy_Session> getSessionByPatientId(String patientId) throws SQLException, ClassNotFoundException, IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        List<Therapy_Session> list = session.createQuery("FROM Therapy_Session WHERE patient.patientId = :id", Therapy_Session.class)
                .setParameter("id", patientId)
                .list();
        transaction.commit();
        session.close();
        return list;
    }
    @Override
    public List<Therapy_Session> getTherapistById(String therapistId) throws SQLException, ClassNotFoundException, IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        List<Therapy_Session> list = session.createQuery("FROM Therapy_Session WHERE therapist.therapistId = :therapistId", Therapy_Session.class)
                .setParameter("therapistId", therapistId)
                .list();
        transaction.commit();
        session.close();
        return list;
    }

    @Override
    public Therapy_Session findLastSessionByPatientId(String patientId) throws SQLException, ClassNotFoundException, IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        try {
            Query<Therapy_Session> query = session.createQuery(
                    "FROM Therapy_Session WHERE patient.patientId = :patientId ORDER BY sessionDate DESC, sessionTime DESC", Therapy_Session.class);
            query.setParameter("patientId", patientId);
            query.setMaxResults(1);
            Therapy_Session result = query.uniqueResult();
            return result;
        } finally {
            session.close();
        }
    }

    @Override
    public boolean updateStatus(String sessionId) throws SQLException, ClassNotFoundException, IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        Query query = session.createQuery("UPDATE Therapy_Session ts SET ts.status = 'Completed' WHERE ts.sessionId = :sessionId");
        query.setParameter("sessionId", sessionId);
        query.executeUpdate();
        transaction.commit();
        session.close();
        return true;
    }


    @Override
    public boolean update(Therapy_Session therapy_session) throws SQLException, IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        session.update(therapy_session);

        transaction.commit();
        session.close();

        return true;
    }

    @Override
    public boolean delete(String ID) throws SQLException, IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        Therapy_Session therapy_session = new Therapy_Session();
        therapy_session.setSessionId(ID);
        session.remove(therapy_session);


        transaction.commit();
        session.close();

        return true;
    }
}
