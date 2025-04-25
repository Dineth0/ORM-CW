package lk.ijse.gdse.ormcw.bo.custom.impl;

import lk.ijse.gdse.ormcw.bo.custom.TherapistBO;
import lk.ijse.gdse.ormcw.config.FactoryConfiguration;
import lk.ijse.gdse.ormcw.dao.DAOFactory;
import lk.ijse.gdse.ormcw.dao.custom.TherapistDAO;
import lk.ijse.gdse.ormcw.dto.TherapistDTO;
import lk.ijse.gdse.ormcw.entity.Therapist;
import lk.ijse.gdse.ormcw.entity.TherapyProgram;
import org.hibernate.Session;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TherapistBOImpl implements TherapistBO {

    TherapistDAO therapistDAO = (TherapistDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.THERAPIST);
    @Override
    public boolean save(TherapistDTO therapistDTO) throws IOException, SQLException {
        Session session = FactoryConfiguration.getInstance().getSession();

        TherapyProgram therapyProgram = session.get(TherapyProgram.class, therapistDTO.getProgramId());
        if ( therapyProgram == null) {
            return false;
        }
        Therapist therapist = new Therapist(
                therapistDTO.getTherapistId(),
                therapistDTO.getTherapistName(),
                therapistDTO.getSpecialization(),
                therapistDTO.getContactNumber(),
                therapyProgram
        );
            return therapistDAO.save(therapist);
    }

    @Override
    public String getNextId() throws SQLException, IOException {
        return therapistDAO.getNextId();
    }

    @Override
    public TherapistDTO findById(String therapistId) throws SQLException, ClassNotFoundException {
        Therapist therapist = therapistDAO.findById(therapistId);

        String programId = null;
        if(therapist.getTherapyProgram() != null){
            programId = therapist.getTherapyProgram().getProgramId();
        }
        return new TherapistDTO(
                therapist.getTherapistId(),
                therapist.getTherapistName(),
                therapist.getSpecialization(),
                therapist.getContactNumber(),
                programId
        );
    }

    @Override
    public ArrayList<String> getAllTherapistIDs() throws SQLException, ClassNotFoundException, IOException {
        ArrayList<String> allIds = new ArrayList<>();
        ArrayList<String>all = therapistDAO.getAllTherapistIDs();
        for(String p: all){
            allIds.add(p);

        }
        return allIds;
    }

    @Override
    public List<TherapistDTO> getAll() throws SQLException, IOException {
        List<Therapist> therapists = therapistDAO.getAll();
        List<TherapistDTO> therapistDTOS = new ArrayList<>();

        for (Therapist therapist : therapists) {
            TherapistDTO therapistDTO = new TherapistDTO();
            therapistDTO.setTherapistId(therapist.getTherapistId());
            therapistDTO.setTherapistName(therapist.getTherapistName());
            therapistDTO.setSpecialization(therapist.getSpecialization());
            therapistDTO.setContactNumber(therapist.getContactNumber());
            if (therapist.getTherapyProgram() != null) {
                therapistDTO.setProgramId(therapist.getTherapyProgram().getProgramId());
            } else {
                therapistDTO.setProgramId("N/A");
            }
            therapistDTOS.add(therapistDTO);
        }
        return therapistDTOS;
    }

    @Override
    public boolean update(TherapistDTO therapistDTO) throws IOException, SQLException {
        Session session = FactoryConfiguration.getInstance().getSession();

        TherapyProgram therapyProgram = session.get(TherapyProgram.class, therapistDTO.getProgramId());
        if ( therapyProgram == null) {
            return false;
        }
        Therapist therapist = new Therapist(
                therapistDTO.getTherapistId(),
                therapistDTO.getTherapistName(),
                therapistDTO.getSpecialization(),
                therapistDTO.getContactNumber(),
                therapyProgram
        );
        return therapistDAO.update(therapist);    }

    @Override
    public boolean delete(String ID) throws SQLException, IOException {
        return therapistDAO.delete(ID);
    }

    @Override
    public int getTotalTherapists() throws SQLException, ClassNotFoundException, IOException {
        return therapistDAO.getTotalTherapists();
    }
}

