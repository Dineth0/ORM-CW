package lk.ijse.gdse.ormcw.bo.custom.impl;

import lk.ijse.gdse.ormcw.bo.custom.PatientRegistrationBO;
import lk.ijse.gdse.ormcw.config.FactoryConfiguration;
import lk.ijse.gdse.ormcw.dao.DAOFactory;
import lk.ijse.gdse.ormcw.dao.custom.PatientRegistrationDAO;
import lk.ijse.gdse.ormcw.dto.PatientRegistrationDTO;
import lk.ijse.gdse.ormcw.entity.Patient;
import lk.ijse.gdse.ormcw.entity.Patient_Registration;
import lk.ijse.gdse.ormcw.entity.TherapyProgram;
import org.hibernate.Session;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PatientRegistrationBOImpl implements PatientRegistrationBO {

    PatientRegistrationDAO patientRegistrationDAO = (PatientRegistrationDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.PATIENT_REGISTRATION);

    @Override
    public boolean save(PatientRegistrationDTO patientRegistrationDTO) throws IOException, SQLException {
        Session session = FactoryConfiguration.getInstance().getSession();

        Patient patient = session.get(Patient.class, patientRegistrationDTO.getPatientId());
        TherapyProgram therapyProgram = session.get(TherapyProgram.class, patientRegistrationDTO.getProgramId());

        if (patient == null || therapyProgram == null) {
            return false;
        }

        Patient_Registration patient_registration = new Patient_Registration(
                patientRegistrationDTO.getRegistrationId(),
                patient,
                therapyProgram,
                patientRegistrationDTO.getRegistrationDate(),
                patientRegistrationDTO.getRegisterFee(),
                patientRegistrationDTO.getBalance()
        );
        return patientRegistrationDAO.save(patient_registration);
    }

    @Override
    public String getNextId() throws SQLException, IOException {
        return patientRegistrationDAO.getNextId();
    }

    @Override
    public List<PatientRegistrationDTO> getAll() throws SQLException, IOException {
        List<Patient_Registration> patientRegistrations = patientRegistrationDAO.getAll();
        List<PatientRegistrationDTO> patientRegistrationDTOs = new ArrayList<>();

        for (Patient_Registration patientRegistration : patientRegistrations) {
            PatientRegistrationDTO patientRegistrationDTO = new PatientRegistrationDTO();
            patientRegistrationDTO.setRegistrationId(patientRegistration.getRegistrationId());
            if (patientRegistration.getPatient() != null) {
                patientRegistrationDTO.setPatientId(patientRegistration.getPatient().getPatientId());
            } else {
                patientRegistrationDTO.setPatientId("N/A");
            }
            if (patientRegistration.getTherapyProgram() != null) {
                patientRegistrationDTO.setProgramId(patientRegistration.getTherapyProgram().getProgramId());
            } else {
                patientRegistrationDTO.setProgramId("N/A");
            }
            patientRegistrationDTO.setRegistrationDate(patientRegistration.getRegistrationDate());
            patientRegistrationDTO.setRegisterFee(patientRegistration.getRegisterFee());
            patientRegistrationDTO.setBalance(patientRegistration.getBalance());
            patientRegistrationDTOs.add(patientRegistrationDTO);
        }
        return patientRegistrationDTOs;
    }

    @Override
    public boolean update(PatientRegistrationDTO patientRegistrationDTO) throws IOException, SQLException {

        Session session = FactoryConfiguration.getInstance().getSession();

        Patient patient = session.get(Patient.class, patientRegistrationDTO.getPatientId());
        TherapyProgram therapyProgram = session.get(TherapyProgram.class, patientRegistrationDTO.getProgramId());

        if (patient == null || therapyProgram == null) {
            return false;
        }

        Patient_Registration patient_registration = new Patient_Registration(
                patientRegistrationDTO.getRegistrationId(),
                patient,
                therapyProgram,
                patientRegistrationDTO.getRegistrationDate(),
                patientRegistrationDTO.getRegisterFee(),
                patientRegistrationDTO.getBalance()
        );
        return patientRegistrationDAO.update(patient_registration);    }

    @Override
    public boolean delete(String ID) throws SQLException, IOException {
        return patientRegistrationDAO.delete(ID);
    }

    @Override
    public PatientRegistrationDTO findById(String patientId) throws SQLException, ClassNotFoundException, IOException {
        Patient_Registration reg = patientRegistrationDAO.findById(patientId);
        if (reg == null) return null;

        return new PatientRegistrationDTO(
                reg.getRegistrationId(),
                reg.getPatient().getPatientId(),
                reg.getTherapyProgram(),
                reg.getRegistrationDate(),
                reg.getRegisterFee(),
                reg.getBalance()
        );
    }


    @Override
    public boolean updateBalance(String patientId) throws SQLException, ClassNotFoundException, IOException {
        return patientRegistrationDAO.updateBalance(patientId);
    }
}
