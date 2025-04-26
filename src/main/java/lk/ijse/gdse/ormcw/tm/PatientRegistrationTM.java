package lk.ijse.gdse.ormcw.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PatientRegistrationTM {
    private String registrationId;
    private String patientId;
    private String programId;
    private Date registrationDate;
    private double registerFee;
    private double balance;


}
