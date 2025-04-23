package lk.ijse.gdse.ormcw.bo.custom;

import lk.ijse.gdse.ormcw.bo.SuperBO;
import lk.ijse.gdse.ormcw.dto.PaymentDTO;
import lk.ijse.gdse.ormcw.entity.Payment;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface PaymentBO extends SuperBO {
    public boolean save(PaymentDTO paymentDTO) throws IOException, SQLException, ClassNotFoundException;
    public String getNextId() throws SQLException, IOException;
    public List<PaymentDTO> getAll() throws SQLException, IOException;
    public boolean update(PaymentDTO paymentDTO) throws IOException, SQLException;
    public boolean delete(String ID) throws SQLException, IOException;
    public List<Payment> searchPayment(String name) throws SQLException, IOException, ClassNotFoundException;
    public double getPreviousTotalAmount(String patientId) throws IOException;
    }
