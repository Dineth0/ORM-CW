package lk.ijse.gdse.ormcw.controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.gdse.ormcw.bo.BOFactory;
import lk.ijse.gdse.ormcw.bo.custom.TherapistBO;
import lk.ijse.gdse.ormcw.bo.custom.TherapyProgramBO;
import lk.ijse.gdse.ormcw.dto.PatientDTO;
import lk.ijse.gdse.ormcw.dto.TherapistDTO;
import lk.ijse.gdse.ormcw.dto.TherapyProgramDTO;
import lk.ijse.gdse.ormcw.dto.UserDTO;
import lk.ijse.gdse.ormcw.tm.PatientTM;
import lk.ijse.gdse.ormcw.tm.TherapistTM;
import lk.ijse.gdse.ormcw.util.Regex;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class TherapistManageController  implements Initializable {

        @FXML
        private TableView<TherapistTM> TherapistTable;

        @FXML
        private Button btndelete;

        @FXML
        private Button btnsave;

        @FXML
        private Button btnupdate;

        @FXML
        private TableColumn<TherapistTM,Integer> colnumber;

        @FXML
        private TableColumn<TherapistTM,String> colid;

        @FXML
        private TableColumn<TherapistTM,String> colname;

        @FXML
        private TableColumn<TherapistTM,String> colspecail;

        @FXML
        private Label lblbirthday;

        @FXML
        private Label lblid;

        @FXML
        private Label lblregister;

        @FXML
        private Label lblrole;

        @FXML
        private AnchorPane patientpage;

        @FXML
        private TextField txtnumber;

        @FXML
        private TextField txtname;

        @FXML
        private TextField txtspecail;

        @FXML
        private Label lblprogram;


        @FXML
        private TableColumn<TherapistTM,String> colprogram;

        @FXML
        private ComboBox<String> comoProgram;

        TherapistBO therapistBO = (TherapistBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.THERAPIST);
        TherapyProgramBO therapyProgramBO = (TherapyProgramBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.THERAPYOROGRAM);

        @Override
        public void initialize(URL url, ResourceBundle resourceBundle) {
                colid.setCellValueFactory(new PropertyValueFactory<>("therapistId"));
                colname.setCellValueFactory(new PropertyValueFactory<>("therapistName"));
                colspecail.setCellValueFactory(new PropertyValueFactory<>("specialization"));
                colnumber.setCellValueFactory(new PropertyValueFactory<>("contactNumber"));
                colprogram.setCellValueFactory(new PropertyValueFactory<>("programId"));

                try {
                        LoadNextID();
                        loadTableData();
                        loadProgramIDs();
                } catch (Exception e) {
                        throw new RuntimeException(e);
                }
        }
        @FXML
        void ComboProgramOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
                String selectedId = comoProgram.getValue();
                TherapyProgramDTO therapyProgramDTO = therapyProgramBO.findById(selectedId);
                if (therapyProgramDTO != null) {
                        lblprogram.setText(therapyProgramDTO.getProgramName());
                }
        }

        @FXML
        void DeleteOnAction(ActionEvent event) throws SQLException, IOException, ClassNotFoundException {
                String ID = lblid.getText();

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?", ButtonType.YES, ButtonType.NO);
                Optional<ButtonType> optionalButtonType = alert.showAndWait();

                if (optionalButtonType.isPresent() && optionalButtonType.get() == ButtonType.YES) {

                        boolean isDelete = therapistBO.delete(ID);
                        if (isDelete) {
                                refreshPage();
                                new Alert(Alert.AlertType.INFORMATION, "Labor deleted...!").show();

                        } else {
                                new Alert(Alert.AlertType.ERROR, "Fail to delete Labor...!").show();

                        }
                }
        }

        @FXML
        void ResetOnAction(ActionEvent event) throws SQLException, IOException, ClassNotFoundException {
                refreshPage();
        }

        @FXML
        void SaveOnAction(ActionEvent event) {
                String therapistId = lblid.getText();
                String therapistName = txtname.getText();
                String specialization = txtspecail.getText();
                String contactNumber = txtnumber.getText();
                String programId = comoProgram.getValue();

                if(isValid()) {

                        try {
                                boolean isRegistered = therapistBO.save(new TherapistDTO(therapistId, therapistName, specialization, contactNumber, programId));
                                if (isRegistered) {
                                        refreshPage();
                                        new Alert(Alert.AlertType.INFORMATION, "User Saved SUCCESSFULLY 😎").show();

                                } else {
                                        new Alert(Alert.AlertType.ERROR, "PLEASE TRY AGAIN 😥").show();
                                }
                        } catch (IOException e) {
                                new Alert(Alert.AlertType.ERROR, "duplicate Id");
                        } catch (SQLException e) {
                                throw new RuntimeException(e);
                        } catch (ClassNotFoundException e) {
                                throw new RuntimeException(e);
                        }
                }
        }

        @FXML
        void TableOnClicked(MouseEvent event) {
                TherapistTM therapistTM = (TherapistTM) TherapistTable.getSelectionModel().getSelectedItem();
                if (therapistTM != null) {
                        lblid.setText(therapistTM.getTherapistId());
                        txtname.setText(therapistTM.getTherapistName());
                        txtspecail.setText(therapistTM.getSpecialization());
                        txtnumber.setText(String.valueOf(therapistTM.getContactNumber()));
                        comoProgram.setValue(therapistTM.getProgramId());

                        btndelete.setDisable(false);
                        btnsave.setDisable(true);
                        btnupdate.setDisable(false);
                }
        }

        @FXML
        void UpdateOnAction(ActionEvent event) {
                String therapistId = lblid.getText();
                String therapistName = txtname.getText();
                String specialization = txtspecail.getText();
                String contactNumber = txtnumber.getText();
                String programId = comoProgram.getValue();

                if (isValid()) {

                        try {
                                boolean isRegistered = therapistBO.update(new TherapistDTO(therapistId, therapistName, specialization, contactNumber, programId));
                                if (isRegistered) {
                                        refreshPage();
                                        new Alert(Alert.AlertType.INFORMATION, "User Saved SUCCESSFULLY 😎").show();

                                } else {
                                        new Alert(Alert.AlertType.ERROR, "PLEASE TRY AGAIN 😥").show();
                                }
                        } catch (IOException e) {
                                new Alert(Alert.AlertType.ERROR, "duplicate Id");
                        } catch (SQLException e) {
                                throw new RuntimeException(e);
                        } catch (ClassNotFoundException e) {
                                throw new RuntimeException(e);
                        }
                }
        }
        private void LoadNextID() throws SQLException, IOException {
                String nextID = therapistBO.getNextId();
                lblid.setText(nextID);
        }
        private void loadTableData() throws SQLException, ClassNotFoundException, IOException {
                ArrayList<TherapistDTO> therapistDTOS = (ArrayList<TherapistDTO>) therapistBO.getAll();
                ObservableList<TherapistTM> therapistTMS = FXCollections.observableArrayList();

                for (TherapistDTO therapistDTO : therapistDTOS) {
                        TherapistTM therapistTM = new TherapistTM(
                                therapistDTO.getTherapistId(),
                                therapistDTO.getTherapistName(),
                                therapistDTO.getSpecialization(),
                                therapistDTO.getContactNumber(),
                                therapistDTO.getProgramId()


                        );
                        therapistTMS.add(therapistTM);
                }
                TherapistTable.setItems(therapistTMS);
        }
        void refreshPage() throws SQLException, ClassNotFoundException, IOException {
                LoadNextID();
                loadTableData();

                btndelete.setDisable(true);
                btnsave.setDisable(false);
                btnupdate.setDisable(true);

                txtname.setText("");
                txtspecail.setText("");
                txtnumber.setText("");

        }
        private void loadProgramIDs() throws SQLException, ClassNotFoundException, IOException {
                ArrayList<String> programIds = therapyProgramBO.getAllProgramIDs();
                comoProgram.getItems().addAll(programIds);
                }
        @FXML
        void txtContactOnKeyReleased(KeyEvent event) {
                Regex.setTextColor(lk.ijse.gdse.ormcw.util.TextField.TEL,txtnumber);
        }

        @FXML
        void txtNameOnKeyReleased(KeyEvent event) {
                Regex.setTextColor(lk.ijse.gdse.ormcw.util.TextField.NAME, txtname);
        }
        public boolean isValid(){
                if(!Regex.setTextColor(lk.ijse.gdse.ormcw.util.TextField.TEL, txtnumber)) return false;
                if(!Regex.setTextColor(lk.ijse.gdse.ormcw.util.TextField.NAME, txtname)) return false;

                return true;
        }

}


