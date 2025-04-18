package lk.ijse.gdse.ormcw.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDTO {
    private String Id;
    private String UserName;
    private String Password;
    private String Role;
}
