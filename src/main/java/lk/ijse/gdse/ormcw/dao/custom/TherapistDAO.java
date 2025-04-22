package lk.ijse.gdse.ormcw.dao.custom;

import lk.ijse.gdse.ormcw.dao.CrudDAO;
import lk.ijse.gdse.ormcw.entity.Therapist;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public interface TherapistDAO extends CrudDAO<Therapist> {
    public Therapist findById(String therapistId) throws SQLException, ClassNotFoundException;
    public ArrayList<String> getAllTherapistIDs() throws SQLException, ClassNotFoundException, IOException;
    public int getTotalTherapists() throws SQLException, ClassNotFoundException, IOException;
    public Therapist getTherapistById(String therapistId) throws SQLException, ClassNotFoundException, IOException;

    }
