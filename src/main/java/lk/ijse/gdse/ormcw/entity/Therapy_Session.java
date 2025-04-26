package lk.ijse.gdse.ormcw.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

@Entity
@Table(name = "Therapy_Session")
public class Therapy_Session implements SuperEntity{
    @Id
    private String sessionId;

    @ManyToOne

    @JoinColumn(name = "patientId")
    private Patient patient;

    @ManyToOne

    @JoinColumn(name = "therapistId")
    private Therapist therapist;

    private Date sessionDate;
    private String sessionTime;
    private String status;


    public Therapy_Session(String sessionId, Date sessionDate, String sessionTime, String status, Therapist therapist, Patient patient) {
        this.sessionId = sessionId;
        this.sessionDate = sessionDate;
        this.sessionTime = sessionTime;
        this.status = status;
        this.therapist = therapist;
        this.patient = patient;

    }



}
