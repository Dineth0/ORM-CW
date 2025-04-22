package lk.ijse.gdse.ormcw.bo.custom;

import lk.ijse.gdse.ormcw.bo.SuperBO;
import lk.ijse.gdse.ormcw.dto.PatientDTO;
import lk.ijse.gdse.ormcw.dto.TherapyProgramDTO;
import lk.ijse.gdse.ormcw.entity.Patient;
import lk.ijse.gdse.ormcw.entity.TherapyProgram;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface TherapyProgramBO extends SuperBO {
    public TherapyProgramDTO findById(String programId) throws SQLException, ClassNotFoundException;

    public ArrayList<String> getAllProgramIDs() throws SQLException, ClassNotFoundException, IOException;

    public String getNextId() throws SQLException, IOException;

    public List<TherapyProgramDTO> getAll() throws SQLException, IOException;

    public boolean save(TherapyProgramDTO therapyProgramDTO) throws SQLException, IOException;

    public boolean update(TherapyProgramDTO therapyProgramDTO) throws SQLException, IOException;

    public boolean delete(String ID) throws SQLException, IOException;

    public int getTotalPrograms() throws SQLException, ClassNotFoundException, IOException;





    }
