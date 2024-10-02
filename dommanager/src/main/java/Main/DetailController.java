/*
 */
package Main;

import data.*;
import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import student.Dashboard;

/**
 *
 * 
 */
public class DetailController {
//nodes
    //bill
    @FXML
    TextField name, electricity, water, total, payment;
    //student and other
    @FXML
    TextField dSId, dSName, dSGender, dSEmail, dSPhone, dSAddress, dSJoinedDate, dSUsername;
    @FXML
    ChoiceBox dSRoom, dSRole;
    @FXML
    Label dSPhoneError;
    //room
    @FXML
    TextField dSCapacity, dSFree, dSTotal;
    @FXML
    Label dSCapacityError;
    @FXML
    TableView<DetailLoader> roomStudent;
    @FXML
    TableColumn<DetailLoader, String> studentName, studentGender, studentPhone, studentJoinedDate;
    @FXML
    TableColumn<DetailLoader, Integer> studentId;
    //buttons
    @FXML
    Button deleteBtn, confirmBtn;

//build
    //build data
    public void buildStudent(int id) {
        DetailLoader d = new DetailLoader();
        Student s = new Student();
        ObservableList rooms = Common.getRooms();

        d.detailStudent(id);
        dSId.setText(String.valueOf(d.getId()));
        dSName.setText(d.getName());
        dSGender.setText(d.getGender());
        dSEmail.setText(d.getEmail());
        dSPhone.setText(d.getPhone());
        dSAddress.setText(d.getAddress());
        dSJoinedDate.setText(d.getJoinedDate());
        dSName.setText(d.getName());
        dSRoom.setItems(rooms);
        dSRoom.setValue(d.getRoomName());
    }

    public void buildBill(int id) {
        DetailLoader d = new DetailLoader();
        Dashboard s = new Dashboard();

        d.detailBill(id);
        
        name.setText(d.getName());
        electricity.setText(String.valueOf(d.getElectric()));
        water.setText(String.valueOf(d.getWater()));
        total.setText(String.valueOf(d.getElectric() + d.getWater()));
        payment.setText("Not paid");
        
    }

    public void buildAccount(int id) {
        DetailLoader d = new DetailLoader();
        Account a = new Account();

        ObservableList<String> rolls = FXCollections.observableArrayList("Admin", "Staff");
        d.detailAccount(id);
        
        dSId.setText(String.valueOf(d.getId()));
        dSUsername.setText(d.getUsername());
        dSName.setText(d.getName());
        dSRole.setItems(rolls);
        dSRole.setValue("Admin");
        dSEmail.setText(d.getEmail());
        dSPhone.setText(d.getPhone());
        dSAddress.setText(d.getAddress());
        dSJoinedDate.setText(d.getJoinedDate());
        
    }

    public void buildRoom(int id) {
        DetailLoader d = new DetailLoader();
        d.detailRoom(id);
        dSName.setText(d.getRoomName());
        dSTotal.setText(Common.formatCurrency(String.valueOf(d.getWater() + d.getElectric())) + " VND");
        dSCapacity.setText(String.valueOf(d.getCapacity()));
        dSFree.setText(String.valueOf(d.getCapacity() - d.getOccupied()));

        studentId.setCellValueFactory(new PropertyValueFactory<>("StudentId"));
        studentName.setCellValueFactory(new PropertyValueFactory<>("StudentName"));
        studentGender.setCellValueFactory(new PropertyValueFactory<>("StudentGender"));
        studentPhone.setCellValueFactory(new PropertyValueFactory<>("StudentPhone"));
        studentJoinedDate.setCellValueFactory(new PropertyValueFactory<>("StudentJoinedDate"));

        roomStudent.setItems(d.loadStudentList(id));

        roomStudent.setRowFactory(tv -> {
            TableRow<DetailLoader> row = new TableRow<>();
            row.setOnMouseClicked(e -> {
                if (e.getClickCount() == 2 && (!row.isEmpty())) {
                    try {
                        DetailLoader rowData = row.getItem();
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("DetailStudent.fxml"));
                        Parent root1 = (Parent) fxmlLoader.load();
                        DetailController d1 = fxmlLoader.getController();
                        d1.buildStudent(rowData.getStudentId());

                        Stage x = new Stage();
                        x.initModality(Modality.APPLICATION_MODAL);
                        x.setTitle("Detail");
                        x.setScene(new Scene(root1));
                        x.setResizable(false);
                        x.show();
                    } catch (Exception ee) {

                    }
                }
            });
            return row;
        });

    }

//methods
    //delete student <ok>
    public void deleteStudent(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Student deletion");
        alert.setContentText("Do you really sure to delete this student\nThis action can't be undone");
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                DetailLoader d = new DetailLoader();
                if (d.deleteStudent(Integer.parseInt(dSId.getText()))) {
                    Stage sta = (Stage) deleteBtn.getScene().getWindow();
                    sta.close();

                    try {
                        SceneController s = new SceneController();
                        s.reloadStudent(event);
                    } catch (IOException e) {
                        System.out.println("Error deleting bill: " + e.getMessage());
                    }

                }
            }
        });

    }

    //close window or update student
    public void confirmStudent(ActionEvent event) throws IOException {
        int id = Integer.parseInt(dSId.getText());
        String room = (String) dSRoom.getValue();
        String phone = dSPhone.getText();
        DetailLoader d = new DetailLoader();
        Validator v = new Validator();
        if (v.phoneCheck(phone) == true) {
            d.updateStudent(id, room, phone);
            Stage sta = (Stage) confirmBtn.getScene().getWindow();
            sta.close();

            SceneController s = new SceneController();
            s.reloadStudent(event);

        } else {
            dSPhoneError.setText("Invalid phone format");
        }
    }

    //delete bill
    public void deleteBill(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Account deletion");
        alert.setContentText("Do you really sure to delete this Bill\nThis action can't be undone");
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                DetailLoader d = new DetailLoader();
                if (d.deleteBill(Integer.parseInt(dSId.getText()))) {
                    Stage sta = (Stage) deleteBtn.getScene().getWindow();
                    sta.close();

                    try {
                        SceneController s = new SceneController();
                        s.reloadBill(event);
                    } catch (IOException e) {
                        System.out.println("Error deleting bill: " + e.getMessage());
                    }

                }
            }
        });
    }

    //close window or update bill
    public void confirmBill(ActionEvent event) throws IOException {
        int id = Integer.parseInt(dSId.getText());
        String electricity = this.electricity.getText();
        String name =  this.name.getText();
        String water = this.water.getText();
        String total = this.total.getText();
        String payment = this.payment.getText();
        
        DetailLoader d = new DetailLoader();
        Validator v = new Validator();
        if (v.moneyCheck(total) == true && v.moneyCheck(water) == true && v.moneyCheck(electricity) == true  ) {
            d.updateBill(id,payment);
            Stage sta = (Stage) confirmBtn.getScene().getWindow();
            sta.close();

            SceneController s = new SceneController();
            s.reloadStudent(event);

        } else {
            dSPhoneError.setText("Invalid phone format");
        }
    }

    //delete account
    public void deleteAccount(ActionEvent event) {
         Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Account deletion");
        alert.setContentText("Do you really sure to delete this Account\nThis action can't be undone");
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                DetailLoader d = new DetailLoader();
                if (d.deleteAccount(Integer.parseInt(dSId.getText()))) {
                    Stage sta = (Stage) deleteBtn.getScene().getWindow();
                    sta.close();

                    try {
                        SceneController s = new SceneController();
                        s.reloadAccount(event);
                    } catch (IOException e) {
                        System.out.println("Error deleting bill: " + e.getMessage());
                    }

                }
            }
        });
    }

    //close or update account
    public void confirmAccount(ActionEvent event) throws IOException {
        int id = Integer.parseInt(dSId.getText());
        String role = (String) dSRole.getValue();
        String phone = dSPhone.getText();
        DetailLoader d = new DetailLoader();
        Validator v = new Validator();
        if (v.phoneCheck(phone) == true) {
            d.updateAccount(id, role, phone);
            Stage sta = (Stage) confirmBtn.getScene().getWindow();
            sta.close();

            SceneController s = new SceneController();
            s.reloadAccount(event);

        } else {
            dSPhoneError.setText("Invalid phone format");
        }
    }

    //delete room
    public void deleteRoom(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Account deletion");
        alert.setContentText("Do you really sure to delete this room\nThis action can't be undone");
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                DetailLoader d = new DetailLoader();
                if (d.deleteRoom(dSName.getText())) {
                    Stage sta = (Stage) deleteBtn.getScene().getWindow();
                    sta.close();

                    try {
                        SceneController s = new SceneController();
                        s.reloadRoom(event);
                    } catch (IOException e) {
                        System.out.println("Error deleting room: " + e.getMessage());
                    }

                } else {
                    Alert notok = new Alert(Alert.AlertType.INFORMATION);
                    notok.setTitle("Information");
                    notok.setContentText("This room can't be deleted, there are still student in there");
                    notok.showAndWait();
                }
            }
        });

    }

    //close or update room
    public void confirmRoom(ActionEvent event) throws IOException {
        try {
            int cap = Integer.parseInt(dSCapacity.getText());
            int id = Common.getRoomId(dSName.getText());
            DetailLoader d = new DetailLoader();
            if (d.updateRoom(id, cap)) {
                Stage sta = (Stage) deleteBtn.getScene().getWindow();
                sta.close();

                SceneController s = new SceneController();
                s.reloadRoom(event);
            } else {
                dSCapacityError.setText("Invalid number format");
            }
        } catch (NumberFormatException e) {
            dSCapacityError.setText("Invalid number format");
        }

    }

    //close window
    public void closeWindow(ActionEvent event) throws IOException {
        Stage sta = (Stage) confirmBtn.getScene().getWindow();
        sta.close();
    }

}
