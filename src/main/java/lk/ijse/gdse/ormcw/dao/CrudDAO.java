package lk.ijse.gdse.ormcw.dao;

import lk.ijse.gdse.ormcw.entity.SuperEntity;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface CrudDAO<T> extends SuperDAO {
    String getNextId() throws SQLException, IOException;
    List<T> getAll() throws SQLException, IOException;
    boolean save(T entity) throws SQLException, IOException;
    boolean update(T entity) throws SQLException, IOException;
    boolean delete(String ID) throws SQLException, IOException;
}
