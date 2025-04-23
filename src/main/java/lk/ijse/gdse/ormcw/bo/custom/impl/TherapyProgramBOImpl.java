package lk.ijse.gdse.ormcw.bo.custom.impl;

import lk.ijse.gdse.ormcw.bo.custom.TherapyProgramBO;
import lk.ijse.gdse.ormcw.dao.DAOFactory;
import lk.ijse.gdse.ormcw.dao.custom.TherapyProgramDAO;
import lk.ijse.gdse.ormcw.dto.PatientDTO;
import lk.ijse.gdse.ormcw.dto.TherapyProgramDTO;
import lk.ijse.gdse.ormcw.entity.Patient;
import lk.ijse.gdse.ormcw.entity.TherapyProgram;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TherapyProgramBOImpl implements TherapyProgramBO {

    TherapyProgramDAO therapyProgramDAO = (TherapyProgramDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.THERAPYOROGRAM);

    @Override
    public TherapyProgramDTO findById(String programId) throws SQLException, ClassNotFoundException {
        TherapyProgram therapyProgram = therapyProgramDAO.findById(programId);
        return new TherapyProgramDTO(therapyProgram.getProgramId(),therapyProgram.getProgramName(),therapyProgram.getDuration(),therapyProgram.getCost());
    }

    @Override
    public ArrayList<String> getAllProgramIDs() throws SQLException, ClassNotFoundException, IOException {
        ArrayList<String> allIds = new ArrayList<>();
        ArrayList<String>all = therapyProgramDAO.getAllProgramIDs();
        for(String p: all){
            allIds.add(p);

        }
        return allIds;
    }


    @Override
    public String getNextId() throws SQLException, IOException {
        return therapyProgramDAO.getNextId();
    }

    @Override
    public List<TherapyProgramDTO> getAll() throws SQLException, IOException {
        List<TherapyProgram> therapyPrograms = therapyProgramDAO.getAll();
        List<TherapyProgramDTO> therapyProgramDTOS = new ArrayList<>();

        for (TherapyProgram therapyProgram : therapyPrograms) {
            TherapyProgramDTO therapyProgramDTO = new TherapyProgramDTO();
            therapyProgramDTO.setProgramId(therapyProgram.getProgramId());
            therapyProgramDTO.setProgramName(therapyProgram.getProgramName());
            therapyProgramDTO.setDuration(therapyProgram.getDuration());
            therapyProgramDTO.setCost(therapyProgram.getCost());
            therapyProgramDTOS.add(therapyProgramDTO);
        }
        return therapyProgramDTOS;
    }

    @Override
    public boolean save(TherapyProgramDTO therapyProgramDTO) throws SQLException, IOException {
        return therapyProgramDAO.save(new TherapyProgram(therapyProgramDTO.getProgramId(),therapyProgramDTO.getProgramName(),therapyProgramDTO.getDuration(),therapyProgramDTO.getCost()));
    }

    @Override
    public boolean update(TherapyProgramDTO therapyProgramDTO) throws SQLException, IOException {
        return therapyProgramDAO.update(new TherapyProgram(therapyProgramDTO.getProgramId(),therapyProgramDTO.getProgramName(),therapyProgramDTO.getDuration(),therapyProgramDTO.getCost()));

    }

    @Override
    public boolean delete(String ID) throws SQLException, IOException {
        return therapyProgramDAO.delete(ID);
    }

    @Override
    public int getTotalPrograms() throws SQLException, ClassNotFoundException, IOException {
        return therapyProgramDAO.getTotalPrograms();
    }
}
