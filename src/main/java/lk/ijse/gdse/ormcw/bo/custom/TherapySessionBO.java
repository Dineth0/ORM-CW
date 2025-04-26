package lk.ijse.gdse.ormcw.bo.custom;

import lk.ijse.gdse.ormcw.bo.SuperBO;
import lk.ijse.gdse.ormcw.dto.TherapySessionDTO;
import lk.ijse.gdse.ormcw.entity.Therapy_Session;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface TherapySessionBO extends SuperBO {
    public boolean save(TherapySessionDTO therapySessionDTO) throws IOException, SQLException;
    public String getNextId() throws SQLException, IOException;
    public List<TherapySessionDTO> getAll() throws SQLException, IOException;
    public boolean update(TherapySessionDTO therapySessionDTO) throws IOException, SQLException;
    public boolean delete(String ID) throws SQLException, IOException;
    public boolean updateStatus(String sessionId) throws SQLException, ClassNotFoundException, IOException;
    public List<Therapy_Session> searchTherapySession(String name) throws SQLException, IOException, ClassNotFoundException;
    public List<Therapy_Session> searchTherapistTherapySession(String therapistId) throws SQLException, IOException, ClassNotFoundException;
    Therapy_Session findLastSessionByPatientId(String patientId) throws SQLException, ClassNotFoundException, IOException;




}
