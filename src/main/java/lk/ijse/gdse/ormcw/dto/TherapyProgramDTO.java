package lk.ijse.gdse.ormcw.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class TherapyProgramDTO {
    private String programId;
    private String programName;
    private String duration;
    private double cost;
    private String Description;

    public TherapyProgramDTO(String programId, String programName, String duration, double cost, String Description) {
        this.programId = programId;
        this.programName = programName;
        this.duration = duration;
        this.cost = cost;
        this.Description = Description;
    }

}
