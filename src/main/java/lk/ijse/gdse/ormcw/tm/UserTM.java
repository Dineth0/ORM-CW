package lk.ijse.gdse.ormcw.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public class UserTM {
        private String UserID;
        private String UserName;
        private String Password;
        private String Role;
    }

