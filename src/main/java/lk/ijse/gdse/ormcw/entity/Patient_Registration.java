package lk.ijse.gdse.ormcw.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;


@NoArgsConstructor
@Getter
@Setter

@Entity
@Table(name = "Patient_Registration")
public class Patient_Registration implements SuperEntity{
    @Id
    private String registrationId;

    @ManyToOne
    @JoinColumn(name = "patientId")
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "programId")
    private TherapyProgram therapyProgram;

    private Date registrationDate;
    private int sessionCount;
    private double registerFee;
    private double balance;

    public Patient_Registration(String registrationId, Patient patient, TherapyProgram therapyProgram, Date registrationDate) {
        this.registrationId = registrationId;
        this.patient = patient;
        this.therapyProgram = therapyProgram;
        this.registrationDate = registrationDate;

    }

    public Patient_Registration(String registrationId, String patientId, String programId, Date registrationDate) {
//        this.registrationId = registrationId;
//        this.getPatient().setPatientId(patientId);
//        this.getTherapyProgram().setProgramId(programId);
//        this.registrationDate = registrationDate;
    }

    public Patient_Registration(String registrationId, Patient patient, TherapyProgram therapyProgram, Date registrationDate, double registerFee, double balance) {
        this.registrationId = registrationId;
        this.patient = patient;
        this.therapyProgram = therapyProgram;
        this.registrationDate = registrationDate;
        this.registerFee = registerFee;
        this.balance = balance;
    }


//    public Patient_Registration(String sessionId, Date sessionDate, String sessionTime, String status, String therapistId) {
//        this.sessionId = sessionId;
//        this.sessionDate = sessionDate;
//        this.sessionTime = sessionTime;
//        this.status = status;
//        this.therapist = new Therapist(therapistId,null,null,null);
//    }
}
