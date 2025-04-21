package lk.ijse.gdse.ormcw.bo.custom.impl;

import lk.ijse.gdse.ormcw.bo.BOFactory;
import lk.ijse.gdse.ormcw.bo.custom.PatientRegistrationBO;
import lk.ijse.gdse.ormcw.bo.custom.PaymentBO;
import lk.ijse.gdse.ormcw.bo.custom.TherapySessionBO;
import lk.ijse.gdse.ormcw.config.FactoryConfiguration;
import lk.ijse.gdse.ormcw.dao.DAOFactory;
import lk.ijse.gdse.ormcw.dao.custom.PatientRegistrationDAO;
import lk.ijse.gdse.ormcw.dao.custom.PaymentDAO;
import lk.ijse.gdse.ormcw.dao.custom.impl.PaymentDAOImpl;
import lk.ijse.gdse.ormcw.dto.PaymentDTO;
import lk.ijse.gdse.ormcw.dto.TherapistDTO;
import lk.ijse.gdse.ormcw.entity.Patient;
import lk.ijse.gdse.ormcw.entity.Patient_Registration;
import lk.ijse.gdse.ormcw.entity.Payment;
import lk.ijse.gdse.ormcw.entity.Therapist;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PaymentBOImpl implements PaymentBO {

    PaymentDAO paymentDAO = (PaymentDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.PAYMENT);
    PatientRegistrationBO patientRegistrationBO = (PatientRegistrationBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.PATIENT_REGISTRATION);
    TherapySessionBO therapySessionBO = (TherapySessionBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.THERAPY_SESSION);
    PatientRegistrationDAO patientRegistrationDAO = (PatientRegistrationDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.PATIENT_REGISTRATION);

    @Override
    public boolean save(PaymentDTO paymentDTO) throws IOException, SQLException, ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        try{
            Patient patient = session.get(Patient.class, paymentDTO.getPatientId());
            if (patient == null) {
                return false;
            }
            Payment payment = new Payment(
                    paymentDTO.getPaymentId(),
                    patient,
                    paymentDTO.getAmount(),
                    paymentDTO.getPaymentDate(),
                    paymentDTO.getStatus()
            );
            session.save(payment);

                Patient_Registration patient_registration = patientRegistrationDAO.findById(paymentDTO.getPatientId());
                if(patient_registration == null) {
                    transaction.rollback();
                    return false;
                }
                double currentBalance = patient_registration.getBalance();
                double newBalance = currentBalance - paymentDTO.getAmount();
                if(newBalance < 0) {
                    transaction.rollback();
                    return false;
                }
                patient_registration.setBalance(newBalance);
                session.update(patient_registration);

            transaction.commit();
            return true;

        }catch (Exception e){
            transaction.rollback();
            e.printStackTrace();
            return false;
        }finally {
            session.close();
        }

    }


    @Override
    public String getNextId() throws SQLException, IOException {
        return paymentDAO.getNextId();
    }

    @Override
    public List<PaymentDTO> getAll() throws SQLException, IOException {
        List<Payment> payments = paymentDAO.getAll();
        List<PaymentDTO> paymentDTOS = new ArrayList<>();

        for (Payment payment : payments) {
            PaymentDTO paymentDTO = new PaymentDTO();
            paymentDTO.setPaymentId(payment.getPaymentId());
            if (payment.getPatient() != null) {
                paymentDTO.setPatientId(payment.getPatient().getPatientId());
            }else {
                paymentDTO.setPatientId("N/A");
            }
            paymentDTO.setAmount(payment.getAmount());
            paymentDTO.setPaymentDate(payment.getPaymentDate());
            paymentDTO.setStatus(payment.getStatus());
            paymentDTOS.add(paymentDTO);
        }
        return paymentDTOS;
    }

    @Override
    public boolean update(PaymentDTO paymentDTO) throws IOException, SQLException {
        Session session = FactoryConfiguration.getInstance().getSession();

        Patient patient = session.get(Patient.class, paymentDTO.getPatientId());
        if (patient == null) {
            return false;
        }
        Payment payment = new Payment(
                paymentDTO.getPaymentId(),
                patient,
                paymentDTO.getAmount(),
                paymentDTO.getPaymentDate(),
                paymentDTO.getStatus()
        );
        return paymentDAO.update(payment);    }

    @Override
    public boolean delete(String ID) throws SQLException, IOException {
        return paymentDAO.delete(ID);
    }
}
