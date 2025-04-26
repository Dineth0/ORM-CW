package lk.ijse.gdse.ormcw.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TherapySessionTM {
    private String sessionId;
    private Date sessionDate;
    private String sessionTime;
    private String status;
    private String therapistId;
    private String patientId;



}
