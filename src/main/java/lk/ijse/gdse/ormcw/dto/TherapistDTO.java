package lk.ijse.gdse.ormcw.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TherapistDTO {
    private String therapistId;
    private String therapistName;
    private String specialization;
    private int contactNumber;
    private String programId;



    public TherapistDTO(String therapistId, String therapistName, String specialization, String contactNumber, String programId) {
        this.therapistId = therapistId;
        this.therapistName = therapistName;
        this.specialization = specialization;
        this.contactNumber = Integer.parseInt(contactNumber);
        this.programId = programId;
    }
}
