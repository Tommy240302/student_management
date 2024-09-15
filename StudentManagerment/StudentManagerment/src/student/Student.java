/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package student;

import java.sql.Connection;
import student.AccountConection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;



public class Student {
    Connection con = AccountConection.getConnection();
    PreparedStatement ps;
    public int getMax(){
        int idMax = 0;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT MAX(id) AS max_id FROM student";
        
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
            
            Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
        }
        return idMax +1; 
    }

    public void insert(int id, String name, String date, String gender, String email,
            String phonenumber, String address) {
        String sql = "INSERT INTO student (id, name, date_of_birth, gender, email, phone, address) VALUES (?,?,?,?,?,?,?)";
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.setString(2, name);
            ps.setString(3, date);
            ps.setString(4, gender);
            ps.setString(5, email);
            ps.setString(6, phonenumber);
            ps.setString(7, address);

            if (ps.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(null, "add new student successfully");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    // check email's student is already exists
    public boolean isEmailExist (String email){
        try {
            ps = con.prepareStatement("select * from student where email = ?");
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
     // check phone's student is already exists
     public boolean isPhoneExist (String phone){
        try {
            ps = con.prepareStatement("select * from student where phone = ?");
            ps.setString(1, phone);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
     //check student's id
          public boolean isIdExist (int id){
        try {
            ps = con.prepareStatement("select * from student where id = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
     //get all student's values from database table
     public void getStudentValue(JTable table, String searchValue) {
        String sql = "select * from student where concat(id, name, email, phone) like ? order by id desc";
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
            Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     //update student
     public void updateStudent(int id, String name, String date, String gender, String email,
            String phonenumber, String address){
         String sql = "update student set name=?,date_of_birth=?,gender=?,email=?,phone=?,address=? where id=?";
        try {
            ps =con.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, date);
            ps.setString(3, gender);
            ps.setString(4, email);
            ps.setString(5, phonenumber);
            ps.setString(6, address);
            ps.setInt(7, id);
            if(ps.executeUpdate()>0){
                JOptionPane.showMessageDialog(null, "Student data update succesfully");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
        }
         
     }
     
     // delete student's data
     public void delete(int id){
        int yesOrNo = JOptionPane.showConfirmDialog(null, "Course and score records will also be deleted",
                "Student Delete", JOptionPane.OK_CANCEL_OPTION, 0);
        if(yesOrNo == JOptionPane.OK_OPTION){
            try {
                ps = con.prepareStatement("delete from student where id = ?");
                ps.setInt(1, id);
                if(ps.executeUpdate() > 0){
                    JOptionPane.showMessageDialog(null, "Delete student succesfully");
                }
            } catch (SQLException ex) {
                Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
     }
}
