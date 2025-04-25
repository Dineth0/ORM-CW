package lk.ijse.gdse.ormcw.bo.custom.impl;

import lk.ijse.gdse.ormcw.bo.custom.TherapySessionBO;
import lk.ijse.gdse.ormcw.config.FactoryConfiguration;
import lk.ijse.gdse.ormcw.dao.DAOFactory;
import lk.ijse.gdse.ormcw.dao.custom.PatientDAO;
import lk.ijse.gdse.ormcw.dao.custom.TherapistDAO;
import lk.ijse.gdse.ormcw.dao.custom.TherapySessionDAO;
import lk.ijse.gdse.ormcw.dto.TherapySessionDTO;
import lk.ijse.gdse.ormcw.entity.Patient;
import lk.ijse.gdse.ormcw.entity.Therapist;
import lk.ijse.gdse.ormcw.entity.Therapy_Session;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TherapySessionBOImpl implements TherapySessionBO {

    TherapySessionDAO therapySessionDAO = (TherapySessionDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.THERAPY_SESSION);
    PatientDAO patientDAO = (PatientDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.PATIENT);
    TherapistDAO therapistDAO = (TherapistDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.THERAPIST);

    @Override
    public boolean save(TherapySessionDTO therapySessionDTO) throws IOException, SQLException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        try {

            Therapist therapist = session.get(Therapist.class, therapySessionDTO.getTherapistId());
            Patient patient = session.get(Patient.class, therapySessionDTO.getPatientId());
            if (therapist == null || patient == null) {
                return false;
            }


            Therapy_Session therapySession = new Therapy_Session(
                    therapySessionDTO.getSessionId(),
                    Date.valueOf(therapySessionDTO.getSessionDate().toLocalDate()),
                    therapySessionDTO.getSessionTime(),
                    therapySessionDTO.getStatus(),
                    therapist,
                    patient,
                    therapySessionDTO.getPayment()
            );

            boolean isSaved = therapySessionDAO.save(therapySession);
            if (!isSaved) {
                transaction.rollback();
                return false;
            }

            transaction.commit();
            return true;
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
            return false;
        } finally {
            session.close();
        }
    }


    @Override
    public String getNextId() throws SQLException, IOException, lk.ijse.gdse.ormcw.bo.exception.ClassNotFoundException {
        return therapySessionDAO.getNextId();
    }

    @Override
    public List<TherapySessionDTO> getAll() throws SQLException, IOException {
        List<Therapy_Session> therapy_sessions = therapySessionDAO.getAll();
        List<TherapySessionDTO> therapySessionDTOS = new ArrayList<>();

        for (Therapy_Session therapy_session : therapy_sessions) {
            TherapySessionDTO therapySessionDTO = new TherapySessionDTO();
            therapySessionDTO.setSessionId(therapy_session.getSessionId());
            therapySessionDTO.setSessionDate(therapy_session.getSessionDate());
            therapySessionDTO.setSessionTime(therapy_session.getSessionTime());
            therapySessionDTO.setStatus(therapy_session.getStatus());
            if (therapy_session.getTherapist() != null) {
                therapySessionDTO.setTherapistId(therapy_session.getTherapist().getTherapistId());
            } else {
                therapySessionDTO.setTherapistId("N/A");
            }
            if (therapy_session.getPatient() != null) {
                therapySessionDTO.setPatientId(therapy_session.getPatient().getPatientId());
            } else {
                therapySessionDTO.setPatientId("N/A");
            }
            therapySessionDTO.setPayment(therapy_session.getPayment());
            therapySessionDTOS.add(therapySessionDTO);
        }
        return therapySessionDTOS;
    }

    @Override
    public boolean update(TherapySessionDTO therapySessionDTO) throws IOException, SQLException {

        Session session = FactoryConfiguration.getInstance().getSession();

        Therapist therapist = session.get(Therapist.class, therapySessionDTO.getTherapistId());
        Patient patient = session.get(Patient.class, therapySessionDTO.getPatientId());

        if (therapist == null || patient == null) {
            return false;
        }

        Therapy_Session therapySession = new Therapy_Session(
                therapySessionDTO.getSessionId(),
                Date.valueOf(therapySessionDTO.getSessionDate().toLocalDate()),
                therapySessionDTO.getSessionTime(),
                therapySessionDTO.getStatus(),
                therapist,
                patient,
                therapySessionDTO.getPayment()
        );

        return therapySessionDAO.update(therapySession);
    }

    @Override
    public boolean delete(String ID) throws SQLException, IOException {
        return therapySessionDAO.delete(ID);
    }


    @Override
    public boolean updateStatus(String sessionId) throws SQLException, ClassNotFoundException, IOException {
        return therapySessionDAO.updateStatus(sessionId);
    }

    @Override
    public List<Therapy_Session> searchTherapySession(String name) throws SQLException, IOException, ClassNotFoundException {
        Patient patient = patientDAO.getPatientByName(name);

        if (patient != null) {
            return therapySessionDAO.getSessionByPatientId(patient.getPatientId());
        }else {
            return null;
        }
    }
    @Override
    public List<Therapy_Session> searchTherapistTherapySession(String therapistId) throws SQLException, IOException, ClassNotFoundException {

        Therapist therapist = therapistDAO.getTherapistById(therapistId);
        if (therapist != null) {
            return therapySessionDAO.getTherapistById(therapist.getTherapistId());
        }else {
            return null;
        }
    }

}
