package lk.ijse.gdse.ormcw.dao.custom;

import lk.ijse.gdse.ormcw.dao.CrudDAO;
import lk.ijse.gdse.ormcw.entity.Payment;

import java.io.IOException;
import java.util.List;

public interface PaymentDAO extends CrudDAO<Payment> {
    public List<Payment> searchPayment(String PatientId) throws IOException;
    public double getPreviousTotalAmount(String patientId) throws IOException;
}
