package lk.ijse.gdse.ormcw.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PaymentTM {

    private String patientId;
    private String paymentId;
    private double amount;
    private Date paymentDate;
    private String Status;
    private double totalAmount;
    private Date sessionDate;
    private String sessionTime;

}
