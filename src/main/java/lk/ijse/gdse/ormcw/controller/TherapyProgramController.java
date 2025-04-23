package lk.ijse.gdse.ormcw.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.gdse.ormcw.bo.BOFactory;
import lk.ijse.gdse.ormcw.bo.custom.TherapyProgramBO;
import lk.ijse.gdse.ormcw.dto.PatientDTO;
import lk.ijse.gdse.ormcw.dto.TherapyProgramDTO;
import lk.ijse.gdse.ormcw.tm.PatientTM;
import lk.ijse.gdse.ormcw.tm.TherapyProgramTM;
import lk.ijse.gdse.ormcw.tm.TherapySessionTM;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class TherapyProgramController implements Initializable {

    @FXML
    private TableView<TherapyProgramTM> ProgramTable;

    @FXML
    private Button btndelete;

    @FXML
    private Button btnsave;

    @FXML
    private Button btnupdate;

    @FXML
    private TableColumn<TherapyProgramTM,String> colduration;

    @FXML
    private TableColumn<TherapyProgramTM,Double> colfee;

    @FXML
    private TableColumn<TherapyProgramTM,String> colid;

    @FXML
    private TableColumn<TherapyProgramTM,String> colname;



    @FXML
    private Label lblbirthday;

    @FXML
    private Label lblid;

    @FXML
    private Label lblrole;

    @FXML
    private AnchorPane patientpage;

    @FXML
    private TextField txtduration;

    @FXML
    private TextField txtfee;

    @FXML
    private TextField txtname;


    @FXML
    private TextField txtdes;


    TherapyProgramBO therapyProgramBO = (TherapyProgramBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.THERAPYOROGRAM);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colid.setCellValueFactory(new PropertyValueFactory<>("programId"));
        colname.setCellValueFactory(new PropertyValueFactory<>("programName"));
        colduration.setCellValueFactory(new PropertyValueFactory<>("duration"));
        colfee.setCellValueFactory(new PropertyValueFactory<>("cost"));



        try {
            LoadNextID();
            loadTableData();
            refreshPage();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void DeleteOnAction(ActionEvent event) throws SQLException, IOException, ClassNotFoundException {
        String ID = lblid.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> optionalButtonType = alert.showAndWait();

        if (optionalButtonType.isPresent() && optionalButtonType.get() == ButtonType.YES) {

            boolean isDelete = therapyProgramBO.delete(ID);
            if (isDelete) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "TherapyProgram deleted...!").show();

            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to delete TherapyProgram...!").show();

            }
        }
    }

    @FXML
    void ResetOnAction(ActionEvent event) throws SQLException, IOException, ClassNotFoundException {
        refreshPage();
    }

    @FXML
    void SaveOnAction(ActionEvent event) {
        String programId = lblid.getText();
        String programName = txtname.getText();
        String duration = txtduration.getText();
        double cost = Double.parseDouble(txtfee.getText());


        TherapyProgramDTO therapyProgramDTO = new TherapyProgramDTO(programId, programName, duration, cost);
        try {
            boolean isSaved = therapyProgramBO.save( therapyProgramDTO);
            if(isSaved){
                new Alert(Alert.AlertType.INFORMATION,"Therapy Program Saved SUCCESSFULLY 😎").show();
                refreshPage();
            }
            else {
                new Alert(Alert.AlertType.ERROR,"PLEASE TRY AGAIN 😥").show();
            }
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR,"duplicate Id");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void TableOnClicked(MouseEvent event) {
        TherapyProgramTM therapyProgramTM = ProgramTable.getSelectionModel().getSelectedItem();
        if (therapyProgramTM != null) {
            lblid.setText(therapyProgramTM.getProgramId());
            txtname.setText(therapyProgramTM.getProgramName());
            txtduration.setText(therapyProgramTM.getDuration());
            txtfee.setText(String.valueOf(therapyProgramTM.getCost()));




            btndelete.setDisable(false);
            btnsave.setDisable(true);
            btnupdate.setDisable(false);
        }
    }

    @FXML
    void UpdateOnAction(ActionEvent event) {
        String programId = lblid.getText();
        String programName = txtname.getText();
        String duration = txtduration.getText();
        double cost = Double.parseDouble(txtfee.getText());


        TherapyProgramDTO therapyProgramDTO = new TherapyProgramDTO(programId, programName, duration, cost);
        try {
            boolean isSaved = therapyProgramBO.update( therapyProgramDTO);
            if(isSaved){
                new Alert(Alert.AlertType.INFORMATION,"Therapy Program Saved SUCCESSFULLY 😎").show();
                refreshPage();
            }
            else {
                new Alert(Alert.AlertType.ERROR,"PLEASE TRY AGAIN 😥").show();
            }
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR,"duplicate Id");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    private void LoadNextID() throws SQLException, IOException {
        String nextID = therapyProgramBO.getNextId();
        lblid.setText(nextID);
    }
    private void loadTableData() throws SQLException, ClassNotFoundException, IOException {
        ArrayList<TherapyProgramDTO> therapyProgramDTOS = (ArrayList<TherapyProgramDTO>) therapyProgramBO.getAll();
        ObservableList<TherapyProgramTM> therapyProgramTMS = FXCollections.observableArrayList();

        for (TherapyProgramDTO therapyProgramDTO : therapyProgramDTOS) {
            TherapyProgramTM therapyProgramTM = new TherapyProgramTM(
                    therapyProgramDTO.getProgramId(),
                    therapyProgramDTO.getProgramName(),
                    therapyProgramDTO.getDuration(),
                    therapyProgramDTO.getCost()


            );
            therapyProgramTMS.add(therapyProgramTM);
        }
        ProgramTable.setItems(therapyProgramTMS);
    }
    void refreshPage() throws SQLException, ClassNotFoundException, IOException {
        LoadNextID();
        loadTableData();

        btndelete.setDisable(true);
        btnsave.setDisable(false);
        btnupdate.setDisable(true);

        txtname.setText("");
        txtname.setText("");
        txtduration.setText("");
        txtfee.setText("");


    }

}

