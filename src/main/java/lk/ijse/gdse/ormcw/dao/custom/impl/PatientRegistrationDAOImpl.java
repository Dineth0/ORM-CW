package lk.ijse.gdse.ormcw.dao.custom.impl;

import lk.ijse.gdse.ormcw.config.FactoryConfiguration;
import lk.ijse.gdse.ormcw.dao.custom.PatientRegistrationDAO;
import lk.ijse.gdse.ormcw.dto.PatientRegistrationDTO;
import lk.ijse.gdse.ormcw.entity.Patient;
import lk.ijse.gdse.ormcw.entity.Patient_Registration;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class PatientRegistrationDAOImpl implements PatientRegistrationDAO {
    @Override
    public String getNextId() throws SQLException, IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        String hql = "SELECT l.registrationId FROM Patient_Registration l ORDER BY l.registrationId DESC";
        Query<String> query = session.createQuery(hql);
        query.setMaxResults(1);
        String lastId = query.uniqueResult();

        transaction.commit();
        session.close();

        if (lastId != null) {
            String substring = lastId.substring(1);
            int i = Integer.parseInt(substring);
            int newIdIndex = i + 1;
            return String.format("R%03d", newIdIndex);
        }

        return "R001";
    }

    @Override
    public List<Patient_Registration> getAll() throws SQLException, IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        Query<Patient_Registration> query = session.createQuery("SELECT c FROM Patient_Registration c", Patient_Registration.class);
        List<Patient_Registration> patientRegistrations = query.list();

        transaction.commit();
        session.close();
        return patientRegistrations;
    }

    @Override
    public boolean save(Patient_Registration patient_registration) throws SQLException, IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        session.save(patient_registration);
        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public boolean update(Patient_Registration patient_registration) throws SQLException, IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        session.update(patient_registration);
        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public boolean delete(String ID) throws SQLException, IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        Patient_Registration patient_registration = new Patient_Registration();
        patient_registration.setRegistrationId(ID);
        session.remove(patient_registration);


        transaction.commit();
        session.close();

        return true;
    }

    @Override
    public Patient_Registration findById(String patientId ) throws SQLException, ClassNotFoundException, IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        Query<Patient_Registration> query = session.createNativeQuery(
                "SELECT * FROM Patient_Registration WHERE patientId = :pid", Patient_Registration.class);
        query.setParameter("pid", patientId);

        Patient_Registration registration = query.uniqueResult();

        transaction.commit();
        session.close();
        return registration;

    }
//
    @Override
    public boolean updateBalance(String patientId) throws SQLException, ClassNotFoundException, IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        Query query = session.createQuery("UPDATE Patient_Registration pr SET pr.balance = 0 WHERE pr.patient.patientId = :patientId");
        query.setParameter("patientId", patientId);
        int update = query.executeUpdate();
        transaction.commit();
        session.close();

        return update > 0;
    }
    @Override
    public double getBalanceByPatientId(String patientId) throws IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        Double balance = session.createQuery("SELECT pr.balance FROM Patient_Registration pr WHERE pr.patient.patientId = :patientId", Double.class)
                .setParameter("patientId", patientId).uniqueResult();

        transaction.commit();
        session.close();
        return balance != null ? balance : 0.00;
        }


}
