package lk.ijse.gdse.ormcw.dao.custom;

import lk.ijse.gdse.ormcw.dao.CrudDAO;
import lk.ijse.gdse.ormcw.dto.PatientRegistrationDTO;
import lk.ijse.gdse.ormcw.entity.Patient_Registration;

import java.io.IOException;
import java.sql.SQLException;

public interface PatientRegistrationDAO extends CrudDAO<Patient_Registration> {
    public Patient_Registration findById(String patientId) throws SQLException, ClassNotFoundException, IOException;
    public boolean updateBalance(String patientId) throws SQLException, ClassNotFoundException, IOException;
    public double getBalanceByPatientId(String patientId) throws IOException;
    public double getRegisterFeeByPatientId(String patientId) throws IOException;

    }
