package lk.ijse.gdse.ormcw.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

@Entity
@Table(name = "User")
public class User implements SuperEntity{
    @Id
    @Column(name = "UserID")
    private String Id;
    private String UserName;
    private String Password;
    private String Role;

    public User(String id, String userName, String password, String hashedPassword, String role) {
    }
}