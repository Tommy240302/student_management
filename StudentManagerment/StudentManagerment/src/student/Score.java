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


public class Score {
    Connection con = AccountConection.getConnection();
    PreparedStatement ps;
    public int getMax(){
        int idMax = 0;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT max(id) from FROM score";
        
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
            
            Logger.getLogger(Score.class.getName()).log(Level.SEVERE, null, ex);
        }
        return idMax +1; 
    }
    
    public boolean getDetails(int sid, int semesterNo){
        try {
            ps = con.prepareStatement("select * from score where student_id = ? and semester = ?");
            ps.setInt(1, sid);
            ps.setInt(2, semesterNo);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                Home.jTextField14.setText(String.valueOf(rs.getInt(2)));
                Home.jTextField27.setText(String.valueOf(rs.getInt(3)));
                Home.jTextField18.setText(rs.getString(4));
                Home.jTextField19.setText(rs.getString(5));
                Home.jTextField20.setText(rs.getString(6));
                Home.jTextField28.setText(rs.getString(7));
                return true;
            }else{
                JOptionPane.showMessageDialog(null, "Student's id or semester doesn't exist");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Score.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    public boolean isIdExist (int id){
        try {
            ps = con.prepareStatement("select * from score where id = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Score.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public boolean isSidSemesterExist (int sid, int semesterNo){
        try {
            ps = con.prepareStatement("select * from score where student_id = ? and semester = ?");
            ps.setInt(1, sid);
            ps.setInt(2, semesterNo);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Score.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    //insert score into score table
    public void insert(int id, int sid, int semester, String course1, String course2,
            String course3, String course4, double score1, double score2, double score3,
            double score4, double average) {
        String sql = "INSERT INTO score (id, studen_id, course1, score1, course2, score2, "
                + "course3, score3, course4, score4, average) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.setInt(2, sid);
            ps.setInt(3, semester);
            ps.setString(4, course1);
            ps.setDouble(5, score1);
            ps.setString(6, course2);
            ps.setDouble(7, score2);
            ps.setString(8, course3);
            ps.setDouble(9, score3);
            ps.setString(10, course4);
            ps.setDouble(11, score4);
            ps.setDouble(12, average);

            if (ps.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(null, "Score add successfully");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Score.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // show Score's data to table
      public void getScoreValue(JTable table, String searchValue) {
        String sql = "select * from score where concat(id, student_id, semester) like ? order by id desc";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, "%" + searchValue + "%");
            ResultSet rs = ps.executeQuery();
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            Object [] row;
            while(rs.next()){
                row = new Object[12];
                row[0] = rs.getInt(1);
                row[1] = rs.getInt(2);
                row[2] = rs.getInt(3);
                row[3] = rs.getString(4);
                row[4] = rs.getDouble(5);
                row[5] = rs.getString(6);
                row[6] = rs.getDouble(7);
                row[7] = rs.getString(8);
                row[8] = rs.getDouble(9);
                row[9] = rs.getString(10);
                row[10] = rs.getDouble(11);
                row[11] = rs.getDouble(12);
             
                model.addRow(row);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Score.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
