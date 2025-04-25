package lk.ijse.gdse.ormcw.dao.custom.impl;

import lk.ijse.gdse.ormcw.config.FactoryConfiguration;
import lk.ijse.gdse.ormcw.dao.custom.QueryDAO;
import lk.ijse.gdse.ormcw.entity.Patient;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.IOException;
import java.util.List;

public class QueryDAOImpl implements QueryDAO {
    @Override
    public List<Patient> getPatientsEnrolledInPrograms() throws IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction tx = session.beginTransaction();

        String hql = "SELECT p \n" +
                "            FROM Patient p \n" +
                "            WHERE \n" +
                "                (SELECT COUNT(DISTINCT pr.therapyProgram.programId) \n" +
                "                 FROM Patient_Registration pr \n" +
                "                 WHERE pr.patient = p) = \n" +
                "                (SELECT COUNT(tp.programId) FROM TherapyProgram tp)";
        List<Patient> patients = session.createQuery(hql, Patient.class).getResultList();
        tx.commit();
        session.close();
        return patients;


    }
}
