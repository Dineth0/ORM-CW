package lk.ijse.gdse.ormcw.bo.custom;

import lk.ijse.gdse.ormcw.bo.SuperBO;
import lk.ijse.gdse.ormcw.dto.PatientRegistrationDTO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface PatientRegistrationBO extends SuperBO {
    public boolean save(PatientRegistrationDTO patientRegistrationDTO) throws IOException, SQLException;
    public String getNextId() throws SQLException, IOException;
    public List<PatientRegistrationDTO> getAll() throws SQLException, IOException;
    public boolean update(PatientRegistrationDTO patientRegistrationDTO) throws IOException, SQLException;
    public boolean delete(String ID) throws SQLException, IOException;
    public boolean updateBalance(String patientId) throws SQLException, ClassNotFoundException, IOException;
    public double getBalanceByPatientId(String patientId) throws IOException;
    public double getRegisterFeeByPatientId(String patientId) throws IOException;


    }
