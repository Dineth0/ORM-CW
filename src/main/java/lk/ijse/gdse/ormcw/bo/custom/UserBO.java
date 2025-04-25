package lk.ijse.gdse.ormcw.bo.custom;

import lk.ijse.gdse.ormcw.bo.SuperBO;
import lk.ijse.gdse.ormcw.dto.UserDTO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface UserBO extends SuperBO {
    String getUser(String name) throws Exception;

    public boolean save(UserDTO userDTO) throws IOException, SQLException;
    public String getNextId() throws SQLException, IOException;
    public List<UserDTO> getAll() throws SQLException, IOException;
    public boolean update(UserDTO userDTO) throws IOException, SQLException;

    }
