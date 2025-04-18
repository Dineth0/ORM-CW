package lk.ijse.gdse.ormcw.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TherapyProgramTM {
    private String programId;
    private String programName;
    private String duration;
    private double cost;
    private String Description;
}
