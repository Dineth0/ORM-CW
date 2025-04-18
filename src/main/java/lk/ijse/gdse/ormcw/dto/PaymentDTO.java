package lk.ijse.gdse.ormcw.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PaymentDTO {
    private String paymentId;
    private String patientId;
    private double amount;
    private Date paymentDate;
    private String Status;

    public PaymentDTO(String paymentId, String patientId, double amount, String paymentDate, String status) {
        this.paymentId = paymentId;
        this.patientId = patientId;
        this.amount = amount;
        this.paymentDate = Date.valueOf(paymentDate);
        this.Status = status;
    }
}
