package lk.ijse.gdse.ormcw.bo.custom;

import lk.ijse.gdse.ormcw.bo.SuperBO;
import lk.ijse.gdse.ormcw.dto.PatientRegistrationDTO;
import lk.ijse.gdse.ormcw.dto.PaymentDTO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface PaymentBO extends SuperBO {
    public boolean save(PaymentDTO paymentDTO) throws IOException, SQLException, ClassNotFoundException;
    public String getNextId() throws SQLException, IOException;
    public List<PaymentDTO> getAll() throws SQLException, IOException;
    public boolean update(PaymentDTO paymentDTO) throws IOException, SQLException;
    public boolean delete(String ID) throws SQLException, IOException;
}
