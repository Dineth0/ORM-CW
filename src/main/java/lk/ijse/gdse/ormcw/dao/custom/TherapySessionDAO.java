package lk.ijse.gdse.ormcw.dao.custom;

import lk.ijse.gdse.ormcw.dao.CrudDAO;
import lk.ijse.gdse.ormcw.entity.Therapy_Session;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface TherapySessionDAO extends CrudDAO<Therapy_Session> {
    public List<Therapy_Session> getSessionByPatientId(String patientId) throws SQLException, ClassNotFoundException, IOException;
    public boolean updateStatus(String sessionId) throws SQLException, ClassNotFoundException, IOException;
    public List<Therapy_Session> getTherapistById(String therapistId) throws SQLException, ClassNotFoundException, IOException;

    }
