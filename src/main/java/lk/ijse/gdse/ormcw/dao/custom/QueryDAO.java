package lk.ijse.gdse.ormcw.dao.custom;

import lk.ijse.gdse.ormcw.dao.SuperDAO;
import lk.ijse.gdse.ormcw.entity.Patient;

import java.io.IOException;
import java.util.List;

public interface QueryDAO extends SuperDAO {
    public List<Patient> getPatientsEnrolledInPrograms() throws IOException;
}
