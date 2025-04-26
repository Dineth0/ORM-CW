package lk.ijse.gdse.ormcw.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data

@Entity
@Table(name = "Payment")
public class Payment implements SuperEntity{
    @Id
    private String paymentId;
    private double amount;
    private Date paymentDate;
    private String Status;
    private double totalAmount;
    private Date sessionDate;
    private String sessionTime;

    @ManyToOne
    @JoinColumn(name = "patientId")
    private Patient patient;



    public Payment(String paymentId, Patient patient, double amount, Date paymentDate, String Status, double totalAmount,String sessionDate,String sessionTime) {
        this.paymentId = paymentId;
        this.patient = patient;
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.Status = Status;
        this.totalAmount = totalAmount;
        this.sessionDate = Date.valueOf(sessionDate);
        this.sessionTime = sessionTime;
    }


}
