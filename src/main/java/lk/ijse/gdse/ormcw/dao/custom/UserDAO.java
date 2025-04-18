package lk.ijse.gdse.ormcw.dao.custom;

import lk.ijse.gdse.ormcw.dao.CrudDAO;
import lk.ijse.gdse.ormcw.dao.SuperDAO;
import lk.ijse.gdse.ormcw.entity.User;

import java.io.IOException;
import java.sql.SQLException;

public interface UserDAO extends CrudDAO<User> {
    String getUser(String name) throws SQLException, IOException;
}
