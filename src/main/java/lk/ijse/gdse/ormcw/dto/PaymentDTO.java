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
    private double totalAmount;

    public PaymentDTO(String paymentId, String patientId, double amount, String paymentDate, String Status,double totalAmount) {
        this.paymentId = paymentId;
        this.patientId = patientId;
        this.amount = amount;
        this.paymentDate = Date.valueOf(paymentDate);
        this.Status = Status;
        this.totalAmount = totalAmount;
    }

}
