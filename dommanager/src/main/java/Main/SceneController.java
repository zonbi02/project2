package Main;

import data.*;
import database.Connect_db;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import local.UserSession;
import student.Dashboard;

public class SceneController {

//fxml
    //bill
    @FXML
    TableColumn<Dashboard, String> bRoom, bWater, bElectricity, bTotal, bmonth, bStatus;
    @FXML
    TableColumn<Dashboard, Integer> bId;
    @FXML
    TableView<Dashboard> billList;
    //add new acoount
    @FXML
    TextField aAId, aAUsername, aAPassword, aAName, aAEmail, aAPhone, aAAddress;
    @FXML
    ChoiceBox aARole;
    @FXML
    Label aAUsernameError, aAEmailError, aAPhoneError, aANameError, aAPasswordError;
//dashboard
    @FXML
    Label totalStudent, totalRoom;
//manager
    @FXML
    Label name, role;
//account staff
    @FXML
    Label aSUsername, aSRole, aSJoinedDate, aSFullname, aSEmail, aSPhone, aSAddress;
//account admin
    @FXML
    TableView<Account> accountList;
    @FXML
    TableColumn<Account, Integer> tbid;
    @FXML
    TableColumn<Account, String> tbusername, tbrole, tbname, tbemail, tbphone, tbaddress, tbjoineddate;
//room
    @FXML
    TableView<Room> roomList;
    @FXML
    TableColumn<Room, Integer> rId, rTotal, rOccupied, rFree;
    @FXML
    TableColumn<Room, String> rName;
//add new room
    @FXML
    TextField aRId, aRName, aROccupied, aRCapacity;
    @FXML
    Label aRError;
//student
    @FXML
    TableView<Student> studentList;
    @FXML
    TableColumn<Student, String> sLRoom, sLName, sLGender, sLPhone, sLEmail, sLAddress, sLJoinedDate;
    @FXML
    TableColumn<Student, Integer> sLId;
    @FXML
    TextField searchPattern;
//add student
    @FXML
    TextField aStId, aStName, aStEmail, aStPhone, aStAddress;
    @FXML
    ChoiceBox aStGender, aStRoom;
    @FXML
    Label aStNameErr, aStEmailErr, aStPhoneErr;
//setting
    @FXML
    TextField sUsername, sJoinedDate, sRole, sFullname, sEmail, sPhone, sAddress;
    @FXML
    Label fullnameErr, emailErr, phoneErr;
    @FXML
    PasswordField cPPassword, cPNewPassword, cPConfirmPassword;
    @FXML
    Label cPError, cFPError;
//login
    @FXML
    TextField lUsername;
    @FXML
    PasswordField password;
    @FXML
    Label error;
//reset password
    @FXML
    TextField rPUsername, rPEmail, rPPhone;
    @FXML
    Label rPError;
//fields
    private Stage stage;
    private Stage cpw, aSt, aB, aR, aA;
    private Scene scene;
    private Parent root;
    public static String username;

    public int isok = 0;

//    public ObservableList<Room> rooms = FXCollections.observableArrayList();
    @FXML
    Button addNewStudentBtn, addNewRoomBtn, changePasswordBtn, addNewAccountBtn;
//methods
    //build stage <ok>

    public void buildStage(Stage stage, Scene scene, ActionEvent event) throws IOException {
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root, 1280, 800);
        stage.setScene(scene);
        stage.getIcons().add(new Image(App.class.getResourceAsStream("img/bill.png")));
        stage.setResizable(false);
        stage.show();
    }

    //build dashboard <ok>
    public void buildDashboard() {
        Basic d = new Basic();
        d.loadDashboard();
        totalStudent.setText(String.valueOf(d.getTotalStudent()));
        totalRoom.setText(String.valueOf(d.getTotalRoom()));
    }

    //build manager <ok>
    public void buildManager(String username) {
        Basic m = new Basic();
        m.loadManager(username);
        name.setText(m.getFullName());
        role.setText(m.getRole());
    }

    //build bill <ok>
    public void buildBill() {
        
    }

    
    //build room <ok>
    public void buildRoom() {
        Room r = new Room();
        rId.setCellValueFactory(new PropertyValueFactory<>("Id"));
        rTotal.setCellValueFactory(new PropertyValueFactory<>("Total"));
        rOccupied.setCellValueFactory(new PropertyValueFactory<>("Occupied"));
        rFree.setCellValueFactory(new PropertyValueFactory<>("Free"));
        rName.setCellValueFactory(new PropertyValueFactory<>("Name"));

        roomList.setItems(r.loadRoom());

        roomList.setRowFactory(tv -> {
            TableRow<Room> row = new TableRow<>();
            row.setOnMouseClicked(e -> {
                if (e.getClickCount() == 2 && (!row.isEmpty())) {
                    try {
                        Room rowData = row.getItem();
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("DetailRoom.fxml"));
                        Parent root1 = (Parent) fxmlLoader.load();
                        DetailController d = fxmlLoader.getController();
                        d.buildRoom(rowData.getId());

                        Stage x = new Stage();
                        x.initModality(Modality.APPLICATION_MODAL);
                        x.setTitle("Detail");
                        x.setScene(new Scene(root1));
                        x.setResizable(false);
                        x.show();
                    } catch (Exception ee) {
                        System.out.println("Error room detail: " + ee.getMessage());
                    }
                }
            });
            return row;
        });

    }

    //build account
    
    //manager info <ok>
    public void buildData() {
        Account a = new Account();
        a.loadData(username);
        aSUsername.setText(a.getaSUsername());
        aSRole.setText(a.getaSRole());
        aSJoinedDate.setText(a.getaSJoinedDate());
        aSFullname.setText(a.getaSFullname());
        aSEmail.setText(a.getaSEmail());
        aSPhone.setText(a.getaSPhone());
        aSAddress.setText(a.getaSAddress());
    }

    //build account <admin> <ok>
    public void buildAdminData() {
        Account a = new Account();
        tbid.setCellValueFactory(new PropertyValueFactory<>("id"));
        tbusername.setCellValueFactory(new PropertyValueFactory<>("username"));
        tbrole.setCellValueFactory(new PropertyValueFactory<>("role"));
        tbname.setCellValueFactory(new PropertyValueFactory<>("name"));
        tbemail.setCellValueFactory(new PropertyValueFactory<>("email"));
        tbphone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        tbaddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        tbjoineddate.setCellValueFactory(new PropertyValueFactory<>("joineddate"));

        accountList.setItems(a.loadAccountList());
        accountList.setRowFactory(tv -> {
            TableRow<Account> row = new TableRow<>();
            row.setOnMouseClicked(e -> {
                if (e.getClickCount() == 2 && (!row.isEmpty())) {
                    try {
                        Account rowData = row.getItem();
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("DetailAccount.fxml"));
                        Parent root1 = (Parent) fxmlLoader.load();
                        DetailController d = fxmlLoader.getController();
                        d.buildAccount(rowData.getId());

                        Stage x = new Stage();
                        x.initModality(Modality.APPLICATION_MODAL);
                        x.setTitle("Detail");
                        x.setScene(new Scene(root1));
                        x.setResizable(false);
                        x.show();
                    } catch (Exception ee) {
                        System.out.println("Error: " + ee.getMessage());
                    }
                }
            });
            return row;
        });

    }

    //build setting <ok>
    public void buildSetting() {
        Setting s = new Setting();
        s.loadData(username);
        sRole.setText(s.getsRole());
        sUsername.setText(s.getsUsername());
        sJoinedDate.setText(s.getsJoinedDate());
        sFullname.setText(s.getsFullname());
        sEmail.setText(s.getsEmail());
        sPhone.setText(s.getsPhone());
        sAddress.setText(s.getsAddress());
    }

    //build add student <ok>
    public void buildAddStudent() {
        Student s = new Student();
        ObservableList<String> gender = FXCollections.observableArrayList("Male", "Female");

        aStId.setText(String.valueOf(s.getId()));
        aStRoom.setItems(Common.getRooms());
        aStRoom.setValue(Common.getRooms().get(0));
        aStGender.setItems(gender);
        aStGender.setValue("Male");

    }

    //build student
    public void buildStudent(ActionEvent event) {
        Student s = new Student();
        sLId.setCellValueFactory(new PropertyValueFactory<>("LId"));
        sLRoom.setCellValueFactory(new PropertyValueFactory<>("LRoom"));
        sLName.setCellValueFactory(new PropertyValueFactory<>("LName"));
        sLGender.setCellValueFactory(new PropertyValueFactory<>("LGender"));
        sLPhone.setCellValueFactory(new PropertyValueFactory<>("LPhone"));
        sLEmail.setCellValueFactory(new PropertyValueFactory<>("LEmail"));
        sLAddress.setCellValueFactory(new PropertyValueFactory<>("LAddress"));
        sLJoinedDate.setCellValueFactory(new PropertyValueFactory<>("LJoinedDate"));

        studentList.setItems(s.loadStudentList());

        studentList.setRowFactory(tv -> {
            TableRow<Student> row = new TableRow<>();
            row.setOnMouseClicked(e -> {
                if (e.getClickCount() == 2 && (!row.isEmpty())) {
                    try {
                        Student rowData = row.getItem();
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("DetailStudent.fxml"));
                        Parent root1 = (Parent) fxmlLoader.load();
                        DetailController d = fxmlLoader.getController();
                        d.buildStudent(rowData.getLId());

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

    //build add bill <ok>
    public void buildAddBill() {
        
    }

    //build add room <ok>
    public void buildAddRoom() {
        Room r = new Room();
        aRId.setText(String.valueOf(r.getNewId()));
        aRName.setText(r.setNewName());
    }

    //build add account <ok>
    
    public void buildAddAccount() {
        Account a = new Account();
        ObservableList<String> rolls = FXCollections.observableArrayList("Admin", "Staff");
        aAId.setText(String.valueOf(a.getNewId()));
        aAPassword.setText("12345678a");
        aAPassword.setEditable(false);
        aARole.setItems(rolls);
        aARole.setValue("Admin");
       
    }

//load scene
    // dashboard <ok>
    public void dashboard(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Dashboard.fxml"));
        root = loader.load();

        SceneController c = loader.getController();
        c.buildDashboard();
        c.buildManager(username);

        buildStage(stage, scene, event);
    }

    //load student scene
    public void student(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Student.fxml"));
        root = loader.load();
        SceneController c = loader.getController();
        c.buildStudent(event);
        c.buildManager(username);
        buildStage(stage, scene, event);
    }

    //add student <ok>
    public void addStudent(ActionEvent event) throws IOException {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AddStudentForm.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            aSt = new Stage();
            aSt.initModality(Modality.APPLICATION_MODAL);
            aSt.setTitle("Add new student");
            aSt.setScene(new Scene(root1));
            aSt.setResizable(false);

            SceneController c = fxmlLoader.getController();
            c.buildAddStudent();
            aSt.show();
        } catch (IndexOutOfBoundsException e) {
            Dialog er = new Dialog();
            ButtonType btn = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
            er.setTitle("Error");
            er.setContentText("You have to add a room first");
            er.getDialogPane().getButtonTypes().add(btn);
            er.showAndWait();
        }
    }

    // rooms <ok>
    public void room(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Room.fxml"));
        root = loader.load();
        SceneController c = loader.getController();
        c.buildRoom();
        c.buildManager(username);
        buildStage(stage, scene, event);
    }

    //add room <ok>
    public void addRoom(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AddRoomForm.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        aR = new Stage();
        aR.initModality(Modality.APPLICATION_MODAL);
        aR.setTitle("Add new room");
        aR.setScene(new Scene(root1));
        aR.setResizable(false);
        SceneController c = fxmlLoader.getController();
        c.buildAddRoom();
        aR.show();

    }

    // bills <ok>
    public void bill(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Bill.fxml"));
        root = loader.load();
        SceneController c = loader.getController();
        c.buildBill();
        c.buildManager(username);
        buildStage(stage, scene, event);
    }

    //add bill <ok>
    public void addBill(ActionEvent event) throws IOException {
    }

    // account <ok>
    public void account(ActionEvent event) throws IOException {
        String f;
        if (role.getText().equals("Admin")) {
            f = "AccountAdmin.fxml";
        } else {
            f = "AccountStaff.fxml";
        }
        FXMLLoader loader = new FXMLLoader(getClass().getResource(f));
        root = loader.load();
        SceneController c = loader.getController();
        c.buildManager(username);
        if (role.getText().equals("Admin")) {
            c.buildAdminData();
        } else {
            c.buildData();
        }
        buildStage(stage, scene, event);
    }

    //add account <ok>
    public void addAccount(ActionEvent event) throws IOException {
        
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AddAccountForm.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            aA = new Stage();
            aA.initModality(Modality.APPLICATION_MODAL);
            aA.setTitle("Add new account");
            aA.setScene(new Scene(root1));
            aA.setResizable(false);

            SceneController c = fxmlLoader.getController();
            c.buildAddAccount();
            aA.show();
    }


    // setting <ok>
    public void setting(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Setting.fxml"));
        root = loader.load();
        SceneController c = loader.getController();
        c.buildManager(username);
        c.buildSetting();
        buildStage(stage, scene, event);
    }

    //change password <ok>
    public void changePasswordForm() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ChangePassword.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        cpw = new Stage();
        cpw.initModality(Modality.APPLICATION_MODAL);
        cpw.setTitle("Change password");
        cpw.setResizable(false);
        cpw.setScene(new Scene(root1));
        cpw.show();
    }

    //reset password <ok>
    public void resetPassword(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ResetPassword.fxml"));
        root = loader.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root, 569, 399);
        stage.setScene(scene);
        stage.show();
    }

//scene's methods
    //add new account <ok>
    public void addNewAccount(ActionEvent event) throws IOException {
        Account s = new Account();
        
        String un = aAUsername.getText();
        String pw = aAPassword.getText();
        String n = aAName.getText();
        String e = aAEmail.getText();
        String p = aAPhone.getText();
        String a = aAAddress.getText();

        //empty error
        aAUsernameError.setText("");
        aAEmailError.setText("");
        aAPhoneError.setText("");
        aANameError.setText("");
        aAPasswordError.setText("");

        var r = (String) aARole.getValue();

        if (s.add(un,pw,r,n,e,p,a)) {
            Dialog changeOk = new Dialog();
            ButtonType btn = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
            changeOk.setTitle("Information");
            changeOk.setContentText("New account has been addded");
            changeOk.getDialogPane().getButtonTypes().add(btn);
            changeOk.showAndWait();
            aA = (Stage) addNewAccountBtn.getScene().getWindow();
            aA.close();

            reloadAccount(event);
        } else {
            aAUsernameError.setText(s.getUsernameError());
            aAEmailError.setText(s.getEmailError());
            aAPhoneError.setText(s.getPhoneError());
            aANameError.setText(s.getNameError());
            aAPasswordError.setText(s.getPasswordError());
    }
    }

    //add new student <ok>
    public void addNewStudent(ActionEvent event) throws IOException {
        Student s = new Student();
        String stName = aStName.getText();
        String email = aStEmail.getText();
        String phone = aStPhone.getText();
        String address = aStAddress.getText();
        //empty error
        aStEmailErr.setText("");
        aStNameErr.setText("");
        aStPhoneErr.setText("");

        var gender = (String) aStGender.getValue();
        int room = Common.getRoomId((String) aStRoom.getValue());

        if (s.add(stName, gender, room, email, phone, address)) {
            Dialog changeOk = new Dialog();
            ButtonType btn = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
            changeOk.setTitle("Information");
            changeOk.setContentText("New student has been addded");
            changeOk.getDialogPane().getButtonTypes().add(btn);
            changeOk.showAndWait();
            aSt = (Stage) addNewStudentBtn.getScene().getWindow();
            aSt.close();

            reloadStudent(event);
        } else {
            aStEmailErr.setText(s.getEmailErr());
            aStNameErr.setText(s.getNameErr());
            aStPhoneErr.setText(s.getPhoneErr());
        }
    }

    //add new room <ok>
    public void addNewRoom(ActionEvent event) throws IOException {
        Room r = new Room();
        String capacity = aRCapacity.getText();
        String occupied = aROccupied.getText();
        //empty error
        aRError.setText("");
        if (r.add(capacity, occupied)) {
            Dialog changeOk = new Dialog();
            ButtonType btn = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
            changeOk.setTitle("Information");
            changeOk.setContentText("New room has been addded");
            changeOk.getDialogPane().getButtonTypes().add(btn);
            changeOk.showAndWait();
            aR = (Stage) addNewRoomBtn.getScene().getWindow();
            aR.close();

            reloadRoom(event);

        } else {
            aRError.setText("Please enter valid number");
        }
    }

    //search student <ok>
    public void searchStudent(ActionEvent event) throws IOException {
        Student s = new Student();
        sLId.setCellValueFactory(new PropertyValueFactory<>("LId"));
        sLRoom.setCellValueFactory(new PropertyValueFactory<>("LRoom"));
        sLName.setCellValueFactory(new PropertyValueFactory<>("LName"));
        sLGender.setCellValueFactory(new PropertyValueFactory<>("LGender"));
        sLPhone.setCellValueFactory(new PropertyValueFactory<>("LPhone"));
        sLEmail.setCellValueFactory(new PropertyValueFactory<>("LEmail"));
        sLAddress.setCellValueFactory(new PropertyValueFactory<>("LAddress"));
        sLJoinedDate.setCellValueFactory(new PropertyValueFactory<>("LJoinedDate"));

        studentList.setItems(s.searchStudent(searchPattern.getText()));
    }

    //add bill <ok>
    public void addNewBill(ActionEvent event) throws IOException {
    }

    //filter bill <ok>
    public void filterMonth() {
    }

    //update total money on loose focus <ok>
    public void updateTotal() {
    }

    //update manager <ok>
    public void updateManager(ActionEvent event) throws IOException {
        Setting s = new Setting();
        boolean b = s.updateManager(username, sFullname, sEmail, sPhone, sAddress);
        if (b) {
            Dialog updateOk = new Dialog();
            ButtonType btn = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
            updateOk.setTitle("Inforamtion");
            updateOk.setContentText("Your information has been updated");
            updateOk.getDialogPane().getButtonTypes().add(btn);
            updateOk.showAndWait();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("Setting.fxml"));
            root = loader.load();

            SceneController c = loader.getController();
            c.buildManager(username);
            c.buildSetting();

            buildStage(stage, scene, event);

        } else {
            fullnameErr.setText(s.getNameErr());
            emailErr.setText(s.getEmailErr());
            phoneErr.setText(s.getPhoneErr());
        }
    }

    //delete manager <ok>
    public void deleteManager(ActionEvent event) throws IOException {
        if (username.equals("admin")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Account deletion");
            alert.setContentText("This account cannot be deleted ");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Account deletion");
            alert.setContentText("Do you really sure to delete this account\nThis action can't be undone");
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    Setting s = new Setting();
                    if (s.deleteManager(username)) {
                        try {
                            logout(event);
                        } catch (IOException e) {
                            System.out.println("Error deleting account: " + e.getMessage());
                        }
                    }

                }
            });

        }

    }

    //change password <ok>
    public void changePassword(ActionEvent event) throws IOException {
        //change password
        Setting s = new Setting();
        if (s.changePassword(username, cPPassword, cPNewPassword, cPConfirmPassword)) {
            Dialog changeOk = new Dialog();
            ButtonType btn = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
            changeOk.setTitle("Information");
            changeOk.setContentText("Your password has been changed");
            changeOk.getDialogPane().getButtonTypes().add(btn);
            changeOk.showAndWait();

            cpw = (Stage) changePasswordBtn.getScene().getWindow();
            cpw.close();

        } else {
            cPError.setText(s.getcPError());
            cFPError.setText(s.getcFPError());
        }
    }

    //recover password <ok>
    public void recoverPassword() {
        ResetPassword r = new ResetPassword();
        if (r.resetPassword(rPUsername, rPEmail, rPPhone)) {
            rPError.setText("");
            Dialog resetOk = new Dialog();
            ButtonType btn = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
            Label l = new Label("Your password has been recovered\nYour new password is");
            TextArea n = new TextArea(r.getNewPw());
            n.setPrefWidth(300);
            n.setPrefHeight(20);
            GridPane gridPane = new GridPane();
            gridPane.setMaxWidth(Double.MAX_VALUE);
            gridPane.add(n, 0, 1);
            gridPane.add(l, 0, 0);
            resetOk.getDialogPane().setContent(gridPane);
            resetOk.setTitle("Information");
            resetOk.getDialogPane().getButtonTypes().add(btn);
            resetOk.showAndWait();

        } else {
            rPError.setText(r.getErr());
        }
    }

    //to login <ok>
    public void toLogin(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
        root = loader.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root, 569, 399);
        stage.setScene(scene);
        stage.show();
    }

//other methods <ok>
    public void login(ActionEvent event) throws IOException {
        UserSession u = new UserSession();
        String un = lUsername.getText();
        String pass = password.getText();
        if (un.contains("@gmail.com")) {
            if (loginS(un, pass) == true) {
                u.saveUser(un);
                FXMLLoader loader = new FXMLLoader(getClass().getResource("StudentDashboard.fxml"));
                root = loader.load();
                buildStage(stage, scene, event);

            } else {
                error.setText("Invalid username or password");
            }
        } else {
            if (loginM(un, pass) == true) {
                u.saveUser(un);
                username = un;
                dashboard(event);
            } else {
                error.setText("Invalid username or password");
            }
        }
    }

    //login components
    //manager login handler
    private boolean loginM(String username, String password) {
        try {
            Connection conn = Connect_db.getConnection();
            //sql
            String sql = "select * from users where username = '" + username + "'";
            //ResultSet
            ResultSet p = conn.createStatement().executeQuery(sql);
            p.next();
            //check login
            return p.getString("username").equals(username) && p.getString("password").equals(password);
        } catch (SQLException e) {
            System.out.println("Error loading data: " + e.getMessage());
        }
        return false;
    }

    //student login handler
    private boolean loginS(String username, String password) {
        try {
            Connection conn = Connect_db.getConnection();
            //sql
            String sql = "select * from students where email = '" + username + "'";
            //ResultSet
            ResultSet p = conn.createStatement().executeQuery(sql);
            p.next();
            //check login
            return p.getString("email").equals(username) && p.getString("phone").equals(password);
        } catch (SQLException e) {
            System.out.println("Error loading data: " + e.getMessage());
        }
        return false;
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

//reload scene <ok>
    public void reloadAccount(ActionEvent event) throws IOException {
        aA = (Stage) Stage.getWindows().stream().filter(Window::isShowing).findFirst().orElse(null);
        aA.close();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("AccountAdmin.fxml"));
        root = loader.load();
        SceneController c = loader.getController();
        c.buildManager(username);
        c.buildAdminData();
        buildStage(stage, scene, event);
    }

    public void reloadBill(ActionEvent event) throws IOException {
        aB = (Stage) Stage.getWindows().stream().filter(Window::isShowing).findFirst().orElse(null);
        aB.close();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("Bill.fxml"));
        root = loader.load();
        SceneController c = loader.getController();
        c.buildManager(username);
        c.buildBill();
        buildStage(stage, scene, event);
    }

    public void reloadRoom(ActionEvent event) throws IOException {
        aR = (Stage) Stage.getWindows().stream().filter(Window::isShowing).findFirst().orElse(null);
        aR.close();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Room.fxml"));
        root = loader.load();
        SceneController c = loader.getController();
        c.buildManager(username);
        c.buildRoom();
        buildStage(stage, scene, event);
    }

    public void reloadStudent(ActionEvent event) throws IOException {
        aSt = (Stage) Stage.getWindows().stream().filter(Window::isShowing).findFirst().orElse(null);
        aSt.close();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("Student.fxml"));
        root = loader.load();
        SceneController c = loader.getController();
        c.buildManager(username);
        c.buildStudent(event);
        buildStage(stage, scene, event);
    }

}
