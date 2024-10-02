/*
 */
package Main;

import student.*;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import local.UserSession;

/**
 *
 * 
 */
public class StudentController {
//nodes
    //dashboard

    @FXML
    Label name, electricity, water, total, payment;
    //room
    @FXML
    Label freeSlot;
    @FXML
    TableView<Room> roomMates;
    @FXML
    TableColumn<Room, String> tbName, tbGender, tbPhone, tbAddress, tbJoinedDate;
    @FXML
    TableColumn<Room, Integer> tbId;
    //personal
    @FXML
    Label pId, pName, pGender, pRoom, pAddress, pPhone, pEmail, pJoinedDate;
//global
    private Stage stage;
    private Scene scene;
    private Parent root;
    public static String email;

//initialize
    @FXML
    public void initialize() {
        UserSession u = new UserSession();
        email = u.readUser();
    }

//build
    //build stage
    public void buildStage(Stage stage, Scene scene, ActionEvent event) throws IOException {
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root, 1280, 800);
        stage.setScene(scene);
        stage.getIcons().add(new Image(App.class.getResourceAsStream("img/bill.png")));
        stage.setResizable(false);
        stage.show();
    }

//build data
    //build student information
    public void buildStudent(String email) {
        Student s = new Student();
        s.loadStudent(email);
        name.setText(s.getName());
    }

    public void buildDashboard() {
        Dashboard d = new Dashboard();
        d.loadDashboard();
        electricity.setText(Common.formatCurrency(d.getElectricity()) + " VND");
        water.setText(Common.formatCurrency(d.getWater()) + " VND");
        total.setText(Common.formatCurrency(d.getTotal()) + " VND");
        payment.setText(d.getPayment());
    }

    public void buildRoom() {
        Room r = new Room();
        freeSlot.setText(r.getFreeSlot());

        tbId.setCellValueFactory(new PropertyValueFactory<>("Id"));
        tbName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        tbGender.setCellValueFactory(new PropertyValueFactory<>("Gender"));
        tbPhone.setCellValueFactory(new PropertyValueFactory<>("Phone"));
        tbAddress.setCellValueFactory(new PropertyValueFactory<>("Address"));
        tbJoinedDate.setCellValueFactory(new PropertyValueFactory<>("JoinedDate"));

        roomMates.setItems(r.loadStudentList());
    }


    public void buildPersonal() {
        Student s = new Student();
        s.loadStudent(email);
        pId.setText(String.valueOf(s.getId()));
        pName.setText(s.getName());
        pGender.setText(s.getGender());
        pRoom.setText(s.getRoom());
        pAddress.setText(s.getAddress());
        pPhone.setText(s.getPhone());
        pEmail.setText(s.getEmail());
        pJoinedDate.setText(s.getJoinedDate());

    }

//load scene
    public void dashboard(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("StudentDashboard.fxml"));
        root = loader.load();

        StudentController c = loader.getController();
        c.buildDashboard();
        c.buildStudent(email);

        buildStage(stage, scene, event);
    }

    public void room(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("StudentRoom.fxml"));
        root = loader.load();

        StudentController c = loader.getController();
        c.buildRoom();
        c.buildStudent(email);

        buildStage(stage, scene, event);
    }

    public void personal(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("StudentPersonal.fxml"));
        root = loader.load();

        StudentController c = loader.getController();
        c.buildPersonal();
        c.buildStudent(email);

        buildStage(stage, scene, event);
    }

    public void logout(ActionEvent event) throws IOException {
        UserSession u = new UserSession();
        u.clearUser();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
        root = loader.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root, 569, 399);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

}
