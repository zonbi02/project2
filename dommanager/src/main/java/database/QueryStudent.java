
package database;

/**
 *
 * 
 */
public class QueryStudent {
//common
    //get room id by student's email
    public static String getRoomIdByEmail = "select rooms.id from rooms join students on rooms.id = students.room_id where email = ?";
    //get student's id by email
    public static String getStudentIdByEmail = "select id from students where email = ?";
//student
    //get student information by email
    public static String getStsInfo = "select * from students join rooms on students.room_id = rooms.id where email = ?";
//dashboard
    //bill info
    public static String getBillInfo = "select * from bills where room_id = ?";
//room
    //student list
    public static String getSts = "select * from students where room_id = ?";
    //get free slot
    public static String getFreeSlot = "select * from rooms where id = ?";
}
