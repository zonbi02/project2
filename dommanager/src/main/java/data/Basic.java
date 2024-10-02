/*
 */
package data;

import database.Connect_db;
import database.Query;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * dashboard data and manager data
 */
public class Basic {

    //field
    //dashboard
    int totalStudent, totalRoom;
    //manager
    String fullName, role, email, phone, created_date;
    int id;

    //getter
    //dashboard
    public int getTotalStudent() {
        return totalStudent;
    }

    public int getTotalRoom() {
        return totalRoom;
    }

    //manager
    public String getFullName() {
        return fullName;
    }

    public String getRole() {
        return role;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getCreated_date() {
        return created_date;
    }

    public int getId() {
        return id;
    }

    //setter
    //dashboard
    public void setTotalStudent(int totalStudent) {
        this.totalStudent = totalStudent;
    }

    public void setTotalRoom(int totalRoom) {
        this.totalRoom = totalRoom;
    }

    //manager
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setCreated_date(String created_date) {
        this.created_date = created_date;
    }
    public void setId(int id) {
        this.id = id;
    }

    //methods
    //load manager data
    public void loadManager(String username) {
        try {
            Connection conn = Connect_db.getConnection();
            //ResultSet
            ResultSet m = conn.createStatement().executeQuery(Query.getManager(username));

            //set data
            m.next(); 
            setFullName(m.getString("full_name"));
            setRole(m.getString("role"));
            setEmail(m.getString("email"));
            setPhone(m.getString("phone"));
            setCreated_date(m.getString("joined_date"));
            setId(m.getInt("id"));
        } catch (SQLException e) {
            System.out.println("Error loading data: " + e.getMessage());
        }
    }

    //load dashboard
    public void loadDashboard() {
        try {
            Connection conn = Connect_db.getConnection();

            //result
            ResultSet s = conn.createStatement().executeQuery(Query.getStsInfo);
            ResultSet r = conn.createStatement().executeQuery(Query.getRsInfo);

            //assign data to vars
            //student
            s.next();
            setTotalStudent(s.getInt("totalStudent"));
            //room
            while (r.next()) {
                totalRoom++;
            }
            setTotalRoom(totalRoom);
        } catch (Exception e) {
            System.out.println("Error loading data: " + e.getMessage());
        }

    }
        
}
