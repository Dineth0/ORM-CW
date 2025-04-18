package lk.ijse.gdse.ormcw.dao.custom;

import lk.ijse.gdse.ormcw.dao.CrudDAO;
import lk.ijse.gdse.ormcw.entity.Patient;
import lk.ijse.gdse.ormcw.entity.Therapist;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface PatientDAO extends CrudDAO<Patient> {
    public Patient findById(String patientId) throws SQLException, ClassNotFoundException;
    public ArrayList<String> getAllPatientIDs() throws SQLException, ClassNotFoundException, IOException;
    public int getTotalPatients() throws SQLException, ClassNotFoundException, IOException;
    public Object[] getPatientsBySessionId(String sessionId) throws SQLException, ClassNotFoundException, IOException;
    public Patient getPatientByName(String name) throws SQLException, ClassNotFoundException, IOException;


    }
