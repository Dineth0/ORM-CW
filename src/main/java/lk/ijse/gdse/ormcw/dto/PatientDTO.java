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
    private String age;
    private int contactNumber;
    private String medicalHistory;




}
