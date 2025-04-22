package lk.ijse.gdse.ormcw.dao.custom;

import lk.ijse.gdse.ormcw.dao.CrudDAO;
import lk.ijse.gdse.ormcw.entity.Patient;
import lk.ijse.gdse.ormcw.entity.TherapyProgram;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public interface TherapyProgramDAO extends CrudDAO<TherapyProgram> {
    public TherapyProgram findById(String programId) throws SQLException, ClassNotFoundException;
    public ArrayList<String> getAllProgramIDs() throws SQLException, ClassNotFoundException, IOException;
    public int getTotalPrograms() throws SQLException, ClassNotFoundException, IOException;

}
