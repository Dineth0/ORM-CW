package lk.ijse.gdse.ormcw.bo.custom.impl;

import lk.ijse.gdse.ormcw.bo.custom.PatientBO;
import lk.ijse.gdse.ormcw.dao.DAOFactory;
import lk.ijse.gdse.ormcw.dao.custom.PatientDAO;
import lk.ijse.gdse.ormcw.dto.PatientDTO;
import lk.ijse.gdse.ormcw.dto.UserDTO;
import lk.ijse.gdse.ormcw.entity.Patient;
import lk.ijse.gdse.ormcw.entity.User;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PatientBOImpl implements PatientBO {

    PatientDAO patientDAO = (PatientDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.PATIENT);
    @Override
    public boolean save(PatientDTO patientDTO) throws IOException, SQLException {
        System.out.println("PatientBOImpl Saving ID: " + patientDTO.getPatientId()); // Debugging

        return patientDAO.save(new Patient(patientDTO.getPatientId(),patientDTO.getName(),patientDTO.getAge(),patientDTO.getContactNumber(),patientDTO.getMedicalHistory()));
    }

    @Override
    public String getNextId() throws SQLException, IOException {
        return patientDAO.getNextId();
    }

    @Override
    public List<PatientDTO> getAll() throws SQLException, IOException {
        List<Patient> patients = patientDAO.getAll();
        List<PatientDTO> patientDTOS = new ArrayList<>();

        for (Patient patient : patients) {
            PatientDTO patientDTO = new PatientDTO();
            patientDTO.setPatientId(patient.getPatientId());
            patientDTO.setName(patient.getName());
            patientDTO.setAge(patient.getAge());
            patientDTO.setContactNumber(patient.getContactNumber());
            patientDTO.setMedicalHistory(patient.getMedicalHistory());

            patientDTOS.add(patientDTO);
        }
        return patientDTOS;
    }

    @Override
    public boolean update(PatientDTO patientDTO) throws IOException, SQLException {
        return patientDAO.update(new Patient(patientDTO.getPatientId(),patientDTO.getName(),patientDTO.getAge(),patientDTO.getContactNumber(),patientDTO.getMedicalHistory()));

    }

    @Override
    public boolean delete(String ID) throws SQLException, IOException {
        return patientDAO.delete(ID);
    }

    @Override
    public PatientDTO findById(String patientId) throws SQLException, ClassNotFoundException {
        Patient patient = patientDAO.findById(patientId);
        return new PatientDTO(patient.getPatientId(), patient.getName(), patient.getAge(), patient.getContactNumber(), patient.getMedicalHistory());
    }

    @Override
    public ArrayList<String> getAllPatientIds() throws SQLException, ClassNotFoundException, IOException {
        ArrayList<String> allIds = new ArrayList<>();
        ArrayList<String>all = patientDAO.getAllPatientIDs();
        for(String p: all){
            allIds.add(p);

        }
        return allIds;
    }

    @Override
    public int getTotalPatients() throws SQLException, ClassNotFoundException, IOException {
        return patientDAO.getTotalPatients();
    }

    @Override
    public List<Object[]> getPatientsBySessionId(String sessionId) throws SQLException, ClassNotFoundException, IOException {
        return List.of();
    }

}
