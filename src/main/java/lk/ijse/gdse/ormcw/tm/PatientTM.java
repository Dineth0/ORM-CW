package lk.ijse.gdse.ormcw.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PatientTM {
    private String patientId;
    private String name;
    private Date birthday;
    private int contactNumber;
    private String medicalHistory;

}
