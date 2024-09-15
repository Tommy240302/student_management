/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Quang dep trai
 */
public class Course {
    Connection con = AccountConection.getConnection();
    PreparedStatement ps;
    public int getMax(){
        int idMax = 0;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT MAX(id) AS max_id FROM course";
        
        try {
            ps = con.prepareStatement(sql);

            // Thực thi câu lệnh SQL
            rs = ps.executeQuery();

            // Lấy kết quả
            if (rs.next()) {
                idMax = rs.getInt("max_id");
                System.out.println("Giá trị lớn nhất của cột id là: " + idMax);
            }
            
        } catch (SQLException ex) {
            
            Logger.getLogger(Course.class.getName()).log(Level.SEVERE, null, ex);
        }
        return idMax +1; 
    }
    public boolean getId(int id){
        try {
            ps = con.prepareStatement("select * from student where id = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                Home.jTextField14.setText(String.valueOf(rs.getInt(1)));
                return true;
            }else{
                JOptionPane.showMessageDialog(null, "Student's id doesn't exist");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Course.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public int countSemester(int id){
        int total = 0;
        try {
            ps = con.prepareStatement("select count (*) as 'total' from course where student_id = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                total = rs.getInt(1);
            }if(total == 8){
                 JOptionPane.showMessageDialog(null, "This student has completed all course");
                 return -1;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Course.class.getName()).log(Level.SEVERE, null, ex);
        }
        return total;
    }
    
     public boolean isSemesterExist (int sid, int semesterNo){
        try {
            ps = con.prepareStatement("select * from course where student_id = ? and semester = ?");
            ps.setInt(1, sid);
            ps.setInt(2, semesterNo);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Course.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
     
     
     public boolean isCourseExist (int sid, String courseNo, String course){
         String sql = "select * from course where student_id = ? and " + courseNo + "= ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, sid);
            ps.setString(2, course);
           
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Course.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
     
     //insert data into course table
      public void insert(int id, int sid, int semester, String course1, String course2,
            String course3, String course4) {
        String sql = "INSERT INTO course (id, student_id, semester, course1, course2, course3, course4) VALUES (?,?,?,?,?,?,?)";
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.setInt(2, sid);
            ps.setInt(3, semester);
            ps.setString(4, course1);
            ps.setString(5, course2);
            ps.setString(6, course3);
            ps.setString(7, course4);

            if (ps.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(null, "Course add successfully");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Course.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
      
      // show course's data to table
      public void getCourseValue(JTable table, String searchValue) {
        String sql = "select * from course where concat(id, student_id, semester) like ? order by id desc";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, "%" + searchValue + "%");
            ResultSet rs = ps.executeQuery();
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            Object [] row;
            while(rs.next()){
                row = new Object[7];
                row[0] = rs.getInt(1);
                row[1] = rs.getString(2);
                row[2] = rs.getString(3);
                row[3] = rs.getString(4);
                row[4] = rs.getString(5);
                row[5] = rs.getString(6);
                row[6] = rs.getString(7);
                
                model.addRow(row);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Course.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
