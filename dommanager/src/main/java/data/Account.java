/*
 */
package data;

import database.Connect_db;
import database.Query;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * 
 */
public class Account {

//fields
    //staff
    String aSUsername, aSRole, aSJoinedDate, aSFullname, aSEmail, aSPhone, aSAddress;

    //admin
    String usernameError, passwordError, nameError, emailError, phoneError;

    //accoun list
    private SimpleStringProperty username, role, name, email, phone, address, joineddate;
    private SimpleIntegerProperty id;

    public Account() {

    }
    
//constructor for account list
    public Account(String username, String role, String name, String email, String phone, String address, String joineddate, int id) {
        this.username = new SimpleStringProperty(username);
        this.role = new SimpleStringProperty(role);
        this.name = new SimpleStringProperty(name);
        this.email = new SimpleStringProperty(email);
        this.phone = new SimpleStringProperty(phone);
        this.address = new SimpleStringProperty(address);
        this.joineddate = new SimpleStringProperty(joineddate);
        this.id = new SimpleIntegerProperty(id);
    }

//getter and setter for account list
    public String getUsername() {
        return username.get();
    }

    public String getRole() {
        return role.get();
    }

    public String getName() {
        return name.get();
    }

    public String getEmail() {
        return email.get();
    }

    public String getPhone() {
        return phone.get();
    }

    public String getAddress() {
        return address.get();
    }

    public String getJoineddate() {
        return joineddate.get();
    }

    public int getId() {
        return id.get();
    }

    public void setUsername(String username) {
        this.username = new SimpleStringProperty(username);
    }

    public void setRole(String role) {
        this.role = new SimpleStringProperty(role);
    }

    public void setName(String name) {
        this.name = new SimpleStringProperty(name);
    }

    public void setEmail(String email) {
        this.email = new SimpleStringProperty(email);
    }

    public void setPhone(String phone) {
        this.phone = new SimpleStringProperty(phone);
    }

    public void setAddress(String address) {
        this.address = new SimpleStringProperty(address);
    }

    public void setJoineddate(String joineddate) {
        this.joineddate = new SimpleStringProperty(joineddate);
    }

    public void setId(int id) {
        this.id = new SimpleIntegerProperty(id);
    }

//getter
    //staff
    public String getaSUsername() {
        return aSUsername;
    }

    public String getaSRole() {
        return aSRole;
    }

    public String getaSJoinedDate() {
        return aSJoinedDate;
    }

    public String getaSFullname() {
        return aSFullname;
    }

    public String getaSEmail() {
        return aSEmail;
    }

    public String getaSPhone() {
        return aSPhone;
    }

    public String getaSAddress() {
        return aSAddress;
    }

    //admin
    public String getUsernameError() {
        return usernameError;
    }

    public String getPasswordError() {
        return passwordError;
    }

    public String getNameError() {
        return nameError;
    }

    public String getEmailError() {
        return emailError;
    }

    public String getPhoneError() {
        return phoneError;
    }

    //setter
    //staff
    public void setaSUsername(String aSUsername) {
        this.aSUsername = aSUsername;
    }

    public void setaSRole(String aSRole) {
        this.aSRole = aSRole;
    }

    public void setaSJoinedDate(String aSJoinedDate) {
        this.aSJoinedDate = aSJoinedDate;
    }

    public void setaSFullname(String aSFullname) {
        this.aSFullname = aSFullname;
    }

    public void setaSEmail(String aSEmail) {
        this.aSEmail = aSEmail;
    }

    public void setaSPhone(String aSPhone) {
        this.aSPhone = aSPhone;
    }

    public void setaSAddress(String aSAdress) {
        this.aSAddress = aSAdress;
    }

    //admin
    public void setUsernameError(String usernameError) {
        this.usernameError = usernameError;
    }

    public void setPasswordError(String passwordError) {
        this.passwordError = passwordError;
    }

    public void setNameError(String nameError) {
        this.nameError = nameError;
    }

    public void setEmailError(String emailError) {
        this.emailError = emailError;
    }

    public void setPhoneError(String phoneError) {
        this.phoneError = phoneError;
    }

//methods
    //load manager data
    public void loadData(String username) {
        //getManager
       try {
            Connection conn = Connect_db.getConnection();
            //ResultSet
            ResultSet a = conn.createStatement().executeQuery(Query.getManager(username));

            //add data
            while (a.next()) {
                //Iterate Row
                setaSRole(a.getString("role"));
                setaSUsername(a.getString("username"));
                setaSJoinedDate(a.getString("joined_date"));
                setaSFullname(a.getString("full_name"));
                setaSEmail(a.getString("email"));
                setaSPhone(a.getString("phone"));
                setaSAddress(a.getString("address"));
            }
        } catch (SQLException e) {
            System.out.println("Error loading data: " + e.getMessage());
        }
    }
    
    //get new user id
    public int getNewId() {
        int id;
        try {
            Connection conn = Connect_db.getConnection();
            ResultSet irs = conn.createStatement().executeQuery(Query.getUId);
            irs.next();
            id = irs.getInt("id") + 1;
        } catch (SQLException ex) {
            id = 1;
        }
        return id;
    }

    //add manager
    public boolean add(String un, String pw, String r, String n, String e, String p, String a) {
        Validator v = new Validator();
        int id = getNewId();
        
        int isok = 1;
        if (v.roleCheck(r) == false) {
            isok = 0;
        }
        
        if (v.emailcheck(e) == false) {
            isok = 0;
            setEmailError("Invalid Email");
        }
        
        if (v.emailAvailable(e) == false) {
            isok = 0;
            setEmailError("Email already exists");
        }
        
       if (v.phoneAvailable(p) == false) {
            isok = 0;
            setPhoneError("Phone already exists");
        }
        
        if (v.phoneCheck(p) == false) {
            isok = 0;
              setPhoneError("Invalid Phone");
        }
          
        if (isok == 1) {
            try {
                Connection conn = Connect_db.getConnection();
                
                PreparedStatement stm = conn.prepareStatement(Query.insertUser);
                stm.setString(1, un);
                stm.setString(2, pw);
                stm.execute();
                
                PreparedStatement stmt = conn.prepareStatement(Query.insertUserDetail);
                stmt.setInt(1,id);
                stmt.setString(2, n);
                stmt.setString(3, e);
                stmt.setString(4, p);
                stmt.setString(5, r);
                stmt.setString(6, a);
                stmt.execute();
                return true;
            } catch (SQLException se) {
                System.out.println("Error inserting data: " + se.getLocalizedMessage());
            }
        }
        return false;
    }

    

    //load account list
    public ObservableList<Account> loadAccountList() {
         ObservableList data = FXCollections.observableArrayList();
        try {
            Connection conn = Connect_db.getConnection();
            ResultSet srs = conn.createStatement().executeQuery(Query.getAccountList);
            while (srs.next()) {
                data.add(
                        new Account(
                                srs.getString("username"),
                                srs.getString("role"),
                                srs.getString("full_name"),
                                srs.getString("email"),
                                srs.getString("phone"),
                                srs.getString("address"),
                                srs.getString("joined_date"),
                                srs.getInt("users.id")
                        ));
            }
        } catch (SQLException e) {
            System.out.println("Error loading data: " + e.getMessage());
        }
        return data;
    }
    
    //search Account
    public ObservableList<Account> searchAccount(String name) {
        ObservableList data = FXCollections.observableArrayList();
        try {
            Connection conn = Connect_db.getConnection();
            ResultSet srs = conn.createStatement().executeQuery(Query.searchAccount(name));
            while (srs.next()) {
                data.add(
                        new Account(
                                srs.getString("username"),
                                srs.getString("role"),
                                srs.getString("full_name"),
                                srs.getString("email"),
                                srs.getString("phone"),
                                srs.getString("address"),
                                srs.getString("joined_date"),
                                srs.getInt("users.id")
                        ));
            }
        } catch (SQLException e) {
            System.out.println("Error loading data: " + e.getMessage());
        }
        return data;
    }


}
