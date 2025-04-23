package lk.ijse.gdse.ormcw.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TherapistTM {
    private String therapistId;
    private String therapistName;
    private String specialization;
    private int contactNumber;
    private String programId;
}
