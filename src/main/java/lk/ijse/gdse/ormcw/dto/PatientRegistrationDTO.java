package lk.ijse.gdse.ormcw.dto;

import lk.ijse.gdse.ormcw.entity.TherapyProgram;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PatientRegistrationDTO {
    private String registrationId;
    private String patientId;
    private String programId;
    private Date registrationDate;
    private double registerFee;
    private double balance;


    public PatientRegistrationDTO(String registrationId, String patientId, String programId, String registrationDate) {
        this.registrationId = registrationId;
        this.patientId = patientId;
        this.programId = programId;
        this.registrationDate = Date.valueOf(registrationDate);

    }

    public PatientRegistrationDTO(String registrationId, String patientId, String programId, String registrationDate, double registerFee, double balance) {
        this.registrationId = registrationId;
        this.patientId = patientId;
        this.programId = programId;
        this.registrationDate = Date.valueOf(registrationDate);
        this.registerFee = registerFee;
        this.balance = balance;
    }

    public PatientRegistrationDTO(String registrationId, String patientId, TherapyProgram therapyProgram, Date registrationDate, double registerFee, double balance) {
    }
}
