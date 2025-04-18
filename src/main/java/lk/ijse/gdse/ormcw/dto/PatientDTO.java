package lk.ijse.gdse.ormcw.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PatientDTO {
    private String patientId;
    private String name;
    private Date birthday;
    private int contactNumber;
    private String medicalHistory;


    public PatientDTO(String patientId, String name, String birthday, int contactNumber, String medicalHistory) {
        System.out.println("Inside PatientDTO Constructor - ID: " + patientId); // Debugging
        this.patientId = patientId;
        this.name = name;
        this.birthday = Date.valueOf(birthday);
        this.contactNumber = contactNumber;
        this.medicalHistory = medicalHistory;
    }

}
