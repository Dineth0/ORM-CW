package lk.ijse.gdse.ormcw.dao.custom.impl;

import lk.ijse.gdse.ormcw.config.FactoryConfiguration;
import lk.ijse.gdse.ormcw.dao.custom.TherapyProgramDAO;
import lk.ijse.gdse.ormcw.entity.Patient;
import lk.ijse.gdse.ormcw.entity.TherapyProgram;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TherapyProgramDAOImpl implements TherapyProgramDAO {
    @Override
    public TherapyProgram findById(String programId) throws SQLException, ClassNotFoundException {
        TherapyProgram therapyProgram = null;

        try (Session session = FactoryConfiguration.getInstance().getSession()) {
            // Fetch the Student entity using the primary key
            therapyProgram = session.get(TherapyProgram.class, programId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to fetch the student by ID: " + programId);
        }

        return therapyProgram;
    }

    @Override
    public ArrayList<String> getAllProgramIDs() throws SQLException, ClassNotFoundException, IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        String hql = ("SELECT programId FROM TherapyProgram");

        Query query = session.createQuery(hql);
        ArrayList<String> list = (ArrayList<String>) query.list();

        transaction.commit();
        session.close();
        return list;
    }

    @Override
    public String getNextId() throws SQLException, IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        String hql = "SELECT l.programId FROM TherapyProgram l ORDER BY l.programId DESC";
        Query<String> query = session.createQuery(hql);
        query.setMaxResults(1);
        String lastId = query.uniqueResult();

        transaction.commit();
        session.close();

        if (lastId != null) {
            String substring = lastId.substring(1);
            int i = Integer.parseInt(substring);
            int newIdIndex = i + 1;
            return String.format("G%03d", newIdIndex);
        }

        return "G001";
    }

    @Override
    public List<TherapyProgram> getAll() throws SQLException, IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        Query<TherapyProgram> query = session.createQuery("SELECT p FROM TherapyProgram p", TherapyProgram.class);
        List<TherapyProgram> therapyPrograms = query.list();

        transaction.commit();
        session.close();
        return therapyPrograms;
    }

    @Override
    public boolean save(TherapyProgram therapyProgram) throws SQLException, IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        session.save(therapyProgram);

        transaction.commit();
        session.close();

        return true;

    }

    @Override
    public boolean update(TherapyProgram therapyProgram) throws SQLException, IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        session.update(therapyProgram);

        transaction.commit();
        session.close();

        return true;
    }

    @Override
    public boolean delete(String ID) throws SQLException, IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        TherapyProgram therapyProgram = new TherapyProgram();
        therapyProgram.setProgramId(ID);
        session.remove(therapyProgram);


        transaction.commit();
        session.close();

        return true;
    }
    @Override
    public int getTotalPrograms() throws SQLException, ClassNotFoundException, IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        Query<Long> query = session.createQuery("SELECT COUNT(tp) FROM TherapyProgram tp", Long.class);
        Long total = query.uniqueResult();

        transaction.commit();
        session.close();
        return total != null ? total.intValue() : 0;
    }
}
